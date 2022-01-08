package com.banzo.donation.registry.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DonationFileEntity {

  String fileName;
  long size;
  String contentType;
}
