package com.mph.TradeFileManagement.model;


import java.sql.Date;
import java.time.LocalDate;


public class FileLoadResponseDTO {
	
	private Long id;
	
	public FileLoadResponseDTO() {
		
	}
	public FileLoadResponseDTO(Long id, String filename, Date localDate, String status, Double recordCount,
			String errors) {
		
		this.id = id;
		this.filename = filename;
		this.localDate = localDate.toLocalDate();
		this.status = status;
		this.recordCount = recordCount;
		this.errors = errors;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	private String filename;
	private LocalDate localDate;
	private String status;
	private Double recordCount;
	private String errors;
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public LocalDate getLocalDate() {
		return localDate;
	}
	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Double recordCount) {
		this.recordCount = recordCount;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	
}
