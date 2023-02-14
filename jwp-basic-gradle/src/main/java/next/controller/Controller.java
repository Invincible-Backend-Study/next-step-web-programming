package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@FunctionalInterface
public interface Controller {
    String execute(HttpServletRequest request, HttpServletResponse response);
}
