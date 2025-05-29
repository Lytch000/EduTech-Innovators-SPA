package com.EduTech.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/** 
 * @author Franco Carrasco
 * @version 1.0
 */
@Service
public class BackupService {

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    private static final String BACKUP_DIR = "backups";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    public String createBackup() throws IOException, InterruptedException {
        // Create backups directory if it doesn't exist
        File backupDir = new File(BACKUP_DIR);
        if (!backupDir.exists()) {
            backupDir.mkdir();
        }

        // Generate backup filename with timestamp
        String timestamp = DATE_FORMAT.format(new Date());
        String backupFileName = String.format("backup_%s.sql", timestamp);
        String backupPath = Paths.get(BACKUP_DIR, backupFileName).toString();

        // Extract database name from URL
        String dbName = dbUrl.substring(dbUrl.lastIndexOf("/") + 1);
        if (dbName.contains("?")) {
            dbName = dbName.substring(0, dbName.indexOf("?"));
        }

        // Create mysqldump command
        ProcessBuilder processBuilder = new ProcessBuilder(
                "mysqldump",
                "-u", dbUsername,
                "-p" + dbPassword,
                dbName,
                "--result-file=" + backupPath
        );

        // Execute backup command
        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new IOException("Database backup failed with exit code: " + exitCode);
        }

        return backupFileName;
    }

    public void restoreBackup(String backupFileName) throws IOException, InterruptedException {
        String backupPath = Paths.get(BACKUP_DIR, backupFileName).toString();
        if (!new File(backupPath).exists()) {
            throw new IOException("Backup file not found: " + backupFileName);
        }

        // Extract database name from URL
        String dbName = dbUrl.substring(dbUrl.lastIndexOf("/") + 1);
        if (dbName.contains("?")) {
            dbName = dbName.substring(0, dbName.indexOf("?"));
        }

        // Create mysql restore command
        ProcessBuilder processBuilder = new ProcessBuilder(
                "mysql",
                "-u", dbUsername,
                "-p" + dbPassword,
                dbName
        );

        // Execute restore command
        Process process = processBuilder.start();
        process.getOutputStream().write(Files.readAllBytes(Paths.get(backupPath)));
        process.getOutputStream().close();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("Database restore failed with exit code: " + exitCode);
        }
    }

    public List<String> listBackups() throws IOException {
        File backupDir = new File(BACKUP_DIR);
        if (!backupDir.exists()) {
            return new ArrayList<>();
        }

        return Files.list(backupDir.toPath())
                .map(Path::getFileName)
                .map(Path::toString)
                .filter(name -> name.endsWith(".sql"))
                .collect(Collectors.toList());
    }
} 