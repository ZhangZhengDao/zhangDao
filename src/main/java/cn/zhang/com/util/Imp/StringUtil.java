package cn.zhang.com.util.Imp;

import org.thymeleaf.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {
    public static String  CharOfThis(String search){
        if (search.isEmpty()){
            return null;
        }
            /** . ? + $ ^ [ ] ( ) { } | \ /*/
            if (StringUtils.equals(search, "*") ||
                    StringUtils.equals(search, "*") ||
                    StringUtils.equals(search, ".") ||
                    StringUtils.equals(search, "?") ||
                    StringUtils.equals(search, "$") ||
                    StringUtils.equals(search, "+") ||
                    StringUtils.equals(search, "^") ||
                    StringUtils.equals(search, "[") ||
                    StringUtils.equals(search, "]") ||
                    StringUtils.equals(search, "(") ||
                    StringUtils.equals(search, ")") ||
                    StringUtils.equals(search, "{") ||
                    StringUtils.equals(search, "}") ||
                    StringUtils.equals(search, "|") ||
                    StringUtils.equals(search, "\\") ||
                    StringUtils.equals(search, "/")) {
                search = "\\" + search;
        }
        return search;
    }
    /*
    * 获取当前系统时间
    * */
    public static String getData(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = simpleDateFormat.format(date);
        return str;
    }
}
