package UTIL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.DataFormatException;

public class Util {
    public static Date strToDate(String dateStr,String param) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(param);
        Date dt = sf.parse(dateStr);
        return dt;
    }
    public static String dateToStr(Date dt,String param){
        SimpleDateFormat sf = new SimpleDateFormat(param);
        String dateStr = sf.format(dt);
        return dateStr;
    }
//    public static void main(String[] args) {
//        Date dt = null;
//        try {
//            dt = strToDate("1999-01-28 02:10","yyyy-MM-dd hh:mm");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        String dtstr = dateToStr(dt,"yyyy-MM-dd hh:mm:ss");
//        System.out.println(dt);
//        System.out.println(dtstr);
//    }
}
