package com.apap.tutorial5.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.model.DealerModel;
import com.apap.tutorial5.service.CarService;
import com.apap.tutorial5.service.DealerService;

/**
 * CarController
 * @author Raihan Mahendra
 *
 */
@Controller
public class CarController {
	@Autowired
	private CarService carService;
	
	@Autowired
	private DealerService dealerService;
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		CarModel car = new CarModel();
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		ArrayList<CarModel> listCar = new ArrayList<>();
		listCar.add(car);
		dealer.setListCar(listCar);
		
		model.addAttribute("dealer", dealer);
		model.addAttribute("title", "Add Car");
		return "addCar";
	}
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.POST, params= {"submit"})
	private String addCarSubmit(@ModelAttribute DealerModel dealer, Model model) {
		
		DealerModel dealerNow = dealerService.getDealerDetailById(dealer.getId()).get();
		for (CarModel car : dealer.getListCar()) {
			car.setDealer(dealerNow);
			carService.addCar(car);
		}
		
		model.addAttribute("title", "Add Car");
		return "add";
	}
	
//	@RequestMapping(value = "/car/add", method = RequestMethod.POST)
//	private String addCarSubmit(@ModelAttribute CarModel car, Model model) {
//		carService.addCar(car);
//		model.addAttribute("title", "Add Car");
//		return "add";
//	}
	
	@RequestMapping(value="/car/add/{dealerId}", method = RequestMethod.POST, params= {"addRow"})
	private String addRow(@ModelAttribute DealerModel dealer, BindingResult bindingResult, Model model) {
		System.out.println("masuk sini cuyyy");
		if (dealer.getListCar() == null) {
			dealer.setListCar(new ArrayList<CarModel>());
		}
		dealer.getListCar().add(new CarModel());
		model.addAttribute("dealer", dealer);
		return "addCar";
	}
	
	@RequestMapping(value="/car/add/{dealerId}", method = RequestMethod.POST, params= {"removeRow"})
	private String removeRow(@ModelAttribute DealerModel dealer, BindingResult bindingResult, HttpServletRequest req, Model model) {
		Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
		dealer.getListCar().remove(rowId.intValue());
		model.addAttribute("dealer", dealer);
		return "addCar";
	}
	
	@RequestMapping(value = "/car/delete", method = RequestMethod.POST)
	private String deleteCar(@ModelAttribute DealerModel dealer, Model model) {
		for (CarModel car : dealer.getListCar()) {
			carService.deleteCar(carService.getCarDetailById(car.getId()).get());
		}
		model.addAttribute("title", "Delete Car");
		return "delete";
	}
	
	@RequestMapping(value = "/car/update/{carId}", method = RequestMethod.GET)
	private String update(@PathVariable(value = "carId") Long carId, Model model) {
		model.addAttribute("oldCar", carService.getCarDetailById(carId).get());
		model.addAttribute("newCar", new CarModel());
		model.addAttribute("title", "Update Car");
		return "updateCar";
	}
	
	@RequestMapping(value = "/car/update/{carId}", method = RequestMethod.POST)
	private String updateCarSubmit(@PathVariable(value = "carId") Long carId, @ModelAttribute CarModel newCar, Model model) {
		carService.updateCar(carId, newCar);
		model.addAttribute("title", "Update Car");
		return "update";
	}
	
}
