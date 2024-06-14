package HospitalProject.DTO;


import HospitalProject.model.HealthStaff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupIncidenceDTO {

    private String id;
    private String incidenceType;
    private String idDirector;
    private List<String> idsMedicalStaff = new ArrayList<>();

    public void emptyGroupIncidenceDTO (){
        this.id = "";
        this.incidenceType = "";
        this.idDirector = "";
    }

    // Constructor para crear un GroupIncidenceDTO de error
    public GroupIncidenceDTO (String errorMessage) {
        this.id = "error";
        this.incidenceType = errorMessage;
        this.idDirector = null;
        this.idsMedicalStaff = null;
    }

}
