package com.banzo.donation.registry.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  void saveFile(MultipartFile file, String id, String path);

  byte[] readFile(String id, String path);

  void deleteFile(String id, String path);
}
