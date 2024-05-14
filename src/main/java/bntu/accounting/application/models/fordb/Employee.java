package bntu.accounting.application.models.fordb;

import bntu.accounting.application.models.Item;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employee")
public class Employee extends Item {
    @Id
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Load load;
    @Column(name = "name")
    private String name;
    @Column(name = "post")
    private String post;
    @Column(name = "subject")
    private String subject;
    @Column(name = "experience")
    private String experience;
    @Column(name = "qualification")
    private String qualification;
    @Column(name = "category")
    private Integer category;
    @Column(name = "young_specialist")
    private Boolean isYoungSpecialist;
    @Column(name = "contract")
    private Double contractValue;
    @Column(name = "work_quality_grade")
    private Integer workQualityGrade;
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "employee")
    private Salary salary;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id", referencedColumnName = "id")
    private Vacancy vacancy;

    @OneToMany(mappedBy = "key.employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

    public Employee() {
    }

    public Employee(String name, String post, String subject, String experience, String qualification,
                    Integer category, Boolean isYoungSpecialist, Double contractValue) {
        this.name = name;
        this.post = post;
        this.subject = subject;
        this.experience = experience;
        this.qualification = qualification;
        this.category = category;
        this.isYoungSpecialist = isYoungSpecialist;
        this.contractValue = contractValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Boolean getYoungSpecialist() {
        return isYoungSpecialist;
    }

    public void setYoungSpecialist(Boolean youngSpecialist) {
        isYoungSpecialist = youngSpecialist;
    }

    public Double getContractValue() {
        if (contractValue != null) return contractValue;
        contractValue = 0D;
        return contractValue;
    }

    public void setContractValue(Double contractValue) {
        this.contractValue = contractValue;
    }

    public Load getLoad() {
        return load;
    }

    public void setLoad(Load load) {
        this.load = load;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public Integer getWorkQualityGrade() {
        return workQualityGrade;
    }

    public void setWorkQualityGrade(Integer workQualityGrade) {
        this.workQualityGrade = workQualityGrade;
    }

    public List<Rating> getRatings() {
        return ratings;
    }
    public Rating getScoreByExpert(Expert expert){
        List<Rating> list = ratings.stream().filter(rating -> rating.getKey().getExpert().equals(expert)).toList();
        return list.get(0);
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(load, employee.load)
                && Objects.equals(name, employee.name)
                && Objects.equals(post, employee.post)
                && Objects.equals(subject, employee.subject)
                && Objects.equals(experience, employee.experience)
                && Objects.equals(qualification, employee.qualification)
                && Objects.equals(category, employee.category)
                && Objects.equals(isYoungSpecialist, employee.isYoungSpecialist)
                && Objects.equals(contractValue, employee.contractValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(load, name, post, subject, experience, qualification, category, isYoungSpecialist, contractValue);
    }

    @Override
    public String toString() {
        return "Employee{" +
                ", name='" + name + '\'' +
                ", post='" + post + '\'' +
                ", subject='" + subject + '\'' +
                ", experience='" + experience + '\'' +
                ", qualification='" + qualification + '\'' +
                ", category=" + category +
                ", isYoungSpecialist=" + isYoungSpecialist +
                ", contractValue=" + contractValue +
                '}';
    }
}
