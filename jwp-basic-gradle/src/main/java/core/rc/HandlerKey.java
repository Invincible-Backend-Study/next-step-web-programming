package core.rc;


import core.annotation.RequestMethod;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;


@EqualsAndHashCode
@RequiredArgsConstructor
public class HandlerKey {
    private final String url;
    private final RequestMethod requestMethod;

    @Override
    public String toString() {
        return "HandlerKey{" +
                "url='" + url + '\'' +
                ", requestMethod=" + requestMethod +
                '}';
    }
}
