package HospitalProject.repository;

import HospitalProject.model.HealthStaff;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface HealthStaffRepository extends CrudRepository<HealthStaff, String> {

    Optional<HealthStaff> findHealthStaffById(String id);

    Optional<HealthStaff> findHealthStaffByName(String name);

}
