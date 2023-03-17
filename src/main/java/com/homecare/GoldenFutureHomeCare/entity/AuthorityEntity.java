package com.homecare.GoldenFutureHomeCare.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

public class AuthorityEntity implements Serializable {

	
	private static final long serialVersionUID = 3610052599153364896L;
    
	
	 @Id
	    @GeneratedValue(strategy=GenerationType.AUTO)
	private long Id;
	
	 @Column(nullable=false,length=20)
	private String name;
	
	 @ManyToMany(mappedBy="authorities")
	 private Collection<RoleEntity> roles;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Collection<RoleEntity> roles) {
		this.roles = roles;
	}
	 
	 
	
}
