package com.web.SpringService.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
	private Logger LOGGER = LoggerFactory.getLogger(S3Service.class);
	
	@Autowired
	AmazonS3 s3Client;
	
	@Value("${s3.bucket}")
	private String bucketName;
	
	public void uplodFile(String localFilePath) {
		try {
			File file = new File(localFilePath);
			LOGGER.info("Iniciando upload");
			s3Client.putObject(new PutObjectRequest(bucketName, "teste", file));
			LOGGER.info("Finalizando");
		}catch (AmazonServiceException e) {
			LOGGER.info("AmazonServiceException: " + e.getMessage());
			LOGGER.info("Status code: " + e.getErrorCode());
		}catch (AmazonClientException e) {
			LOGGER.info("AmazonServiceException: " + e.getMessage());
		}
	}
}
