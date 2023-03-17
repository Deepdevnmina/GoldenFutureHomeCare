package com.homecare.GoldenFutureHomeCare.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="")
public class RoleEntity implements Serializable {

	private static final long serialVersionUID = 5916581175380623043L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
    @Column(nullable=false,length=20)
	private String name;
   
    @ManyToMany(mappedBy="roles")
	 private Collection<ConsumerEntity> consumers;
    
    
    @ManyToMany(cascade={CascadeType.PERSIST})
    @JoinTable(name="role_authorities",
    joinColumns=@JoinColumn(name="consumer_id",referencedColumnName="id"),
    inverseJoinColumns=@JoinColumn(name="authorities_id",referencedColumnName="id"))
	private Collection<AuthorityEntity>authorities;
    
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<ConsumerEntity> getConsumers() {
		return consumers;
	}

	public void setConsumers(Collection<ConsumerEntity> consumers) {
		this.consumers = consumers;
	}

	public Collection<AuthorityEntity> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<AuthorityEntity> authorities) {
		this.authorities = authorities;
	}
	
}






