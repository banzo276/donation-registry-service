package com.banzo.donation.registry.service;

import com.banzo.donation.registry.exception.RecordNotFoundException;
import com.banzo.donation.registry.model.*;
import com.banzo.donation.registry.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

  private final DonationRepository donationRepository;
  private final Clock clock;
  private final FileService fileService;

  private static final String DONATION_NOT_FOUND_MESSAGE = "Not found donation with id=";

  @Value("${file.upload-dir}")
  private String fileDirectory;

  @Override
  public List<DonationDetails> findAll() {
    return donationRepository.findAll().stream()
        .map(this::mapToDonationDetails)
        .collect(Collectors.toList());
  }

  @Override
  public DonationDetails findById(String id) {
    return donationRepository
        .findById(id)
        .map(this::mapToDonationDetails)
        .orElseThrow(() -> new RecordNotFoundException(DONATION_NOT_FOUND_MESSAGE + id));
  }

  @Override
  public DonationDetails save(Donation donation) {

    DonationEntity donationEntity =
        donationRepository.save(
            DonationEntity.builder()
                .createdDate(LocalDateTime.now(clock))
                .donationDate(donation.getDonationDate())
                .donationItem(donation.getDonationItem())
                .donorName(donation.getDonorName())
                .description(donation.getDescription())
                .unit(donation.getUnit())
                .quantity(donation.getQuantity())
                .unitValue(donation.getUnitValue())
                .totalValue(DonationValueCalculator.calculate(donation))
                .file(
                    Objects.nonNull(donation.getFile())
                        ? DonationFileEntity.builder()
                            .fileName(donation.getFile().getOriginalFilename())
                            .contentType(donation.getFile().getContentType())
                            .size(donation.getFile().getSize())
                            .build()
                        : null)
                .build());

    saveFile(donation.getFile(), donationEntity.getId());

    return mapToDonationDetails(donationEntity);
  }

  @Override
  public DonationDetails update(String id, Donation donation) {

    DonationEntity donationEntity =
        donationRepository
            .findById(id)
            .orElseThrow(() -> new RecordNotFoundException(DONATION_NOT_FOUND_MESSAGE + id));

    DonationEntity updatedDonation =
        donationRepository.save(
            DonationEntity.builder()
                .id(donationEntity.getId())
                .createdDate(donationEntity.getCreatedDate())
                .donationDate(donation.getDonationDate())
                .donationItem(donation.getDonationItem())
                .donorName(donation.getDonorName())
                .description(donation.getDescription())
                .unit(donation.getUnit())
                .quantity(donation.getQuantity())
                .unitValue(donation.getUnitValue())
                .totalValue(DonationValueCalculator.calculate(donation))
                .file(
                    Objects.nonNull(donation.getFile())
                        ? DonationFileEntity.builder()
                            .fileName(donation.getFile().getOriginalFilename())
                            .contentType(donation.getFile().getContentType())
                            .size(donation.getFile().getSize())
                            .build()
                        : null)
                .build());

    fileService.deleteFile(id, fileDirectory);
    saveFile(donation.getFile(), id);

    return mapToDonationDetails(updatedDonation);
  }

  @Override
  public void deleteById(String id) {

    DonationEntity donationEntity =
        donationRepository
            .findById(id)
            .orElseThrow(() -> new RecordNotFoundException(DONATION_NOT_FOUND_MESSAGE + id));

    donationRepository.delete(donationEntity);
    fileService.deleteFile(id, fileDirectory);
  }

  private DonationDetails mapToDonationDetails(DonationEntity donationEntity) {
    return DonationDetails.builder()
        .id(donationEntity.getId())
        .createdDate(donationEntity.getCreatedDate())
        .donationDate(donationEntity.getDonationDate())
        .donationItem(donationEntity.getDonationItem())
        .donorName(donationEntity.getDonorName())
        .description(donationEntity.getDescription())
        .unit(donationEntity.getUnit())
        .quantity(donationEntity.getQuantity())
        .unitValue(donationEntity.getUnitValue())
        .totalValue(donationEntity.getTotalValue())
        .file(
            Objects.nonNull(donationEntity.getFile())
                ? DonationFileDetails.builder()
                    .fileName(donationEntity.getFile().getFileName())
                    .size(donationEntity.getFile().getSize())
                    .content(fileService.readFile(donationEntity.getId(), fileDirectory))
                    .contentType(donationEntity.getFile().getContentType())
                    .build()
                : null)
        .build();
  }

  private void saveFile(MultipartFile file, String id) {
    if (Objects.isNull(file) || file.isEmpty()) {
      return;
    }

    fileService.saveFile(file, id, fileDirectory);
  }
}
