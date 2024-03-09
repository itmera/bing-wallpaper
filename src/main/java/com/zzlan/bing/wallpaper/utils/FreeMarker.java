package com.zzlan.bing.wallpaper.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;

public class FreeMarker {
    public  static  void doGenerate (String templateFile, String outputFile, HashMap<String, Object> modelData) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        //     指定模版文件所在的路径
        cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        // 设置字符编码
        cfg.setDefaultEncoding("utf-8");
        // 设置数字格式
        cfg.setNumberFormat("0.########");
        // 加载模版
        Template template = cfg.getTemplate(templateFile);
        //写入文件
        Writer out = new FileWriter(outputFile);
        // 生成文件
        template.process(modelData,out);
        out.close();
    }
}
