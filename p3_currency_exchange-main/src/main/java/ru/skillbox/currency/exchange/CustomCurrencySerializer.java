package ru.skillbox.currency.exchange;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.entity.Currency;

import java.io.IOException;

public class CustomCurrencySerializer extends StdSerializer<CurrencyDto> {

    public CustomCurrencySerializer() {
        this(null);
    }

    public CustomCurrencySerializer(Class<CurrencyDto> t) {
        super(t);
    }

    @Override
    public void serialize(
            CurrencyDto currency, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", currency.getName());
        jsonGenerator.writeNumberField("value", currency.getValue());
        jsonGenerator.writeEndObject();
    }
}
