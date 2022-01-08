package com.banzo.donation.registry.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class DonationPayload {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  LocalDateTime donationDate;

  String donorName;
  @NotBlank String donationItem;
  String description;
  @NotBlank String unit;
  @Positive BigDecimal quantity;
  @Positive BigDecimal unitValue;
}
