package JAVABEAN;

import java.util.Date;

public class student {
    private String  studentid;
    private String  studentname;
    private String  major;
    private String  gender;
    private String  department;
    private String  classes;
    private Dorm dorm;
    private String  phoneid;
    private Date    entrytime;
    private String  username;
    private String  password;

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public Dorm getDorm() {
        return dorm;
    }

    public void setDorm(Dorm dorm) {
        this.dorm = dorm;
    }

    public String getPhoneid() {
        return phoneid;
    }

    public void setPhoneid(String phoneid) {
        this.phoneid = phoneid;
    }

    public Date getEntrytime() {
        return entrytime;
    }

    public void setEntrytime(Date entrytime) {
        this.entrytime = entrytime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "student{" +
                "studentid='" + studentid + '\'' +
                ", studentname='" + studentname + '\'' +
                ", major='" + major + '\'' +
                ", gender='" + gender + '\'' +
                ", department='" + department + '\'' +
                ", classes='" + classes + '\'' +
                ", dormitoryid=" + dorm +
                ", phoneid='" + phoneid + '\'' +
                ", entrytime=" + entrytime +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
