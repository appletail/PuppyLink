package com.web.puppylink.config.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AWSConfig {
	
	private @Value("${cloud.aws.region.static}") String region;
	private @Value("${cloud.aws.credentials.accessKey}") String iamAccessKey; 
	private @Value("${cloud.aws.credentials.secretKey}") String iamSecretKey; 
	
	@Bean
	public AmazonS3Client amazonS3Client() {
		BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(iamAccessKey, iamSecretKey);
		return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                                                             .withRegion(region)
                                                             .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                                                             .build();
	}
}