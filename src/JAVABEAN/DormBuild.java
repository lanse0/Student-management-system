package JAVABEAN;

public class DormBuild {
    private int number;
    private String status = "1";
    public DormBuild() { }
    public DormBuild(int number) {
        this.number = number;
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

    @Override
    public String toString() {
        return "DormBuild{" +
                ", number=" + number +
                ", status='" + status + '\'' +
                '}';
    }
}
