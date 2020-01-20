package com.oneaston.db.universe.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oneaston.db.universe.domain.ClientLoginAccount;
import com.oneaston.db.universe.domain.WebAddress;

public interface ClientLoginAccountRepository extends JpaRepository<ClientLoginAccount, Long>{
	
	ClientLoginAccount findClientLoginAccountByLoginAccountId(long loginAccountId);
	List<ClientLoginAccount> findClientLoginAccountsByWebAddressId(WebAddress webAddressId);
	
	@Transactional
	@Query("SELECT cla FROM ClientLoginAccount cla WHERE cla.accountType = 'script'")
	List<ClientLoginAccount> findAllClientLoginAccountsByAccountType();
	
	@Transactional
	@Query("SELECT cla FROM ClientLoginAccount cla WHERE cla.webAddressId = :webAddressId AND cla.accountType = 'generic'")
	List<ClientLoginAccount> findClientLoginAccountByWebAddressIdAndAccountType(@Param("webAddressId")WebAddress webAddressId);

}
