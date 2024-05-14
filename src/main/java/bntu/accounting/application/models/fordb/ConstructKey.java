package bntu.accounting.application.models.fordb;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class ConstructKey implements Serializable {
    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "expert_id", referencedColumnName = "id")
    private Expert expert;

    public ConstructKey() {
    }

    public ConstructKey(Employee employee, Expert expert) {
        this.employee = employee;
        this.expert = expert;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Expert getExpert() {
        return expert;
    }

    public void setExpert(Expert expert) {
        this.expert = expert;
    }
}
