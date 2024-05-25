package com.rockettstudios.taller_2.services.Impls;


import com.rockettstudios.taller_2.domains.dtos.SaveBookDTO;
import com.rockettstudios.taller_2.domains.entities.Book;
import com.rockettstudios.taller_2.domains.entities.Category;
import com.rockettstudios.taller_2.repositories.BookRepository;
import com.rockettstudios.taller_2.services.BookService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //Ahora ya lo puede ocupas el entityManager para poder inyectarlo
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void save(SaveBookDTO info, Category category) {
        Book book = this.findByIsbn(info.getISBN());

        if (book == null) {
            book = new Book();
        }

        book.setIsbn(info.getISBN());
        book.setTitle(info.getTitle());
        book.setCategory(category);
        bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).orElse(null);
    }

    @Override
    public void deleteByIsbn(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

	@Override
	public List<Book> findByCategory(Category category) {
		
		return bookRepository.findAllByCategory(category);
	
	}
}
