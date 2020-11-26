package com.example.demo.utils;

import com.example.demo.model.Film;
import com.example.demo.model.User;

import java.io.File;
import java.util.List;

public interface XmlMarshaller {

    File marshal(Film film);
    List<File> marshal(List<Film> films);
    Film unmarshal(File marshalled);
    List<Film> unmarshall(List<File> marshalled);
}
