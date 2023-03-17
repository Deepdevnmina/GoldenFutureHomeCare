package com.homecare.GoldenFutureHomeCare.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name ="consumers")

public class ConsumerEntity implements Serializable {

	private static final long serialVersionUID = -5277309092244434895L;

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String consumerId;

	@Column(nullable = false, length = 50)
	private String firstName;

	@Column(nullable = false, length = 50)
	private String lastName;

	@Column(nullable = false, length = 50)
	private String dob;

	@Column(nullable = false, length = 50)
	private String gender;

	@Column(nullable = false, length = 50)
	private String phone;

	@Column(nullable = false, length = 120)
	private String email;

	@Column(nullable = false)
	private String encryptedPassword;

	private String emailVerificationToken;

	@Column(nullable = false)
	private Boolean emailVerificationStatus = false;

	@OneToMany(mappedBy = "consumerDetailsVO", cascade = CascadeType.ALL)
	private List<AddressEntity> addresses;
	
	
	@ManyToMany(cascade={CascadeType.PERSIST})
    @JoinTable(name="user_roles",
    joinColumns=@JoinColumn(name="user_id",referencedColumnName="id"),
    inverseJoinColumns=@JoinColumn(name="roles_id",referencedColumnName="id"))
	private Collection<RoleEntity>roles;
	
	public Collection<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Collection<RoleEntity> roles) {
		this.roles = roles;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getEmailVerificationToken() {
		return emailVerificationToken;
	}

	public void setEmailVerificationToken(String emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}

	public Boolean getEmailVerificationStatus() {
		return emailVerificationStatus;
	}

	public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
		this.emailVerificationStatus = emailVerificationStatus;
	}

	public List<AddressEntity> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressEntity> addresses) {
		this.addresses = addresses;
	}

}
