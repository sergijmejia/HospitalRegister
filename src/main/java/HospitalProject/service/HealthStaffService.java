package HospitalProject.service;

import HospitalProject.model.HealthStaff;
import HospitalProject.repository.HealthStaffRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HealthStaffService {

    @Autowired
    HealthStaffRepository healthStaffRepository;

    /*
    public Iterable<HealthStaff> getAllHealthStaff(){

        return healthStaffRepository.findAll();
    }
    */

    public void createNewFakeHealthStaffList(){

        Faker faker = new Faker(new Locale("en-GB"));

        for(int i=0; i<100; i++){
            HealthStaff healthStaff = new HealthStaff();
            healthStaff.setId(UUID.randomUUID().toString());
            healthStaff.setName(faker.name().firstName() + " " + faker.name().lastName());
            healthStaff.setMail(healthStaff.getName().replace(" ", ".")
                    .replace("'", "").toLowerCase() + "@fakehealthstaff.com");

            healthStaffRepository.save(healthStaff);
        }

    }

    //PROVISIONAL
    public List<HealthStaff> fakeHealthStaffList(){
        Faker faker = new Faker(new Locale("en-GB"));
        List<HealthStaff> healthStaffList = new ArrayList<>();
        HealthStaff newHealthStaff;

        for(int i=0; i<4; i++){
            boolean repeatedHealthStaff = true;
            while (repeatedHealthStaff){
                newHealthStaff = getRandomEntity();
                if(!healthStaffList.contains(newHealthStaff)){
                    break;
                }
            }
            healthStaffList.add(new HealthStaff());
        }

        return healthStaffList;
    }

    /*
    public HealthStaff getEntityById(String id) {

        return healthStaffRepository.findById(id).orElse(null);
    }
    */

    public HealthStaff getRandomEntity() {

        Iterable<HealthStaff> allStaff = healthStaffRepository.findAll();
        List<HealthStaff> allStaffList= new ArrayList<>();
        allStaff.forEach(allStaffList::add);
        Random random = new Random();
        int randomStaff = random.nextInt(allStaffList.size());

        return allStaffList.get(randomStaff);
    }

}
