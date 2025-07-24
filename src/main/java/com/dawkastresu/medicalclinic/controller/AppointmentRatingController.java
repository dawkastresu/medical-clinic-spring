package com.dawkastresu.medicalclinic.controller;

import com.dawkastresu.medicalclinic.command.AppointmentRatingCommand;
import com.dawkastresu.medicalclinic.dto.AppointmentRatingDto;
import com.dawkastresu.medicalclinic.dto.DoctorAppointmentStatsDto;
import com.dawkastresu.medicalclinic.dto.DoctorAverageRatingDto;
import com.dawkastresu.medicalclinic.model.AppointmentRating;
import com.dawkastresu.medicalclinic.service.AppointmentRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rating")
public class AppointmentRatingController {

    private final AppointmentRatingService ratingService;

    @PostMapping
    public AppointmentRatingDto rateAppointment(@RequestBody AppointmentRatingCommand command) {
        return ratingService.rateAppointment(command);
    }

    @GetMapping("/doctor/{doctorId}/stats")
    public DoctorAppointmentStatsDto getDoctorStats(@PathVariable Long doctorId) {
        return ratingService.getDoctorVisitStats(doctorId);
    }

    @GetMapping("/doctors/ranking")
    public List<DoctorAverageRatingDto> getDoctorRanking() {
        return ratingService.getDoctorRanking();
    }

    @GetMapping("/average-rating")
    public Double getAverageRatingInRange(@RequestParam LocalDateTime start,
                                          @RequestParam LocalDateTime end) {
        return ratingService.getAverageRatingInRange(start, end);
    }


}
