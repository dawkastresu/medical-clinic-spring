package com.dawkastresu.medicalclinic;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="INSTITUTIONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String postalCode;

    private String adress;

    @ManyToMany(mappedBy = "institutions")
    private List<Doctor> doctors;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Institution))
            return false;

        Institution other = (Institution) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public String toString() {
        return "Institution{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", adress='" + adress + '\'' +
                '}';
    }

}
