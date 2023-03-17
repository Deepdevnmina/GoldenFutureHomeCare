package com.homecare.GoldenFutureHomeCare.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.homecare.GoldenFutureHomeCare.entity.ConsumerEntity;
import com.homecare.GoldenFutureHomeCare.entity.PasswordResetTokenEntity;
import com.homecare.GoldenFutureHomeCare.exceptions.ConsumerServiceException;
import com.homecare.GoldenFutureHomeCare.repository.ConsumerRepository;
import com.homecare.GoldenFutureHomeCare.repository.PasswordResetTokenRepository;

import com.homecare.GoldenFutureHomeCare.response.ErrorMessages;
import com.homecare.GoldenFutureHomeCare.service.ConsumerService;
import com.homecare.GoldenFutureHomeCare.shared.AmazonSES;
import com.homecare.GoldenFutureHomeCare.shared.ConsumerUtils;
import com.homecare.GoldenFutureHomeCare.shared.dto.AddressDTO;
import com.homecare.GoldenFutureHomeCare.shared.dto.ConsumerDto;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsumerServiceImpl implements ConsumerService {

	@Autowired
	ConsumerRepository consumerRepository;

	@Autowired
	ConsumerUtils consumerUtils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Autowired
   AmazonSES amazonSES;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		ConsumerEntity consumerEntity = consumerRepository.findByEmail(email);

		if (consumerEntity == null)
			throw new UsernameNotFoundException(email);

		return new User(consumerEntity.getEmail(), consumerEntity.getEncryptedPassword(),
				consumerEntity.getEmailVerificationStatus(), true, true, true, new ArrayList<>());

		// return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new
		// ArrayList<>());

	}

	@Override
	public ConsumerDto createConsumer(ConsumerDto consumer) {
		if (consumerRepository.findByEmail(consumer.getEmail()) != null)
			throw new ConsumerServiceException("Record already exists");

		for (int i = 0; i < consumer.getAddresses().size(); i++) {
			AddressDTO address = consumer.getAddresses().get(i);
			address.setConsumerDetailsVO(consumer);
			address.setAddressId(consumerUtils.generateAddressId(30));
			consumer.getAddresses().set(i, address);
		}

		// BeanUtils.copyProperties(user, userEntity);
		ModelMapper modelMapper = new ModelMapper();
		ConsumerEntity consumerEntity = modelMapper.map(consumer, ConsumerEntity.class);

		String publicConsumerId = consumerUtils.generateConsumerId(30);
		consumerEntity.setConsumerId(publicConsumerId);
		consumerEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(consumer.getPassword()));
		consumerEntity.setEmailVerificationToken(consumerUtils.generateEmailVerificationToken(publicConsumerId));

		ConsumerEntity storedUserDetails = consumerRepository.save(consumerEntity);

		// BeanUtils.copyProperties(storedUserDetails, returnValue);
		ConsumerDto returnValue = modelMapper.map(storedUserDetails, ConsumerDto.class);

		// Send an email message to user to verify their email address
		 amazonSES.verifyEmail(returnValue);

		return returnValue;
	}

	@Override
	public ConsumerDto getConsumer(String email) {
		ConsumerEntity consumerEntity = consumerRepository.findByEmail(email);

		if (consumerEntity == null)
			throw new UsernameNotFoundException(email);

		ConsumerDto returnValue = new ConsumerDto();
		BeanUtils.copyProperties(consumerEntity, returnValue);

		return returnValue;

	}

	@Override
	public ConsumerDto getConsumerByConsumerId(String consumerId) {
		ConsumerDto returnValue = new ConsumerDto();
		ConsumerEntity consumerEntity = consumerRepository.findByConsumerId(consumerId);

		if (consumerEntity == null)
			throw new UsernameNotFoundException("Consumer with ID: " + consumerId + " not found");

		BeanUtils.copyProperties(consumerEntity, returnValue);

		return returnValue;
	}

	@Override
	public ConsumerDto updateConsumer(String consumerId, ConsumerDto consumer) {
		ConsumerDto returnValue = new ConsumerDto();

		ConsumerEntity consumerEntity = consumerRepository.findByConsumerId(consumerId);

		if (consumerEntity == null)
			throw new ConsumerServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		consumerEntity.setFirstName(consumer.getFirstName());
		consumerEntity.setLastName(consumer.getLastName());

		ConsumerEntity updatedConsumerDetailsVO = consumerRepository.save(consumerEntity);
		returnValue = new ModelMapper().map(updatedConsumerDetailsVO, ConsumerDto.class);

		return returnValue;
	}

	@Transactional
	@Override
	public void deleteConsumer(String consumerId) {
		ConsumerEntity consumerEntity = consumerRepository.findByConsumerId(consumerId);

		if (consumerEntity == null)
			throw new ConsumerServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		consumerRepository.delete(consumerEntity);

	}

	@Override
	public List<ConsumerDto> getConsumerList(int page, int limit) {
		List<ConsumerDto> returnValue = new ArrayList<>();

		if (page > 0)
			page = page - 1;

		Pageable pageableRequest = PageRequest.of(page, limit);

		Page<ConsumerEntity> consumersPage = consumerRepository.findAll(pageableRequest);
		List<ConsumerEntity> consumers = consumersPage.getContent();

		for (ConsumerEntity consumerEntity : consumers) {
			ConsumerDto consumerDto = new ConsumerDto();
			BeanUtils.copyProperties(consumerEntity, consumerDto);
			returnValue.add(consumerDto);
		}

		return returnValue;

	}

	@Override
	public boolean verifyEmailToken(String token) {
		boolean returnValue = false;

		// Find user by token
		ConsumerEntity consumerEntity = consumerRepository.findConsumerByEmailVerificationToken(token);

		if (consumerEntity != null) {
			boolean hastokenExpired = ConsumerUtils.hasTokenExpired(token);
			if (!hastokenExpired) {
				consumerEntity.setEmailVerificationToken(null);
				consumerEntity.setEmailVerificationStatus(Boolean.TRUE);
				consumerRepository.save(consumerEntity);
				returnValue = true;
			}
		}

		return returnValue;

	}

	@Override
	public boolean requestPasswordReset(String email) {
		boolean returnValue = false;

		ConsumerEntity consumerEntity = consumerRepository.findByEmail(email);

		if (consumerEntity == null) {
			return returnValue;
		}

		String token = new ConsumerUtils().generatePasswordResetToken(consumerEntity.getConsumerId());

		PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
		passwordResetTokenEntity.setToken(token);
		passwordResetTokenEntity.setConsumerDetailsVO(consumerEntity);
		passwordResetTokenRepository.save(passwordResetTokenEntity);

		// returnValue = new AmazonSES().sendPasswordResetRequest(
		//getFirstName(),
		//getEmail(),
		//token);

		return returnValue;

	}

	@Override
	public boolean resetPassword(String token, String password) {
		boolean returnValue = false;

		if (ConsumerUtils.hasTokenExpired(token)) {
			return returnValue;
		}

		PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.findByToken(token);

		if (passwordResetTokenEntity == null) {
			return returnValue;
		}

		// Prepare new password
		String encodedPassword = bCryptPasswordEncoder.encode(password);

		// Update User password in database
		ConsumerEntity consumerEntity = passwordResetTokenEntity.getConsumerDetailsVO();
		consumerEntity.setEncryptedPassword(encodedPassword);
		ConsumerEntity savedConsumerEntity = consumerRepository.save(consumerEntity);

		// Verify if password was saved successfully
		if (savedConsumerEntity != null
				&& savedConsumerEntity.getEncryptedPassword().equalsIgnoreCase(encodedPassword)) {
			returnValue = true;
		}

		// Remove Password Reset token from database
		passwordResetTokenRepository.delete(passwordResetTokenEntity);

		return returnValue;
	}
}
