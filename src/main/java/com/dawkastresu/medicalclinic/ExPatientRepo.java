//package com.dawkastresu.medicalclinic;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
////Adnotacja Repository mówi, że ta klasa jest beanem odpowiedzialnym za komunikacje z bazą danych, w tym wypadku z Listą pacjentów.
//@Repository
////ReauiredArgsConstruktor generuje konstruktor który przyjmuje pola finalne
//@RequiredArgsConstructor
//public class PatientRepository {
//    //pusta lista domyślnie jest odczytywana jako bean i obslugiwana przez Spring
//    private final List<Patient> patients;
//
//    public List<Patient> findAll() {
//        return new ArrayList<>(patients);
//    }
//
//    public Optional<Patient> findByEmail(String email){
//        return patients.stream()
//                .filter(patient -> patient.getEmail().equalsIgnoreCase(email))
//                .findFirst();
//    }
//
//    public void add(Patient patient) {
//        patients.add(patient);
//    }
//
//    public void removeByEmail(String email) {
//        patients.removeIf(patient -> patient.getEmail().equalsIgnoreCase(email));
//    }
//
//}
