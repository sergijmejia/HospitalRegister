package HospitalProject.Controller;

import HospitalProject.DTO.GroupIncidenceDTO;
import HospitalProject.model.GroupIncidence;
import HospitalProject.model.Inform;
import HospitalProject.repository.GroupIncidenceRepository;
import HospitalProject.service.GroupIncidenceDTOService;
import HospitalProject.service.GroupIncidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/group_incidence/")
public class GroupIncidenceRestController {

    @Autowired
    GroupIncidenceRepository groupIncidenceRepository;

    @Autowired
    GroupIncidenceDTOService groupIncidenceDTOService;

    @Autowired
    GroupIncidenceService groupIncidenceService;

    //CRUD: create
    @PostMapping(path="create", consumes = "application/JSON")
    public ResponseEntity<GroupIncidenceDTO> createGroupIncidence(@RequestBody GroupIncidenceDTO groupIncidenceDTO){

        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "createGroupIncidence");
        headers.add("version", "api 1.0");

        GroupIncidenceDTO completeGroupIncidenceDTO = groupIncidenceDTOService.completeGroupIncidenceDTO(groupIncidenceDTO);

        GroupIncidence newGroupIncidence = groupIncidenceService.convertToGroupIncidence(completeGroupIncidenceDTO);

        if(newGroupIncidence.getId().equals("error")){
            headers.add("message", newGroupIncidence.getIncidenceType());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).build();
        }

        if(!newGroupIncidence.getIncidenceType().isEmpty()){
            groupIncidenceRepository.save(newGroupIncidence);
        }

        Optional<GroupIncidence> groupIncidenceFound = groupIncidenceRepository.findGroupIncidenceById(newGroupIncidence.getId());

        if(groupIncidenceFound.isPresent()){
            GroupIncidenceDTO newGroupIncidenceDTO = groupIncidenceDTOService.convertToGroupIncidendeDTO(newGroupIncidence);
            return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(newGroupIncidenceDTO);
        } else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).build();
        }
    }

    //CRUD: read
    //Read all group incidents
    @RequestMapping("/list_groups")
    public Iterable<GroupIncidenceDTO> getAllGroups (){

        return groupIncidenceDTOService.getAllGroupIncidents();
    }

    //Read one group incidence by id
    @RequestMapping("/read_group/{id}")
    public ResponseEntity<GroupIncidenceDTO> readGroup(@PathVariable String id){

        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "readGroupIncidence");
        headers.add("version", "api 1.0");

        Optional<GroupIncidence> groupFound = groupIncidenceRepository.findGroupIncidenceById(id);

        //If id is not found we will return 404 (not found) status code
        if(groupFound.isPresent()){
            GroupIncidence groupIncidence = groupFound.get();
            GroupIncidenceDTO groupIncidenceDTO = groupIncidenceDTOService.convertToGroupIncidendeDTO(groupIncidence);
            return ResponseEntity.accepted().headers(headers).body(groupIncidenceDTO);
        } else{
            return ResponseEntity.notFound().headers(headers).build();
        }
    }


    //CRUD: update
    @PatchMapping("/update/{id}")
    public ResponseEntity<GroupIncidenceDTO> updateGroupIncidence (@PathVariable String id, @RequestBody GroupIncidenceDTO groupIncidenceDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "updateGroupIncidence");
        headers.add("version", "api 1.0");

        GroupIncidenceDTO completeGroupIncidenceDTO = groupIncidenceDTOService.completeGroupIncidenceDTO(groupIncidenceDTO);

        GroupIncidence groupIncidence = groupIncidenceService.convertToGroupIncidence(completeGroupIncidenceDTO);

        if(groupIncidence.getId().equals("error")){
            headers.add("message", groupIncidence.getIncidenceType());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).build();
        }

        Optional<GroupIncidence> groupIncidenceFound = groupIncidenceRepository.findById(id);

        //If id is not found we will return 404 (not found) status code
        if(groupIncidenceFound.isPresent()){
            GroupIncidence groupToUpdate = groupIncidenceFound.get();

            if(groupIncidence.getGroupIncidenceList() != groupToUpdate.getGroupIncidenceList()){
                groupToUpdate.setGroupIncidenceList(groupIncidence.getGroupIncidenceList());
            }

            if(!Objects.equals(groupIncidence.getGroupDirector(), groupToUpdate.getGroupDirector())){
                groupToUpdate.setGroupDirector(groupIncidence.getGroupDirector());
            }

            GroupIncidence groupUpdated = groupIncidenceRepository.save(groupToUpdate);

            GroupIncidenceDTO newGroupIncidenceDTO = groupIncidenceDTOService.convertToGroupIncidendeDTO(groupUpdated);

            return ResponseEntity.accepted().headers(headers).body(newGroupIncidenceDTO);

        } else{
            return ResponseEntity.notFound().headers(headers).build();
        }



    }

    //CRUD: delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GroupIncidenceDTO> deleteGroupIncidence(@PathVariable String id){

        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "deleteGroupIncidence");
        headers.add("version", "api 1.0");

        Optional<GroupIncidence> groupIncidenceFound = groupIncidenceRepository.findGroupIncidenceById(id);

        //If id is not found we will return 404 (not found) status code
        if(groupIncidenceFound.isPresent()){
            GroupIncidence groupIncidenceToDelete = groupIncidenceFound.get();
            groupIncidenceRepository.deleteById(id);
            GroupIncidenceDTO groupIncidenceDTO = groupIncidenceDTOService.convertToGroupIncidendeDTO(groupIncidenceToDelete);
            return ResponseEntity.accepted().headers(headers).body(groupIncidenceDTO);
        } else{
            return ResponseEntity.notFound().headers(headers).build();
        }

    }

}
