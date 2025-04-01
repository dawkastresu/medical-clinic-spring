package com.dawkastresu.medicalclinic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Institution {

    private String name;
    private String postalCode;
    private String adress;

}
