package org.example.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.example.dto.NewsDTO;
import org.example.entities.Category;
import org.example.entities.News;
import org.example.repositories.CategoryRepository;
import org.example.repositories.NewsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsCRUDService implements CRUDService<NewsDTO> {
//    private TreeMap<Long, NewsDTO> storage = new TreeMap<>();
    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;

//    private static JSONParser parser = new JSONParser();

//    private static ObjectMapper mapper = new ObjectMapper();

//    Supplier<CustomExce> exceptionSupplier = () -> new Except

    @Override
    public ResponseEntity<?> create(NewsDTO newsDTO) {
        newsDTO.setDate(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        News news = mapToEntity(newsDTO);
        String categoryTitle = newsDTO.getCategory();
        Category category = categoryRepository.findByTitle(categoryTitle);
        news.setCategory(category);
        newsRepository.save(news);
        return new ResponseEntity<>(mapToDto(news), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        try {
            NewsDTO newsDTO = mapToDto(newsRepository.findById(id.intValue()).orElseThrow(() -> new Exception()));
            return new ResponseEntity<>(newsDTO.toString(), HttpStatus.OK);
        } catch (Exception e) {
            String text = "{\n" +
                    "    \"message\": \"Новость с ID " + id + " не найдена.\"\n" +
                            "}";
            return new ResponseEntity<>(text, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getAll(){
        System.out.println("get all news");
        try {
//            List<NewsDTO> dtoList = newsRepository.findAll()
//                    .stream()
//                    .map(NewsCRUDService::mapToDto)
//                    .toList();
//            List<String> array = new ArrayList<>();
//            for (NewsDTO dto : dtoList) {
//                array.add(dto.toString());
//            }
            return new ResponseEntity<>(//array
                newsRepository.findAll()
                .stream()
                .map(NewsCRUDService :: mapToDto)
                .toList()
//                        .stream()
//                        .map(NewsCRUDService :: toString)
//                        .toList()
                    , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<> (HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getAllNewsByCategory(Long id) {
        Category category = categoryRepository.findById(id.intValue()).orElseThrow();
        List<News> newsList = newsRepository.findByCategory(category);
        List<NewsDTO> newsDTOList = newsList
                .stream()
                .map(NewsCRUDService :: mapToDto)
                .toList();
        return new ResponseEntity<>(newsDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> update(NewsDTO newsDTO) {
        System.out.println("update news by id " + newsDTO.getId());
        try {
            newsRepository.findById(newsDTO.getId().intValue()).orElseThrow();
            News news = mapToEntity(newsDTO);
            String categoryTitle = newsDTO.getCategory();
            Category category = categoryRepository.findByTitle(categoryTitle);
            news.setCategory(category);
            news.setDate(Instant.now().truncatedTo(ChronoUnit.SECONDS));
            //должно быть время обновления или должно оставаться время создания?
            newsRepository.save(news);
            return new ResponseEntity<>(mapToDto(news), HttpStatus.OK);
        } catch (Exception e) {
            String text = "{\n" +
                    "    \"message\": \"Новость с ID " + newsDTO.getId() + " не найдена.\"\n" +
                    "}";
            return new ResponseEntity<>(text, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        try {
            newsRepository.findById(id.intValue()).orElseThrow();
            newsRepository.deleteById(id.intValue());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            String text = "{\n" +
                    "    \"message\": \"Новость с ID " + id + " не найдена.\"\n" +
                    "}";
            return new ResponseEntity<>(text, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getNewsByCategory(Long id) {
        Category category = categoryRepository.findById(id.intValue()).orElseThrow();
        List<News> newsList = newsRepository.findByCategory(category);
        List<NewsDTO> newsDTOList = newsList
                .stream()
                .map(NewsCRUDService :: mapToDto)
                .toList();
        return new ResponseEntity<>(newsDTOList, HttpStatus.OK);
    }

    public static NewsDTO mapToDto (News news) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(news.getId());
        newsDTO.setTitle(news.getTitle());
        newsDTO.setText(news.getText());
        newsDTO.setCategory(news.getCategory().getTitle());
        newsDTO.setDate(news.getDate());
        return newsDTO;
    }

    public static News mapToEntity (NewsDTO newsDTO) {
        News news = new News();
        news.setId(newsDTO.getId());
        news.setTitle(newsDTO.getTitle());
        news.setText(newsDTO.getText());
//        news.setCategory(categoryRepository);
        news.setDate(newsDTO.getDate());
        return news;
    }

//    public static JSONObject getNewsDtoInfo (NewsDTO newsDTO) throws ParseException {
//        String info = "{\n" +
//                "    \"id\": " + newsDTO.getId() + ",\n" +
//                "    \"title\": \"" + newsDTO.getTitle() + "\",\n" +
//                "    \"text\": \"" + newsDTO.getText() + "\",\n" +
//                "    \"date\": \"" + newsDTO.getDate() + "\",\n" +
//                "\n" +
//                "    \"category\": \"" + newsDTO.getCategory() + "\"\n" +
//                "}";
//        JSONObject object = (JSONObject) parser.parse(info);
//        return object;
//    }

}
