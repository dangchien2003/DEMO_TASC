package org.example.orm.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.example.orm.entity.Course;
import org.example.orm.entity.Student;
import org.example.orm.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student save() {
        Student student = Student.builder()
                .name("chien")
                .id(1)
                .course(Course.builder()
                        .id(1)
                        .name("khoá 1")
                        .build())
                .build();
        return studentRepository.save(student);
    }

    @Transactional
    public Student get() {
        Student its = studentRepository.findById(1).orElse(null);
        Course course = its.getCourse();
        return its;
    }
}
