package core.di.factory.example;

import core.annotation.Controller;
import core.annotation.Inject;

@Controller
public class ExampleController {
    private ExampleService exampleService;

    @Inject
    public void setExampleService(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    public String findExample() {
        return exampleService.findExample();
    }

    public ExampleService getExampleService() {
        return exampleService;
    }
}
