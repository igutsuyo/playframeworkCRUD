package controllers;
import play.data.validation.Constraints.*;
import play.data.validation.Constraints.Validate;
import play.data.validation.Constraints.Validatable;

public class UserForm  {

    @Required @MinLength(1) @MaxLength(15) private String name;
    @Required @Min(0) @Max(150) private int age;

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}