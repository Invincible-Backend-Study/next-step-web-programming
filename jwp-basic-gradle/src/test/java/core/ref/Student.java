package core.ref;

public class Student {
    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "name: " + name + ",  age: " + age;
    }
}
