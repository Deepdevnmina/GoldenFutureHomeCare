package com.homecare.GoldenFutureHomeCare.service;

import java.util.List;

import com.homecare.GoldenFutureHomeCare.shared.dto.AddressDTO;

public interface AddressService {
	List<AddressDTO> getAddresses(String cosumerId);
    AddressDTO getAddress(String addressId);
}
