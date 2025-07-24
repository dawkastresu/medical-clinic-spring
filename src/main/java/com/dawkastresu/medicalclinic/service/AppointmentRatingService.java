package com.dawkastresu.medicalclinic.service;

import com.dawkastresu.medicalclinic.command.AppointmentRatingCommand;
import com.dawkastresu.medicalclinic.dto.AppointmentRatingDto;
import com.dawkastresu.medicalclinic.dto.DoctorAppointmentStatsDto;
import com.dawkastresu.medicalclinic.dto.DoctorAverageRatingDto;
import com.dawkastresu.medicalclinic.exception.AppointmentNotFoundException;
import com.dawkastresu.medicalclinic.exception.DoctorNotFoundException;
import com.dawkastresu.medicalclinic.model.Appointment;
import com.dawkastresu.medicalclinic.model.AppointmentRating;
import com.dawkastresu.medicalclinic.model.Doctor;
import com.dawkastresu.medicalclinic.repository.AppointmentRatingRepository;
import com.dawkastresu.medicalclinic.repository.AppointmentRepository;
import com.dawkastresu.medicalclinic.repository.DoctorRepository;
import com.dawkastresu.medicalclinic.utils.AppointmentRatingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentRatingService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentRatingRepository appointmentRatingRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRatingMapper appointmentRatingMapper;

//    public DoctorAppointmentStatsDto getDoctorVisitStats(Long doctorId) {
//        long totalAppointments = appointmentRepository.countByDoctorId(doctorId);
//        long unassignedAppointments = appointmentRepository.countByDoctorIdAndPatientIdIsNull(doctorId);
//        long attendedAppointments = appointmentRepository.countByDoctorIdAndPatientIdIsNotNull(doctorId);
//
//        double attendanceRate = totalAppointments == 0 ? 0 :
//                ((double) attendedAppointments / totalAppointments) * 100;
//
//        Double averageRating = appointmentRatingRepository.findAverageRatingByDoctor(doctorId);
//
//        return new DoctorAppointmentStatsDto(
//                totalAppointments,
//                unassignedAppointments,
//                attendedAppointments,
//                attendanceRate,
//                averageRating
//        );
//    }

    public AppointmentRatingDto rateAppointment(AppointmentRatingCommand command) {
        Appointment appointment = appointmentRepository.findById(command.getAppointmentId())
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found", HttpStatus.NOT_FOUND));

        Doctor doctor = doctorRepository.findById(command.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found", HttpStatus.NOT_FOUND));

        AppointmentRating rating = new AppointmentRating();
        rating.setAppointment(appointment);
        rating.setDoctor(doctor);
        rating.setRatingValue(command.getRatingValue());
        rating.setRatingDate(LocalDateTime.now());

        appointmentRatingRepository.save(rating);

        return appointmentRatingMapper.toDto(rating);
    }

    public DoctorAppointmentStatsDto getDoctorVisitStats(Long doctorId) {
        long totalAppointments = appointmentRepository.countByDoctorId(doctorId);
        long unassignedAppointments = appointmentRepository.countByDoctorIdAndPatientIdIsNull(doctorId);
        long attendedAppointments = appointmentRepository.countByDoctorIdAndPatientIdIsNotNull(doctorId);

        double attendanceRate = totalAppointments > 0
                ? 100.0 * attendedAppointments / totalAppointments
                : 0.0;

        Double averageRating = appointmentRatingRepository.findAverageRatingByDoctorId(doctorId);

        return new DoctorAppointmentStatsDto(
                totalAppointments,
                unassignedAppointments,
                attendedAppointments,
                attendanceRate,
                averageRating
        );
    }

    public List<DoctorAverageRatingDto> getDoctorRanking() {
        return appointmentRatingRepository.getDoctorRanking();
    }

    public Double getAverageRatingInRange(LocalDateTime start, LocalDateTime end) {
        return appointmentRatingRepository.findAverageRatingInDateRange(start, end);
    }

}




