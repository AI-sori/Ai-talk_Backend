package com.example.aitalk.infra.s3;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Uploader {

	private S3Client s3;

	@Value("${cloud.aws.region.static}")
	private String region;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@PostConstruct
	public void init() {
		s3 = S3Client.builder()
			.region(Region.of(region))
			// AWS SDK의 기본 자격 증명 공급자 체인 사용으로 변경 (EC2 IAM Role 자동 인식)
			// .credentialsProvider(StaticCredentialsProvider.create(
			// 	AwsBasicCredentials.create(accessKey, secretKey)))
			.build();
	}

	// MultipartFile을 받아 S3에 업로드 후, URL 반환
	public String upload(MultipartFile file, String dirName) throws IOException {
		String fileName = dirName + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
			.bucket(bucket)
			.key(fileName)
			.contentType(file.getContentType())
			//                .acl("public-read") // 공개 읽기 권한 설정 (필요시)
			.build();

		s3.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

		return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + fileName;
	}

	public String upload(MultipartFile file) throws IOException {
		return upload(file, "default-dir");  // 기본 디렉토리를 지정해서 호출
	}
}
