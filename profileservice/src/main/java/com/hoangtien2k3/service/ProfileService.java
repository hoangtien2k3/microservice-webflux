package com.hoangtien2k3.service;

import com.google.gson.Gson;
import com.hoangtien2k3.common.CommonException;
import com.hoangtien2k3.event.EventProducer;
import com.hoangtien2k3.model.ProfileDTO;
import com.hoangtien2k3.repository.ProfileRepository;
import com.hoangtien2k3.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class ProfileService {
    Gson gson = new Gson();

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    EventProducer eventProducer;

    public Flux<ProfileDTO> getAllProfile() {
        return profileRepository.findAll()
                .map(ProfileDTO::entityToDto)
                .switchIfEmpty(Mono.error(new CommonException("PF02","Profile isEmpty!", HttpStatus.BAD_REQUEST)));
    }

    public Mono<Boolean> checkDuplicate(String email) {
        return profileRepository.findByEmail(email)
                .flatMap(profile -> Mono.just(true))
                .switchIfEmpty(Mono.just(false));
    }

    public Mono<ProfileDTO> createNewProfile(ProfileDTO profileDTO) {
        return checkDuplicate(profileDTO.getEmail())
                .flatMap(aBoolean -> {
                    if (Boolean.TRUE.equals(aBoolean)) {
                        return Mono.error(new CommonException("PF02","Duplicate profile !", HttpStatus.BAD_REQUEST));
                    } else {
                        profileDTO.setStatus(Constants.STATUS_PROFILE_PENDING);
                        return createProfile(profileDTO);
                    }
                });
    }

    public Mono<ProfileDTO> createProfile(ProfileDTO profileDTO) {
        return Mono.just(profileDTO)
                .map(ProfileDTO::dtoToEntity)
                .flatMap(profile -> profileRepository.save(profile))
                .map(ProfileDTO::entityToDto)
                .doOnSuccess(dto -> {
                    if(Objects.equals(dto.getStatus(), Constants.STATUS_PROFILE_PENDING)){
                        dto.setInitialBalance(profileDTO.getInitialBalance());
                        eventProducer.send(Constants.PROFILE_ONBOARDING_TOPIC,gson.toJson(dto)).subscribe();
                    }
                })
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    public Mono<ProfileDTO> updateStatusProfile(ProfileDTO profileDTO){
        return getDetailProfileByEmail(profileDTO.getEmail())
                .map(ProfileDTO::dtoToEntity)
                .flatMap(profile -> {
                    profile.setStatus(profileDTO.getStatus());
                    return profileRepository.save(profile);
                })
                .map(ProfileDTO::entityToDto)
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    public Mono<ProfileDTO> getDetailProfileByEmail(String email){
        return profileRepository.findByEmail(email)
                .map(ProfileDTO::entityToDto)
                .switchIfEmpty(Mono.error(new CommonException("PF03", "Profile not found", HttpStatus.NOT_FOUND)));
    }

}
