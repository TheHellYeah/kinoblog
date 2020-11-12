package com.example.demo.utils;


import com.example.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class JaxbMarshaller implements XmlMarshaller {

    private final static String FILE_EXTENSION = ".xml";

    @Override
    public File marshal(User user) {
        File file = new File(user.getUsername() + FILE_EXTENSION);
        try {
            JAXBContext context = JAXBContext.newInstance(User.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(user, file);

        } catch (JAXBException e) {
            log.warn("Error while marshalling object {}, error: {}", user, e.getMessage());
        }
        return file;
    }

    @Override
    public List<File> marshal(List<User> users) {
        List<File> marshalledFiles = new ArrayList<>();
        users.forEach(user -> marshalledFiles.add(this.marshal(user)));
        return marshalledFiles;
    }

    @Override
    public User unmarshal(File marshalled) {
        User user = null;
        try{
            JAXBContext context = JAXBContext.newInstance(User.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            user = (User) unmarshaller.unmarshal(marshalled);

        } catch (JAXBException e) {
            log.warn("Error while unmarshalling file {}, error: {}", marshalled.getName(), e.getMessage());
        }
        return user;
    }

    @Override
    public List<User> unmarshall(List<File> marshalled) {
        List<User> users = new ArrayList<>();
        marshalled.forEach(file -> users.add(this.unmarshal(file)));
        return users;
    }
}
