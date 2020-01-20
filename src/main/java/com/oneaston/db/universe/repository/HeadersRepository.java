package com.oneaston.db.universe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneaston.db.universe.domain.Headers;
import com.oneaston.db.universe.domain.WebAddress;

public interface HeadersRepository extends JpaRepository<Headers, Long>{
	
	List<Headers> findHeadersByWebAddressId(WebAddress webAddressId);

}
