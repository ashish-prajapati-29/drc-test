package com.drc.test.service;

import com.drc.test.entity.Student;
import com.drc.test.exception.GlobalError;
import com.drc.test.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public Student findOne(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new GlobalError("Student with this id is not found", 404, HttpStatus.NO_CONTENT.value()));
    }

    public Student findByRollNo(String rollNo) {
        return studentRepository.findByRollNumber(rollNo).orElse(null);
    }
}
