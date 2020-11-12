package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface BackupService {

    void backup();
    List<String> getAvailableBackups();
    void restore(String backupName);
}
