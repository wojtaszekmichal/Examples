package hello.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import hello.domain.Book;

@Service
public class BookService {

    private Map<Integer, Book> books;
    private int nextId;

    public BookService() {
        books = new HashMap<>();
        nextId = 0;
        initBooks();
    }

    private void initBooks() {
        books.put(++nextId, new Book(1, "W pustyni i w puszczy"));
        books.put(++nextId, new Book(2, "Pan Tadeusz"));
        books.put(++nextId, new Book(3, "Pan Tadeusz"));
        books.put(++nextId, new Book(4, "Alicja w krainie czarów"));
        books.put(++nextId, new Book(5, "Tomek Sawer"));
    }

    public Collection<Book> getAllBooks() {
        return books.values();
    }

    public void createBook(Book book) {
        int createdId = this.nextId;
        book.setId(++createdId);
        books.put(createdId, book);
    }

    public void removeBook(Integer id) {
        books.remove(id);
    }
}
