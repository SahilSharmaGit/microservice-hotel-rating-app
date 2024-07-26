package com.rating.service;

import java.util.List;

import com.rating.entities.Rating;

public interface RatingService {

	Rating save(Rating rating);

	Rating getOne(String ratingId);

	List<Rating> getAll();

	List<Rating> getRatingByUserId(String userId);

	List<Rating> getRatingByHotelId(String hotelId);

}
