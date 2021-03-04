package com.ruoyi.web.controller.recording;

import com.ruoyi.common.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author 马小姐
 * @ClassName Test
 * @Date 2021-03-04 14:27
 * @Version 1.0
 * @Description:
 */
public class Test {
    public static void main(String[] args) throws ParseException {


        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        System.out.println(sdf.format(date));
        String time = DateUtils.dateTimeNow();
        System.out.println("time = " + time);
    }
}
