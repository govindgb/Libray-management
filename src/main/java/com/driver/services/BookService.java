package com.driver.services;

import com.driver.models.Author;
import com.driver.models.Book;
import com.driver.models.CardStatus;
import com.driver.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {


    @Autowired
    BookRepository bookRepository2;


    public void createBook(Book book){ //book is a child of author and card
        // while creating a new book I have to declare who is the author
        //that meant i have to set the books in the author entity
      //  book.
        Author author = book.getAuthor();
        if(author != null) {
            if(author.getBooksWritten() == null) {
                author.setBooksWritten(new ArrayList<>());
            }
            author.getBooksWritten().add(book);
        }

        bookRepository2.save(book);
    }

    public List<Book> getBooks(String genre, boolean available, String author){

        List<Book> books = null; //find the elements of the list by yourself

        books.addAll(bookRepository2.findBooksByAuthor(author,available));
        books.addAll(bookRepository2.findBooksByGenre(genre,available));
        books.addAll(bookRepository2.findBooksByGenreAuthor(genre, author, available));
        books.addAll(bookRepository2.findByAvailability(available));


        return books;
//
//        List<Book> books = new ArrayList<>();
//        if(available){
//
//            if(genre != null && author != null){
//                books.addAll(bookRepository2.findBooksByGenreAuthor(genre,author,true));
//            }
//            else if(genre != null && author == null){
//                books.addAll(bookRepository2.findBooksByGenre(genre,true));
//            }
//            else if(genre == null && author != null){
//                books.addAll(bookRepository2.findBooksByAuthor(author,true));
//            }
//            else {
//                books.addAll(bookRepository2.findByAvailability(true));
//            }
//        }
//        else{
//            if(genre != null && author != null){
//                books.addAll(bookRepository2.findBooksByGenreAuthor(genre,author,false));
//            }
//            else if(genre != null && author == null){
//                books.addAll(bookRepository2.findBooksByGenre(genre,false));
//            }
//            else if(genre == null && author != null){
//                books.addAll(bookRepository2.findBooksByAuthor(author,false));
//            }
//            else{
//                books.addAll(bookRepository2.findByAvailability(false));
//            }
//        }
//       return  books;


    }

}
