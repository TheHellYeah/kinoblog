package com.example.demo.utils;

import java.io.File;
import java.util.List;

public interface Archiver {

    File archiveAll(List<File> files);
    List<File> unarchive(File archivedFile);

}
