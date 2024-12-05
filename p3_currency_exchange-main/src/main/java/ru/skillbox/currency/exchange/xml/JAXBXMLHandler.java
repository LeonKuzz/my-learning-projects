package ru.skillbox.currency.exchange.xml;

import ru.skillbox.currency.exchange.xmlobj.ValCurs;
import ru.skillbox.currency.exchange.xmlobj.Valute;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


    public class JAXBXMLHandler {

        // Export
//        public static void marshal(List<Valute> valCurs, File selectedFile)
//                throws IOException, JAXBException {
//            JAXBContext context;
//            BufferedWriter writer = null;
//            writer = new BufferedWriter(new FileWriter(selectedFile));
//            context = JAXBContext.newInstance(ValCurs.class);
//            Marshaller m = context.createMarshaller();
//            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            m.marshal(new ValCurs(valCurs), writer);
//            writer.close();
//        }

        // Import
        public static List<Valute> unmarshal(File importFile) throws JAXBException {
            ValCurs curs = new ValCurs();

            JAXBContext context = JAXBContext.newInstance(ValCurs.class);
            Unmarshaller um = context.createUnmarshaller();
            curs = (ValCurs) um.unmarshal(importFile);

            return curs.getCurrencies();
        }
}
