package cn.zhang.com.util.Imp;

import org.thymeleaf.util.StringUtils;

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
}
