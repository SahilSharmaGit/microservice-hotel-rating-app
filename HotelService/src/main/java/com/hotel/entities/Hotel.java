package com.hotel.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "hotels")
public class Hotel {

	@Id
	private String id;
	private String name;
	private String location;
	private String about;
}
