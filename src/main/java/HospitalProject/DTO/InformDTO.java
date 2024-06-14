package HospitalProject.DTO;

//Data Transfer Objects

import HospitalProject.model.GroupIncidence;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformDTO {

    private String id;
    private String inform;
    private int gravityOfIncidence;
    private boolean complete;
    private String dateCompleted;
    private String idGroupIncidence;

    public void emptyInformDTO(){
        this.id = "";
        this.inform = "";
        this.gravityOfIncidence = 0;
        this.complete = false;
        this.dateCompleted = null;
        this.idGroupIncidence = "";
    }

}
