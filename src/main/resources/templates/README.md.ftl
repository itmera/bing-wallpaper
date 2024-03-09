## Bing Wallpaper <#if name ==""><#else>(${name})</#if>
![](${one.url}&w=1000)Today: [${one.desc}](${one.url})
|      |      |      |
| :----: | :----: | :----: |
<#list list?chunk(3) as row>
<#list row as item>|![](${item.url}&pid=hp&w=384&h=216&rs=1&c=4) ${item.date} [download 4k](${item.url})</#list>|
</#list>
### 历史归档：
<#list month?chunk(8) as row>
<#list row as item>[${item}](/picture/${item}/) |</#list>
</#list>