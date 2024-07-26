package com.hotel.service;

import java.util.List;

import com.hotel.entities.Hotel;

public interface HotelService {

	Hotel save(Hotel hotel);

	Hotel getOne(String id);

	List<Hotel> getALL();

}
