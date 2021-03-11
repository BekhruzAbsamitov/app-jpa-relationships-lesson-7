package uz.pdp.appjparelationshipslesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appjparelationshipslesson7.entity.Group;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    List<Group> findAllByFaculty_University_Id(Integer faculty_university_id);

//    @Query("select gr from groups gr where gr.faculty.university.id =: universityId")
//    List<Group> getGroupsByUniversityId(Integer universityId);

    @Query(value = "select *\n" +
            "from groups g\n" +
            "         join faculty f on f.id = g.faculty_id\n" +
            "         join university u on f.university_id = u.id\n" +
            "where u.id = : universityId", nativeQuery = true)
    List<Group> getGroupsByUniversityIdNative(Integer universityId);

}

