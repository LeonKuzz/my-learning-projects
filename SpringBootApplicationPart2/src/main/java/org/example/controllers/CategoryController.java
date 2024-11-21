package org.example.controllers;

import lombok.RequiredArgsConstructor;
import net.minidev.json.parser.ParseException;
import org.example.dto.CategoryDto;
import org.example.services.CRUDService;
import org.example.services.CategoryCRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("api/category")
@RequiredArgsConstructor
public class CategoryController {

        private final CRUDService<CategoryDto> crudService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById (@PathVariable long id) {
        try {
            return crudService.getById(id);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCategory () {
        try {
            return crudService.getAll();
        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCategory (@RequestBody CategoryDto categoryDto) {
        return crudService.create(categoryDto);
    }

    @PutMapping
    public ResponseEntity<?> updateCategory (@RequestBody CategoryDto categoryDto) {
        try {
            return crudService.update(categoryDto);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById (@PathVariable long id) {

        try {
            return crudService.delete(id);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
