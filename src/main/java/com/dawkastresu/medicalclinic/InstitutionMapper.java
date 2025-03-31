package com.dawkastresu.medicalclinic;

public final class InstitutionMapper {

    public static InstitutionDto maptoDto(Institution institution) {
        return new InstitutionDto(
                institution.getName(),
                institution.getPostalCode(),
                institution.getAdress()
        );
    }

    public static Institution mapToInstitution(CreateInstitutionCommand createInstitutionCommand) {
        return new Institution(
                createInstitutionCommand.getName(),
                createInstitutionCommand.getPostalCode(),
                createInstitutionCommand.getAdress()
        );
    }

}
