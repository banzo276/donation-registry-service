package com.banzo.donation.registry.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DonationFileDetails {

  String fileName;
  long size;
  String contentType;
  byte[] content;
}
