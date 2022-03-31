package JAVABEAN;

public class fee {
    private String year;
    private String  months;
    private Dorm dorm;
    private String  electricfee;
    private String  electricnum;
    private String  waterfee;
    private String  waternum;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return months;
    }

    public void setMonth(String month) {
        this.months = month;
    }

    public Dorm getDorm() {
        return dorm;
    }

    public void setDorm(Dorm dorm) {
        this.dorm = dorm;
    }

    public String getElectricfee() {
        return electricfee;
    }

    public void setElectricfee(String electricfee) {
        this.electricfee = electricfee;
    }

    public String getElectricnum() {
        return electricnum;
    }

    public void setElectricnum(String electricnum) {
        this.electricnum = electricnum;
    }

    public String getWaterfee() {
        return waterfee;
    }

    public void setWaterfee(String waterfee) {
        this.waterfee = waterfee;
    }

    public String getWaternum() {
        return waternum;
    }

    public void setWaternum(String waternum) {
        this.waternum = waternum;
    }

    @Override
    public String toString() {
        return "fee{" +
                "year='" + year + '\'' +
                ", months='" + months + '\'' +
                ", dorm=" + dorm +
                ", electricfee='" + electricfee + '\'' +
                ", electricnum='" + electricnum + '\'' +
                ", waterfee='" + waterfee + '\'' +
                ", waternum='" + waternum + '\'' +
                '}';
    }
}
