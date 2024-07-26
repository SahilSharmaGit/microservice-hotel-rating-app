package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.entities.User;
import com.user.services.UserService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User model = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(model);
	}

	int retryCount = 1;
	@GetMapping("/{userId}")
//	@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
//	@Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
	
	@RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> singleUser(@PathVariable String userId) {
		log.info("****** singleUser Retry Count {} *******",retryCount);
		retryCount++;
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}

	public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
		log.info("****** Insie ratingHotelFallback *******");
		ex.printStackTrace();
		User user = User.builder().email("dummy@gmail.com").name("dummy").about("dummy user").userId("dummy-123")
				.build();
		return ResponseEntity.ok(user);
	}

	@GetMapping
	public ResponseEntity<List<User>> allUser() {
		List<User> user = userService.getAllUser();
		return ResponseEntity.ok(user);
	}
}
