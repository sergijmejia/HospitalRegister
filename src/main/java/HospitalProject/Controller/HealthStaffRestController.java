package HospitalProject.Controller;


import HospitalProject.model.HealthStaff;
import HospitalProject.model.Inform;
import HospitalProject.repository.HealthStaffRepository;
import HospitalProject.service.HealthStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/health_staff")
public class HealthStaffRestController {

    @Autowired
    HealthStaffService healthStaffService;

    @Autowired
    HealthStaffRepository healthStaffRepository;

    //CRUD:read
    @RequestMapping("/staff")
    public Iterable<HealthStaff> getAllHealthStaff(){

        return healthStaffRepository.findAll();
    }

    @RequestMapping("/healthStaff/{id}")
    public ResponseEntity<HealthStaff> getSpecificHealthStaff(@PathVariable String id){

        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "readHealthStaffInfo");
        headers.add("version", "api 1.0");

        Optional<HealthStaff> healthStaffFound = healthStaffRepository.findHealthStaffById(id);

        //If id is not found we will return 404 (not found) status code
        if(healthStaffFound.isPresent()){
            HealthStaff healthStaff = healthStaffFound.get();
            return ResponseEntity.accepted().headers(headers).body(healthStaff);
        } else{
            return ResponseEntity.notFound().headers(headers).build();
        }
    }
}
