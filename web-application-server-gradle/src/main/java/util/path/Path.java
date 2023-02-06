package util.path;

import controller.*;

import java.util.Arrays;
import java.util.function.Supplier;

public enum Path implements Router {
    INDEX_PAGE("/index.html") {
            @Override
        public Controller selectController() {
            return new HomeController();
        }
    },
    REGISTER("/user/create") {
        @Override
        public Controller selectController() {
            return new MemberRegistrationController();
        }
    },
    USER_LIST("/user/list") {
        @Override
        public Controller selectController() {
            return new UserListController();
        }
    },
    USER_LOGIN("/user/login") {
        @Override
        public Controller selectController() {
            return new UserLoginController();
        }
    };
    private final String path;

    Path(final String path) {
        this.path = path;
    }

    public static Path from(String requestPath) {
        return Arrays.stream(values())
                .filter(url -> url.path.equals(requestPath))
                .findAny()
                .orElseGet(()->Path.INDEX_PAGE);
    }
}
