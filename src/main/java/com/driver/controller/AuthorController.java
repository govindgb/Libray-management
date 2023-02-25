package com.driver.controller;


import com.driver.models.Author;
import com.driver.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//Add required annotations
@RestController
public class AuthorController {
    @Autowired
    AuthorService authorService;

    //Write createAuthor API with required annotations
//    @PostMapping("/author")
//    public ResponseEntity<String> createAuthor(@RequestBody()AuthorDto authorDto) {
//        authorService.create(authorDto);
//        return new ResponseEntity("An Author Added", HttpStatus.ACCEPTED);
//    }

    @PostMapping("/author")
    public ResponseEntity<String> createAuthor(@RequestBody() Author author) {
        authorService.create(author);
        return new ResponseEntity("An Author Added", HttpStatus.ACCEPTED);
    }
}
