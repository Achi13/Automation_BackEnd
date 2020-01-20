package com.oneaston.db.universe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneaston.db.universe.domain.Client;
import com.oneaston.db.universe.domain.Universe;

public interface ClientRepository extends JpaRepository<Client, Long>{
	
	Client findClientByClientId(long clientId);
	Client findClientByClientName(String clientName);
	List<Client> findClientsByUniverseId(Universe universeId);

}
