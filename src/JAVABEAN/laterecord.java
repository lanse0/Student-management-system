package JAVABEAN;

import java.util.Date;

public class laterecord {
    private String  studentname;
    private Dorm dorm;
    private Date    latetime;
    private String  reason;

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public Dorm getDorm() {
        return dorm;
    }

    public void setDorm(Dorm dorm) {
        this.dorm = dorm;
    }

    public Date getLatetime() {
        return latetime;
    }

    public void setLatetime(Date latetime) {
        this.latetime = latetime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "laterecord{" +
                "studentname='" + studentname + '\'' +
                ", dorm=" + dorm +
                ", latetime=" + latetime +
                ", reason='" + reason + '\'' +
                '}';
    }
}
