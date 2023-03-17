package com.homecare.GoldenFutureHomeCare.request.model;

import java.util.List;

public class ConsumerDetailsVO {
	String firstName;
	String lastName;
	String dob;
	String gender;
	String phone;
	String email;
	String password;
	private List<AddressDetailsVO> addresses;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<AddressDetailsVO> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressDetailsVO> addresses) {
		this.addresses = addresses;
	}

}
