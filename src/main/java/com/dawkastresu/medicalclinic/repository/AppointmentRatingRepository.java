package com.dawkastresu.medicalclinic.repository;

import com.dawkastresu.medicalclinic.dto.DoctorAverageRatingDto;
import com.dawkastresu.medicalclinic.model.AppointmentRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRatingRepository extends JpaRepository<AppointmentRating, Long> {

//    // Wszystkie oceny danego lekarza
//    @Query("SELECT r.ratingValue FROM AppointmentRating r WHERE r.doctor.id = :doctorId")
//    List<Double> findRatingsByDoctor(@Param("doctorId") Long doctorId);
//
//    // Oceny wszystkich lekarzy w przedziale czasowym
//    @Query("SELECT r.ratingValue FROM AppointmentRating r WHERE r.ratingDate BETWEEN :start AND :end")
//    List<Double> findAllRatingsBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
//
//    // Ranking lekarzy – średnie ocen
//    @Query("SELECT r.doctor.id, AVG(r.ratingValue) " +
//            "FROM AppointmentRating r " +
//            "GROUP BY r.doctor.id " +
//            "ORDER BY AVG(r.ratingValue) DESC")
//    List<Object[]> findDoctorRanking();

    @Query("""
    SELECT AVG(ar.ratingValue)
    FROM AppointmentRating ar
    WHERE ar.doctor.id = :doctorId
    """)
    Double findAverageRatingByDoctorId(@Param("doctorId") Long doctorId);

    @Query("""
    SELECT new com.dawkastresu.medicalclinic.dto.DoctorAverageRatingDto(
        ar.doctor.id,
        AVG(ar.ratingValue)
    )
    FROM AppointmentRating ar
    GROUP BY ar.doctor.id
    ORDER BY AVG(ar.ratingValue) DESC
    """)
    List<DoctorAverageRatingDto> getDoctorRanking();

    @Query("""
    SELECT AVG(ar.ratingValue)
    FROM AppointmentRating ar
    WHERE ar.ratingDate BETWEEN :start AND :end
    """)
    Double findAverageRatingInDateRange(@Param("start") LocalDateTime start,
                                        @Param("end") LocalDateTime end);

}
