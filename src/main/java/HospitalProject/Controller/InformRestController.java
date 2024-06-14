package HospitalProject.Controller;

import HospitalProject.DTO.InformDTO;
import HospitalProject.repository.InformRepository;
import HospitalProject.service.InformDTOService;
import HospitalProject.service.InformService;
import HospitalProject.model.Inform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/inform/")
public class InformRestController {

    @Autowired
    InformService informService;

    @Autowired
    InformRepository informRepository;

    @Autowired
    InformDTOService informDTOService;

    //CRUD: create
    @PostMapping(path="create", consumes = "application/JSON")
    public ResponseEntity<InformDTO> createInform(@RequestBody InformDTO informDTO){

        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "createInform");
        headers.add("version", "api 1.0");

        Inform newInform = informService.newFakeInform(informDTO);
        if(!newInform.getInform().isEmpty()){
            informRepository.save(newInform);
        }

        Optional<Inform> informFound = informRepository.findInformById(newInform.getId());

        if(informFound.isPresent()){
            InformDTO newInformDTO = informDTOService.convertToInformDTO(newInform);
            return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(newInformDTO);
        } else {
            //return null;
            //que otro fallo se podria retornar?
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).build();
        }

    }

    //CRUD: read
    //Read all informs
    @RequestMapping("/list_informs")
    public Iterable<InformDTO> getAllInforms(){

        return informDTOService.getAllInforms();
    }

    //Read one inform by id
    @RequestMapping("/read_inform/{id}")
    public ResponseEntity<InformDTO> readInform(@PathVariable String id){

        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "readInform");
        headers.add("version", "api 1.0");

        Optional<Inform> informFound = informRepository.findInformById(id);

        //If id is not found we will return 404 (not found) status code
        if(informFound.isPresent()){
            Inform inform = informFound.get();
            InformDTO informDTO = informDTOService.convertToInformDTO(inform);
            return ResponseEntity.accepted().headers(headers).body(informDTO);
        } else{
            return ResponseEntity.notFound().headers(headers).build();
        }

    }

    //CRUD: update
    @PatchMapping("/update/{id}")
    public ResponseEntity<InformDTO> updateInform (@PathVariable String id, @RequestBody InformDTO informDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "updateInform");
        headers.add("version", "api 1.0");

        InformDTO completeInformDTO = informDTOService.completeInformDTO(informDTO);

        Inform inform = informService.newFakeInform(completeInformDTO);

        Optional<Inform> informFound = informRepository.findById(id);

        //If id is not found we will return 404 (not found) status code
        if (informFound.isPresent()) {
            Inform informToUpdate = informFound.get();
            boolean completeModification = false;
            if(!inform.getInform().isEmpty()){
                informToUpdate.setInform(inform.getInform());
            }
            if((inform.getGravityOfIncidence() != informToUpdate.getGravityOfIncidence())
                         && (inform.getGravityOfIncidence() != 0)){
                informToUpdate.setGravityOfIncidence(inform.getGravityOfIncidence());
            }
            if((inform.isComplete() != informToUpdate.isComplete()) && (!informToUpdate.isComplete())){
                informToUpdate.setComplete(true);
                completeModification = true;
            }
            if(completeModification){
                Date date = new Date();
                informToUpdate.setDateCompleted(date.toString());
            }
            Inform informUpdated = informRepository.save(informToUpdate);

            InformDTO newinformDTO = informDTOService.convertToInformDTO(informUpdated);
            return ResponseEntity.accepted().headers(headers).body(newinformDTO);

            //return informUpdated;
            //return ResponseEntity.accepted().headers(headers).body(informUpdated);
        } else{
            //return null;
            System.out.println("No se encuentra");
            return ResponseEntity.notFound().headers(headers).build();
        }

    }

    //CRUD: delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<InformDTO> deleteInform(@PathVariable String id){

        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "deleteInform");
        headers.add("version", "api 1.0");

        Optional<Inform> informFound = informRepository.findInformById(id);

        //If id is not found we will return 404 (not found) status code
        if(informFound.isPresent()){
            Inform informToDelete = informFound.get();
            informRepository.deleteById(id);
            InformDTO informDTO = informDTOService.convertToInformDTO(informToDelete);
            return ResponseEntity.accepted().headers(headers).body(informDTO);
        } else{
            return ResponseEntity.notFound().headers(headers).build();
        }

    }
}
