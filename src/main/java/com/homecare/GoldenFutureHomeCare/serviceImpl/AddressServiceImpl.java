package com.homecare.GoldenFutureHomeCare.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homecare.GoldenFutureHomeCare.entity.AddressEntity;
import com.homecare.GoldenFutureHomeCare.entity.ConsumerEntity;
import com.homecare.GoldenFutureHomeCare.repository.AddressRepository;
import com.homecare.GoldenFutureHomeCare.repository.ConsumerRepository;
import com.homecare.GoldenFutureHomeCare.service.AddressService;
import com.homecare.GoldenFutureHomeCare.shared.dto.AddressDTO;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	ConsumerRepository consumerRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Override
	public List<AddressDTO> getAddresses(String consumerId) {
        List<AddressDTO> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        
        ConsumerEntity consumerEntity =consumerRepository.findByConsumerId(consumerId);
        if(consumerEntity==null) return returnValue;
 
        Iterable<AddressEntity> addresses = addressRepository.findAllByConsumerDetailsVO(consumerEntity);
        for(AddressEntity addressEntity:addresses)
        {
            returnValue.add( modelMapper.map(addressEntity, AddressDTO.class) );
        }
        
        return returnValue;
	}

	@Override
	public AddressDTO getAddress(String addressId) {
        AddressDTO returnValue = null;

        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
        
        if(addressEntity!=null)
        {
            returnValue = new ModelMapper().map(addressEntity, AddressDTO.class);
        }
 
        return returnValue;
	}

}
