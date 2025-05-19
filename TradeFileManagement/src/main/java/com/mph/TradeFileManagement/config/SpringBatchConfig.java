package com.mph.TradeFileManagement.config;

import java.io.File;
import java.time.LocalDate;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import org.springframework.transaction.PlatformTransactionManager;

import com.mph.TradeFileManagement.dao.FileLoadRepository;
import com.mph.TradeFileManagement.model.FileLoad;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
	@Autowired
	private FileLoadRepository fileLoadRepository;

    public SpringBatchConfig(FileLoadRepository fileLoadRepository) {
        this.fileLoadRepository = fileLoadRepository;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<FileLoad> reader(@Value("#{jobParameters[fullPathFileName]}") String pathToFIle) {
       
    	FlatFileItemReader<FileLoad> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(new File(pathToFIle)));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<FileLoad> lineMapper() {
        DefaultLineMapper<FileLoad> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setStrict(false);
        tokenizer.setNames("filename", "status", "record_count", "errors");

        BeanWrapperFieldSetMapper<FileLoad> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(FileLoad.class);
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public RepositoryItemWriter<FileLoad> writer() {
        RepositoryItemWriter<FileLoad> writer = new RepositoryItemWriter<>();
        writer.setRepository(fileLoadRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step step1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,FlatFileItemReader<FileLoad> itemReader) {
        return new StepBuilder("step1", jobRepository)
                .<FileLoad, FileLoad>chunk(10, transactionManager)
                .reader(itemReader)
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job runJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("runJob", jobRepository)
                .start(step1)
                .build();
    }

   

    @Bean
    public ItemProcessor<FileLoad, FileLoad> processor() {
    	
        return item -> {
        	if(fileLoadRepository.existsByFilename(item.getFilename()))
        	{
        		return null;
        	}
        	
            item.setLocalDate(LocalDate.now());
            item.setFilename(item.getFilename().toUpperCase());
            return item;
        };
    }
}
