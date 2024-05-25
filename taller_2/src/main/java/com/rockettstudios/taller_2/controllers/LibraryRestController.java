package com.rockettstudios.taller_2.controllers;


import com.rockettstudios.taller_2.domains.dtos.GeneralResponse;
import com.rockettstudios.taller_2.domains.dtos.SaveBookDTO;
import com.rockettstudios.taller_2.domains.entities.Book;
import com.rockettstudios.taller_2.domains.entities.Category;
import com.rockettstudios.taller_2.services.BookService;
import com.rockettstudios.taller_2.services.CategoryService;
import com.rockettstudios.taller_2.utils.ErrorsTool;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
public class LibraryRestController {

    private final CategoryService categoryService;
    private final ErrorsTool errorsTool;
    private final BookService bookService;

    public LibraryRestController(ErrorsTool errorsTool, BookService bookService, CategoryService categoryService) {
        this.errorsTool = errorsTool;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<GeneralResponse> findAll(){
        return GeneralResponse.getResponse(
                HttpStatus.OK,
                "List of books!",
                bookService.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<GeneralResponse> saveBook(@RequestBody @Valid SaveBookDTO info) {
        Category category = categoryService.findCategoryByCode(info.getCategory());

        if (category == null){
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Categoría no encontrada");
        }

        bookService.save(info, category);

        return GeneralResponse.getResponse(HttpStatus.OK, "Libro guardado");
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<GeneralResponse> deleteByIsbn(@PathVariable String isbn){
        Book book = bookService.findByIsbn(isbn);
        if (book == null){
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND);
        }

        bookService.deleteByIsbn(isbn);

        return GeneralResponse.getResponse(HttpStatus.OK);
    }

    @GetMapping("/all/category/{code}")
    public ResponseEntity<GeneralResponse> findBooksbyCategory(@PathVariable("code") String code){

        Category category = categoryService.findCategoryByCode(code);

        if (category == null){
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND);
        }
        
        List<Book> books = bookService.findByCategory(category);
        
        if (books.isEmpty()) {
        	return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "No existen libros con esta Categoria");
        }else{
        	return GeneralResponse.getResponse(HttpStatus.OK, books);
        }
        
    }
    
}
