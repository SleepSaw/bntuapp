package bntu.accounting.application.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "vacancy")
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
}
