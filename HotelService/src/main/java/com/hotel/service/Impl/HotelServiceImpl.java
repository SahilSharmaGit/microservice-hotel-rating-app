package com.hotel.service.Impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.entities.Hotel;
import com.hotel.exception.ResourceNotFoundException;
import com.hotel.repositories.HotelRepository;
import com.hotel.service.HotelService;

@Service
public class HotelServiceImpl implements HotelService {

	@Autowired
	private HotelRepository repository;

	@Override
	public Hotel save(Hotel hotel) {
		String id = UUID.randomUUID().toString();
		hotel.setId(id);
		return repository.save(hotel);
	}

	@Override
	public Hotel getOne(String id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel not found " + id));
	}

	@Override
	public List<Hotel> getALL() {
		return repository.findAll();
	}

}
