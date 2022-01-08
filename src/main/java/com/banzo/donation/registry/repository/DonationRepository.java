package com.banzo.donation.registry.repository;

import com.banzo.donation.registry.model.DonationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DonationRepository extends MongoRepository<DonationEntity, String> {}
