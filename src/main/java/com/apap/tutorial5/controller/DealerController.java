package com.apap.tutorial5.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.model.DealerModel;
import com.apap.tutorial5.service.CarService;
import com.apap.tutorial5.service.DealerService;

/**
 * DealerController
 * @author Raihan Mahendra
 *
 */
@Controller
public class DealerController {
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private CarService carService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("title", "Home");
		return "home";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.GET)
	private String add(Model model) {
		System.out.println("add GET");
		model.addAttribute("dealer", new DealerModel());
		model.addAttribute("title", "Add Dealer");
		return "addDealer";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.POST)
	private String addDealerSubmit(@ModelAttribute DealerModel dealer, Model model) {
		System.out.println("addDealerSubmit POST");
		dealerService.addDealer(dealer);
		model.addAttribute("title", "Add");
		return "add";
	}
	
	@RequestMapping(value = "/dealer/view", method = RequestMethod.GET)
	private String view(@RequestParam(value = "dealerId", required = true) Long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		model.addAttribute("dealer", dealer);
		
		List<CarModel> listCar = dealer.getListCar();
		Collections.sort(listCar);
		System.out.println(listCar.size());
		model.addAttribute("listCar", listCar);
		model.addAttribute("title", "View Dealer");
		return "view-dealer";
	}
	
	@RequestMapping(value = "/dealer/delete/{dealerId}", method = RequestMethod.GET)
	private String deleteDealer(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		dealerService.deleteDealer(dealerService.getDealerDetailById(dealerId).get());
		model.addAttribute("title", "Delete Dealer");
		return "delete";
	}
	
	@RequestMapping(value = "/dealer/update/{dealerId}", method = RequestMethod.GET)
	private String update(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		model.addAttribute("oldDealer", dealerService.getDealerDetailById(dealerId).get());
		model.addAttribute("newDealer", new DealerModel());
		model.addAttribute("title", "Update Dealer");
		return "updateDealer";
	}
	
	@RequestMapping(value = "/dealer/update/{dealerId}", method = RequestMethod.POST)
	private String updateDealerSubmit(@PathVariable(value = "dealerId") Long dealerId, @ModelAttribute DealerModel newDealer, Model model) {
		dealerService.updateDealer(dealerId, newDealer);
		model.addAttribute("title", "Update Dealer");
		return "update";
	}
	
	@RequestMapping(value = "/dealer/view/all", method = RequestMethod.GET)
	private String viewAll(Model model) {
		List<DealerModel> listDealer = dealerService.getAllDealer();
		model.addAttribute("listDealer", listDealer);
		model.addAttribute("title", "View All Dealer");
		return "view-all";
	}
	
}
