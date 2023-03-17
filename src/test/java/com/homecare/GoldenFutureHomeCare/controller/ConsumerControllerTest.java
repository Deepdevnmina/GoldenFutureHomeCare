package com.homecare.GoldenFutureHomeCare.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import com.homecare.GoldenFutureHomeCare.contoller.ConsumerController;
import com.homecare.GoldenFutureHomeCare.response.ConsumerRest;
import com.homecare.GoldenFutureHomeCare.serviceImpl.ConsumerServiceImpl;
import com.homecare.GoldenFutureHomeCare.shared.dto.AddressDTO;
import com.homecare.GoldenFutureHomeCare.shared.dto.ConsumerDto;

class ConsumerControllerTest {
	@InjectMocks
	ConsumerController consumerController;
	@Mock
	ConsumerServiceImpl consumerServiceImpl;
	
	ConsumerDto consumerDto;
	
	final String USER_ID = "bfhry47fhdjd7463gdh";

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		consumerDto = new ConsumerDto();
		consumerDto.setFirstName("Devi");
        consumerDto.setLastName("Sharma");
        consumerDto.setEmail("test@test.com");
        consumerDto.setEmailVerificationStatus(Boolean.FALSE);
        consumerDto.setEmailVerificationToken(null);
        consumerDto.setConsumerId(USER_ID);
        consumerDto.setAddresses(getAddressesDto());
        consumerDto.setEncryptedPassword("xcf58tugh47");
	}

	@Test
	void testGetConsumer() {
    when(consumerServiceImpl.getConsumerByConsumerId(anyString())).thenReturn(consumerDto);	
	    
	    ConsumerRest userRest = consumerController.getConsumer(USER_ID);
	    
	    assertNotNull(userRest);
	    assertEquals(USER_ID, userRest.getConsumerId());
	    assertEquals(consumerDto.getFirstName(), userRest.getFirstName());
	    assertEquals(consumerDto.getLastName(), userRest.getLastName());
	    assertTrue(consumerDto.getAddresses().size() == userRest.getAddresses().size());
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
}
