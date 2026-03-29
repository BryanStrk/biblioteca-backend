package com.biblioteca.bibliotecabackend.service;

import com.biblioteca.bibliotecabackend.dto.BookDTO;
import com.biblioteca.bibliotecabackend.exception.BadRequestException;
import com.biblioteca.bibliotecabackend.exception.ResourceNotFoundException;
import com.biblioteca.bibliotecabackend.model.Book;
import com.biblioteca.bibliotecabackend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<BookDTO> getAvailableBooks() {
        return bookRepository.findByAvailableTrue()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with id: " + id));
        return toDTO(book);
    }

    public BookDTO createBook(BookDTO dto) {
        if (bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new BadRequestException(
                    "ISBN already registered: " + dto.getIsbn());
        }
        Book book = Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .isbn(dto.getIsbn())
                .available(true)
                .build();
        return toDTO(bookRepository.save(book));
    }

    public BookDTO updateBook(Long id, BookDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with id: " + id));

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());

        return toDTO(bookRepository.save(book));
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    // Mapper
    private BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .available(book.isAvailable())
                .build();
    }
}