package com.homecare.GoldenFutureHomeCare.contoller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homecare.GoldenFutureHomeCare.exceptions.ConsumerServiceException;
import com.homecare.GoldenFutureHomeCare.request.model.ConsumerDetailsVO;
import com.homecare.GoldenFutureHomeCare.request.model.PasswordResetModel;
import com.homecare.GoldenFutureHomeCare.request.model.PasswordResetRequestModel;
import com.homecare.GoldenFutureHomeCare.response.AddressesRes;
import com.homecare.GoldenFutureHomeCare.response.ConsumerRest;
import com.homecare.GoldenFutureHomeCare.response.ErrorMessages;
import com.homecare.GoldenFutureHomeCare.response.OperationStatusModel;
import com.homecare.GoldenFutureHomeCare.response.RequestOperationName;
import com.homecare.GoldenFutureHomeCare.response.RequestOperationStatus;
import com.homecare.GoldenFutureHomeCare.service.AddressService;
import com.homecare.GoldenFutureHomeCare.service.ConsumerService;
import com.homecare.GoldenFutureHomeCare.shared.dto.AddressDTO;
import com.homecare.GoldenFutureHomeCare.shared.dto.ConsumerDto;

@RestController
@RequestMapping("/consumers") // http://localhost:2018/
public class ConsumerController {

	@Autowired
	ConsumerService consumerService;

	@Autowired
	AddressService addressService;

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	// consumerDetailsVO is an object of model class.
	public ConsumerRest createConsumer(@RequestBody ConsumerDetailsVO consumerDetailsVO) throws Exception {
		ConsumerRest returnValue = new ConsumerRest();// This is the info that is going to return.

		if (consumerDetailsVO.getFirstName().isEmpty())// after creating enum for customError we write this code.
			throw new ConsumerServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		// ConsumerDto consumerDto = new ConsumerDto();
		// BeanUtils.copyProperties(consumerDetailsVO, consumerDto);
		ModelMapper modelMapper = new ModelMapper();
		ConsumerDto consumerDto = modelMapper.map(consumerDetailsVO, ConsumerDto.class);

		ConsumerDto createdConsumer = consumerService.createConsumer(consumerDto);
		returnValue = modelMapper.map(createdConsumer, ConsumerRest.class);
		// BeanUtils.copyProperties(createdConsumer, returnValue);
		return returnValue;

	}

	// Media type is to make our format response on both JSON anD HTML.
	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ConsumerRest getConsumer(@PathVariable String id) {
		ConsumerRest returnValue = new ConsumerRest();
		ConsumerDto consumerDto = consumerService.getConsumerByConsumerId(id);
		// BeanUtils.copyProperties(consumerDto, returnValue);
		ModelMapper modelMapper = new ModelMapper();
		returnValue = modelMapper.map(consumerDto, ConsumerRest.class);
		return returnValue;
	}

	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ConsumerRest updateConsumer(@PathVariable String id, @RequestBody ConsumerDetailsVO consumerDetailsVO)
			throws Exception {
		ConsumerRest returnValue = new ConsumerRest();// This is the info that is going to return.
		if (consumerDetailsVO.getFirstName().isEmpty())// after creating enum for customError we write this code.
			throw new ConsumerServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		ConsumerDto consumerDto = new ConsumerDto();
		// BeanUtils.copyProperties(consumerDetailsVO, consumerDto);
		consumerDto = new ModelMapper().map(consumerDetailsVO, ConsumerDto.class);

		ConsumerDto updatedConsumer = consumerService.updateConsumer(id, consumerDto);
		// BeanUtils.copyProperties(updatedConsumer, returnValue);
		returnValue = new ModelMapper().map(updatedConsumer, ConsumerRest.class);
		return returnValue;

	}

	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel deleteConsumer(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());

		consumerService.deleteConsumer(id);

		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}

	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<ConsumerRest> getConsumerList(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {
		List<ConsumerRest> returnValue = new ArrayList<>();
		List<ConsumerDto> consumers = consumerService.getConsumerList(page, limit);

		for (ConsumerDto consumerDto : consumers) {
			ConsumerRest consumerModel = new ConsumerRest();
			BeanUtils.copyProperties(returnValue, consumerModel);

			returnValue.add(consumerModel);
		}
		return returnValue;
	}

	// http://localhost:8888/GoldenFutureHomrCare/users/userID/addresses
	@GetMapping(path = "/{id}/addresses", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public List<AddressesRes> getConsumerAddresses(@PathVariable String id) {
		List<AddressesRes> returnValue = new ArrayList<>();
		List<AddressDTO> addressDto = addressService.getAddresses(id);

		if (addressDto != null && !addressDto.isEmpty()) {
			Type listType = new TypeToken<List<AddressesRes>>() {
			}.getType();
			returnValue = new ModelMapper().map(addressDto, listType);
		}
		return returnValue;
	}

	@GetMapping(path = "/{userId}/addresses/{addressId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public AddressesRes getConsumerAddress(@PathVariable String addressId) {

		AddressDTO addressesDto = addressService.getAddress(addressId);

		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(addressesDto, AddressesRes.class);

	}

	/*
	 * http://localhost:8080/GoldenFutureHomeCare/users/email-verification?token=
	 * sdfsdfddjsa
	 */
	@GetMapping(path = "/email-verification", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public OperationStatusModel verifyEmailToken(@RequestParam(value = "token") String token) {

		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());

		boolean isVerified = consumerService.verifyEmailToken(token);

		if (isVerified) {
			returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		} else {
			returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
		}

		return returnValue;
	}

	/*
	 * http://localhost:8080/GoldenFutureHomeCare/users/password-reset-request
	 */
	@PostMapping(path = "/password-reset-request", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public OperationStatusModel requestReset(@RequestBody PasswordResetRequestModel passwordResetRequestModel) {
		OperationStatusModel returnValue = new OperationStatusModel();

		boolean operationResult = consumerService.requestPasswordReset(passwordResetRequestModel.getEmail());

		returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
		returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

		if (operationResult) {
			returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		}

		return returnValue;
	}

	@PostMapping(path = "/password-reset", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public OperationStatusModel resetPassword(@RequestBody PasswordResetModel passwordResetModel) {
		OperationStatusModel returnValue = new OperationStatusModel();

		boolean operationResult = consumerService.resetPassword(passwordResetModel.getToken(),
				passwordResetModel.getPassword());

		returnValue.setOperationName(RequestOperationName.PASSWORD_RESET.name());
		returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

		if (operationResult) {
			returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		}

		return returnValue;
	}
}
