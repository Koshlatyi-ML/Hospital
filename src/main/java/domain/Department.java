package domain;

import dao.metadata.annotation.mapping.Column;
import dao.metadata.annotation.mapping.OneToMany;
import dao.metadata.annotation.mapping.Table;

import java.util.List;
import java.util.Objects;

@Table("departments")
public class Department extends IdHolder {
    @Column("name")
    private String name;
    @OneToMany(table = "stuff", foreignKey = "department_id")
    private List<Doctor> doctors;
    private List<Medic> medics;

    private Department() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Medic> getMedics() {
        return medics;
    }

    public void setMedics(List<Medic> medics) {
        this.medics = medics;
    }

    public static class Builder extends IdHolder.Builder<Department, Builder> {
        public Builder() {
            instance = new Department();
        }

        public Builder setId(long id) {
            instance.setId(id);
            return getSelf();
        }

        public Builder setName(String name) {
            instance.setName(name);
            return getSelf();
        }

        public Builder setDoctors(List<Doctor> doctors) {
            instance.setDoctors(doctors);
            return getSelf();
        }

        public Builder setNurses(List<Medic> medics) {
            instance.setMedics(medics);
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
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return  Objects.equals(name, that.name) &&
                Objects.equals(doctors, that.doctors) &&
                Objects.equals(medics, that.medics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, doctors, medics);
    }
}
