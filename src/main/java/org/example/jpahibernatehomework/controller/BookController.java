package org.example.jpahibernatehomework.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.jpahibernatehomework.model.Book;
import org.example.jpahibernatehomework.model.request.BookRequest;
import org.example.jpahibernatehomework.model.response.APIResponse;
import org.example.jpahibernatehomework.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/book")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @PostMapping("add-book")
    @Operation(summary = "Add new book")
    public ResponseEntity<?> addNewBook(@RequestBody BookRequest bookRequest){
        Book book = bookRepository.addNewBook(bookRequest);
        return new ResponseEntity<>(new APIResponse<>("New book has been added successfully",
                HttpStatus.CREATED,book,201, LocalDateTime.now()
                ),HttpStatus.CREATED);
    }
    @GetMapping("get-all-book")
    @Operation(summary = "Get all book")
    public ResponseEntity<?> getAllBook(){
        List<Book> books = bookRepository.getAllBook();
        return new ResponseEntity<>(new APIResponse<>("Get all books successfully",
                HttpStatus.OK,books,200, LocalDateTime.now()
        ),HttpStatus.OK);
    }
    @GetMapping("get-book-id/{bookId}")
    @Operation(summary = "Get book by ID")
    public ResponseEntity<?> getBookById(@PathVariable UUID bookId){
        Book book = bookRepository.getBookById(bookId);
        return new ResponseEntity<>(new APIResponse<>("Get book by ID successfully",
                HttpStatus.OK,book,200, LocalDateTime.now()
        ),HttpStatus.OK);
    }
    @GetMapping("get-book-title/{title}")
    @Operation(summary = "Get book by Title")
    public ResponseEntity<?> getBookByTitle(@PathVariable String title){
        List<Book> books = bookRepository.getBookByTitle(title);
        return new ResponseEntity<>(new APIResponse<>("Get book by Title successfully",
                HttpStatus.OK,books,200, LocalDateTime.now()
        ),HttpStatus.OK);
    }
    @DeleteMapping("delete-book-id/{bookId}")
    @Operation(summary = "Delete book by ID")
    public ResponseEntity<?> deleteBookById(@PathVariable UUID bookId){
        bookRepository.deleteBookById(bookId);
        return new ResponseEntity<>(new APIResponse<>("Delete book by ID successfully",
                HttpStatus.OK,null,200, LocalDateTime.now()
        ),HttpStatus.OK);
    }
    @PutMapping("update-book-id/{bookId}")
    @Operation(summary = "Update book by ID")
    public ResponseEntity<?> updateBookById(@PathVariable UUID bookId,@RequestBody BookRequest bookRequest){
        Book book = bookRepository.updateBookById(bookId,bookRequest);
        return new ResponseEntity<>(new APIResponse<>("Update book by ID successfully",
                HttpStatus.OK,book,200, LocalDateTime.now()
        ),HttpStatus.OK);
    }
}
