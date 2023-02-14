package next.model;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        validationLogin();
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (userId == null) {
            if (other.userId != null) {
                return false;
            }
        } else if (!userId.equals(other.userId)) {
            return false;
        }
        return true;
    }

    public void update(User newUserData) {
        this.userId = newUserData.userId;
        this.password = newUserData.password;
        this.email = newUserData.email;
        this.name = newUserData.name;
    }

    public void validationLogin() {
        if (name == null) {
            throw new IllegalStateException("[ERROR] 이름이 입력되지 않았습니다.");
        }
        if (email == null) {
            throw new IllegalStateException("[ERROR] 이메일이 입력되지 않았습니다.");
        }
        if (password == null) {
            throw new IllegalStateException("[ERROR] 패스워드가 입력되지 않았습니다.");
        }
        if (userId == null) {
            throw new IllegalStateException("[ERROR] 아이디가 입력되지 않았습니다.");
        }
    }
}
