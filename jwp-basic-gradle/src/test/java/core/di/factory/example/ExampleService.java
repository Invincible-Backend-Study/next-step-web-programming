package core.di.factory.example;

import core.annotation.Inject;
import core.annotation.Service;

@Service
public class ExampleService {
   @Inject
   private ExampleRepository exampleRepository;

   public String findExample() {
       return exampleRepository.findExample();
   }

   public ExampleRepository getExampleRepository() {
       return exampleRepository;
   }
}
