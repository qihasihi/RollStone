package com.school.util.taglib;
/**
 * Created by zhengzhou on 14-10-13.
 */
public class SchoolTab{
    public SchoolTab(){super();}
    /**
     * 自定义标签,使用lastIndexOf
     * @param text
     * @param searchString
     * @return
     */
    public static int lastIndexOf(String text, String searchString) {
        return text.lastIndexOf(searchString);

    }

}
