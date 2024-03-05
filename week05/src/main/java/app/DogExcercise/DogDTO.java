package app.DogExcercise;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DogDTO {
    private static int idCount;
    private int id;
    private String name;
    private int age;
    private String breed;

    public DogDTO(String name, int age, String breed) {
        this.name = name;
        this.age = age;
        this.breed = breed;
    }

    public static void startIdCount() {
        idCount = 0;
    }

    public void setId() {
        this.id = idCount++;
    }
}
