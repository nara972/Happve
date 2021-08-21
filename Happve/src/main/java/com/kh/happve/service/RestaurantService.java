package com.kh.happve.service;


import com.kh.happve.entity.Restaurant;
import com.kh.happve.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public List<Restaurant> findAll(){
        return restaurantRepository.findAll();
    }

    public Restaurant save(Restaurant restaurant){
        Restaurant saveRest = restaurantRepository.save(restaurant);
        return saveRest;
    }

}
