package core.di.factory.example;

import core.annotation.Repository;

@Repository
public class ExampleRepository {
    public String findExample() {
        return "example";
    }
}
