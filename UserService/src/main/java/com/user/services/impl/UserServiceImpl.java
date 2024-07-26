package com.user.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.entities.Hotel;
import com.user.entities.Rating;
import com.user.entities.User;
import com.user.exception.ResourceNotFoundException;
import com.user.external.services.HotelService;
import com.user.repository.UserRepository;
import com.user.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HotelService hotelService;

	@Override
	public User saveUser(User user) {
		String userId = UUID.randomUUID().toString();
		user.setUserId(userId);
		return repository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return repository.findAll();
	}

	@Override
	public User getUser(String userId) {
		User user = repository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with this is " + userId));

		Rating[] ratings = getUserRatings(userId);
		List<Rating> ratingList = Arrays.asList(ratings);
		setHotel(ratingList);
		user.setRatings(ratingList);
		return user;
	}

	private void setHotel(List<Rating> ratings) {
		ratings.forEach(rating -> rating.setHotel(hotelService.getHotel(rating.getHotelId())));
	}

	private Hotel getHotel(String hotelId) {
		final String URL = "http://HOTEL-SERVICE/hotels/" + hotelId;
		return restTemplate.getForObject(URL, Hotel.class);

	}

	private Rating[] getUserRatings(String userId) {

		final String URL = "http://RATING-SERVICE/ratings/users/" + userId;
		return restTemplate.getForObject(URL, Rating[].class);
	}

}
