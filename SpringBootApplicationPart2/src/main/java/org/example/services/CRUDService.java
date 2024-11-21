package org.example.services;


import net.minidev.json.parser.ParseException;
import org.springframework.http.ResponseEntity;

public interface CRUDService<T> {
    ResponseEntity<?> create (T item);
    ResponseEntity<?> getById (Long id);
    ResponseEntity<?> getAll () throws ParseException;
    ResponseEntity<?> getAllNewsByCategory(Long id);
    ResponseEntity<?> update (T item);
    ResponseEntity<?> delete (Long id);
}
