package core.rc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ExecutionMapper {

    public static ExecutionMapper getInstance() {
        return ExecutionMapperHolder.executionMapper;
    }

    public Object[] doMapping(Method method, HttpServletRequest request, HttpServletResponse response) {
        return Arrays.stream(method.getParameters())
                .map(parameter -> doMapping(request, response, parameter))
                .toArray();
    }

    private Object doMapping(HttpServletRequest request, HttpServletResponse response, Parameter parameter) {
        final var parameterType = parameter.getType();
        if (parameterType == HttpServletRequest.class) {
            return request;
        }
        if (parameterType == HttpServletResponse.class) {
            return response;
        }
        if (parameterType == HttpSession.class) {
            return request.getSession();
        }
        final var paramterAnnotation = parameter.getAnnotations();
        return Arrays.stream(paramterAnnotation)
                .filter(annotation -> annotation.annotationType() == ModelAttribute.class)
                .map(ModelAttribute.class::cast)
                .map(annotation -> createRequestBodyInstance(annotation, request, parameterType))
                .findAny()
                .orElse(null);
    }

    private Object createRequestBodyInstance(ModelAttribute modelAttribute, HttpServletRequest request, Class<?> parameterType) {
        try {
            // 클래스 매핑의 경우
            if (modelAttribute.value().isEmpty()) {
                final var constructor = parameterType.getDeclaredConstructor();
                final var instance = constructor.newInstance();
                for (final var field : parameterType.getDeclaredFields()) {
                    final var type = field.getType();
                    Primitive.of(type).setField(instance, field, request.getParameter(field.getName()));
                }
                return instance;
            }

            if (!parameterType.getTypeName().equals(String.class.getName())) {
                throw new IllegalArgumentException("단일 매핑에서는 String 타입만 선언 가능합니다.");
            }
            // 단일 매핑의 경우
            return request.getParameter(modelAttribute.value());

        } catch (NoSuchMethodException e) {
            log.error("{}의 기본 생성자를 필수로 작성해주세요.", parameterType);
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.error("{}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static class ExecutionMapperHolder {
        private static final ExecutionMapper executionMapper = new ExecutionMapper();
    }
}
