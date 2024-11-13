package org.example;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class StationSerializer  extends StdSerializer<Station> {

    public StationSerializer (){
        this(null);
    }

    protected StationSerializer(Class<Station> t) {
        super(t);
    }

    @Override
    public void serialize(Station station, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        JsonGenerator jgen = jsonGenerator;
        jgen.writeStartObject();
        jgen.writeStringField("name", station.getName());
        jgen.writeStringField("line", StationIndex.number2line.get(station.getNumberLine()).getName());
        if (station.getDate() != null) {
            jgen.writeStringField("date", station.getDate());
        }
        if (station.getDepth() != null && !station.getDepth().equals("?")) {
            jgen.writeStringField("depth", station.getDepth());
        }
        jgen.writeBooleanField("hasConnection", StationIndex.connections.containsKey(station));
        jgen.writeEndObject();
    }
}
