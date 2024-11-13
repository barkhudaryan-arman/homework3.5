package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final Path root = Paths.get("uploads");

    @Autowired
    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public Avatar saveAvatar(MultipartFile file, Student student) throws IOException {
        Files.createDirectories(root);

        String filePath = root.resolve(file.getOriginalFilename()).toString();
        Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));

        Avatar avatar = new Avatar();
        avatar.setFilePath(filePath);
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatar.setStudent(student);

        return avatarRepository.save(avatar);
    }

    public Avatar getAvatarFromDb(Long id) {
        return avatarRepository.findById(id).orElse(null);
    }

    public byte[] getAvatarFromDirectory(String fileName) throws IOException {
        Path file = root.resolve(fileName);
        return Files.readAllBytes(file);
    }
}
