package JAVABEAN;

import java.util.Date;

public class leavereturn {
    private String studentname;
    private Dorm dorm;
    private Date   leavetime;
    private Date   returntime;

    public Dorm getDorm() {
        return dorm;
    }

    public void setDorm(Dorm dorm) {
        this.dorm = dorm;
    }

    public Date getLeavetime() {
        return leavetime;
    }

    public void setLeavetime(Date leavetime) {
        this.leavetime = leavetime;
    }

    public Date getReturntime() {
        return returntime;
    }

    public void setReturntime(Date returntime) {
        this.returntime = returntime;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    @Override
    public String toString() {
        return "leavereturn{" +
                "studentname='" + studentname + '\'' +
                ", dormitoryid=" + dorm +
                ", leavetime=" + leavetime +
                ", returntime=" + returntime +
                '}';
    }
}
