package com.theus.health.core.util;

import java.io.File;
import java.net.URLDecoder;
import java.util.Objects;

/**
 * 文件处理工具
 * @author tangwei
 * @date 2020-03-08 10:48
 */
public class HFileUtil {

    /**
     * 获取项目目录绝对路径
     *
     * @return 获取项目目录绝对路径
     */
    public static String getProjectPath() {
        try {
            String path = Objects.requireNonNull((com.theus.health.core.util.HFileUtil.class).getClassLoader()
                    .getResource("")).getPath();
            File dir = new File(new File(path).getParent());
            path = dir.getParent();
            //处理在中文编码环境下，空格会变成“%20”问题
            path = URLDecoder.decode(path, "utf-8");
            return path;
        }catch (Exception ex){
            ex.printStackTrace();
            return "C:";
        }
    }
}
