package HospitalProject.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GroupIncidence {

    private String id;
    private String groupDirector;
    private String[] groupIncidence = new String[3];
    @Id
    private String incidenceType;

    @OneToMany(mappedBy = "groupIncidence", cascade = CascadeType.ALL)
    private List<Inform> informs = new ArrayList<>();

    public void addInform(Inform inform){
        this.getInforms().add(inform);
        inform.setGroupIncidence(this);
    }

}
