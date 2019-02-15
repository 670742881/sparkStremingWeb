import com.spark.service.TimeUtil;

import java.util.Date;

public class test {


    public static void main(String[] args) {
        String day=   TimeUtil.parseLong2String(new Date().getTime(),"yyyy-MM-dd");
        System.out.println(day);
    }


    }

