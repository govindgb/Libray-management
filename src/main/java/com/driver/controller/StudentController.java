package com.driver.controller;

import com.driver.models.Student;
import com.driver.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Add required annotations
@RestController
public class StudentController {
    @Autowired
    StudentService studentService;

    //Add required annotations
    @GetMapping("/student/studentByEmail")
    public ResponseEntity getStudentByEmail(@RequestParam("email") String email){

        //return new ResponseEntity<>("Student details printed successfully ", HttpStatus.OK);
        return new ResponseEntity<>(studentService.getDetailsByEmail(email), HttpStatus.OK);
    }

    //Add required annotations
    @GetMapping("/student/studentById")
    public ResponseEntity getStudentById(@RequestParam("id") int id){

       // return new ResponseEntity<>("Student details printed successfully ", HttpStatus.OK);
        return new ResponseEntity(studentService.getDetailsById(id), HttpStatus.OK);
    }

    //Add required annotations
    @PostMapping("/student")
    public ResponseEntity createStudent(@RequestBody Student student){
        studentService.createStudent(student);

        return new ResponseEntity<>("the student is successfully added to the system", HttpStatus.CREATED);
    }

    //Add required annotations
    @PutMapping("/student")
    public ResponseEntity updateStudent(@RequestBody Student student){

                studentService.updateStudent(student);
        return new ResponseEntity<>("student is updated", HttpStatus.ACCEPTED);
    }

    //Add required annotations
    @DeleteMapping("student")
    public ResponseEntity deleteStudent(@RequestParam("id") int id){
        studentService.deleteStudent(id);

        return new ResponseEntity<>("student is deleted", HttpStatus.ACCEPTED);
    }

}
