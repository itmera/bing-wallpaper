## Bing Wallpaper <#if name ==""><#else>(${name})</#if>
![](${one.url}&w=1000)Today: [${one.desc}](${one.url})
|      |      |      |
| :----: | :----: | :----: |
<#list list>|<#items as item>![](${item.url}&pid=hp&w=384&h=216&rs=1&c=4) ${item.date} [download 4k](${item.url})<#if item_index % 3 ==2>|
</#if>|</#items></#list>
### 历史归档：
<#list month as item>[${item}](/picture/${item}/) | <#if item_index % 8 ==7 >
</#if></#list>