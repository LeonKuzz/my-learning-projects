package ru.skillbox.currency.exchange.xmlobj;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement (name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Getter
@Setter
public class ValCurs {

    @XmlAttribute (name = "Date")
    private String date;

    @XmlAttribute (name = "name")
    private String name;

    @XmlElement (name = "Valute", type = Valute.class)
    private List<Valute> currencies = new ArrayList<>();

    public List<Valute> getCurrencies() {
        return currencies;
    }
    public ValCurs(List<Valute> currencies) {
        this.currencies = currencies;
    }

    public void setCurrencies(List<Valute> currencies) {
        this.currencies = currencies;
    }
}
