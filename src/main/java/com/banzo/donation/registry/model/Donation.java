package com.banzo.donation.registry.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class Donation {

  String id;
  LocalDateTime createdDate;
  LocalDateTime donationDate;
  String donorName;
  String donationItem;
  String description;
  String unit;
  BigDecimal quantity;
  BigDecimal unitValue;
  BigDecimal totalValue;
  MultipartFile file;
}
