package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.ReportMinDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

public Page<ReportMinDTO> searchReport(String minDate, String maxDate, String name, Pageable pageable) {
		
		LocalDate checkedMaxDate = checkDate(maxDate, 
				LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()));
		
		LocalDate checkedMinDate = checkDate(minDate, checkedMaxDate.minusYears(1L));
		
		return repository.searchReportByParams(checkedMinDate, checkedMaxDate, name, pageable);
	}
	
	
	/*
	 * public Page<ReportMinDTO> searchReport(String minDate, String maxDate, String
	 * name, Pageable pageable) {
	 * 
	 * LocalDate checkedMaxDate = checkDate(maxDate,
	 * LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()));
	 * 
	 * LocalDate checkedMinDate = checkDate(minDate, checkedMaxDate.minusYears(1L));
	 * 
	 * Page<Sale> result = repository.searchReportByParams(checkedMinDate,
	 * checkedMaxDate, name, pageable);
	 * 
	 * return result.map(sale -> new ReportMinDTO(sale)); }
	 */

	private LocalDate checkDate(String date, LocalDate localDateDefault) {
		LocalDate setDate;
		if (date != null) {
			setDate = LocalDate.parse(date);
		}else {
			setDate = localDateDefault;
		}
		
		return setDate;
	}
	
	
	
}
