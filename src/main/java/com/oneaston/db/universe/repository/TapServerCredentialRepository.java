package com.oneaston.db.universe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneaston.db.universe.domain.TapServerCredential;
import com.oneaston.db.universe.domain.WebAddress;

public interface TapServerCredentialRepository extends JpaRepository<TapServerCredential, Long>{
	
	TapServerCredential findTapServerCredentialByCredentialId(long credentialId);
	TapServerCredential findTapServerCredentialByWebAddressId(WebAddress webAddressId);
	
}
