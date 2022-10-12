package com.drc.test.service;

import com.drc.test.entity.Teacher;
import com.drc.test.exception.GlobalError;
import com.drc.test.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher checkEmailExistOrNot(String email) {
        return teacherRepository.findByEmail(email).orElse(null);
    }
}
