package com.vn.investion;

import com.vn.investion.dto.auth.UserResponse;
import com.vn.investion.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @GetMapping
    public UserResponse sayHello(Authentication authentication) {
         var res = new UserResponse();
         res.setFirstname("nguyen");
         return res;
    }

    private String getName(Authentication authentication) {
        return Optional.of(authentication)
                .filter(User.class::isInstance)
                .map(User.class::cast)
                .map(User::getPhone)
                .orElseGet(authentication::getName);
    }
}
