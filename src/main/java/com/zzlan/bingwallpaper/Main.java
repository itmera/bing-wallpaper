package com.zzlan.bingwallpaper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zzlan.bingwallpaper.model.Image;
import com.zzlan.bingwallpaper.utils.FreeMarker;
import com.zzlan.bingwallpaper.utils.HttpClient;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        List<String> monthLink = images.stream().map(Image::getDate).map(date -> date.format(DateTimeFormatter.ofPattern("yyyy-MM")))
                .distinct()
                .collect(Collectors.toList());
        System.out.println(monthLink);
        // 生成 README.md
        doGenerateTOMd(images.subList(0,30),monthLink,"md","");
        // 生成 index.html
        doGenerateTOMd(images.subList(0,30),monthLink,"html","");

        // 按日期循环生成 历史 README.md 存入 picture
        Map<String, List<Image>> monthMap = convertImgListToMonthMap(images);
        for (String key : monthMap.keySet()) {
            System.out.println(key);
            // 生成月份 README.md
            doGenerateTOMd(monthMap.get(key),monthLink,"md",key);
            // 生成月份 html
            doGenerateTOMd(monthMap.get(key),monthLink,"html",key);
        }
    }


    private static void doGenerateTOMd(List<Image> images,List<String> monthLink,String fileType,String month) throws IOException, TemplateException {
        HashMap<String, Object> modelData = new HashMap<>();
        // today 大图
        HashMap<String, Object> imageOne = new HashMap<>();
        modelData.put("name",month);
        imageOne.put("url", images.get(0).getUrl());
        imageOne.put("desc", images.get(0).getDesc());
        modelData.put("one",imageOne);
        modelData.put("list",images);
        modelData.put("month",monthLink);
        String templateFile ="README.md.ftl";
        String outputFile = "README.md";


        // 写入模版
        if (!Objects.equals(month, "")){
            Path fileDir;
            if (Objects.equals(fileType,"html")){
                fileDir = Paths.get("docs/");
                templateFile ="index.html.ftl";
                outputFile = fileDir.resolve(month+".html").toString();
            }else{
                fileDir = Paths.get("picture/").resolve(month);
                templateFile ="README.md.ftl";
                outputFile = fileDir.resolve("README.md").toString();
            }
            // 创建目录
            if (!Files.exists(fileDir)) {
                Files.createDirectories(fileDir);
            }
        }else{
            if (Objects.equals(fileType,"html")){
                templateFile ="index.html.ftl";
                outputFile = Paths.get("docs/").resolve("index.html").toString();
            }
        }
        FreeMarker.doGenerate(templateFile,outputFile,modelData);
    }

    public static Map<String, List<Image>> convertImgListToMonthMap(List<Image> imagesList) {

        Map<String, List<Image>> monthMap = new LinkedHashMap<>();
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
        List<Image> bingList = mapper.readValue(new File("bing-wallpaper.json"), new TypeReference<List<Image>>() {});
        bingList.add(day,getDate());
        // 去重复
        bingList = bingList.stream().distinct().collect(Collectors.toList());
        // 根据日期进行排序
        bingList.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        // 写入到json文件
        mapper.writeValue(new File("bing-wallpaper.json"),bingList);
        return bingList;

    }

    private static Image getDate() throws IOException, InterruptedException {
        String data = new HttpClient().getData(BING_API);
        ObjectMapper mapper =  new ObjectMapper();
        JsonNode json = mapper.readTree(data);
        JsonNode image = json.get("images").get(0);
        String enddate = image.get("enddate").asText();
        System.out.println(enddate);
        LocalDate date = LocalDate.parse(enddate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        String url = image.get("url").asText();
        String fullUrl=BING_URL+url.substring(0,url.indexOf("&"));
        String desc = image.get("copyright").asText();
        return new Image(date, fullUrl, desc);
    }
}