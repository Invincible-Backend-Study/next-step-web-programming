package core.rc.filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExecutionFilter {

    /**
     * 낮을 수록 우선순위가 가도록
     *
     * @return
     */
    int order() default -1;
}
