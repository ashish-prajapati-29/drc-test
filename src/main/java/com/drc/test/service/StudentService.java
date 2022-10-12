package com.drc.test.service;

import com.drc.test.entity.Student;
import com.drc.test.exception.GlobalError;
import com.drc.test.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    /**
     * Save student
     *
     * @param student the entity.
     */
    @Transactional
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    /**
     * Find one student by student id.
     *
     * @param id of the student.
     */
    public Student findOne(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new GlobalError("Student with this id is not found", 404, HttpStatus.NO_CONTENT.value()));
    }

    /**
     * Find student by student roll no.
     *
     * @param rollNo the roll number of student.
     */
    public Student findByRollNo(String rollNo) {
        return studentRepository.findByRollNumber(rollNo).orElse(null);
    }

    /**
     * Update student.
     *
     * @param student the entity.
     */
    public Student update(Student student) {
        Student findStudent = findOne(student.getId());
        findStudent.setAge(student.getAge());
        findStudent.setDepartment(student.getDepartment());
        findStudent.setGender(student.getGender());
        findStudent.setName(student.getName());
        findStudent.setStandard(student.getStandard());
        findStudent.setRollNumber(student.getRollNumber());
        return studentRepository.save(findStudent);
    }

    /**
     * Get all student.
     *
     * @param pageable the pageable class object.
     */
    public PageImpl<Student> getAll(Pageable pageable) {
        Page<Student> students = studentRepository.findAll(pageable);
        return new PageImpl<>(new ArrayList<>(students.getContent()), pageable, students.getTotalElements());
    }
}
