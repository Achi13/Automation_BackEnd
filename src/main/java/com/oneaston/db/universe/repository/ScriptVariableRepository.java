package com.oneaston.db.universe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneaston.db.universe.domain.Script;
import com.oneaston.db.universe.domain.ScriptVariable;

public interface ScriptVariableRepository extends JpaRepository<ScriptVariable, Long>{
	
	List<ScriptVariable> findScriptVariablesByScriptId(Script scriptId);
	

}
