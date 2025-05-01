package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.ReportMinDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<ReportMinDTO> searchReport(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate checkedMaxDate = checkDate(maxDate, today);
		LocalDate checkedMinDate = checkDate(minDate, checkedMaxDate.minusYears(1L));
		
		Page<Sale> result = repository.searchReportByParams(checkedMinDate,
				  checkedMaxDate, name, pageable);
				  
		return result.map(sale -> new ReportMinDTO(sale));
	}
	
	public List<SummaryMinDTO> searchSummary(String minDate, String maxDate) {
		LocalDate checkedMaxDate = checkDate(maxDate, today);
		LocalDate checkedMinDate = checkDate(minDate, checkedMaxDate.minusYears(1L));

		return repository.searchSummaryByParams(checkedMinDate, checkedMaxDate); 
	}
	
	private LocalDate checkDate(String date, LocalDate defaultDate) {
		LocalDate setDate = date != null ? LocalDate.parse(date) : defaultDate;

		return setDate;
	}

}
