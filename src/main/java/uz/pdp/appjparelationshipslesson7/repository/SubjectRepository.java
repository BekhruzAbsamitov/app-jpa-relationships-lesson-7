package uz.pdp.appjparelationshipslesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appjparelationshipslesson7.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    boolean existsByName(String name);
}
