package com.hoangtien2k3.event;

import com.google.gson.Gson;
import com.hoangtien2k3.model.AccountDTO;
import com.hoangtien2k3.model.ProfileDTO;
import com.hoangtien2k3.service.AccountService;
import com.hoangtien2k3.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collections;

@Service
@Slf4j
public class EventConsumer {
    Gson gson = new Gson(); // convert Json -> DTO
    @Autowired
    AccountService accountService;

    @Autowired
    EventProducer eventProducer;

    public EventConsumer(ReceiverOptions<String,String> receiverOptions){
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(Constants.PROFILE_ONBOARDING_TOPIC)))
                .receive()
                .subscribe(this::profileOnboarding);
    }

    public void profileOnboarding(ReceiverRecord<String,String> receiverRecord){
        log.info("Profile Onboarding event");
        ProfileDTO dto = gson.fromJson(receiverRecord.value(),ProfileDTO.class);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(String.valueOf(dto.getId()));
        accountDTO.setEmail(dto.getEmail());
        accountDTO.setReserved(0);
        accountDTO.setBalance(dto.getInitialBalance());
        accountDTO.setCurrency("USD");
        accountService.createNewAccount(accountDTO).subscribe(res ->{
            dto.setStatus(Constants.STATUS_PROFILE_ACTIVE);
            eventProducer.send(Constants.PROFILE_ONBOARDED_TOPIC,gson.toJson(dto)).subscribe();
        });
    }
}