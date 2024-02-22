package exercise1;

import lombok.ToString;

@ToString
public class NotesAndNamesDTO {
    String note;
    String name;
    int age;
    public NotesAndNamesDTO(String note, String name, int age) {
        this.note = note;
        this.name = name;
        this.age = age;
    }
}
