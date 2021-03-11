package uz.pdp.appjparelationshipslesson7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationshipslesson7.entity.Faculty;
import uz.pdp.appjparelationshipslesson7.entity.Group;
import uz.pdp.appjparelationshipslesson7.payload.GroupDto;
import uz.pdp.appjparelationshipslesson7.repository.FacultyRepository;
import uz.pdp.appjparelationshipslesson7.repository.GroupRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/group")
public class GroupController {

    GroupRepository groupRepository;
    FacultyRepository facultyRepository;

    @Autowired
    public GroupController(GroupRepository groupRepository, FacultyRepository facultyRepository) {
        this.groupRepository = groupRepository;
        this.facultyRepository = facultyRepository;
    }

    @PostMapping
    public String addGroup(@RequestBody GroupDto groupDto) {
        Group group = new Group();
        group.setName(groupDto.getName());

        final Optional<Faculty> optionalFaculty = facultyRepository.findById(groupDto.getFacultyId());
        if (!optionalFaculty.isPresent()) {
            return "Faculty not found";
        }
        group.setFaculty(optionalFaculty.get());
        groupRepository.save(group);
        return "Saved!";
    }

    @GetMapping
    public List<Group> getGroupList() {
        return groupRepository.findAll();
    }

    @GetMapping("byUniversityId/{universityId}")
    public List<Group> getGroupsByUniversityId(@PathVariable Integer universityId) {
        return groupRepository.getGroupsByUniversityIdNative(universityId);
    }

}
