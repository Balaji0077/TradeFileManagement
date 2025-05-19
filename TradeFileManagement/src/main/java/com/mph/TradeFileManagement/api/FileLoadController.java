package com.mph.TradeFileManagement.api;

import java.sql.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mph.TradeFileManagement.model.FileLoad;
import com.mph.TradeFileManagement.model.FileLoadRequestDTO;
import com.mph.TradeFileManagement.model.FileLoadResponseDTO;
import com.mph.TradeFileManagement.model.SearchCriteriaDTO;
import com.mph.TradeFileManagement.service.FileLoadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins="http://localhost:4200",methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping("/api/file-loads")
public class FileLoadController {
    
	
	@Autowired
	FileLoadService fileLoadService;
	
	//Create a post mapping
	@PostMapping 
	@Operation(summary="Create a record by providing filename,status,recordcount and errors",description="Uploads a new file details if file doesnot exists.")
	public ResponseEntity<FileLoadResponseDTO> create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description="Enter files details like filename,recordcount,status and errors to create a record") @RequestBody @Valid FileLoadRequestDTO flr){
		 return new ResponseEntity<FileLoadResponseDTO>(fileLoadService.createFileLoad(flr),HttpStatus.CREATED);
	}
	
	//Get a each record by id
	@GetMapping("/{id}")
	@Operation(summary="Read file by id",description="Returns a file based on id if file exists else returns resource not found!.")
	public ResponseEntity<FileLoadResponseDTO> get(@Parameter(description=" Id of file to be retrieved") @PathVariable Long id){
		FileLoadResponseDTO response = fileLoadService.getFileLoadById(id);
		return ResponseEntity.ok(response);
	}
	
	//Get row by search
	@GetMapping("/search")
	@Operation(summary="Get the file details by id or filename or date range or status",description="Search a file by id or filename or status or date range to get the matched file details.")
	public ResponseEntity<List<FileLoad>> search(
			@RequestParam(required=false) Long id,
			@RequestParam(required=false) String filename,
			@RequestParam(required=false) Date fromDate,
			@RequestParam(required=false) Date toDate,
			@RequestParam(required=false) String status){
		
//		  System.out.println(filename+status+id);
//		  System.out.println(fromDate);
//		  System.out.println(toDate);
		 
		SearchCriteriaDTO searchParams = new SearchCriteriaDTO(id,filename,fromDate,toDate,status);
		List<FileLoad> searchedRows= fileLoadService.searchFileLoads(searchParams);
		return ResponseEntity.ok(searchedRows);
		
	}
	
	//delete by row
	@DeleteMapping("/{id}")
	@Operation(summary="Delete a file",description="Delete a file record.")
	public String delete(@PathVariable Long id) {
		fileLoadService.deleteFileLoad(id);
		
		return "Deleted Row Succcessfully";
		
		
	}
	
	//update the row 
	@PutMapping("/{id}/status")
	@Operation(summary="Update the file status",description="Update a file status of a file by providing the id")
	public ResponseEntity<FileLoadResponseDTO> update(@PathVariable Long id,@RequestBody String status)
	{  
		return ResponseEntity.ok(fileLoadService.updateFileLoadStatus(id,status));
	}

}
