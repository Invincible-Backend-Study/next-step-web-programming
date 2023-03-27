//package sample;
//
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//public class arrayTest {
//    private String[] fruits;
//    private List<String> grades;
//
//    @Before
//    public void beforeEach() throws Exception {
//        fruits = new String[]{"apple", "banana", "cherry"};
//        grades = List.of("a", "b", "c", "d", "e");
//    }
//
//    @Test
//    public void cloneString() throws Exception {
//        cloneFruits();
//        cloneFruits()[0];
//
//        String[] newFruits = cloneFruits();
//        newFruits[0];
//        String name = newFruits[0];
//    }
//
//    private String[] cloneFruits() throws Exception {
//        return fruits.clone();
//    }
//
//    private List<String> cloneGrades() throws Exception {
//        return grades.clone();
//    }
//}
