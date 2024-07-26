package com.rating.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rating.entities.Rating;
import com.rating.service.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

	@Autowired
	private RatingService service;

	@PreAuthorize("hasAuthority('Admin')")
	@PostMapping
	public ResponseEntity<Rating> create(@RequestBody Rating rating) {
		Rating ratingModel = service.save(rating);
		return ResponseEntity.status(HttpStatus.CREATED).body(ratingModel);
	}

	@GetMapping
	public ResponseEntity<List<Rating>> getAll() {
		List<Rating> all = service.getAll();
		return ResponseEntity.ok(all);
	}


	@PreAuthorize("hasAuthority('Admin')")
	@GetMapping("/users/{id}")
	public ResponseEntity<List<Rating>> getAllByUsersId(@PathVariable String id) {
		List<Rating> all = service.getRatingByUserId(id);
		return ResponseEntity.ok(all);
	}

	@GetMapping("/hotels/{id}")
	public ResponseEntity<List<Rating>> getAllByHotelId(@PathVariable String id) {
		List<Rating> all = service.getRatingByHotelId(id);
		return ResponseEntity.ok(all);
	}

}
