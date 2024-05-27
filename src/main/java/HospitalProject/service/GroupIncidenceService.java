package HospitalProject.service;

import HospitalProject.model.GroupIncidence;
import HospitalProject.model.HealthStaff;
import HospitalProject.model.Inform;
import HospitalProject.repository.GroupIncidenceRepository;
import HospitalProject.repository.HealthStaffRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroupIncidenceService {

    @Autowired
    GroupIncidenceRepository groupIncidenceRepository;

    @Autowired
    InformService informService;

    @Autowired
    HealthStaffService healthStaffService;

    @Autowired
    HealthStaffRepository healthStaffRepository;

    public void populate() {

        String[] groupIncidenceNames = {"Fugas", "Caidas",
                "Auto-Heteroagresiones", "Errores de medicación",
                "Infecciones", "Heridas cronicas", "Desvinculación " +
                "terapeutica", "Conductas adictivas"};

        Faker faker = new Faker(new Locale("en-GB"));

        List<Inform> informs;

        String uniqueID;

        for(int i=0; i<8; i++){


            uniqueID = UUID.randomUUID().toString();
            GroupIncidence groupIncidence = new GroupIncidence();
            groupIncidence.setId(uniqueID);

            //groupIncidence.setGroupDirector(faker.book().author());
            groupIncidence.setGroupDirector(healthStaffService.getRandomEntity());
            String[] healthStaffNames = new String[3];
            for(int j=0; j<3; j++){
                healthStaffNames[j] = faker.esports().player();
            }
            groupIncidence.setGroupIncidence(healthStaffNames);

            /*
            groupIncidence.setGroupIncidenceList(healthStaffService.fakeHealthStaffList());
            Random random = new Random();
            int randomStaff = random.nextInt(groupIncidence.getGroupIncidenceList().size());
            HealthStaff groupDirector = groupIncidence.getGroupIncidenceList().get(randomStaff);
            groupIncidence.setGroupDirector(groupDirector);
            */

            groupIncidence.setIncidenceType(groupIncidenceNames[i]);
            informs = informService.createNewFakeInforms(groupIncidence);

            groupIncidenceRepository.save(
                    new GroupIncidence (groupIncidence.getId(),
                            groupIncidence.getGroupDirector(),
                            groupIncidence.getGroupIncidence(),
                            groupIncidence.getIncidenceType(),
                            informs)
            );
        }
    }

}
