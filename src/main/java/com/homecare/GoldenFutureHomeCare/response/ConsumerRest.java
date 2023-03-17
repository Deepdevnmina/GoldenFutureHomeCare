package com.homecare.GoldenFutureHomeCare.response;

import java.util.List;

public class ConsumerRest {
	private String consumerId;
	private String firstName;
	private String lastName;
	private String email;
	private List<AddressesRes>addresses;

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<AddressesRes> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressesRes> addresses) {
		this.addresses = addresses;
	}

}
