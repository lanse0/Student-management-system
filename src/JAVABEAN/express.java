package JAVABEAN;

import java.util.Date;

public class express {
    private String studentname;
    private Dorm dorm;
    private Date arrivetime;
    private Date collecttime;
    private int collectnum;
    private String collectname;
    private String collectphoneid;

    public String getCollectname() {
        return collectname;
    }

    public void setCollectname(String collectname) {
        this.collectname = collectname;
    }

    public String getCollectphoneid() {
        return collectphoneid;
    }

    public void setCollectphoneid(String collectphoneid) {
        this.collectphoneid = collectphoneid;
    }

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

    public Date getArrivetime() {
        return arrivetime;
    }

    public void setArrivetime(Date arrivetime) {
        this.arrivetime = arrivetime;
    }

    public Date getCollecttime() {
        return collecttime;
    }

    public void setCollecttime(Date collecttime) {
        this.collecttime = collecttime;
    }

    public int getCollectnum() {
        return collectnum;
    }

    public void setCollectnum(int collectnum) {
        this.collectnum = collectnum;
    }
}
