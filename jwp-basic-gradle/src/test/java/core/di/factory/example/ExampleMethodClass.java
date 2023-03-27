package core.di.factory.example;

import core.annotation.Inject;

public class ExampleMethodClass {
    private String one;
    private String two;
    private String three;

    @Inject
    public void setOne(String one) {
        this.one = one;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public void setThree(String three) {
        this.three = three;
    }
}
