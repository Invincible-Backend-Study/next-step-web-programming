package core.di.factory.example2;

import core.annotation.Inject;
import core.annotation.Service;

@Service
public class DummyWithField {

    @Inject
    private String name;

    public DummyWithField() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DummyWithField{" +
                "name='" + name + '\'' +
                '}';
    }

}
