package HospitalProject.service;

import HospitalProject.model.GroupIncidence;
import HospitalProject.model.Inform;
import HospitalProject.repository.GroupIncidenceRepository;
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
            groupIncidence.setGroupDirector(faker.book().author());
            String[] healthStaffNames = new String[3];
            for(int j=0; j<3; j++){
                healthStaffNames[j] = faker.esports().player();
            }
            groupIncidence.setGroupIncidence(healthStaffNames);
            groupIncidence.setIncidenceType(groupIncidenceNames[i]);

            informs = informService.createNewFakeInforms(groupIncidence);


            groupIncidenceRepository.save(
                    new GroupIncidence (uniqueID,
                            faker.book().author(),
                            healthStaffNames,
                            groupIncidenceNames[i],
                            informs)
            );

        }

    }

}
