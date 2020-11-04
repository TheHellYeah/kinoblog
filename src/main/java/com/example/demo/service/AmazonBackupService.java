package com.example.demo.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.example.demo.dto.UserListDTO;
import com.example.demo.utils.XMLMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;

@Service
public class AmazonBackupService implements BackupService {

    @Autowired
    private UserService userService;
    private AmazonS3 client;

    @Value("${amazon.access-key}")
    private String accessKey;
    @Value("${amazon.secret-key}")
    private String secretKey;
    @Value("${amazon.bucket-name}")
    private String bucketName;

    @PostConstruct
    private void connect() {
//        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
//        this.client =
//                AmazonS3ClientBuilder
//                        .standard()
//                        .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                        .withRegion(Regions.EU_CENTRAL_1)
//                        .build();
    }

    @Override
    @Scheduled(cron = "0 0 12 * * *") //Cron-expression, 12 часов дня каждый день
    public void backup() {
        UserListDTO usersDTO = new UserListDTO(userService.getAll());
        File file = XMLMarshaller.marshall(usersDTO);
        client.putObject(bucketName, file.getName(), file);
    }

    //TODO
    @Override
    public void restore() {

    }
}
