package uz.pdp.appjparelationshipslesson7.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appjparelationshipslesson7.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Page<Student> findAllByGroup_Faculty_UniversityId(Integer group_faculty_university_id, Pageable pageable);
    List<Student> findAllByGroup_Id(Integer id);
    List<Student> findAllByGroup_Faculty_Id(Integer facultyId);
}
