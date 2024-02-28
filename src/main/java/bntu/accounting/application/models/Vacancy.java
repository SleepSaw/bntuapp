package bntu.accounting.application.models;

import bntu.accounting.application.util.enums.VacancyStatus;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "vacancy")
public class Vacancy {
    @Id
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Load load;
    @Column(name = "post")
    private String post;
    @Column(name = "subject")
    private String subject;
    @Column(name = "comment")
    private String comment;
    @Column(name = "status")
    private String status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "vacancy", cascade = CascadeType.REMOVE)
    private List<Employee> employeeList;

    public Vacancy() {
    }

    public Load getLoad() {
        return load;
    }

    public void setLoad(Load load) {
        this.load = load;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(VacancyStatus status) {
        this.status = String.valueOf(status);
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
    public void addEmployee(Employee employee){
        if (employeeList != null) employeeList.add(employee);
        employeeList = new ArrayList<>(Arrays.asList(employee));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(load, vacancy.load)
                && Objects.equals(post, vacancy.post)
                && Objects.equals(subject, vacancy.subject)
                && Objects.equals(comment, vacancy.comment)
                && Objects.equals(status, vacancy.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(load, post, subject, comment, status);
    }
}
