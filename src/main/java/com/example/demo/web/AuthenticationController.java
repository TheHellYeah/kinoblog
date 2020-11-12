package com.example.demo.web;

import com.example.demo.security.AuthenticationResponse;
import com.example.demo.model.User;
import com.example.demo.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        if(authenticationService.canLogin(user)) {
            AuthenticationResponse response = authenticationService.login(user);
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.badRequest().body("Не удалось войти в систему. Проверьте логин и пароль и повторите снова.");
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user) {
        if(authenticationService.canRegister(user)) {
            authenticationService.register(user);
            AuthenticationResponse response = authenticationService.register(user);
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.badRequest().body("Пользователь с данным именем или email уже существует.");
    }
}
