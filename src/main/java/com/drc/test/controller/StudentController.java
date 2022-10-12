package com.drc.test.controller;

import com.drc.test.Util.Validation;
import com.drc.test.entity.Student;
import com.drc.test.exception.GlobalError;
import com.drc.test.service.StudentService;
import com.drc.test.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    /**
     * /POST: Save student
     *
     * @param student the entity.
     */
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

    /**
     * /GET: get student
     *
     * @param id the entity.
     */
    @GetMapping("/student/{id}")
    ResponseEntity<Student> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.findOne(id), HttpStatus.FOUND);
    }

    /**
     * /PUT: update the student
     *
     * @param student the entity.
     */
    @PutMapping("/student")
    ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.update(student), HttpStatus.OK);
    }

    /**
     * /PUT: get all the student
     *
     * @param pageable the pageable object.
     */
    @GetMapping("/student/all")
    PageImpl<Student> getAllStudents(Pageable pageable) {
        return studentService.getAll(pageable);
    }
}
