---
title: Hexo-Next 使用设置|细则备注

tags: 
    - hexo 
    - next 
    - setting  

categories: 
    - setting
    - blog

description: Hexo-Next 使用设置|细则备注 🐳🐋🐟🐳🐋🐟🐳🐋🐳🐋🐟🐳🐋🐟🐳🐋  

date: 2017-01-010 13:00:00
---

Next 主题中一部分内容必须被记录
否则在修改时极其容易被遗忘。
主要包括：

<!-- more -->

##全局
###搜索功能
   ### 底部动画
   ### 右上角github连接
   ### 标签自动检测页面
   ### 评论
   ### 点击动态效果
   
   在网址输入如下
   ```
   http://7u2ss1.com1.z0.glb.clouddn.com/love.js
   ```
   然后将里面的代码copy一下，新建love.js文件并且将代码复制进去，然后保存。将love.js文件放到路径/themes/next/source/js/src里面，然后打开\themes\next\layout\_layout.swig文件,在末尾（在前面引用会出现找不到的bug）添加以下代码：
   ```
   <!-- 页面点击小红心 -->
   <script type="text/javascript" src="/js/src/love.js"></script>
   ```

   ### 访问量
   打开\themes\next\layout\_partials\footer.swig文件,
   在《div class="copyright"》 前加入以下这句：
   
   ```
   <script async src="https://dn-lbstatics.qbox.me/busuanzi/2.3/busuanzi.pure.mini.js"></script>
   ```
   之后再合适的位置添加显示统计代码：
   ```
    <div class="powered-by">
    <i class="fa fa-user-md"></i><span id="busuanzi_container_site_uv">
      本站访客数:<span id="busuanzi_value_site_uv"></span>
    </span>
    </div>
   ```
   该脚本有两种不同的计算统计代码
   pv统计：单用户连续点击n篇文章，记录n次访问量
   ```
    <span id="busuanzi_container_site_pv">
        本站总访问量<span id="busuanzi_value_site_pv"></span>次
    </span>
   ```
   uv统计：单用户连续点击n篇文章，记录1次访问量
   ```
    <span id="busuanzi_container_site_uv">
      本站总访问量<span id="busuanzi_value_site_uv"></span>次
    </span>
   ```

###首页
   ### 标签-自检
   ### 网站名称标题对齐
   位置：
   //themes/next/source/css/_variables/Mist.styl
   //themes/next/source/css/_variables/Mist.styl
   //themes/next/source/css/_variables/Mist.styl
   //themes/next/source/css/_variables/Mist.styl
   加入
   ```
   // sit-tittle
   $site-meta-text-align             = left 
   ```
   ### 文章折叠
   ### 侧边栏
   ### 底部内容
   位置：\themes\next\layout\_partials\footer.swig
   搜索标签：
   <div class="author">
   <div class="powered-by">
   <div class="theme-info">
   更改以上3标签内部链接内容
   
   同时可对以上标签弱修改，可修改语言配置文件
   位置：\themes\next\languages\ 
   
   
   ### 头像
   ### 标签
