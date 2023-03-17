package com.homecare.GoldenFutureHomeCare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.homecare.GoldenFutureHomeCare.entity.ConsumerEntity;
@Repository
public interface ConsumerRepository extends JpaRepository<ConsumerEntity, Long> { 
	ConsumerEntity findByEmail(String email);
	
	ConsumerEntity findByConsumerId(String consumerId);
	ConsumerEntity findConsumerByEmailVerificationToken(String token); 
	
}
