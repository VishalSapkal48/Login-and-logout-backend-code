package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.EncryptEntity;
import com.example.demo.Service.ServiceLayer;

import jakarta.servlet.http.HttpSession;

@RestController

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

public class MainController {

	@Autowired
	private ServiceLayer serv;
	
	@PostMapping("/register")
	public ResponseEntity<?>  register(@RequestBody EncryptEntity entity )
	{
	  return  ResponseEntity.ok( serv.register(entity));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody EncryptEntity entity , HttpSession session)
	{
		if(serv.validatedUser(entity.getEmail(), entity.getPassword()))
		{
			session.setAttribute("entity", entity.getEmail());
			return ResponseEntity.ok("Login Successful!");

		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	}
	
	@GetMapping("/dashboard")
	public ResponseEntity<?> dashboard(HttpSession session) {
	    String email = (String) session.getAttribute("entity"); // or "user" - make sure to be consistent
	    if (email != null) {
	        EncryptEntity user = serv.findByEmail(email); // create this repo method to fetch user by username/email
	        if (user != null) {
	            return ResponseEntity.ok(user);
	        }
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	    }
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first");
	}

	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
	    session.invalidate();
	    return ResponseEntity.ok("Logout successful");
	}

	
}
