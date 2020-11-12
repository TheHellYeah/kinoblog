package com.example.demo.utils;

import com.example.demo.model.User;

import java.io.File;
import java.util.Collection;
import java.util.List;

public interface XmlMarshaller {

    File marshal(User user);
    List<File> marshal(List<User> users);
    User unmarshal(File marshalled);
    List<User> unmarshall(List<File> marshalled);
}
