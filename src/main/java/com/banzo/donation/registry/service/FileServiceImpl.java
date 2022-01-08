package com.banzo.donation.registry.service;

import com.banzo.donation.registry.exception.FileTransferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

  @Override
  public void saveFile(MultipartFile file, String id, String path) {

    log.info("Saving file: {}", file.getOriginalFilename());
    Path filePath = Paths.get(path).toAbsolutePath().normalize();

    try (InputStream fileInputStream = file.getInputStream()) {
      Files.createDirectories(filePath);
      Files.copy(fileInputStream, filePath.resolve(id), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException exception) {
      throw new FileTransferException(
          "Error occurred during saving file, message=" + exception.getMessage());
    }
  }

  @Override
  public byte[] readFile(String id, String path) {

    Path filePath = Paths.get(path).toAbsolutePath().normalize().resolve(id);

    try {
      return Files.readAllBytes(filePath);
    } catch (IOException exception) {
      throw new FileTransferException(
          "Error occurred during reading file, message=" + exception.getMessage());
    }
  }

  @Override
  public void deleteFile(String id, String path) {

    Path filePath = Paths.get(path).toAbsolutePath().normalize().resolve(id);

    try {
      Files.deleteIfExists(filePath);
    } catch (IOException exception) {
      throw new FileTransferException(
          "Error occurred during deleting file, message=" + exception.getMessage());
    }
  }
}
