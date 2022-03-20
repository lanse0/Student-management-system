package JAVABEAN;

import java.util.Date;

public class guarantee {
    private Dorm dorm; //需改成对象类型
    private String studentname;
    private String goodsname;
    private String reason;
    private Date guaranteetime;
    private String phoneid;
    private String guaranteestaus="0";


    public String getPhoneid() {
        return phoneid;
    }

    public void setPhoneid(String phoneid) {
        this.phoneid = phoneid;
    }

    public String getGuaranteestaus() {
        return guaranteestaus;
    }

    public void setGuaranteestaus(String guaranteestaus) {
        this.guaranteestaus = guaranteestaus;
    }

    public Dorm getDorm() {
        return dorm;
    }

    public void setDorm(Dorm dorm) {
        this.dorm = dorm;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getGuaranteetime() {
        return guaranteetime;
    }

    public void setGuaranteetime(Date guaranteetime) {
        this.guaranteetime = guaranteetime;
    }

    @Override
    public String toString() {
        return "guarantee{" +
                "dormitoryid=" + dorm +
                ", studentname='" + studentname + '\'' +
                ", goodsname='" + goodsname + '\'' +
                ", reason='" + reason + '\'' +
                ", guaranteetime=" + guaranteetime +
                ", phoneid='" + phoneid + '\'' +
                ", guaranteestaus='" + guaranteestaus + '\'' +
                '}';
    }
}
