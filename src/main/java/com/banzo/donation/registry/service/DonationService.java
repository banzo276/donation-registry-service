package com.banzo.donation.registry.service;

import com.banzo.donation.registry.model.Donation;
import com.banzo.donation.registry.model.DonationDetails;

import java.util.List;

public interface DonationService {

  List<DonationDetails> findAll();

  DonationDetails findById(String id);

  DonationDetails save(Donation donation);

  DonationDetails update(String id, Donation donation);

  void deleteById(String id);
}
