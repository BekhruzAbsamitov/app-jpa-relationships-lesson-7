package uz.pdp.appjparelationshipslesson7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationshipslesson7.entity.Subject;
import uz.pdp.appjparelationshipslesson7.repository.SubjectRepository;

import java.util.List;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {
    SubjectRepository subjectRepository;

    @Autowired
    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @PostMapping
    public String addSubject(@RequestBody Subject subject) {
        final boolean exists = subjectRepository.existsByName(subject.getName());
        if (exists) {
            return "Subject already exists.";
        }
        subjectRepository.save(subject);
        return "Added";
    }

    @GetMapping
    public List<Subject> getSubjectList() {
        return subjectRepository.findAll();
    }
}
