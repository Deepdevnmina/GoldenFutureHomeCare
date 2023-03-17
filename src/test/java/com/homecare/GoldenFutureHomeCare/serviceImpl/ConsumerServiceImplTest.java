package com.homecare.GoldenFutureHomeCare.serviceImpl;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.homecare.GoldenFutureHomeCare.entity.AddressEntity;
import com.homecare.GoldenFutureHomeCare.entity.ConsumerEntity;
import com.homecare.GoldenFutureHomeCare.exceptions.ConsumerServiceException;
import com.homecare.GoldenFutureHomeCare.repository.ConsumerRepository;
import com.homecare.GoldenFutureHomeCare.shared.AmazonSES;
import com.homecare.GoldenFutureHomeCare.shared.ConsumerUtils;
import com.homecare.GoldenFutureHomeCare.shared.dto.AddressDTO;
import com.homecare.GoldenFutureHomeCare.shared.dto.ConsumerDto;

class ConsumerServiceImplTest {
	@InjectMocks
	ConsumerServiceImpl consumerServiceImplImpl;

	@Mock
	ConsumerRepository  consumerRepository;
 
	@Mock
	 ConsumerUtils  consumerUtils;
	
	@Mock
	AmazonSES amazonSES;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
 
	String  consumerId = "hhty57ehfy";
	String encryptedPassword = "74hghd8474jf";
	
	ConsumerEntity  consumerEntity;
 
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		 consumerEntity = new ConsumerEntity();
		 consumerEntity.setId(1L);
		 consumerEntity.setFirstName("Devi");
		 consumerEntity.setLastName("Sharma");
		 consumerEntity.setConsumerId( consumerId);
		 consumerEntity.setEncryptedPassword(encryptedPassword);
		 consumerEntity.setEmail("test@test.com");
		 consumerEntity.setEmailVerificationToken("7htnfhr758");
		 consumerEntity.setAddresses(getAddressesEntity());
	}

	@Test
	final void testGetConsumer() {
 
		when( consumerRepository.findByEmail(anyString())).thenReturn( consumerEntity);

		ConsumerDto  consumerDto =  consumerServiceImplImpl.getConsumer("test@test.com");

		assertNotNull( consumerDto);
		assertEquals("Devi",  consumerDto.getFirstName());

	}

	@Test
	final void testGetConsumer_ConsumernameNotFoundException() {
		when( consumerRepository.findByEmail(anyString())).thenReturn(null);

		assertThrows(UsernameNotFoundException.class,

				() -> {
					 consumerServiceImplImpl.getConsumer("test@test.com");
				}

		);
	}
	
	@Test
	final void testCreateConsumer_CreateConsumerServiceException()
	{
		when( consumerRepository.findByEmail(anyString())).thenReturn( consumerEntity);
		ConsumerDto  consumerDto = new ConsumerDto();
		 consumerDto.setAddresses(getAddressesDto());
		 consumerDto.setFirstName("Sergey");
		 consumerDto.setLastName("Kargopolov");
		 consumerDto.setPassword("12345678");
		 consumerDto.setEmail("test@test.com");
 	
		assertThrows(ConsumerServiceException.class,

				() -> {
					 consumerServiceImplImpl.createConsumer( consumerDto);
				}

		);
	}
	
	@Test
	final void testCreateConsumer()
	{
		when( consumerRepository.findByEmail(anyString())).thenReturn(null);
		when( consumerUtils.generateAddressId(anyInt())).thenReturn("hgfnghtyrir884");
		when( consumerUtils.generateConsumerId(anyInt())).thenReturn( consumerId);
		when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
		when( consumerRepository.save(any(ConsumerEntity.class))).thenReturn( consumerEntity);
		Mockito.doNothing().when(amazonSES).verifyEmail(any(ConsumerDto.class));
 		
		ConsumerDto  consumerDto = new ConsumerDto();
		 consumerDto.setAddresses(getAddressesDto());
		 consumerDto.setFirstName("Sergey");
		 consumerDto.setLastName("Kargopolov");
		 consumerDto.setPassword("12345678");
		 consumerDto.setEmail("test@test.com");

		ConsumerDto storedConsumerDetails =  consumerServiceImplImpl.createConsumer( consumerDto);
		assertNotNull(storedConsumerDetails);
		assertEquals( consumerEntity.getFirstName(), storedConsumerDetails.getFirstName());
		assertEquals( consumerEntity.getLastName(), storedConsumerDetails.getLastName());
		assertNotNull(storedConsumerDetails.getConsumerId());
		assertEquals(storedConsumerDetails.getAddresses().size(),  consumerEntity.getAddresses().size());
		verify( consumerUtils,times(storedConsumerDetails.getAddresses().size())).generateAddressId(30);
		verify(bCryptPasswordEncoder, times(1)).encode("12345678");
		verify( consumerRepository,times(1)).save(any(ConsumerEntity.class));
	}
	
	private List<AddressDTO> getAddressesDto() {
		AddressDTO addressDto = new AddressDTO();
		addressDto.setType("shipping");
		addressDto.setCity("Vancouver");
		addressDto.setCountry("Canada");
		addressDto.setPostalCode("ABC123");
		addressDto.setStreetName("123 Street name");

		AddressDTO billingAddressDto = new AddressDTO();
		billingAddressDto.setType("billling");
		billingAddressDto.setCity("Vancouver");
		billingAddressDto.setCountry("Canada");
		billingAddressDto.setPostalCode("ABC123");
		billingAddressDto.setStreetName("123 Street name");

		List<AddressDTO> addresses = new ArrayList<>();
		addresses.add(addressDto);
		addresses.add(billingAddressDto);

		return addresses;

	}
	
	private List<AddressEntity> getAddressesEntity()
	{
		List<AddressDTO> addresses = getAddressesDto();
		
	    Type listType = new TypeToken<List<AddressEntity>>() {}.getType();
	    
	    return new ModelMapper().map(addresses, listType);
	}

}
