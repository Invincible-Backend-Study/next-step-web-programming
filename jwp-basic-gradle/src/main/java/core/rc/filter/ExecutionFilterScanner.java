package core.rc.filter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;


@Slf4j
public class ExecutionFilterScanner {
    private final Reflections reflections;

    public ExecutionFilterScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    public List<CustomExecutionFilter> getExecutionFilter() {
        return new ArrayList<>(reflections.getTypesAnnotatedWith(ExecutionFilter.class))
                .stream()
                .filter(clazz -> clazz.getSuperclass().equals(CustomExecutionFilter.class))
                .collect(Collectors.toMap(clazz -> clazz.getAnnotation(ExecutionFilter.class), this::convertClazzToInstance))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(executionFilterCustomExecutionFilterEntry -> executionFilterCustomExecutionFilterEntry.getKey().order()))
                .map(Entry::getValue)
                .collect(Collectors.toList());
    }

    private CustomExecutionFilter convertClazzToInstance(Class<?> clazz) {
        try {
            return (CustomExecutionFilter) clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
