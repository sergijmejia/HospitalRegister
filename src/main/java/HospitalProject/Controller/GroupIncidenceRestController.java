package HospitalProject.Controller;

import HospitalProject.model.GroupIncidence;
import HospitalProject.model.Inform;
import HospitalProject.repository.GroupIncidenceRepository;
import HospitalProject.service.GroupIncidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/group_incidence/")
public class GroupIncidenceRestController {

    @Autowired
    GroupIncidenceRepository groupIncidenceRepository;

    //CRUD: create
    @PostMapping(path="create", consumes = "application/JSON")
    public GroupIncidence createGroupIncidence(@RequestBody GroupIncidence groupIncidence){
        return groupIncidenceRepository.save(groupIncidence);
    }

    //CRUD: read
    @RequestMapping("/groups")
    public Iterable<GroupIncidence> getAllUsers (){

        return groupIncidenceRepository.findAll();
    }

    //CRUD: update
    @PatchMapping("/update/{id}")
    public ResponseEntity<GroupIncidence> updateGroupIncidence (@PathVariable String id, @RequestBody GroupIncidence groupIncidence) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "updateGroupIncidence");
        headers.add("version", "api 1.0");

        Optional<GroupIncidence> groupIncidenceFound = groupIncidenceRepository.findById(id);

        //If id is not found we will return 404 (not found) status code
        if (groupIncidenceFound.isPresent()) {
            System.out.println("Si lo encuentra");
            GroupIncidence groupIncidenceToUpdate = groupIncidenceFound.get();

            //return groupIncidenceUpdated;

            //Solo permite actualizar Director y GroupIncidence
            if (groupIncidence.getGroupDirector() != null) {
                groupIncidenceToUpdate.setGroupDirector(groupIncidence.getGroupDirector());
            }
            if (groupIncidence.getGroupIncidenceList() != null) {
                groupIncidenceToUpdate.setGroupIncidenceList(groupIncidence.getGroupIncidenceList());
            }

            GroupIncidence groupIncidenceUpdated = groupIncidenceRepository.save(groupIncidenceToUpdate);
            return ResponseEntity.accepted().headers(headers).body(groupIncidenceUpdated);
        } else{
            System.out.println("No lo encuentra");
            //return null;
            return ResponseEntity.notFound().headers(headers).build();
        }

    }

    //CRUD: delete
    @DeleteMapping("/delete")
    public ResponseEntity<GroupIncidence> deleteGroupIncidence(@RequestParam String id){

        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "deleteGroupIncidence");
        headers.add("version", "api 1.0");

        Optional<GroupIncidence> groupIncidenceFound = groupIncidenceRepository.findGroupIncidenceById(id);

        //If id is not found we will return 404 (not found) status code
        if(groupIncidenceFound.isPresent()){
            groupIncidenceRepository.deleteById(id);
            return ResponseEntity.accepted().headers(headers).body(groupIncidenceFound.get());
        } else{
            return ResponseEntity.notFound().headers(headers).build();
        }

    }

}
