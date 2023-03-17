package com.homecare.GoldenFutureHomeCare.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.homecare.GoldenFutureHomeCare.shared.dto.ConsumerDto;

// UserDetailsService is added here only  after creating WebSecurity class with inbuilt UserDetailsService.
//DO NOT FORGET TO ADD UNIMPLEMENTED METHOD IN SERVICEIMPL AFTER THIS.
public interface ConsumerService extends UserDetailsService {

	ConsumerDto createConsumer(ConsumerDto consumer);

	ConsumerDto getConsumer(String email);

	ConsumerDto getConsumerByConsumerId(String consumerId);

	ConsumerDto updateConsumer(String consumerId, ConsumerDto consumer);

	void deleteConsumer(String consumerId);

	List<ConsumerDto> getConsumerList(int page, int limit);

	boolean verifyEmailToken(String token);

	boolean requestPasswordReset(String email);

	boolean resetPassword(String token, String password);

}
