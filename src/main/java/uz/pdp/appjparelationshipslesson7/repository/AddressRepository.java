package uz.pdp.appjparelationshipslesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appjparelationshipslesson7.entity.Address;
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
