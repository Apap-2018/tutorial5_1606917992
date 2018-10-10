package com.apap.tutorial5.service;

import java.util.Optional;

import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.model.DealerModel;

/**
 * CarService
 * @author Raihan Mahendra
 *
 */
public interface CarService {
	void addCar(CarModel car);
	
	void deleteCar(CarModel car);
	
	Optional<CarModel> getCarDetailById(Long id);
	
	void updateCar(Long oldCarId, CarModel newCar);
}
