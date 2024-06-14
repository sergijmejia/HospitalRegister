package HospitalProject.repository;

import HospitalProject.model.Inform;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InformRepository extends CrudRepository<Inform, String> {
    Optional<Inform> findInformById(String id);
    List<Inform> findInformListByGroupIncidenceId(String groupIncidenceId);

}
