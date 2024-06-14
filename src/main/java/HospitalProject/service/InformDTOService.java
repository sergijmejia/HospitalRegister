package HospitalProject.service;

import HospitalProject.DTO.InformDTO;
import HospitalProject.model.GroupIncidence;
import HospitalProject.model.Inform;
import HospitalProject.repository.GroupIncidenceRepository;
import HospitalProject.repository.InformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class InformDTOService{

    @Autowired
    GroupIncidenceRepository groupIncidenceRepository;

    @Autowired
    InformRepository informRepository;

    public Iterable<InformDTO> getAllInforms(){

        Iterable<Inform> informs = informRepository.findAll();
        return StreamSupport.stream(informs.spliterator(), false)
                .map(this::convertToInformDTO)
                .collect(Collectors.toList());
    }

    public InformDTO convertToInformDTO(Inform inform){
        InformDTO informDTO = new InformDTO();
        Optional<GroupIncidence> groupInformOptional = Optional.ofNullable(inform.getGroupIncidence())
                .map(GroupIncidence::getId)
                .flatMap(id -> groupIncidenceRepository.findGroupIncidenceById(id));

        groupInformOptional.ifPresentOrElse(
                group -> informDTO.setIdGroupIncidence(group.getId()),
                () -> informDTO.setIdGroupIncidence(null)
        );
        informDTO.setId(inform.getId());
        informDTO.setInform(inform.getInform());
        informDTO.setGravityOfIncidence(inform.getGravityOfIncidence());
        informDTO.setComplete(inform.isComplete());
        informDTO.setDateCompleted(inform.getDateCompleted());
        return informDTO;
    }

    public InformDTO completeInformDTO(InformDTO informDTO){
        InformDTO emptyInformDTO = new InformDTO();
        emptyInformDTO.emptyInformDTO();
        if(informDTO.getId() == null){
            informDTO.setId(emptyInformDTO.getId());
        }
        if(informDTO.getInform() == null){
            informDTO.setInform(emptyInformDTO.getInform());
        }
        if(informDTO.getGravityOfIncidence() == 0){
            informDTO.setGravityOfIncidence(emptyInformDTO.getGravityOfIncidence());
        }
        if(!informDTO.isComplete()){
            informDTO.setComplete(emptyInformDTO.isComplete());
        }
        if(informDTO.getIdGroupIncidence() == null){
            informDTO.setIdGroupIncidence(emptyInformDTO.getIdGroupIncidence());
        }
        return informDTO;
    }
}
