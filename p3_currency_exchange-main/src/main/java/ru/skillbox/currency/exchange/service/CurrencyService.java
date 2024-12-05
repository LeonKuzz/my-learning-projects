package ru.skillbox.currency.exchange.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.mapper.CurrencyMapper;
import ru.skillbox.currency.exchange.repository.CurrencyRepository;
import ru.skillbox.currency.exchange.xml.JAXBXMLHandler;
import ru.skillbox.currency.exchange.xmlobj.Valute;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyMapper mapper;
    private final CurrencyRepository repository;

    @Value("${cbr.address}")
    public String address;

    public CurrencyDto getById(Long id) {
        log.info("CurrencyService method getById executed");
        Currency currency = repository.findById(id).orElseThrow(() -> new RuntimeException("Currency not found with id: " + id));
        return mapper.convertToDto(currency);
    }

    public Double convertValue(Long value, Long numCode) {
        log.info("CurrencyService method convertValue executed");
        Currency currency = repository.findByIsoNumCode(numCode);
        return value * currency.getValue();
    }

    public CurrencyDto create(CurrencyDto dto) {
        log.info("CurrencyService method create executed");
        return  mapper.convertToDto(repository.save(mapper.convertToEntity(dto)));
    }

    public CurrencyDto update(CurrencyDto dto) {
        log.info("CurrencyService method update executed");
        Currency currency = repository.findByIsoNumCode(dto.getIsoNumCode());
        Long id = currency.getId();
        currency = mapper.convertToEntity(dto);
        System.out.println(currency.getId());
        currency.setId(id);
        System.out.println(currency.getId());
        repository.save(currency);
        return dto;
    }

    public List<String>  getAll () {
        List<Currency> infoDtoList = repository.findAll();
        List<String> stringList = new ArrayList<>();

        for (Currency currency : infoDtoList) {
            stringList.add("{" +
                    "\"name\": " + "\"" + currency.getName() + "\"" + ", " +
                    "\"value\": " + currency.getValue() +
                    "}");
        }
        return stringList;
    }
    public static CurrencyDto mapXmlToDto(Valute currency) {
        CurrencyDto dto = new CurrencyDto();
        dto.setName(currency.getName());
        dto.setValue(Double.parseDouble(currency.getValue().replace(",", ".")));
        dto.setNominal(currency.getNominal());
        dto.setIsoCharCode(currency.getCharCode());
        dto.setIsoNumCode(currency.getNumCode());
        return dto;
    }
    public void updateDatabase(){
        getCBRCurrenciesList();
        File file = new File("src/main/resources/data/cbrlistvalcurs.xml");
        List<Valute> valutes = null;
        try {
            valutes = JAXBXMLHandler.unmarshal(file);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        for (Valute valute : valutes) {
            System.out.println(valute.toString());
            if (repository.findByIsoNumCode(valute.getNumCode()) == null) {
                System.out.println("create " + valute.getName());
                create(mapXmlToDto(valute));
            } else {
                System.out.println("update " + valute.getName());
                update(mapXmlToDto(valute));
            }
        }
    }
    public void getCBRCurrenciesList() {
        try {
            URL url = new URL(address);
            String fileName = "cbrlistvalcurs.xml";
            Path outputPath = Path.of("src/main/resources/data/" + fileName);

            try (InputStream in = url.openStream()) {
                Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
