package com.EduTech.controller;

import com.EduTech.service.BackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

/** 
 * @author Franco Carrasco
 * @version 1.0
 */
@Tag(name = "Backups", description = "Operaciones CRUD de backups de la base de datos")
@RestController
@RequestMapping("/api/backups")
public class BackupController {

    @Autowired
    private BackupService backupService;

    @Operation(
        summary = "Crear un nuevo backup",
        description = "Crea un nuevo backup de la base de datos y devuelve el nombre del archivo.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Backup creado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error al crear el backup")
        }
    )
    @PostMapping
    public ResponseEntity<String> createBackup() {
        try {
            String backupFileName = backupService.createBackup();
            return ResponseEntity.ok("Backup created successfully: " + backupFileName);
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.internalServerError().body("Failed to create backup: " + e.getMessage());
        }
    }

    @Operation(
        summary = "Restaurar backup",
        description = "Restaura la base de datos desde un archivo de backup.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Base de datos restaurada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error al restaurar el backup")
        }
    )
    @PostMapping("/restore")
    public ResponseEntity<String> restoreBackup(
        @Parameter(description = "Nombre del archivo de backup", required = true)
        @RequestParam String backupFileName) {
        try {
            backupService.restoreBackup(backupFileName);
            return ResponseEntity.ok("Database restored successfully from backup: " + backupFileName);
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.internalServerError().body("Failed to restore backup: " + e.getMessage());
        }
    }

    @Operation(
        summary = "Listar backups disponibles",
        description = "Devuelve una lista de los archivos de backup disponibles.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de backups obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al obtener la lista de backups")
        }
    )
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