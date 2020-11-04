package com.example.demo.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

public class XMLMarshaller {

    public static File marshall(Object o) {
        File file = null;
        try {
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller marshaller = context.createMarshaller();
            file = new File("backup.xml");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(o, file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return file;
    }

    //TODO
    public static List<Object> unmarshall() {
        return null;
    }
}
