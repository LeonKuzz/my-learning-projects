package org.example.controllers;

import net.minidev.json.parser.ParseException;
import org.example.dto.NewsDTO;
import org.example.services.CRUDService;
import org.example.services.NewsCRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/news")
public class NewsController  {

    private final CRUDService<NewsDTO> crudService;

    NewsController (NewsCRUDService newsService) {
        this.crudService = newsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNewsById (@PathVariable long id) {
       try {
           return crudService.getById(id);
       } catch (RuntimeException e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
       }
    }

    @GetMapping
    public ResponseEntity<?> getAllNews () {
        try {
            return crudService.getAll();
        } catch (ParseException e) {
           return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity <?> getCategory (@PathVariable long id) {
        return crudService.getAllNewsByCategory(id);
    }

    @PostMapping
    public ResponseEntity<?> createNews (@RequestBody NewsDTO newsDTO) {
        return crudService.create(newsDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateNews (@RequestBody NewsDTO newsDTO) {
        try {
            return crudService.update(newsDTO);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNewsById (@PathVariable long id) {

        try {
            return crudService.delete(id);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
