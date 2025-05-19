package com.mph.TradeFileManagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mph.TradeFileManagement.dao.FileLoadRepository;
import com.mph.TradeFileManagement.model.FileLoad;
import com.mph.TradeFileManagement.model.FileLoadRequestDTO;
import com.mph.TradeFileManagement.model.FileLoadResponseDTO;
import com.mph.TradeFileManagement.model.SearchCriteriaDTO;

@Service
public class FileLoadService {
    
	@Autowired
	FileLoadRepository fileLoadRepository;
	
	
	//Create Service
	public FileLoadResponseDTO createFileLoad(FileLoadRequestDTO fileLoadRequestDTO) {
		FileLoad file = new FileLoad();
		if(fileLoadRepository.existsByFilename(fileLoadRequestDTO.getFilename().toUpperCase()))
    	{
    		throw new IllegalArgumentException("File already exists Please update if required!!");
    	}
		if(fileLoadRequestDTO.getRecordCount()%1!=0)
    	{
    		throw new IllegalArgumentException("Record Count be Integer!");
    	}
		file.setLocalDate(LocalDate.now());
		file.setFilename(fileLoadRequestDTO.getFilename().toUpperCase());
		file.setStatus(fileLoadRequestDTO.getStatus());
		file.setRecordCount(fileLoadRequestDTO.getRecordCount());
		file.setErrors(fileLoadRequestDTO.getErrors());
	    
		
		file = fileLoadRepository.save(file);
		
		FileLoadResponseDTO responseDTO = new FileLoadResponseDTO ();
		responseDTO.setId(file.getId());
		responseDTO.setFilename(file.getFilename());
		responseDTO.setLocalDate(file.getLocalDate());
		responseDTO.setStatus(file.getStatus());
		responseDTO.setRecordCount(file.getRecordCount());
		responseDTO.setErrors(file.getErrors());
		
		return responseDTO;
		
	}
	
	//Get data by Id
	public FileLoadResponseDTO getFileLoadById(Long id){
	
		Optional<FileLoad> file = fileLoadRepository.findById(id);
		if(file.isPresent())
		{    
			FileLoad filePresent = file.get();
			FileLoadResponseDTO responseDTO = new FileLoadResponseDTO();
			responseDTO.setId(filePresent.getId());
			responseDTO.setFilename(filePresent.getFilename());
			responseDTO.setLocalDate(filePresent.getLocalDate());
			responseDTO.setStatus(filePresent.getStatus());
			responseDTO.setRecordCount(filePresent.getRecordCount());
			responseDTO.setErrors(filePresent.getErrors());
			return responseDTO;
			
		}
		else {
			throw new IllegalArgumentException("Resource Not Available!!!");
		}
		
		
	}
	
	//Search By Criteria
	public List<FileLoad> searchFileLoads(SearchCriteriaDTO searchCriteria){

		return fileLoadRepository.getSearchDetails(searchCriteria.getId(),
				searchCriteria.getFilename(),
				searchCriteria.getFromDate(),
				searchCriteria.getToDate(),
				searchCriteria.getStatus());
	}
	
	
	//Update service
	public FileLoadResponseDTO updateFileLoadStatus(Long id, String status) {
	    if(status==null||!status.matches("NEW|FAILED|PROCESSED"))
	    {
	    	throw new IllegalArgumentException("Provide values only 'new' or 'failed' or 'processed'");
	    }
	
		//Take response Data
	    FileLoadResponseDTO existingFileLoadResponseDTO = getFileLoadById(id);
	    FileLoad fileLoad = new FileLoad();
	    fileLoad.setId(id); 
	    fileLoad.setFilename(existingFileLoadResponseDTO.getFilename());
	    fileLoad.setLocalDate(existingFileLoadResponseDTO.getLocalDate());
	    fileLoad.setStatus(status); 
	    fileLoad.setRecordCount(existingFileLoadResponseDTO.getRecordCount());
	    fileLoad.setErrors(existingFileLoadResponseDTO.getErrors());

	  
	    fileLoad = fileLoadRepository.save(fileLoad);

	    // new responseData
	    FileLoadResponseDTO updatedResponseDTO = new FileLoadResponseDTO();
	    updatedResponseDTO.setId(fileLoad.getId());
	    updatedResponseDTO.setFilename(fileLoad.getFilename());
	    updatedResponseDTO.setLocalDate(fileLoad.getLocalDate());
	    updatedResponseDTO.setStatus(fileLoad.getStatus());
	    updatedResponseDTO.setRecordCount(fileLoad.getRecordCount());
	    updatedResponseDTO.setErrors(fileLoad.getErrors());

	    return updatedResponseDTO;
		
		
	
	}
	
	//delete service
	public void deleteFileLoad(Long id) {
		
		FileLoadResponseDTO existingFileLoadResponseDTO = getFileLoadById(id);
		fileLoadRepository.deleteById(existingFileLoadResponseDTO.getId());
		
	}
	
	
    
	
}
