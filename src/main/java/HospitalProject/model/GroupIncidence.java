package HospitalProject.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GroupIncidence {

    @Id
    private String id;
    //private String groupDirector;
    //private HealthStaff groupDirector;
    //private String[] groupIncidence = new String[3];


    //@OneToOne(mappedBy = "groupIncidence", cascade = CascadeType.ALL)
    private HealthStaff groupDirector;



    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "HEALTHSTAFF_FK_GROUPINCIDENCE",
            joinColumns = @JoinColumn(name = "HEALTHSTAFF_FK"),
            inverseJoinColumns = @JoinColumn(name = "GROUPINCIDENCE"))
    private List<HealthStaff> groupIncidenceList = new ArrayList<>();


    private String incidenceType;

    @OneToMany(mappedBy = "groupIncidence", cascade = CascadeType.ALL)
    private List<Inform> informs = new ArrayList<>();

    public void addInform(Inform inform){
        this.getInforms().add(inform);
        inform.setGroupIncidence(this);
    }

    /*
    public void addHealthStaff(HealthStaff healthStaff){
        this.getGroupIncidenceList().add(healthStaff);
    }

    public HealthStaff getRandomEntity(List<HealthStaff> healthStaffList) {
        Random random = new Random();
        int randomStaff = random.nextInt(healthStaffList.size());

        return healthStaffList.get(randomStaff);
    }
    */

}
