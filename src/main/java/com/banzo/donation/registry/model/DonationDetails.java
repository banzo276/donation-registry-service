package com.banzo.donation.registry.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class DonationDetails {

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
  DonationFileDetails file;
}
