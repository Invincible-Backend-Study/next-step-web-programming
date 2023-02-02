package customwebserver.http;

import java.util.HashMap;
import java.util.Map;

public class RequestParameters {
    private final Map<String, String> parameters = new HashMap<>();

    public void addParams(final Map<String, String> params) {
        parameters.putAll(params);
    }

    public String getParameter(final String parameterName) {
        if (parameters.containsKey(parameterName)) {
            return parameters.get(parameterName);
        }
        throw new IllegalArgumentException("[ERROR] No Parameter about " + parameterName);
    }
}
