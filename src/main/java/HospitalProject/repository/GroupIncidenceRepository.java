package HospitalProject.repository;

import HospitalProject.model.GroupIncidence;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

//import java.util.Optional;

public interface GroupIncidenceRepository extends CrudRepository<GroupIncidence, String> {
    Optional<GroupIncidence> findGroupIncidenceById(String id);

}