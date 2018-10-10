package com.apap.tutorial5.service;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial5.model.DealerModel;

/**
 * DealerService
 * @author Raihan Mahendra
 *
 */
public interface DealerService {
	Optional<DealerModel> getDealerDetailById(Long id);
	
	void addDealer(DealerModel dealer);
	
	void deleteDealer(DealerModel dealer);
	
	void updateDealer(Long oldDealerId, DealerModel newDealer);
	
	List<DealerModel> getAllDealer();
}
