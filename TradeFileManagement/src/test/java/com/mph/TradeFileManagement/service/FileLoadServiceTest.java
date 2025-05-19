package com.mph.TradeFileManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mph.TradeFileManagement.dao.FileLoadRepository;
import com.mph.TradeFileManagement.model.FileLoad;
import com.mph.TradeFileManagement.model.FileLoadRequestDTO;
import com.mph.TradeFileManagement.model.FileLoadResponseDTO;
import com.mph.TradeFileManagement.model.SearchCriteriaDTO;

@ExtendWith(MockitoExtension.class)
public class FileLoadServiceTest {
	    @Mock
	    private FileLoadRepository repo;

	    @InjectMocks
	    private FileLoadService service;
	    
	    @Test
	    void testCreateFileLoadService() {
	        FileLoadRequestDTO requestDTO = new FileLoadRequestDTO();
	        requestDTO.setFilename("test.csv");
	        requestDTO.setStatus("NEW");
	        requestDTO.setRecordCount(1D);
	        requestDTO.setErrors(null);

	        when(repo.existsByFilename("TEST.CSV")).thenReturn(false);

	        FileLoad file = new FileLoad();
	        file.setId(1L);
	        file.setFilename("test.csv");
	        file.setStatus("NEW");
	        file.setLocalDate(LocalDate.now());
	        file.setRecordCount(100D);
	        file.setErrors(null);

	        when(repo.save(any(FileLoad.class))).thenReturn(file);

	        FileLoadResponseDTO response = service.createFileLoad(requestDTO);
             
	        assertNotNull(response);
	        assertEquals("test.csv", response.getFilename());
	        assertEquals("NEW", response.getStatus());
	        assertEquals(100D, response.getRecordCount());
	        assertNull(response.getErrors());
	    }

	    @Test
	    void testGetFileLoadById() {
	        FileLoad file = new FileLoad();
	        file.setId(1L);
	        file.setFilename("test.csv");
	        file.setStatus("NEW");
	        file.setLocalDate(LocalDate.now());
	        file.setRecordCount(100D);
	        file.setErrors(null);

	        when(repo.findById(1L)).thenReturn(Optional.of(file));

	        FileLoadResponseDTO response = service.getFileLoadById(1L);

	        assertNotNull(response);
	        assertEquals(1L, response.getId());
	        assertEquals("test.csv", response.getFilename());
	    }

	    @Test
	    void testSearchFileLoad() {
	        List<FileLoad> fileList = new ArrayList<>();
	        FileLoad file = new FileLoad();
	        file.setId(1L);
	        file.setFilename("test.csv");
	        file.setStatus("NEW");
	        file.setLocalDate(LocalDate.now());
	        file.setRecordCount(100D);
	        file.setErrors(null);
	        fileList.add(file);

	        SearchCriteriaDTO scd = new SearchCriteriaDTO(1L, "test.csv", null, null, "NEW");
	        when(repo.getSearchDetails(1L, "test.csv", null, null, "NEW")).thenReturn(fileList);

	        List<FileLoad> result = service.searchFileLoads(scd);

	        assertNotNull(result);
	        assertFalse(result.isEmpty());
	        assertEquals(1, result.size());
	        assertEquals("test.csv", result.get(0).getFilename());
	    }

	    @Test
	    void testUpdateFileLoadStatus() {
	        FileLoad file = new FileLoad();
	        file.setId(1L);
	        file.setFilename("test.csv");
	        file.setStatus("NEW");
	        file.setLocalDate(LocalDate.now());
	        file.setRecordCount(100);
	        file.setErrors(null);

	        when(repo.findById(1L)).thenReturn(Optional.of(file));

	        file.setStatus("PROCESSED");
	        when(repo.save(any(FileLoad.class))).thenReturn(file);

	        FileLoadResponseDTO response = service.updateFileLoadStatus(1L, "PROCESSED");

	        assertNotNull(response);
	        assertEquals("PROCESSED", response.getStatus());
	    }

	    @Test
	    void testDeleteFileLoad() {
	        FileLoad file = new FileLoad();
	        file.setId(1L);
	        file.setFilename("test.csv");
	        file.setStatus("NEW");
	        file.setLocalDate(LocalDate.now());
	        file.setRecordCount(100);
	        file.setErrors(null);
	        when(repo.findById(1L)).thenReturn(Optional.of(file));
	        service.deleteFileLoad(1L);
	        verify(repo,times(1)).deleteById(1L);
	    }
}
