package com.user.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.user.entities.Rating;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

	@PostMapping("/ratings")
	public Rating createRating(Rating rating);

	@PostMapping("/ratings/{ratingId}")
	public Rating updateRating(@PathVariable String ratingId, Rating rating);

	@DeleteMapping("/ratings/{ratingId}")
	public Rating deleteRating(@PathVariable String ratingId);

}
