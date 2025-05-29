package com.EduTech.controller;

import com.EduTech.service.BackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/backups")
public class BackupController {

    @Autowired
    private BackupService backupService;

    @PostMapping
    public ResponseEntity<String> createBackup() {
        try {
            String backupFileName = backupService.createBackup();
            return ResponseEntity.ok("Backup created successfully: " + backupFileName);
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.internalServerError().body("Failed to create backup: " + e.getMessage());
        }
    }

    @PostMapping("/restore")
    public ResponseEntity<String> restoreBackup(@RequestParam String backupFileName) {
        try {
            backupService.restoreBackup(backupFileName);
            return ResponseEntity.ok("Database restored successfully from backup: " + backupFileName);
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.internalServerError().body("Failed to restore backup: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<String>> listBackups() {
        try {
            List<String> backups = backupService.listBackups();
            return ResponseEntity.ok(backups);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
} 