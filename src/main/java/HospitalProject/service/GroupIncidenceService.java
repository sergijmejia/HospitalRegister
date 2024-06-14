package HospitalProject.service;

import HospitalProject.DTO.GroupIncidenceDTO;
import HospitalProject.DTO.InformDTO;
import HospitalProject.model.GroupIncidence;
import HospitalProject.model.HealthStaff;
import HospitalProject.model.Inform;
import HospitalProject.repository.GroupIncidenceRepository;
import HospitalProject.repository.HealthStaffRepository;
import HospitalProject.repository.InformRepository;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    InformRepository informRepository;

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


            uniqueID = UUID.randomUUID().toString();
            GroupIncidence groupIncidence = new GroupIncidence();
            groupIncidence.setId(uniqueID);

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

            //System.out.println("Crea el fakeHealthStaffList");

            //System.out.println(groupIncidence.getGroupIncidenceList());

            //Random random = new Random();
            //int randomStaff = random.nextInt(groupIncidence.getGroupIncidenceList().size());
            //HealthStaff groupDirector = groupIncidence.getGroupIncidenceList().get(randomStaff);
            //groupIncidence.setGroupDirector(groupDirector);


            groupIncidence.setIncidenceType(groupIncidenceNames[i]);

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

            //assert false;

            groupIncidentsList.add(groupIncidence);

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

        for (GroupIncidence gi : groupIncidents) {
            List<HealthStaff> healthStaffList = healthStaffService.fakeHealthStaffList(); // Generar una nueva lista aquí
            gi.setGroupIncidenceList(healthStaffList); // Asegurarse de establecer la nueva lista en el grupo de incidencias
            gi.setGroupDirector();
            groupIncidenceRepository.save(gi);
        }
    }


    public GroupIncidence convertToGroupIncidence(GroupIncidenceDTO groupIncidenceDTO) {
        GroupIncidence newGroupIncidence = new GroupIncidence();

        if(groupIncidenceDTO.getId().isEmpty()){
            String uniqueID;
            uniqueID = UUID.randomUUID().toString();
            newGroupIncidence.setId(uniqueID);
        } else{
            newGroupIncidence.setId(groupIncidenceDTO.getId());
        }

        newGroupIncidence.setIncidenceType(groupIncidenceDTO.getIncidenceType());

        List<HealthStaff> listHealthStaff = new ArrayList<>();
        for(String idHealthStaff : groupIncidenceDTO.getIdsMedicalStaff()){
            Optional<HealthStaff> optionalHealthStaff = healthStaffRepository.findById(idHealthStaff);
            if(optionalHealthStaff.isPresent()){
                listHealthStaff.add(optionalHealthStaff.get());
            } else{
                return new GroupIncidence("Medical staff with id " + idHealthStaff + " not found");
            }
        }

        newGroupIncidence.setGroupIncidenceList(listHealthStaff);

        if(!groupIncidenceDTO.getIdDirector().isEmpty()){
            //HealthStaff[] directorGroupStaff = new HealthStaff[1];
            Optional<HealthStaff> optionalDirector = healthStaffRepository.findById(groupIncidenceDTO.getIdDirector());
            if(optionalDirector.isPresent()){
                HealthStaff groupDirector = optionalDirector.get();
                boolean directorInHealthStaff = false;

                for(HealthStaff healthStaff : listHealthStaff){
                    if(groupDirector.equals(healthStaff)){
                        newGroupIncidence.setGroupDirector(groupDirector.getName());
                        directorInHealthStaff = true;
                        break;
                    }
                }
                if(!directorInHealthStaff){
                    return new GroupIncidence("Group Director with id " +
                            groupIncidenceDTO.getIdDirector() + " not found in medical staff incidence group");
                }
            } else{
                return new GroupIncidence("Group Director with id " +
                        groupIncidenceDTO.getIdDirector() + " not found in medical staff DB");
            }
        }

        return newGroupIncidence;

    }

    private GroupIncidence createNewGroupIncidence(GroupIncidenceDTO groupIncidenceDTO) {

        GroupIncidence newGroupIncidence = new GroupIncidence();
        GroupIncidenceDTO groupIncidenceDTOToCreate = new GroupIncidenceDTO();

        String uniqueID;
        uniqueID = UUID.randomUUID().toString();
        groupIncidenceDTOToCreate.setId(uniqueID);

        newGroupIncidence = convertToGroupIncidence(groupIncidenceDTOToCreate);

        return newGroupIncidence;
    }

}
