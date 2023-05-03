package com.example.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.example.board.model.restaurant.Restaurant;

@Mapper
public interface RestaurantMapper {
	
	Double findRestaurantLat(Long restaurant_id) ;
	
	Double findRestaurantLng(Long restaurant_id) ;
	
	Restaurant findRestaurant(Long restaurant_id);
	
	List<Restaurant> findAllRestaurant();
}
