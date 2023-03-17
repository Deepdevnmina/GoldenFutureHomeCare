package com.homecare.GoldenFutureHomeCare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.homecare.GoldenFutureHomeCare.entity.AddressEntity;
import com.homecare.GoldenFutureHomeCare.entity.ConsumerEntity;



@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
	List<AddressEntity> findAllByConsumerDetailsVO(ConsumerEntity consumerEntity);
	 AddressEntity findByAddressId(String addressId);
}