package com.banzo.donation.registry.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonationEntity {

  @Id private String id;
  private LocalDateTime createdDate;
  private LocalDateTime donationDate;
  private String donorName;
  private String donationItem;
  private String description;
  private String unit;
  private BigDecimal quantity;
  private BigDecimal unitValue;
  private BigDecimal totalValue;
  private DonationFileEntity file;
}
