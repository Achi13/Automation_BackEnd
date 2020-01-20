package com.oneaston.db.universe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneaston.db.universe.domain.ClientLoginAccount;
import com.oneaston.db.universe.domain.Script;
import com.oneaston.db.universe.domain.Universe;

public interface ScriptRepository extends JpaRepository<Script, Long>{
	
	List<Script> findScriptsByUniverseId(Universe universeId);
	
	List<Script> findScriptsByLoginAccountId(ClientLoginAccount loginAccountId);
	
	Script findScriptByScriptId(long scriptId);
	
}
