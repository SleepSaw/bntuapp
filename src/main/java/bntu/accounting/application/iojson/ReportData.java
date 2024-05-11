package bntu.accounting.application.iojson;

import com.fasterxml.jackson.annotation.JsonProperty;
public class ReportData {
    @JsonProperty("last_save_path")
    private String lastFileSavePath;
    @JsonProperty("approver_name")
    private String approverName;
    @JsonProperty("approver_post")
    private String approverPost;
    @JsonProperty("department_head_name")
    private String departmentHeadName;
    @JsonProperty("academic_year")
    private String academicYear;
    @JsonProperty("half_of_year")
    private String halfOfYear;
    @JsonProperty("day")
    private Integer day;
    @JsonProperty("month")
    private String month;
    @JsonProperty("year")
    private Integer year;
//    @JsonProperty("months")
//    private Map<String, String> pairs = new HashMap<>();

    public String getLastFileSavePath() {
        return lastFileSavePath;
    }

    public void setLastFileSavePath(String lastFileSavePath) {
        this.lastFileSavePath = lastFileSavePath;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getApproverPost() {
        return approverPost;
    }

    public void setApproverPost(String approverPost) {
        this.approverPost = approverPost;
    }

    public String getDepartmentHeadName() {
        return departmentHeadName;
    }

    public void setDepartmentHeadName(String departmentHeadName) {
        this.departmentHeadName = departmentHeadName;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getHalfOfYear() {
        return halfOfYear;
    }

    public void setHalfOfYear(String halfOfYear) {
        this.halfOfYear = halfOfYear;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

//    public Map<String, String> getPairs() {
//        return pairs;
//    }
//
//    public void setPairs(Map<String, String> pairs) {
//        this.pairs = pairs;
//    }
}
