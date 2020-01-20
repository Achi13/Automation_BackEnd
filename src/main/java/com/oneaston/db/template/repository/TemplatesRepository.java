package com.oneaston.db.template.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oneaston.db.template.domain.Templates;
import com.oneaston.db.universe.domain.Client;
import com.oneaston.db.user.domain.User;

public interface TemplatesRepository extends JpaRepository<Templates, Long>{
	
	Templates findTemplatesByTemplateId(long templateId);
	
	@Transactional
	@Query("select t from Templates t where t.clientId = :clientId and t.userId = :userId")
	List<Templates> findTemplatesByClientIdAndUserId(@Param("clientId")Client clientId, 
			@Param("userId")User userId);
	
	@Transactional
	@Query("select t from Templates t where t.clientId = :clientId and t.isPublic = :isPublic and t.userId != :userId")
	List<Templates> findTemplatesByClientIdAndIsPublicAndUserId(@Param("clientId")Client clientId,
			@Param("isPublic")boolean isPublic, @Param("userId")User userId);

}
