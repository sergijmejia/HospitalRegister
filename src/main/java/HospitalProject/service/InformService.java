package HospitalProject.service;

import HospitalProject.DTO.InformDTO;
import HospitalProject.model.GroupIncidence;
import HospitalProject.repository.GroupIncidenceRepository;
import com.github.javafaker.Faker;
import HospitalProject.model.Inform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import HospitalProject.repository.InformRepository;

import java.util.*;

@Service
public class InformService {

    //static ArrayList<Inform> informs = new ArrayList<>();
    @Autowired
    InformRepository informRepository;

    @Autowired
    GroupIncidenceRepository groupIncidenceRepository;

    private Inform convertToInform(InformDTO informDTO){
        Inform newInform = new Inform();
        if(informDTO.getId().isEmpty()){
            String uniqueID;
            uniqueID = UUID.randomUUID().toString();
            newInform.setId(uniqueID);
        } else{
            newInform.setId(informDTO.getId());
        }
        newInform.setInform(informDTO.getInform());
        newInform.setGravityOfIncidence(informDTO.getGravityOfIncidence());
        newInform.setComplete(informDTO.isComplete());
        if(newInform.isComplete()){
            Date date = new Date();
            newInform.setDateCompleted(date.toString());
        } else{
            newInform.setDateCompleted(null);
        }
        return newInform;
    }

    public List<Inform> createNewFakeInforms(GroupIncidence groupIncidence) {

        Faker faker = new Faker(new Locale("en-GB"));
        Date date = new Date();
        List<Inform> informs = new ArrayList<>();

        String uniqueID;

        for(int i=0; i<10; i++){
            uniqueID = UUID.randomUUID().toString();
            boolean completed = faker.bool().bool();
            String dateCompleted = date.toString();
            if (!completed){
                dateCompleted = null;
            }
            Inform inform = new Inform (uniqueID,
                    faker.chuckNorris().fact(),
                    faker.number().numberBetween(1, 7),
                    completed, dateCompleted, groupIncidence);
            informs.add(inform);


        }

        return informs;
    }

    public Inform newFakeInform(InformDTO newInformDTO) {
        Optional<GroupIncidence> groupNewInform = groupIncidenceRepository.findGroupIncidenceById(newInformDTO.getIdGroupIncidence());
        Inform inform = convertToInform(newInformDTO);
        groupNewInform.ifPresentOrElse(
                inform::setGroupIncidence,
                () -> inform.setGroupIncidence(null)
        );
        return inform;
    }

    /*
    public List<Inform> populate(){
        List<Inform> informs = createNewFakeInforms();

        for (int i = 0; i <10 ; i++ ){
            informRepository.save(informs.get(i));
            informs.add(informs.get(i));
        }

        return informs;
    }
    */

}
