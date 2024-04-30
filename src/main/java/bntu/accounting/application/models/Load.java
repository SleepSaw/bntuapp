package bntu.accounting.application.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "load")
public class Load {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "total_hours")
    private Double totalHours;
    @Column(name = "academic_hours")
    private Double academicHours;
    @Column(name = "organization_hours")
    private Double organizationHours;
    @Column(name = "additional_hours")
    private Double additionalHours;
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "load")
    private Employee employee;
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "load")
    private Vacancy vacancy;

    public Load() {
    }

    public Load(Double academicHours, Double organizationHours, Double additionalHours) {
        this.academicHours = academicHours;
        this.organizationHours = organizationHours;
        this.additionalHours = additionalHours;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotalHours() {
        if (totalHours != null) return totalHours;
        totalHours = 0D;
        return roundValue(totalHours);
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = roundValue(totalHours);
    }

    public Double getAcademicHours() {
        return academicHours;
    }

    public void setAcademicHours(Double academicHours) {
        this.academicHours = academicHours;
    }

    public Double getOrganizationHours() {
        return organizationHours;
    }

    public void setOrganizationHours(Double organizationHours) {
        this.organizationHours = organizationHours;
    }

    public Double getAdditionalHours() {
        return additionalHours;
    }

    public void setAdditionalHours(Double additionalHours) {
        this.additionalHours = additionalHours;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Load load = (Load) o;
        return Objects.equals(id, load.id)
                && Objects.equals(totalHours, load.totalHours)
                && Objects.equals(academicHours, load.academicHours)
                && Objects.equals(organizationHours, load.organizationHours)
                && Objects.equals(additionalHours, load.additionalHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalHours, academicHours, organizationHours, additionalHours);
    }

    public void clone(Load load) {
        this.academicHours = load.getAcademicHours();
        this.organizationHours = load.getOrganizationHours();
        this.additionalHours = load.getAdditionalHours();
        this.totalHours = load.getTotalHours();
    }
    private double roundValue(double value) {
        double result = Math.round(value * 100);
        result = result / 100;
        return result;
    }
}
