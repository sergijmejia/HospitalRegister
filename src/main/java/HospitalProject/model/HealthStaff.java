package HospitalProject.model;


import HospitalProject.model.GroupIncidence;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HealthStaff {

    @Id
    private String id;
    private String name;
    private String mail;


    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<GroupIncidence> groupIncidenceList = new ArrayList<>();

    public void addGroupIncidence(GroupIncidence groupIncidence){
        this.getGroupIncidenceList().add(groupIncidence);
    }

    /*
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "GroupIncidence_FK")
    private GroupIncidence groupIncidenceDirector;

     */


}
