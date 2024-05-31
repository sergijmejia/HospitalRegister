package HospitalProject.service;

import HospitalProject.model.GroupIncidence;
import HospitalProject.model.HealthStaff;
import HospitalProject.model.Inform;
import HospitalProject.repository.GroupIncidenceRepository;
import HospitalProject.repository.HealthStaffRepository;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
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

    @Transactional
    public List<GroupIncidence> createGroups() {

        String[] groupIncidenceNames = {"Fugas", "Caidas",
                "Auto-Heteroagresiones", "Errores de medicación",
                "Infecciones", "Heridas cronicas", "Desvinculación " +
                "terapeutica", "Conductas adictivas"};

        Faker faker = new Faker(new Locale("en-GB"));

        List<GroupIncidence> groupIncidentsList = new ArrayList<>();

        List<HealthStaff> healthStaffList = new ArrayList<>();

        List<Inform> informs;

        String uniqueID;

        for(int i=0; i<8; i++){

            System.out.println("Entra en el bucle");


            uniqueID = UUID.randomUUID().toString();
            GroupIncidence groupIncidence = new GroupIncidence();
            groupIncidence.setId(uniqueID);

            System.out.println("Asigna la ID");

            //groupIncidence.setGroupDirector(faker.book().author());
            //groupIncidence.setGroupDirector(healthStaffService.getRandomEntity());
            /*
            String[] healthStaffNames = new String[3];
            for(int j=0; j<3; j++){
                healthStaffNames[j] = faker.esports().player();
            }
            */
            //groupIncidence.setGroupIncidence(healthStaffNames);





            groupIncidence.setGroupIncidenceList(healthStaffList);

            System.out.println("Setea el groupIncidence - vacio");

            //System.out.println("Crea el fakeHealthStaffList");

            //System.out.println(groupIncidence.getGroupIncidenceList());

            //Random random = new Random();
            //int randomStaff = random.nextInt(groupIncidence.getGroupIncidenceList().size());
            //HealthStaff groupDirector = groupIncidence.getGroupIncidenceList().get(randomStaff);
            //groupIncidence.setGroupDirector(groupDirector);


            groupIncidence.setIncidenceType(groupIncidenceNames[i]);

            System.out.println("Setea el incidenceType");

            informs = informService.createNewFakeInforms(groupIncidence);




            /*
            groupIncidenceRepository.save(
                    new GroupIncidence (groupIncidence.getId(),
                            groupIncidence.getGroupDirector(),
                            groupIncidence.getGroupIncidenceList(),
                            groupIncidence.getIncidenceType(),
                            informs)
            );

            System.out.println("crea el grupo " + i);

            */

            groupIncidence.setInforms(informs);

            System.out.println("Setea los informes");

            //assert false;

            groupIncidentsList.add(groupIncidence);

            System.out.println("Adjunta el nuevo groupIncidence a groupIncidentsSet");

        }

        return groupIncidentsList;
    }

    /*
    @Transactional
    public void populate(){

        List<GroupIncidence> groupIncidents = createGroups();

        for (GroupIncidence gi: groupIncidents){
            List<HealthStaff> healthStaffList = healthStaffService.fakeHealthStaffList();
            System.out.println("crea la lista fake");
            for(HealthStaff hs: healthStaffList) {
                gi.addHealthStaff(hs);
            }
            groupIncidenceRepository.save(gi);
        }



    }

     */

    public void populate() {

        List<GroupIncidence> groupIncidents = createGroups();

        System.out.println("Crea groupIncidents");

        for (GroupIncidence gi : groupIncidents) {
            List<HealthStaff> healthStaffList = healthStaffService.fakeHealthStaffList(); // Generar una nueva lista aquí
            gi.setGroupIncidenceList(healthStaffList); // Asegurarse de establecer la nueva lista en el grupo de incidencias
            gi.setGroupDirector();
            System.out.println("Setea el Group Director");
            groupIncidenceRepository.save(gi);
        }
    }

}
