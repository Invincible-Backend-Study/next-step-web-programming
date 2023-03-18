package core.di.factory.example2;

import core.annotation.Inject;
import core.annotation.Service;

@Service
public class DummyWithSetter {

    private String name;

    public DummyWithSetter() {
    }

    @Inject
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DummyWithSetter{" +
                "name='" + name + '\'' +
                '}';
    }

}