package service.dto;

import util.AbstractBuilder;

import java.util.Objects;

public class LoginDTO {

    private String login;
    private String password;
    private String role;

    private LoginDTO() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static class Builder extends AbstractBuilder<LoginDTO> {

        public Builder() {
            instance = new LoginDTO();
        }

        public Builder setRole(String role) {
            instance.setRole(role);
            return getSelf();
        }

        public Builder setPassword(String password) {
            instance.setPassword(password);
            return getSelf();
        }

        public Builder setLogin(String login) {
            instance.setLogin(login);
            return getSelf();
        }

        protected Builder getSelf() {
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginDTO)) return false;
        LoginDTO loginDTO = (LoginDTO) o;
        return Objects.equals(getLogin(), loginDTO.getLogin()) &&
                Objects.equals(getPassword(), loginDTO.getPassword()) &&
                Objects.equals(getRole(), loginDTO.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPassword(), getRole());
    }
}
