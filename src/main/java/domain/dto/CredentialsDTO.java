package domain.dto;

import java.util.Objects;

public class CredentialsDTO extends AbstractDTO {
    private String login;
    private String password;
    private long role;

    private CredentialsDTO() {
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

    public long getRole() {
        return role;
    }

    public void setRole(long role) {
        if (role < 0 || role > 3) {
            throw new IllegalArgumentException();
        }

        this.role = role;
    }

    public static class Builder extends AbstractDTO.Builder<CredentialsDTO, Builder> {

        public Builder() {
            instance = new CredentialsDTO();
        }

        public Builder setLogin(String login) {
            instance.setLogin(login);
            return getSelf();
        }

        public Builder setPassword(String login) {
            instance.setPassword(login);
            return getSelf();
        }

        public Builder setRole(long role) {
            instance.setRole(role);
            return getSelf();
        }

        @Override
        protected Builder getSelf() {
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CredentialsDTO)) return false;
        CredentialsDTO that = (CredentialsDTO) o;
        return getId() == that.getId() &&
                getRole() == that.getRole() &&
                Objects.equals(getLogin(), that.getLogin()) &&
                Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), getPassword(), getRole());
    }
}
