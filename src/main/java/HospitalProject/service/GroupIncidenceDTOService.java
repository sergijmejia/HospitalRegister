package HospitalProject.service;

import HospitalProject.DTO.GroupIncidenceDTO;
import HospitalProject.model.GroupIncidence;
import HospitalProject.model.HealthStaff;
import HospitalProject.repository.GroupIncidenceRepository;
import HospitalProject.repository.HealthStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GroupIncidenceDTOService {

    @Autowired
    GroupIncidenceRepository groupIncidenceRepository;

    @Autowired
    HealthStaffRepository healthStaffRepository;

    public Iterable<GroupIncidenceDTO> getAllGroupIncidents(){

        Iterable<GroupIncidence> groupIncidents = groupIncidenceRepository.findAll();
        return StreamSupport.stream(groupIncidents.spliterator(), false)
                .map(this::convertToGroupIncidendeDTO)
                .collect(Collectors.toList());
    }

    public GroupIncidenceDTO convertToGroupIncidendeDTO(GroupIncidence groupIncidence){

        GroupIncidenceDTO groupIncidenceDTO = new GroupIncidenceDTO();

        if(groupIncidence.getId().equals("error")){
            return new GroupIncidenceDTO(groupIncidence.getIncidenceType());
        }

        groupIncidenceDTO.setId(groupIncidence.getId());
        groupIncidenceDTO.setIncidenceType(groupIncidence.getIncidenceType());

        List<HealthStaff> healthStaffList = groupIncidence.getGroupIncidenceList();

        List<String> groupIncidenceDTOHealthStaffList = new ArrayList<>();

        for(int i=0; i<healthStaffList.size(); i++){
            groupIncidenceDTOHealthStaffList.add(healthStaffList.get(i).getId());
        }

        groupIncidenceDTO.setIdsMedicalStaff(groupIncidenceDTOHealthStaffList);

        Optional<HealthStaff> healthStaffDirectorOptional = Optional.ofNullable(groupIncidence.getGroupDirector())
                .flatMap(name -> healthStaffRepository.findHealthStaffByName(name));

        healthStaffDirectorOptional.ifPresentOrElse(
                staff -> groupIncidenceDTO.setIdDirector(staff.getId()),
                () -> groupIncidenceDTO.setIdDirector(null));

        return groupIncidenceDTO;
    }

    public GroupIncidenceDTO completeGroupIncidenceDTO(GroupIncidenceDTO groupIncidenceDTO) {
        GroupIncidenceDTO emptygroupIncidenceDTO = new GroupIncidenceDTO();
        emptygroupIncidenceDTO.emptyGroupIncidenceDTO();
        if(groupIncidenceDTO.getId() == null){
            groupIncidenceDTO.setId(emptygroupIncidenceDTO.getId());
        }
        if(groupIncidenceDTO.getIncidenceType() == null){
            groupIncidenceDTO.setIncidenceType(emptygroupIncidenceDTO.getIncidenceType());
        }
        if(groupIncidenceDTO.getIdDirector() == null){
            groupIncidenceDTO.setIdDirector(emptygroupIncidenceDTO.getIdDirector());
        }

        return groupIncidenceDTO;
    }
}
