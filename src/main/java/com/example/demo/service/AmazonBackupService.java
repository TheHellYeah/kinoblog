package com.example.demo.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.demo.model.Film;
import com.example.demo.model.Review;
import com.example.demo.model.User;
import com.example.demo.repository.FilmRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Archiver;
import com.example.demo.utils.XmlMarshaller;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AmazonBackupService implements BackupService {

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private XmlMarshaller xmlMarshaller;
    @Autowired
    private Archiver archiver;

    private AmazonS3 client;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("d-MM-yyyy");

    @Value("${amazon.access-key}")
    private String accessKey;
    @Value("${amazon.secret-key}")
    private String secretKey;
    @Value("${amazon.bucket-name}")
    private String bucketName;

    @PostConstruct
    private void connect() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        client =
                AmazonS3ClientBuilder
                        .standard()
                        .withCredentials(new AWSStaticCredentialsProvider(credentials))
                        .withRegion(Regions.EU_CENTRAL_1)
                        .build();
        if (!client.doesBucketExist(bucketName)) {
            log.warn("There is no available buckets with name {}. Creating new bucket.", bucketName);
            client.createBucket(bucketName);
        }
        log.info("Successfully connected to Amazon S3 service");
    }

    @Override
    @Scheduled(cron = "0 0 12 * * *") //Cron-expression, 12 часов дня каждый день
    public void backup() {
        List<Film> films = filmRepository.findAll();
        List<File> marshalledFilms = xmlMarshaller.marshal(films);
        File zipBackupFile = archiver.archiveAll(marshalledFilms);
        String fileName = zipBackupFile.getName() + "-" + dateFormat.format(new Date()) + ".zip";

        client.putObject(bucketName, fileName, zipBackupFile);
        log.info("Backup {} saved. Total objects backuped: {}", fileName, films.size());
    }

    @Override
    public List<String> getAvailableBackups() {
        List<String> availableBackups = new ArrayList<>();
        ObjectListing bucketObjects = client.listObjects(bucketName);
        for (S3ObjectSummary object : bucketObjects.getObjectSummaries()) {
            availableBackups.add(object.getKey());
        }
        return availableBackups;
    }

    @Override
    public void restore(String backupName) {
        File zippedBackupFile = load(backupName);
        List<File> unzippedFiles = archiver.unarchive(zippedBackupFile);
        List<Film> films = xmlMarshaller.unmarshall(unzippedFiles);
        List<User> users = userRepository.findAll();
        users.forEach(u -> u.getReviews().clear());
        List<Film> currentFilms = filmRepository.findAll();
        currentFilms.forEach(f -> f.getReviews().clear());
        filmRepository.deleteAll();
        filmRepository.saveAll(films);
        log.info("Restored database from file {}. Films restored: {}", backupName, films.size());
    }

    private File load(String fileName) {
        S3Object object = client.getObject(bucketName, fileName);
        File file = new File(object.getKey());
        try (S3ObjectInputStream inputStream = object.getObjectContent();
             FileOutputStream outputStream = new FileOutputStream(file);) {
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Error while loading file {} from amazon service: {}", fileName, e.getMessage());
        }
        return file;
    }
}
