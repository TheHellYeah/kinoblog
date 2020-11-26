package com.example.demo.utils;


import com.example.demo.model.Film;
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
    public File marshal(Film film) {
        File file = new File(film.getName() + FILE_EXTENSION);
        try {
            JAXBContext context = JAXBContext.newInstance(Film.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(film, file);

        } catch (JAXBException e) {
            log.warn("Error while marshalling object {}, error: {}", film, e.getMessage());
        }
        return file;
    }

    @Override
    public List<File> marshal(List<Film> films) {
        List<File> marshalledFiles = new ArrayList<>();
        films.forEach(film -> marshalledFiles.add(this.marshal(film)));
        return marshalledFiles;
    }

    @Override
    public Film unmarshal(File marshalled) {
        Film film = null;
        try{
            JAXBContext context = JAXBContext.newInstance(Film.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            film = (Film) unmarshaller.unmarshal(marshalled);

        } catch (JAXBException e) {
            log.warn("Error while unmarshalling file {}, error: {}", marshalled.getName(), e.getMessage());
        }
        return film;
    }

    @Override
    public List<Film> unmarshall(List<File> marshalled) {
        List<Film> films = new ArrayList<>();
        marshalled.forEach(file -> films.add(this.unmarshal(file)));
        return films;
    }
}
