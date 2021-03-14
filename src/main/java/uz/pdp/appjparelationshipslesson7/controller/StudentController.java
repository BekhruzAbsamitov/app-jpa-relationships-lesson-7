package uz.pdp.appjparelationshipslesson7.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationshipslesson7.entity.Address;
import uz.pdp.appjparelationshipslesson7.entity.Group;
import uz.pdp.appjparelationshipslesson7.entity.Student;
import uz.pdp.appjparelationshipslesson7.payload.StudentDto;
import uz.pdp.appjparelationshipslesson7.repository.AddressRepository;
import uz.pdp.appjparelationshipslesson7.repository.GroupRepository;
import uz.pdp.appjparelationshipslesson7.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    StudentRepository studentRepository;
    AddressRepository addressRepository;
    GroupRepository groupRepository;

    public StudentController(StudentRepository studentRepository,
                             GroupRepository groupRepository,
                             AddressRepository addressRepository) {
        this.studentRepository = studentRepository;
        this.addressRepository = addressRepository;
        this.groupRepository = groupRepository;
    }

    @PostMapping
    public String addStudent(@RequestBody StudentDto studentDto) {
        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());

        final Optional<Address> optionalAddress = addressRepository.findById(studentDto.getAddressId());
        student.setAddress(optionalAddress.get());

        final Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        student.setGroup(optionalGroup.get());

        student.setSubjects(student.getSubjects());

        studentRepository.save(student);
        return "Saved";
    }

    @GetMapping("/byGroupId/{id}")
    public List<Student> getStudentListByGroupId(@PathVariable Integer id) {
        return studentRepository.findAllByGroup_Id(id);
    }

    @GetMapping("/byFacultyId/{id}")
    public List<Student> getStudentListByFacultyId(@PathVariable Integer id) {
        return studentRepository.findAllByGroup_Faculty_Id(id);
    }

    @PutMapping("/{id}")
    public String editStudent(@RequestBody StudentDto studentDto, @PathVariable Integer id) {
        final Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            final Student student = optionalStudent.get();
            student.setFirstName(studentDto.getFirstName());
            student.setLastName(studentDto.getLastName());

            final Optional<Address> optionalAddress = addressRepository.findById(studentDto.getAddressId());
            if (!optionalStudent.isPresent()) {
                return "Address not found";
            }
            student.setAddress(optionalAddress.get());

            final Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
            student.setGroup(optionalGroup.get());

            student.setSubjects(student.getSubjects());

            studentRepository.save(student);
        }
        return "Edited";
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        try {
            studentRepository.deleteById(id);
        } catch (Exception e) {
            return "Error";
        }
        return "Deleted";

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
