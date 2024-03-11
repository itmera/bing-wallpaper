package com.zzlan.bing.wallpaper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zzlan.bing.wallpaper.model.Image;
import com.zzlan.bing.wallpaper.utils.FreeMarker;
import com.zzlan.bing.wallpaper.utils.HttpClient;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final String BING_URL = "https://cn.bing.com";
    private static final String BING_API = BING_URL+"/HPImageArchive.aspx?format=js&idx=0&n=10&nc=1612409408851&pid=hp&FORM=BEHPTB&uhd=1&uhdwidth=3840&uhdheight=2160";
    public static void main(String[] args) throws IOException, InterruptedException, TemplateException {

        // 更新数据
        Integer day = args.length >= 1 ? Integer.parseInt(args[0]) : 0;
        List<Image> images = updateData(day);

        // 按日期循环生成 历史 README.md 存入 picture
        LinkedHashMap<String, List<Image>> monthMap = convertImgListToMonthMap(images);
        Set<String> monthLink = monthMap.keySet();
        // 生成 README.md
        doGenerateTOMd(images.subList(0,30),monthLink,"md","");
        // 生成 index.html
        doGenerateTOMd(images.subList(0,30),monthLink,"html","");

        for (String key : monthLink) {
            // 生成月份 README.md
            doGenerateTOMd(monthMap.get(key),monthLink,"md",key);
            // 生成月份 html
            doGenerateTOMd(monthMap.get(key),monthLink,"html",key);
        }
    }


    private static void doGenerateTOMd(List<Image> images,Set<String> monthLink,String fileType,String month) throws IOException, TemplateException {
        HashMap<String, Object> modelData = new HashMap<>();
        // 模型数据
        modelData.put("name",month);

        HashMap<String, Object> imageOne = new HashMap<>();
        imageOne.put("url", images.get(0).getUrl());
        imageOne.put("desc", images.get(0).getDesc());
        modelData.put("one",imageOne);

        modelData.put("list",images);
        modelData.put("month",monthLink);
        // 文件路径
        String templateFile;
        String outputFile;
        Path outputPath= Path.of("docs/");
        // 写入模版

        if (Objects.equals(month, "")) {
            if (Objects.equals(fileType,"html")){
                templateFile ="index.html.ftl";
                outputFile = outputPath.resolve("index.html").toString();
            }else{
                templateFile ="README.md.ftl";
                outputFile = "README.md";
            }
        } else {
            if (Objects.equals(fileType,"html")){
                templateFile ="index.html.ftl";
                outputFile = outputPath.resolve(month+".html").toString();
            }else{
                templateFile ="README.md.ftl";
                outputPath = Path.of("picture/").resolve(month);
                outputFile = outputPath.resolve("README.md").toString();
            }
            // 创建目录
            if (!Files.exists(outputPath)) {
                Files.createDirectories(outputPath);
            }
        }
        FreeMarker.doGenerate(templateFile,outputFile,modelData);
    }

    public static LinkedHashMap<String, List<Image>> convertImgListToMonthMap(List<Image> imagesList) {

        LinkedHashMap<String, List<Image>> monthMap = new LinkedHashMap<>();
        for (Image images : imagesList) {
            if (images.getUrl() == null) {
                continue;
            }
            String key = images.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM"));
            if (monthMap.containsKey(key)) {
                monthMap.get(key).add(images);
            } else {
                List<Image> list = new ArrayList<>();
                list.add(images);
                monthMap.put(key, list);
            }
        }
        return monthMap;
    }

    private static List<Image> updateData(Integer day) throws IOException, InterruptedException {
        ObjectMapper mapper =  new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        // 读取历史数据
        List<Image> bingList = new ArrayList<>();
        String bingFile = "bing-wallpaper.json";
        if ( Files.exists(Path.of(bingFile))){
            bingList = mapper.readValue(new File(bingFile), new TypeReference<>() {});
        }
        bingList.add(0,getDate(day));
        // 去重复
        bingList = bingList.stream().distinct().collect(Collectors.toList());
        // 根据日期进行排序
        bingList.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        // 写入到json文件
        mapper.writeValue(new File(bingFile),bingList);
        return bingList;

    }

    private static Image getDate(Integer day) throws IOException, InterruptedException {
        String data = new HttpClient().getData(BING_API);
        ObjectMapper mapper =  new ObjectMapper();
        JsonNode json = mapper.readTree(data);
        JsonNode image = json.get("images").get(day);

        String enddate = image.get("enddate").asText();
        System.out.println("new date: "+enddate);
        LocalDate date = LocalDate.parse(enddate, DateTimeFormatter.ofPattern("yyyyMMdd"));

        String url = image.get("url").asText();
        String fullUrl=BING_URL+url.substring(0,url.indexOf("&"));

        String desc = image.get("copyright").asText();

        return new Image(date, fullUrl, desc);
    }
}