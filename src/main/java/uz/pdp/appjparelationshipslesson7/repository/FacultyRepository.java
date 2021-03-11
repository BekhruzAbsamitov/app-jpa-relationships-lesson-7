package uz.pdp.appjparelationshipslesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appjparelationshipslesson7.entity.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    boolean existsByNameAndUniversity_Id(String name, Integer university_id);

    List<Faculty> findAllByUniversityId(Integer id);
}
