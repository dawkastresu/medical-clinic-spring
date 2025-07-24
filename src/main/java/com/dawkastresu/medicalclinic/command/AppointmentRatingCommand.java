package com.dawkastresu.medicalclinic.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRatingCommand {

    @NotNull(message = "ID wizyty jest wymagane")
    private Long appointmentId;

    @NotNull(message = "ID lekarza jest wymagane")
    private Long doctorId;

    @NotNull(message = "Ocena jest wymagana")
    @DecimalMin(value = "1.0", message = "Minimalna wartość oceny to 1.0")
    @DecimalMax(value = "5.0", message = "Maksymalna wartość oceny to 5.0")
    private Double ratingValue;

}
