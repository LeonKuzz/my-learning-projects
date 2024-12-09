package org.example.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.CategoryDto;
import org.example.entities.Category;
import org.example.repositories.CategoryRepository;
import org.example.repositories.NewsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryCRUDService implements CRUDService<CategoryDto>{

    private final CategoryRepository categoryRepository;
    private final NewsRepository newsRepository;
    @Override
    public ResponseEntity create(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);
        categoryRepository.save(category);
        return new ResponseEntity<>(mapToDTO(category), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        try {
            CategoryDto categoryDto = mapToDTO(categoryRepository.findById(id.intValue()).orElseThrow());
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        } catch (Exception e) {
            String text = "{\n" +
                    "    \"message\": \"Категория с ID " + id + " не найдена.\"\n" +
                    "}";
            return new ResponseEntity<>(text, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(categoryRepository.findAll()
                .stream()
                .map(CategoryCRUDService :: mapToDTO)
                .toList(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllNewsByCategory(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> update(CategoryDto categoryDto) {
        try {
            Category category = categoryRepository.findById(categoryDto.getId().intValue()).orElseThrow();
            category.setTitle(categoryDto.getTitle());
            categoryRepository.save(category);
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        } catch (Exception e) {
            String text = "{\n" +
                    "    \"message\": \"Категория с ID " + categoryDto.getId() + " не найдена.\"\n" +
                    "}";
            return new ResponseEntity<>(text, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        try {
            categoryRepository.findById(id.intValue()).orElseThrow();
            categoryRepository.deleteById(id.intValue());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            String text = "{\n" +
                    "    \"message\": \"Категория с ID " + id + " не найдена.\"\n" +
                    "}";
            return new ResponseEntity<>(text, HttpStatus.NOT_FOUND);
        }
    }

    public static Category mapToEntity (CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setTitle(categoryDto.getTitle());
//        category.setNewsList(categoryDto.getNewsDTOList()
//                .stream()
//                .map(NewsCRUDService :: mapToEntity)
//                .toList());
        return category;
    }

    public static CategoryDto mapToDTO (Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setTitle(category.getTitle());
//        categoryDto.setNewsDTOList(category.getNewsList()
//                .stream()
//                .map(NewsCRUDService :: mapToDto)
//                .toList());
        return categoryDto;
    }
}
