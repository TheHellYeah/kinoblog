package com.example.demo.web;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.BackupService;
import com.example.demo.service.UserService;
import com.example.demo.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private BackupService backupService;
    @Autowired
    private UserService userService;

    @GetMapping
    @JsonView(Views.UserPage.class)
    public List<User> adminPage() {
        backupService.backup();
        return userService.getAll();
    }

    @GetMapping("/restore")
    public List<String> restorePage() {
        return backupService.getAvailableBackups();
    }

    @PostMapping("/restore")
    public void restore(@RequestBody String backup) {
        backupService.restore(backup);
    }

    @PutMapping("/appoint-mod")
    public void appointToModerator(@RequestBody long userId) {
        userService.appointUser(userId, Role.MODERATOR);
    }

    @PutMapping("/appoint-admin")
    public void appointToAdmin(@RequestBody long userId) {
        userService.appointUser(userId, Role.ADMIN);
    }

    @PutMapping("/dismiss")
    public void dismiss(@RequestBody long userId) {
        userService.dismiss(userId);
    }
}
