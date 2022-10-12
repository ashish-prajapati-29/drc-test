package com.drc.test.controller;

import com.drc.test.Util.Validation;
import com.drc.test.entity.Student;
import com.drc.test.exception.GlobalError;
import com.drc.test.service.StudentService;
import com.drc.test.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/student")
    ResponseEntity<?> createStudent(@RequestBody Student student) {
        if (!Validation.ageValidation(student.getAge())) {
            throw new GlobalError("Age is not valid !!", 400, HttpStatus.BAD_REQUEST.value());
        }
        Student existingStudent = studentService.findByRollNo(student.getRollNumber());
        if (existingStudent != null) {
            throw new GlobalError("Student already exist!!", 302, HttpStatus.FOUND.value());
        }
        return new ResponseEntity<>(studentService.save(student), HttpStatus.OK);
    }

    @GetMapping("/student/{id}")
    ResponseEntity<Student> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.findOne(id), HttpStatus.FOUND);
    }
}
