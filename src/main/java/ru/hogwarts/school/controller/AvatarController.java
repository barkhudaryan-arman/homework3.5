package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;

@RestController
@RequestMapping("/api/avatars")
public class AvatarController {

    private final AvatarService avatarService;

    @Autowired
    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadAvatar(@RequestParam("file") MultipartFile file, @RequestParam("studentId") Long studentId) {
        try {
            Student student = new Student();
            student.setId(studentId);
            avatarService.saveAvatar(file, student);
            return ResponseEntity.ok("Avatar uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload avatar");
        }
    }

    @GetMapping("/{id}/db")
    public ResponseEntity<byte[]> getAvatarFromDb(@PathVariable Long id) {
        Avatar avatar = avatarService.getAvatarFromDb(id);
        if (avatar != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", avatar.getMediaType());
            return new ResponseEntity<>(avatar.getData(), headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{fileName}/directory")
    public ResponseEntity<byte[]> getAvatarFromDirectory(@PathVariable String fileName) {
        try {
            byte[] imageData = avatarService.getAvatarFromDirectory(fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "image/jpeg");  // Здесь вы можете динамически задавать тип медиа
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
