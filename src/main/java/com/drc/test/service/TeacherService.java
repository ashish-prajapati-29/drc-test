package com.drc.test.service;

import com.drc.test.entity.Teacher;
import com.drc.test.exception.GlobalError;
import com.drc.test.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Save Teacher
     *
     * @param teacher the entity.
     */
    public Teacher save(Teacher teacher) {
        teacher.setPassword(bCryptPasswordEncoder.encode(teacher.getPassword()));
        return teacherRepository.save(teacher);
    }

    /**
     * Checked with this email Teacher exist or not
     *
     * @param email the string email.
     */
    public Teacher checkEmailExistOrNot(String email) {
        return teacherRepository.findByEmail(email).orElse(null);
    }
}
