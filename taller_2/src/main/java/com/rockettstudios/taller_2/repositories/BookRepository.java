package com.rockettstudios.taller_2.repositories;


import com.rockettstudios.taller_2.domains.entities.Book;
import com.rockettstudios.taller_2.domains.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID>{
    Optional<Book> findByIsbn(String isbn);
    void deleteByIsbn(String isbn);
	List<Book> findAllByCategory(Category category);
}
