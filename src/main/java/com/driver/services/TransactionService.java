package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.BookRepository;
import com.driver.repositories.CardRepository;
import com.driver.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;


@Service
public class TransactionService {

    @Autowired
    BookRepository bookRepository5;

    @Autowired
    CardRepository cardRepository5;

    @Autowired
    TransactionRepository transactionRepository5;

    @Value("${books.max_allowed}")
    public int max_allowed_books;

    @Value("${books.max_allowed_days}")
    public int getMax_allowed_days;

    @Value("${books.fine.per_day}")
    public int fine_per_day;

    public String issueBook(int cardId, int bookId) throws Exception {
        //check whether bookId and cardId already exist
        Book book = bookRepository5.findById(bookId).orElse(null); // checking book is present or not using the bookid
        Card card = cardRepository5.findById(cardId).orElse(null); // checking card is present or not for the cardId
        //conditions required for successful transaction of issue book:
        //1. book is present and available
        // If it fails: throw new Exception("Book is either unavailable or not present");
        if(card==null || card.getCardStatus().equals(CardStatus.DEACTIVATED)) {
            throw new Exception("Card is invalid");
        }

        // If it fails: throw new Exception("Book limit has reached for this card");
         if( card.getBooks().size() >= max_allowed_books ) {
            throw new Exception("Book limit has reached for this card");
        }

        if(book==null || !book.isAvailable()) {
            throw new Exception("Book is either unavailable or not present");
        }
        //2. card is present and activated
        // If it fails: throw new Exception("Card is invalid");

//        if(card!=null) {
//            String status = String.valueOf(card.getCardStatus());
//            if(status.equals("DEACTIVATED")) {
//                throw new Exception("Card is invalid");
//            }
//        }
        //3. number of books issued against the card is strictly less than max_allowed_books

        book.setCard(card);
        card.getBooks().add(book);
        //If the transaction is successful, save the transaction to the list of transactions and return the id
        Transaction transaction = Transaction.builder()
                .fineAmount(0)
                .transactionStatus(TransactionStatus.SUCCESSFUL)
                .book(book)
                .card(card)
                .transactionId(UUID.randomUUID().toString())
                .isIssueOperation(true)
                .build();

        book.setAvailable(false);
        if(book.getTransactions() == null) {
            ArrayList<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);
            book.setTransactions(transactions);
        }
        else {
            book.getTransactions().add(transaction);
        }

       //transactionRepository5.save(transaction); // saving it in the database table;
        transactionRepository5.save(null);

//        book.setAvailable(false); // occupying the book
//        book.setCard(card);// setting the card id for the issued book
        bookRepository5.save(book);// saving the book details as occupiet or available=false

//        // a book has list of transaction, so i am adding the transaction to the list;
//        book.getTransactions().add(transaction);
//        card.getBooks().add(book);

        cardRepository5.save(card);

        //Note that the error message should match exactly in all cases

      return null;
       // return transaction.getTransactionId();//return transactionId instead
    }





    public Transaction returnBook(int cardId, int bookId) throws Exception{

        List<Transaction> transactions = transactionRepository5.find(cardId, bookId, TransactionStatus.SUCCESSFUL, true);
        Transaction transaction = transactions.get(transactions.size() - 1);

        //for the given transaction calculate the fine amount considering the book has been returned exactly when this function is called
      //  Date before = transaction.getTransactionDate();
       // Date before = (Date) transaction.getTransactionDate();
          Date present = transaction.getTransactionDate();


            Date curr = new Date();


            long timeDifference = curr.getTime() - present.getTime();

            long daysDifference = (timeDifference / (1000 * 60 * 60 * 24)) % 365 ;

            int fine =0;

            if(daysDifference > getMax_allowed_days) {

                int fineDays = (int) (getMax_allowed_days - daysDifference);
                fine = -1 * fineDays * fine_per_day;
            }

        //make the book available for other users
        Book b = transaction.getBook();
        b.setAvailable(true);

        //make a new transaction for return book which contains the fine amount as well
        Transaction newTransaction = Transaction.builder().transactionId(UUID.randomUUID().toString())
                .book(transaction.getBook())
                .card(transaction.getCard())
                .fineAmount(fine)
                .transactionStatus(TransactionStatus.SUCCESSFUL)
                .isIssueOperation(false)
                .build();

        transactionRepository5.save(newTransaction);


        b.setCard(null);
        bookRepository5.save(b);

        return null;
      //  return newTransaction; //return the transaction after updating all details
    }
}
