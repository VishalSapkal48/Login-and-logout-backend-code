package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.EncryptEntity;

public interface RepositoryInterface extends JpaRepository<EncryptEntity, Integer> {
   
	EncryptEntity findByEmail(String username);

}
