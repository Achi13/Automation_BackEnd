package com.oneaston.db.universe.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.oneaston.db.universe.domain.Client;
import com.oneaston.db.universe.domain.WebAddress;

public interface WebAddressRepository extends JpaRepository<WebAddress, Long>{
	
	WebAddress findWebAddressByWebAddressId(long webAddressId);
	WebAddress findWebAddressByUrl(String url);
	List<WebAddress> findWebAddressesByClientId(Client clientId);
	
	
}
