package JAVABEAN;

public class Dorm {
    private int id;
    private int number;
    private int build;    //楼栋
    private String status = "1"; //设置默认值
    public Dorm(){}
    public Dorm(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBuild() {
        return build;
    }

    public void setBuild(int build) {
        this.build = build;
    }

    @Override
    public String toString() {
        return "Dorm{" +
                "id=" + id +
                ", number=" + number +
                ", build=" + build +
                ", status='" + status + '\'' +
                '}';
    }
}
