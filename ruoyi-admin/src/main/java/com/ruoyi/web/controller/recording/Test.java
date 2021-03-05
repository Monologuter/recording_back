package com.ruoyi.web.controller.recording;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;

import java.io.IOException;
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
    public static void main(String[] args) throws ParseException, IOException {
        String[] rmPath = new String[]{"find ", "/home/ck/data/recordings/conference ", "-mtime  0   ", "-name ", "*.zip ", "-exec rm -rf {} ", "\\"};
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < rmPath.length; i++) {
            sb.append(rmPath[i]);
        }

        System.out.println(sb.toString());
    }
}
