package UTIL;

/*
 * 分页的工具类 里面封装分页的信息
 * */
public class PageUtils {
    private int currPage = 1;   //当前页 第几页
    private int pageSize = 5;  //每页的记录条数
    private int totalSize;  //分页表的总的记录条数
    private int totalPage;  //总的页数

    //private List list;    //当前页的记录集合
    public PageUtils() {
    }

    public PageUtils(int currPage, int pageSize) {
        super();
        this.currPage = currPage;
        this.pageSize = pageSize;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    //怎么设置总页数
    //  10  5= 2      11 5  =3
    public void setTotalPage(int totalSize) {
        if (totalSize % this.pageSize == 0) {
            this.totalPage = totalSize / this.pageSize;
        } else {
            this.totalPage = totalSize / this.pageSize + 1;
        }

    }

    @Override
    public String toString() {
        return "PageUtils [currPage=" + currPage + ", pageSize=" + pageSize
                + ", totalSize=" + totalSize + ", totalPage=" + totalPage + "]";
    }
}
