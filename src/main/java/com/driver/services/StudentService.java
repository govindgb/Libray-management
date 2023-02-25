package com.driver.services;

import com.driver.models.Card;
import com.driver.models.CardStatus;
import com.driver.models.Student;
import com.driver.repositories.CardRepository;
import com.driver.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {


    @Autowired
    CardService cardService;

    @Autowired
    StudentRepository studentRepository4;

    @Autowired
    CardRepository cardRepository;

    public Student getDetailsByEmail(String email){
        Student student = studentRepository4.findByEmailId(email);

        return student;
    }

    public Student getDetailsById(int id){
        Student student = studentRepository4.findById(id).orElse(null);

        return student;
    }

    public void createStudent(Student student){
        // creating a new card for the student by default
//        Card card = new Card();
//        card.setStudent(student);
//        cardRepository.save(card);

        // I have written the login in card service for createandReturn
        cardService.createAndReturn(student);


        studentRepository4.save(student);
    }

    public void updateStudent(Student student){
            studentRepository4.updateStudentDetails(student);
    }

    public void deleteStudent(int id){
        //Delete student and deactivate corresponding card
        cardService.deactivateCard(id); // deactivating the card
        studentRepository4.deleteCustom(id);
    }
}
