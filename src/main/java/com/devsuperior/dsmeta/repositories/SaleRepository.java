package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj) "
		 + "  FROM Sale obj"
		 + " WHERE UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%'))"
	     + "   AND obj.date BETWEEN :checkedMinDate AND :checkedMaxDate")
	Optional<SaleMinDTO> searchByParams(LocalDate checkedMinDate, LocalDate checkedMaxDate, String name, Pageable pageable);

}
