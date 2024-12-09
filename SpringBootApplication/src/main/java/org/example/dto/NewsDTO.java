package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Data
public class NewsDTO {
    private Long id;
    private String title;
    private String text;
    private Instant date;
    private String category;

    @Override
    public String toString(){
        return  "{" + System.lineSeparator() +
                "    \"id\": " + getId() + "," + System.lineSeparator() +
                "    \"title\": \"" + getTitle() + "\"," + System.lineSeparator() +
                "    \"text\": \"" + getText() + "\"," + System.lineSeparator() +
                "    \"date\": \"" + getDate() + "\"," + System.lineSeparator() +
                System.lineSeparator() +
                "    \"category\": \"" + category + "\"" + System.lineSeparator() +
                "}";
    }
}
