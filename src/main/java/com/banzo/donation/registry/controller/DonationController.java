package com.banzo.donation.registry.controller;

import com.banzo.donation.registry.model.Donation;
import com.banzo.donation.registry.model.DonationDetails;
import com.banzo.donation.registry.model.DonationPayload;
import com.banzo.donation.registry.service.DonationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
@Validated
public class DonationController {

  private final DonationService donationService;

  @GetMapping
  @ApiOperation("Get all donations")
  public List<DonationDetails> getAllDonations() {
    return donationService.findAll();
  }

  @GetMapping("/{id}")
  @ApiOperation("Get donation by id")
  public DonationDetails getDonationById(@NotBlank @PathVariable("id") String id) {
    return donationService.findById(id);
  }

  @PostMapping(
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation("Create new donation")
  public DonationDetails createDonation(
      @Valid @NotNull @RequestPart(name = "donation") DonationPayload donationPayload,
      @RequestPart(name = "file", required = false) MultipartFile file) {
    return donationService.save(
        Donation.builder()
            .donationDate(donationPayload.getDonationDate())
            .donationItem(donationPayload.getDonationItem())
            .donorName(donationPayload.getDonorName())
            .description(donationPayload.getDescription())
            .unit(donationPayload.getUnit())
            .quantity(donationPayload.getQuantity())
            .unitValue(donationPayload.getUnitValue())
            .file(file)
            .build());
  }

  @PutMapping(
      path = "/{id}",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation("Update donation")
  public DonationDetails updateDonation(
      @NotBlank @PathVariable("id") String id,
      @Valid @NotNull @RequestPart(name = "donation") DonationPayload donationPayload,
      @RequestPart(name = "file", required = false) MultipartFile file) {
    return donationService.update(
        id,
        Donation.builder()
            .donationDate(donationPayload.getDonationDate())
            .donationItem(donationPayload.getDonationItem())
            .donorName(donationPayload.getDonorName())
            .description(donationPayload.getDescription())
            .unit(donationPayload.getUnit())
            .quantity(donationPayload.getQuantity())
            .unitValue(donationPayload.getUnitValue())
            .file(file)
            .build());
  }

  @DeleteMapping("/{id}")
  @ApiOperation("Delete donation")
  public void deleteDonation(@NotBlank @PathVariable("id") String id) {
    donationService.deleteById(id);
  }
}
