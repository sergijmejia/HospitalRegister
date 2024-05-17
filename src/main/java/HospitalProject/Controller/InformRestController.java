package HospitalProject.Controller;

import HospitalProject.repository.InformRepository;
import HospitalProject.service.InformService;
import HospitalProject.model.Inform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/inform/")
public class InformRestController {

    @Autowired
    InformService informService;

    @Autowired
    InformRepository informRepository;

    //CRUD: create
    @PostMapping(path="create", consumes = "application/JSON")
    public Inform createInform(@RequestBody Inform inform){
        return informRepository.save(inform);
    }

    //CRUD: read
    @RequestMapping("/informs")
    public Iterable<Inform> getAllInforms(){

        return informRepository.findAll();
    }

    //CRUD: update
    @PutMapping("/update/{id}")
    public ResponseEntity<Inform> updateInform (@PathVariable String id, @RequestBody Inform inform) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "updateInform");
        headers.add("version", "api 1.0");

        Optional<Inform> informFound = informRepository.findById(id);

        //If id is not found we will return 404 (not found) status code
        if (informFound.isPresent()) {
            Inform informToUpdate = informFound.get();
            Inform informUpdated = informRepository.save(informToUpdate);
            //return informUpdated;
            return ResponseEntity.accepted().headers(headers).body(informUpdated);
        } else{
            //return null;
            return ResponseEntity.notFound().headers(headers).build();
        }

    }

    //CRUD: delete
    @DeleteMapping("/delete")
    public ResponseEntity<Inform> deleteInform(@RequestParam String id){

        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "deleteInform");
        headers.add("version", "api 1.0");

        System.out.println("id: " + id);

        Optional<Inform> informFound = informRepository.findInformById(id);

        //If id is not found we will return 404 (not found) status code
        if(informFound.isPresent()){
            informRepository.deleteById(id);
            return ResponseEntity.accepted().headers(headers).body(informFound.get());
        } else{
            return ResponseEntity.notFound().headers(headers).build();
        }

    }
}
