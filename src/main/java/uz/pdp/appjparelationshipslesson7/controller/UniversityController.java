package uz.pdp.appjparelationshipslesson7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationshipslesson7.entity.Address;
import uz.pdp.appjparelationshipslesson7.entity.University;
import uz.pdp.appjparelationshipslesson7.payload.UniversityDto;
import uz.pdp.appjparelationshipslesson7.repository.AddressRepository;
import uz.pdp.appjparelationshipslesson7.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class UniversityController {

    UniversityRepository universityRepository;
    AddressRepository addressRepository;

    @Autowired
    public UniversityController(UniversityRepository universityRepository, AddressRepository addressRepository) {
        this.universityRepository = universityRepository;
        this.addressRepository = addressRepository;
    }

    @DeleteMapping("/university/{id}")
    public String deleteUniversity(@PathVariable Integer id) {
        universityRepository.deleteById(id);
        return "Deleted";
    }
    @PutMapping("/university/{id}")
    public String editUniversity(@PathVariable Integer id, @RequestBody UniversityDto universityDto) {
        final Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isPresent()) {
            final University university = optionalUniversity.get();
            university.setName(universityDto.getName());
            final Address address = university.getAddress();
            address.setCity(universityDto.getCity());
            address.setDistrict(universityDto.getDistrict());
            address.setStreet(universityDto.getStreet());

            university.setAddress(address);
            universityRepository.save(university);
        }
        return "Edited";
    }

    @PostMapping("/university")
    public String addUniversity(@RequestBody UniversityDto universityDto) {

        Address address = new Address();
        address.setCity(universityDto.getCity());
        address.setDistrict(universityDto.getDistrict());
        address.setStreet(universityDto.getStreet());
        final Address savedAddress = addressRepository.save(address);

        University university = new University();
        university.setName(universityDto.getName());
        university.setAddress(savedAddress);
        universityRepository.save(university);
        return "University added";
    }

    @GetMapping("/university")
    public List<University> getUniversities() {
        return universityRepository.findAll();
    }

}
