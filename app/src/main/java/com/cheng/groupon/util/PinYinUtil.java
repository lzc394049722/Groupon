package com.cheng.groupon.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class PinYinUtil {

    public static String getPinYin(String name) {

        try {
            HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
            format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大写
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//不要语音语调

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < name.length(); i++) {

                char c = name.charAt(i);

                String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if (pinyin.length > 0)
                    sb.append(pinyin[0]);

            }

            return sb.toString();

        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
            throw new RuntimeException("不正确的汉语拼音格式");
        }
    }

    public static char getLetter(String name) {
        return getPinYin(name).charAt(0);
    }

}
