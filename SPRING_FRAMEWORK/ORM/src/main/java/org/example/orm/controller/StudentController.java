package org.example.orm.controller;

import org.example.orm.entity.Student;
import org.example.orm.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("save")
    public Student save(){
        return studentService.save();
    }

    @GetMapping("get")
    public Student get(){
        return studentService.get();
    }

}
