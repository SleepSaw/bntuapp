package bntu.accounting.application.models.builders;


import bntu.accounting.application.models.fordb.Employee;

public class EmployeeBuilder {
    private String name;
    private String post;
    private String subject;
    private String experience;
    private String qualification;
    private Integer category;
    private Boolean isYoungSpecialist;
    private Double contractValue;

    public EmployeeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public EmployeeBuilder setPost(String post) {
        this.post = post;
        return this;
    }

    public EmployeeBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmployeeBuilder setExperience(String experience) {
        this.experience = experience;
        return this;
    }

    public EmployeeBuilder setQualification(String qualification) {
        this.qualification = qualification;
        return this;
    }

    public EmployeeBuilder setCategory(Integer category) {
        this.category = category;
        return this;
    }

    public EmployeeBuilder setYoungSpecialist(Boolean youngSpecialist) {
        isYoungSpecialist = youngSpecialist;
        return this;
    }

    public EmployeeBuilder setContractValue(Double contractValue) {
        this.contractValue = contractValue;
        return this;
    }
    public Employee build(){
        return new Employee(name,post,subject,experience,qualification,category,isYoungSpecialist,contractValue);
    }
}
