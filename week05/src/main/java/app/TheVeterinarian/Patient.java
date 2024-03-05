package app.TheVeterinarian;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {
    private UUID id;
    private String name;
    private String species;
    private List<String> allergies; 
    private List<String> medications;    

    public Patient(String name, String species) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.species = species;
        allergies = new ArrayList<>();
        medications = new ArrayList<>();
    }

    public void addAllergy(String allergy) {
        allergies.add(allergy);
    }

    public void addMedication(String medication) {
        medications.add(medication);
    }
}
