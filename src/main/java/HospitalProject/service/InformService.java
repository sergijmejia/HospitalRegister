package HospitalProject.service;

import HospitalProject.model.GroupIncidence;
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

    public Iterable<Inform> getAllInforms(){

        return informRepository.findAll();

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
