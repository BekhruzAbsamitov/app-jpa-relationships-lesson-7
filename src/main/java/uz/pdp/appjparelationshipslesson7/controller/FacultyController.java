package uz.pdp.appjparelationshipslesson7.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationshipslesson7.entity.Faculty;
import uz.pdp.appjparelationshipslesson7.entity.University;
import uz.pdp.appjparelationshipslesson7.payload.FacultyDto;
import uz.pdp.appjparelationshipslesson7.repository.FacultyRepository;
import uz.pdp.appjparelationshipslesson7.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/faculty")
public class FacultyController {
    FacultyRepository facultyRepository;
    UniversityRepository universityRepository;

    public FacultyController(FacultyRepository facultyRepository, UniversityRepository universityRepository) {
        this.facultyRepository = facultyRepository;
        this.universityRepository = universityRepository;
    }

    @DeleteMapping("/{id}")
    public String deleteFaculty(@PathVariable Integer id) {
        try {
            facultyRepository.deleteById(id);
        } catch (Exception e) {
            return "Error! Faculty not found";
        }
        return null;
    }

    @PutMapping("/{id}")
    public String editFaculty(@PathVariable Integer id, @RequestBody FacultyDto facultyDto) {
        final Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isPresent()) {
            final Faculty faculty = optionalFaculty.get();
            faculty.setName(facultyDto.getName());
            final Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversityId());
            if (!optionalUniversity.isPresent()) {
                return "University not found";
            }
            faculty.setUniversity(optionalUniversity.get());
            facultyRepository.save(faculty);
            return "Faculty edited";
        }
        return "Not found";
    }

    @PostMapping
    public String addFaculty(@RequestBody FacultyDto facultyDto) {

        final boolean b = facultyRepository.existsByNameAndUniversity_Id(facultyDto.getName(), facultyDto.getUniversityId());

        if (b) {
            return "Faculty already exists at university";
        }

        Faculty faculty = new Faculty();
        faculty.setName(facultyDto.getName());
        final Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversityId());

        if (!optionalUniversity.isPresent()) {
            return "University not found!";
        }
        faculty.setUniversity(optionalUniversity.get());

        facultyRepository.save(faculty);
        return "Faculty saved!";

    }

    @GetMapping(value = "/byUniversity/{id}")
    public List<Faculty> getFaculties(@PathVariable Integer id) {

        return facultyRepository.findAllByUniversityId(id);
    }

    @GetMapping
    public List<Faculty> getFaculties() {
        return facultyRepository.findAll();
    }

}
