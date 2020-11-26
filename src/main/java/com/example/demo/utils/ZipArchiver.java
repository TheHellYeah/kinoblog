package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Component
@Slf4j
public class ZipArchiver implements Archiver {

    private static final String FILE_NAME = "backup";

    @Override
    public File archiveAll(List<File> files) {
        File zipBackupFile = new File(FILE_NAME);
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipBackupFile);
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {

            for (File file : files) {
                FileInputStream fileInputStream = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOutputStream.putNextEntry(zipEntry);
                IOUtils.copy(fileInputStream, zipOutputStream);
                fileInputStream.close();
            }
        } catch (IOException e) {
            log.warn("Error during creating zip archive: {}", e.getMessage());
        }
        return zipBackupFile;
    }

    @Override
    public List<File> unarchive(File archivedFile) {
        List<File> unzippedFiles = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(archivedFile);
             ZipInputStream zipInputStream = new ZipInputStream(fileInputStream)) {

            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                File file = new File(entry.getName());
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                IOUtils.copy(zipInputStream, fileOutputStream);
                unzippedFiles.add(file);
                fileOutputStream.close();
            }
        } catch (IOException e) {
            log.warn("Error during unzipping archive: {}", e.getMessage());
        }
        return unzippedFiles;
    }
}
