package models;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class Category {
    private static int count = 1;
    private int id;
    private String name;

    public Category(String name) {
        this.id = count++;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
