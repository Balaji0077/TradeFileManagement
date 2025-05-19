package com.mph.TradeFileManagement.dao;


import java.sql.Date;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mph.TradeFileManagement.model.FileLoad;


@Repository
public interface FileLoadRepository extends JpaRepository<FileLoad,Long> {
        
  //Table name file_load	
   @Query(value="SELECT * FROM file_load  WHERE (:id IS NULL OR id=:id)"
   		+ "AND (:filename IS NULL or filename LIKE :filename)"
   		+ "AND ((:fromDate IS NULL OR loaddate >= :fromDate) AND (:toDate IS NULL OR loaddate <= :toDate))"
   		+ "AND (:status IS NULL or status LIKE :status)",nativeQuery=true)	
	public List<FileLoad> getSearchDetails(
			@Param("id") Long id,
			@Param("filename") String filename,
			@Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate,
			@Param("status") String status);
   
   public boolean existsByFilename(String filename);


}
