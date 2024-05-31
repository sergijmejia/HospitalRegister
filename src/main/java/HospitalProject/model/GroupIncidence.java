package HospitalProject.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GroupIncidence {

    @Id
    private String id;
    private String groupDirector;
    //private HealthStaff groupDirector;
    //private String[] groupIncidence = new String[3];


    //@OneToOne(mappedBy = "groupIncidence", cascade = CascadeType.ALL)
    //private HealthStaff groupDirector;



    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "GROUPINCIDENCE_HEALTHSTAFF_FK",
            joinColumns = @JoinColumn(name = "GROUPINCIDENCE"),
            inverseJoinColumns = @JoinColumn(name = "HEALTHSTAFF_FK"))
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

    public void setGroupDirector(){
        Random random = new Random();
        int randomStaff = random.nextInt(groupIncidenceList.size());
        HealthStaff director = groupIncidenceList.get(randomStaff);
        groupDirector = director.getName();
    }

    public void addHealthStaff(HealthStaff healthStaff){
        this.getGroupIncidenceList().add(healthStaff);
    }

}
