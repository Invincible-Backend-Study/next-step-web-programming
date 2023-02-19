package core.web;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private final View view;
    private final Map<String, Object> model;

    public ModelAndView(View view) {
        this.view = view;
        this.model = new HashMap<>();
    }

    public ModelAndView addModel(String key, Object value) {
        model.put(key, value);
        return this;
    }

    public View getView() {
        return view;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
