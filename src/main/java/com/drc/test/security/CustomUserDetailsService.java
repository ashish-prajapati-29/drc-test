package com.drc.test.security;

import com.drc.test.entity.Teacher;
import com.drc.test.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Teacher> optionalTeacher = teacherRepository.findByEmail(username);
        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            return new User(teacher.getEmail(), teacher.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found!!");
        }
    }
}
