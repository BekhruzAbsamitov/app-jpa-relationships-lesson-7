package uz.pdp.appjparelationshipslesson7.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationshipslesson7.entity.Student;
import uz.pdp.appjparelationshipslesson7.repository.StudentRepository;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    StudentRepository studentRepository;


    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/forMinistry")
    public Page<Student> getStudentListForMinistry(@RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAll(pageable);
    }

    @GetMapping("/forUniversity/{id}")
    public Page<Student> getStudentListForUniversity(@PathVariable Integer id,
                                                     @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page, 10);

        return studentRepository.findAllByGroup_Faculty_UniversityId(id, pageable);
    }
}
