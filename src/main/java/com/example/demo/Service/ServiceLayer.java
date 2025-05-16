package com.example.demo.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.EncryptEntity;
import com.example.demo.Repository.RepositoryInterface;

@Service
public class ServiceLayer {

	@Autowired
  private 	RepositoryInterface  repo;
	
	private BCryptPasswordEncoder  encode = new BCryptPasswordEncoder();
	
	 public String register(EncryptEntity entity) {

	        // Normalize email to lowercase
	        String email = entity.getEmail().toLowerCase();
	        entity.setEmail(email);

	        // Validate if email already exists
	        if (repo.findByEmail(email) != null) {
	            return "Email already exists";
	        }

	        // Validate name is not empty
	        if (entity.getName() == null || entity.getName().trim().isEmpty()) {
	            return "Name is required";
	        }

	        // Validate name does not contain numbers
	        if (entity.getName().matches(".*\\d.*")) {
	            return "Name must not contain numbers";
	        }

	        // Validate email format
	        String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
	        if (!email.matches(emailRegex)) {
	            return "Invalid email format";
	        }

	        // Validate password pattern
	        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@.#$!%*?&])(?=.*\\d).{8,15}$";
	        if (!entity.getPassword().matches(passwordRegex)) {
	            return "Password must be 8–15 characters, include upper/lowercase, digit, and special char";
	        }

	        // Set current date and time (you need to have this field in entity)
	        entity.setLogindataandtime(LocalDateTime.now());

	        // Encrypt the password
	        entity.setPassword(encode.encode(entity.getPassword()));

	        // Save entity in database
	        repo.save(entity);

	        return "Register Successfully";
	    }
	
	
	
	public boolean validatedUser(String email, String password)
	{
		EncryptEntity  user = repo.findByEmail(email);
		return user != null && encode.matches(password, user.getPassword());
		
	}



	public EncryptEntity findByEmail(String email) {
		// TODO Auto-generated method stub
		return repo.findByEmail(email);
	}

}
