package HospitalProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Inform {

    @Id
    private String id;
    private String inform;
    private int gravityOfIncidence;
    private boolean complete;
    private String dateCompleted;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="GROUP_INCIDENCE")
    private GroupIncidence groupIncidence;

    public void emptyInform(){
        this.id = "";
        this.inform = "";
        this.gravityOfIncidence = 0;
        this.complete = false;
        this.dateCompleted = null;
        this.groupIncidence = new GroupIncidence();
    }
}


