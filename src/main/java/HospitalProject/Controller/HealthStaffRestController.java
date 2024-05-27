package HospitalProject.Controller;


import HospitalProject.model.HealthStaff;
import HospitalProject.repository.HealthStaffRepository;
import HospitalProject.service.HealthStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
