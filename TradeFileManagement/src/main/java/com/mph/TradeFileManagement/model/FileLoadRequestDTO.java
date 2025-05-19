package com.mph.TradeFileManagement.model;



import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public class FileLoadRequestDTO {
	
    @NotNull(message="Filename is Mandatory")
    @Pattern(regexp=".*\\.csv$",message="File should be .csv extension")
//    @UniqueElements(message="Same file exists but update")
	private String filename;
    
    @Pattern(regexp="NEW|FAILED|PROCESSED",message="Status must be new,processed,failed")
	private String status;
    
    @PositiveOrZero(message="Record Count must be always positive or zero")
	private double recordCount;
	
	private String errors;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(double recordCount) {
		
		this.recordCount = recordCount;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
}
