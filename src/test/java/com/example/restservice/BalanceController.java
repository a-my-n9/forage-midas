package com.example.restservice;

import com.jpmc.midascore.component.DatabaseConduit;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    private final UserRepository userRepository;
    @Autowired
    public BalanceController (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/balance")
    public Balance balance(@RequestParam("userId") long id) {
        try {
            UserRecord user = userRepository.findById(id);
            return new Balance(user.getBalance());
        } catch (NullPointerException e) {
            UserRecord user = null;
            return  new Balance (0.0f);
        }
    }
}