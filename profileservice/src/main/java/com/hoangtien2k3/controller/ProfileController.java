package com.hoangtien2k3.controller;

import com.google.gson.Gson;
import com.hoangtien2k3.common.ValidateException;
import com.hoangtien2k3.model.ProfileDTO;
import com.hoangtien2k3.service.ProfileService;
import com.hoangtien2k3.utils.CommonFunction;
import com.hoangtien2k3.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {
    @Autowired
    ProfileService profileService;

    Gson gson = new Gson(); // convert Json -> DTO

    @GetMapping
    public ResponseEntity<Flux<ProfileDTO>> getAllProfile(){
        return ResponseEntity.ok(profileService.getAllProfile());
    }

    @GetMapping(value = "/checkDuplicate/{email}")
    public ResponseEntity<Mono<Boolean>> checkDuplicate(@PathVariable String email){
        return ResponseEntity.ok(profileService.checkDuplicate(email));
    }

    @PostMapping
    public ResponseEntity<Mono<ProfileDTO>> createNewProfile(@RequestBody ProfileDTO profileDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(profileService.createNewProfile(profileDTO));
    }

//    @PostMapping
//    public ResponseEntity<Mono<ProfileDTO>> createNewProfile(@RequestBody String requestStr){
//        InputStream inputStream = ProfileController.class.getClassLoader().getResourceAsStream(Constant.JSON_REQ_CREATE_PROFILE);
//        CommonFunction.jsonValidate(inputStream,requestStr);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(profileService.createNewProfile(gson.fromJson(requestStr,ProfileDTO.class)));
//    }

    @PostMapping("/demo")
    public ResponseEntity<Mono<ProfileDTO>> createNewProfile(@RequestBody String requestStr) {
        if (requestStr == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        InputStream inputStream = ProfileController.class.getClassLoader().getResourceAsStream(Constant.JSON_REQ_CREATE_PROFILE);
        if (inputStream == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        try {
            CommonFunction.jsonValidate(inputStream, requestStr);
        } catch (ValidateException e) {
            return ResponseEntity.status(e.getStatus()).body(Mono.error(e));
        }

        ProfileDTO profileDTO = gson.fromJson(requestStr, ProfileDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(profileService.createNewProfile(profileDTO));
    }


}
