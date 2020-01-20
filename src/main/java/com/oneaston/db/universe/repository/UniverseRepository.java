package com.oneaston.db.universe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneaston.db.universe.domain.Universe;

public interface UniverseRepository extends JpaRepository<Universe, Long>{
	
	Universe findUniverseByUniverseId(long universeId);
	Universe findUniverseByUniverseName(String universeName);

}
