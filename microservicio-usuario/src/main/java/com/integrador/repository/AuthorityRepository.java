package com.integrador.repository;

import org.springframework.stereotype.Repository;
import com.integrador.domain.Authority;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String>{
	
	Optional<Authority> findByName(Authority authorityName);

}
