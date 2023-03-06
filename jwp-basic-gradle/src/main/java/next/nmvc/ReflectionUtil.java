package next.nmvc;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ReflectionUtil {
    public static List<Class<?>> getAnnotatedClasses(String packageName, Class<? extends Annotation> annotationClass) throws ClassNotFoundException, IOException {
        List<Class<?>> annotatedClasses = new ArrayList<>();

        // 패키지 내의 모든 클래스를 가져온다
        List<Class<?>> classes = getClasses(packageName);

        // 각 클래스를 검사하여 애노테이션을 가지고 있는지 확인한다.
        for (Class<?> clazz : classes) {
            if (hasAnnotation(clazz, annotationClass)) {
                annotatedClasses.add(clazz);
            }
        }

        return annotatedClasses;
    }

    private static boolean hasAnnotation(AnnotatedElement element, Class<? extends Annotation> annotationClass) {
        return element.isAnnotationPresent(annotationClass);
    }

    private static List<Class<?>> getClasses(String packageName) throws IOException {
        List<Class<?>> classes = new ArrayList<>();
        //패키지 네임 . 을 / path로 바꾸자
        String path = packageName.replace('.', '/');
        //클래스 로더를 사요해서 모든 클래스 가져오기
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        ArrayList<URL> paths = Collections.list(classLoader.getResources(path));

        paths.forEach(url -> {
            Arrays.stream(requireNonNull(new File(url.getFile()).list(), "파일이 존재하지 않습니다."))
                    .filter(file -> file.endsWith(".class"))
                    .forEach(file -> {
                        String className = packageName + "." + file.substring(0, file.length() - 6);
                        try {
                            classes.add(Class.forName(className));
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    });
        });


        return classes;
    }
}