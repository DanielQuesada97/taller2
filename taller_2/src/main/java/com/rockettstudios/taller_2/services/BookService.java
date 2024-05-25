package com.rockettstudios.taller_2.services;



import com.rockettstudios.taller_2.domains.dtos.SaveBookDTO;
import com.rockettstudios.taller_2.domains.entities.Book;
import com.rockettstudios.taller_2.domains.entities.Category;

import java.util.List;

public interface BookService {
    void save(SaveBookDTO info, Category category);
    List<Book> findAll();
    Book findByIsbn(String isbn);
    void deleteByIsbn(String isbn);
	List<Book> findByCategory(Category category);
}
