package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query(value = "SELECT obj FROM Sale obj JOIN FETCH obj.seller"
		         + " WHERE UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))"
	             + "   AND obj.date BETWEEN :checkedMinDate AND :checkedMaxDate",
	  countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller"
	             + " WHERE UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))"
	    	     + "   AND obj.date BETWEEN :checkedMinDate AND :checkedMaxDate")
	Page<Sale> searchReportByParams(LocalDate checkedMinDate, LocalDate checkedMaxDate, String name, Pageable pageable);

	
	
	
	
	
//	@Query(nativeQuery = true, value = 
//	  "SELECT tb_sales.id"
//     + "     , tb_sales.visited"
//     + "     , tb_sales.deals"
//     + "     , tb_sales.amount"
//     + "     , tb_sales.date"
//	   + "     , tb_sales.seller_id"
//	   + "     , tb_seller.name"
//     + "  FROM tb_sales"
//     + " JOIN FETCH tb_seller ON tb_sales.seller_id = tb_seller.id"
//     + " WHERE UPPER(tb_seller.name) LIKE UPPER(CONCAT('%', :name, '%'))"
//     + "   AND tb_sales.date BETWEEN :checkedMinDate AND :checkedMaxDate")
//Page<Sale> searchReportByParams(LocalDate checkedMinDate, LocalDate checkedMaxDate, String name, Pageable pageable);
	
}
