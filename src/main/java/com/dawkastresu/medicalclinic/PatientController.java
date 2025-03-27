package com.dawkastresu.medicalclinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//adnotacja RestController mówi, że ta klasa jest beanem obsługującym endpointy
@RestController
//RequiredArgsConstruktor generuje konstruktor który przyjmuje pola finalne
@RequiredArgsConstructor
//RequestMapping określa miejsce (endpoint), każdy endpoint w tym kontrolerze zaczynał się będzie od /patients
@RequestMapping("/patients")
public class PatientController {
//PatientService rownież musi byc beanem, aby spring odpowiednio dostarczył instancje klasy
    private final PatientService patientService;
//pusta adnotacja GetMapping oznacza endpoint /patients, w którym obslugiwana jest metoda GET, czyli zwrócenie zasobu
    @GetMapping
    public List<PatientDto> getAllPatients() {
        return patientService.getAll();
    }
//GetMapping/email oznacza endpoint /patient/email gdzie email jest zmienną ścieżkową oraz obslugiwana jest metoda HTTP GET, czyli zwrócenie zasobu
    @GetMapping("/{email}")
    public PatientDto getPatientByMail(@PathVariable String email) {
        return patientService.findPatientByName(email);
    }
//ResponseStatus okresla status zwracany w odpowiedzi http - 201 CREATED
    @ResponseStatus(HttpStatus.CREATED)
//PostMapping określa enpoint /patients dla metody http POST - czyli dodanie nowego zasobu
    @PostMapping
    public Patient addNewPatient(@RequestBody Patient patient) {
        patientService.addNew(patient);
        return patient;
    }
//ResponseStatus okresla status zwracany w odpowiedzi http - 204 NO CONTENT
    @ResponseStatus(HttpStatus.NO_CONTENT)
//DeleteMapping/email okresla endpoint /patients/email, gdzie email to zmienna ścieżkowa, obsluguje zapytania HTTP DELETE, całkowicie usuwa zasób
    @DeleteMapping("/{email}")
    public void removePatient(@PathVariable String email) {
        patientService.removeByMail(email);
    }
//PutMapping/email okresla endpoint /patients/email gdzie email to zmienna sciezkowa, obsluguje zapytania HTTP PUT, czyli całkowitą edycje zasobu
    @PutMapping("/{email}")
    public void editPatientByMail(@PathVariable String email, @RequestBody Patient patient) {
        patientService.editByEmail(email, patient);
    }
//PatchMapping/email/password okresla endpoint dla zapytania PATCH, czyli czesciowej edycji zasobu
    @PatchMapping("/{email}/password")
    public void editPasswordByMail(@PathVariable String email, @RequestBody PatientPassword password) {
        patientService.editPasswordByMail(email, password);
    }
}
