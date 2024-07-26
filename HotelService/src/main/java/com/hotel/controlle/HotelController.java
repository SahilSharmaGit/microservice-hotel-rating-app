package com.hotel.controlle;

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

import com.hotel.entities.Hotel;
import com.hotel.service.HotelService;

@RestController
@RequestMapping("/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;


	@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
	@PostMapping
	public ResponseEntity<Hotel> create(@RequestBody Hotel hotel) {

		Hotel save = hotelService.save(hotel);
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}

	@PreAuthorize("hasAuthority('SCOPE_internal')")
	@GetMapping("/{id}")
	public ResponseEntity<Hotel> getOne(@PathVariable String id) {

		Hotel hotel = hotelService.getOne(id);
		return ResponseEntity.ok(hotel);
	}

	@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
	@GetMapping
	public ResponseEntity<List<Hotel>> all() {

		List<Hotel> all = hotelService.getALL();
		return ResponseEntity.ok(all);
	}

}
