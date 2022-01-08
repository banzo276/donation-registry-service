package com.banzo.donation.registry.service;

import com.banzo.donation.registry.model.Donation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DonationValueCalculator {

  public static BigDecimal calculate(Donation donation) {
    if (Objects.isNull(donation.getQuantity()) || Objects.isNull(donation.getUnitValue())) {
      return BigDecimal.ZERO;
    }

    return donation.getQuantity().multiply(donation.getUnitValue());
  }
}
