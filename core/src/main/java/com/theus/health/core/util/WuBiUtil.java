package com.theus.health.core.util;

import cn.hutool.core.io.resource.ClassPathResource;
import com.theus.health.core.model.CharacterElement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 汉字转五笔码工具
 *
 * @author tangwei
 * @date 2019-11-30 16:40
 */
public class WuBiUtil {
    private static Map<Integer, CharacterElement> map = new HashMap<>(20993);

    // 载入数据
    static {
        try {
            // 五笔字典文件存放目录
            ClassPathResource classPathResource = new ClassPathResource("db/dict.db");
            InputStreamReader reader = new InputStreamReader(classPathResource.getStream());
            BufferedReader br = new BufferedReader(reader);
            String l;
            while ((l = br.readLine()) != null) {
                CharacterElement e = new CharacterElement(l);
                map.put(e.getUnicode(), new CharacterElement(l));
            }
            br.close();
        } catch (Exception e) {
            System.out.print("Dict: " + e.getMessage());
        }
    }

    /**
     * 获取汉字五笔码首字母
     *
     * @param str 汉字字符串
     * @return 五笔首拼
     */
    public static String getWubiHeadChar(final String str) {
        final StringBuilder sb = new StringBuilder();
        for (Character ch : str.toCharArray()) {
            sb.append(getWubi(ch).length() > 0 ? getWubi(ch).charAt(0) : "");
        }
        return sb.toString();
    }

    public static String getWubi(final String str) {
        final StringBuilder sb = new StringBuilder();
        for (Character ch : str.toCharArray()) {
            sb.append(getWubi(ch));
        }
        return sb.toString();
    }

    private static String getWubi(final Character ch) {
        if (ch != null) {
            if (map.containsKey(ch.hashCode())) {
                return map.get(ch.hashCode()).getWubi();
            } else {
                return ch.toString();
            }
        }
        return "";
    }
}
