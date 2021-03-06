package domain;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class CredentialsDTO extends AbstractDTO {

    private String login;
    private String password;
    private static final Logger LOG = LogManager.getLogger(CredentialsDTO.class);

    private CredentialsDTO() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (login == null) {
            LOG.log(Level.ERROR, "Login attempted to set a null value");
            throw new IllegalArgumentException();
        }
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null) {
            LOG.log(Level.ERROR, "Password attempted to set a null value");
            throw new IllegalArgumentException();
        }
        this.password = password;
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
                Objects.equals(getLogin(), that.getLogin()) &&
                Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), getPassword());
    }
}
