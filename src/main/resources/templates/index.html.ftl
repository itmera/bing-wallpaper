<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />

  <link href="./css/main.css?v2" rel="stylesheet" />
  <title>Bing 壁纸</title>
</head>
<body class="bg-gray-200">
  <div class="relative h-screen">
    <img src="${one.url}&pid=hp&w=2000"
      class="w-full h-full object-cover object-center overflow-hidden" />
    <div class="absolute inset-0 flex justify-center items-center">
      <div class="flex flex-col items-center">
        <h1 class="text-3xl lg:text-5xl text-gray-100 py-4 rounded hover:bg-slate-500/40">
          Bing Wallpaper <#if name ==""><#else>(${name})</#if>
        </h1>
        <p
          class="text-gray-300 text-lg mx-2 text-center hover:bg-slate-500/40 rounded max-h-20 leading-7 overflow-x-scroll">
          ${one.desc}
        </p>
      </div>
    </div>
    <!-- 导航 -->
    <div class="absolute inset-x-0 bottom-2 flex justify-center text-lg text-center gap-0.5">
      <p class="hover:bg-green-100 py-1 px-4 bg-white rounded-l-xl">
        <a href="./index.html">首页</a>
      </p>
      <p id="lefttabshow" class="hover:bg-green-100 py-1 px-4 bg-white rounded-r-xl">
        归档
      </p>
    </div>
  </div>

  <!-- 图片列表 -->
  <div class="max-w-screen-xl my-8 p-2 mx-auto grid md:grid-cols-2 lg:grid-cols-4 gap-6 divide-y-2">
<#list list as item>
    <div class="img_item">
      <img src="${item.url}&pid=hp&w=384&h=216&rs=1&c=4"  alt=""/>
      <div>
        <p>${item.date}</p>
        <p> <a href="${item.url}" target="_blank">Download
            4k</a> </p>
      </div>
    </div>
</#list>

  </div>

  <!-- 月份链接 -->
  <div class="max-w-screen-xl mx-auto p-6 bg-white rounded-2xl border-x-8 border-solid border-gray-200">
    <p class="text-2xl px-4">按月份查看</p>
    <div class="grid grid-cols-4 lg:grid-cols-6 xl:grid-cols-8 p-4 gap-3">
      <!-- 月份列表 -->
<#list month as item>
      <p class="month_item <#if name ==item>month_now</#if>"><a href="${item}.html">${item}</a></p>
</#list>

    </div>
  </div>

  <div class="md:w-1/2 mx-auto pb-20 text-center p-6 text-gray-600">
    <p class="text-xl md:text-3xl">Powered by bing-wallpaper</p>
    <p>@2024 Copyright Itmera</p>
  </div>

  <div id="lefttab" class="hidden">
    <div
      class="fixed inset-y-0 left-0 bg-white/75 flex flex-col items-center w-40 gap-y-2 text-xl text-gray-600 overflow-y-scroll">
      <p class="text-3xl w-40 border-t-2 text-center bg-gray-100/50">
        月份列表
      </p>
      <!-- 月份列表 -->
<#list month as item>
      <p class="sidabar_item <#if name ==item>month_now</#if>"><a href="${item}.html">${item}</a></p>
</#list>
    </div>
  </div>
</body>

<script>
  window.onload = () => {
    document.getElementById("lefttab").addEventListener("click", (e) => {
      if (e.target.tagName === "P") {
        e.target.parentNode.parentElement.className = "hidden";
      }
    });
    document.getElementById("lefttabshow").addEventListener("click", (e) => {
      document.getElementById("lefttab").className = "";
    });
  };
</script>
</html>