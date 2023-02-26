package core.mvcframework;

import core.mvcframework.view.View;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private final View view;
    private final Map<String, Object> model = new HashMap<>();

    public ModelAndView(final View view) {
        this.view = view;
    }

    public ModelAndView addObject(final String objectName, final Object object) {
        model.put(objectName, object);
        return this;
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }

    public View getView() {
        return view;
    }

}
