package com.example.demo.web;

import com.example.demo.service.BackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
//
//    @Autowired
//    private BackupService backupService;

    @GetMapping("/restore")
    public List<String> restorePage() {
  //      return backupService.getAvailableBackups();
        return Arrays.asList("done");
    }
//
//    @PostMapping("/restore")
//    public void restore(@RequestBody String backupName) {
//        backupService.restore(backupName);
//    }
}
