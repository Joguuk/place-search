package com.jjozerg.search.common.utils;

/**
 * packageName : com.jjozerg.search.common.utils
 * fileName : StringUtils
 * author : joguk
 * description : StringUtils
 */
public class StringUtils {
    public static String removeTagAndWhiteSpace(String text) {
        String whiteSpaceRemovedText = text.replaceAll("\\s", "");
        String tagRemovedText = whiteSpaceRemovedText.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
        return tagRemovedText;
    }
}
