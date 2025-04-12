package com.dawkastresu.medicalclinic;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="INSTITUTIONS")
@Data
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String postalCode;

    private String adress;

    @ManyToMany(mappedBy = "joinedInstitutions")
    private Doctor doctor;

}
