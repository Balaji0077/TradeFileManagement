package com.mph.TradeFileManagement.model;

import java.sql.Date;

public class SearchCriteriaDTO {
    
	private Long id;
	private String filename;
	private Date fromDate;
	private Date toDate;
	private String status;
	public Long getId() {
		return id;
	}
	public SearchCriteriaDTO(Long id, String filename, Date fromDate, Date toDate, String status) {
		
		this.id = id;
		this.filename = filename;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.status = status;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
