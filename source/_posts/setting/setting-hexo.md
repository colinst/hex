---
title: Hexo-Next 设置|备忘
copyright: true  
tags: 
    - hexo 
    - setting  

categories: 
    - setting
    - blog

date: 2017-01-010 13:00:00
---

  嗨，Hello,本文为第一篇Hexo博客文章 🐳🐋🐟🐳🐋🐟🐳🐋🐳🐋🐟
  代码基地[github/hongxii](https://github.com/colinst)!
  个人网站域名(架设中)[gitmmp.com](https://gitmmp.com)!
  欢迎来到我的博客 [Blog.gitmmp.com](https://colinst.github.io)!  
Hexo + Next的组合可以无限优化，甚至某些组织站点都用hexo作为发布,个人博客不必花费太多的时间，如果做组织网站则建议使用灵活度更高的主题（[icarus](https://blog.zhangruipeng.me/hexo-theme-icarus/)）。
仅用一文说明设计部署等关键点，个性化范围等关键点，花太多时间在上面也不好，本文轻改Next主题。以下为目录：
<!-- more -->

## 文章目录：
<!-- TOC -->

- [基建 Base](#基建-base)
    - [依赖环境 environment](#依赖环境-environment)
    - [常用命令 command](#常用命令-command)
- [主题轻改 Theme-Next](#主题轻改-theme-next)
    - [基本信息 baseInfo](#基本信息-baseinfo)
        - [站点设置 siteInfo](#站点设置-siteinfo)
        - [侧边栏信息 authorInfo](#侧边栏信息-authorinfo)
    - [首页样式 viewIndex](#首页样式-viewindex)
        - [博文块样式 blockView](#博文块样式-blockview)
        - [底层动画   cssFlash](#底层动画---cssflash)
        - [右上Github](#右上github)
        - [页尾信息 footer](#页尾信息-footer)
    - [其他样式 views](#其他样式-views)
        - [关于 about](#关于-about)
        - [标签 tags](#标签-tags)
        - [分类 categorise](#分类-categorise)
        - [搜索 search](#搜索-search)
        - [自定义 ...](#自定义-)
    - [文章样式 mainView](#文章样式-mainview)
        - [标签优化](#标签优化)
        - [评论引入](#评论引入)
        - [谷歌-百度收录](#谷歌-百度收录)
        - [访问量查看](#访问量查看)
        - [底部版权信息](#底部版权信息)

<!-- /TOC -->





## 基建 Base
### 依赖环境 environment

    //在node.js官网去下载安装
    node.js       
          
    //Shell 
    //hexo-client  hexo客户端安装         
    npm install hexo-cli -g
    
    //hexo-deployer-git   //git工具
    npm install hexo-deployer-git --save    
   
    //hexo-md-img-plagin  //图片编辑格式统一插件
    npm install https://github.com/CodeFalling/hexo-asset-image --save   
    
    //hexo-generator-search   //搜索插件
    npm install hexo-generator-search --save    
     
    //标签云插件
    npm install hexo-tag-cloud@^2.0.* --save
    
    //站点地图生成插件  百度和谷歌
    npm install hexo-generator-baidu-sitemap --save    
    npm install hexo-generator-sitemap --save           //谷歌
    
    //多媒体插件
    npm install hexo-tag-aplayer    //音频
    npm install hexo-tag-dplayer    //视频
    
    //貌似全局用的安装工具
    npm install -g npm-install-peers
         
github-create the Rep[repName=uid.github.io]  

### 常用命令 command

####[写作细节](https://hexo.io/docs/writing.html)

    $ hexo init <folder>    //hexo io 建立hexo项目文件夹
    $ cd <folder>           //cd io
    $ npm install           //        部署项目文件

####转移项目时  
1.先git项目到本地，文件夹改名（如io->ioo）  
2.新建io 文件夹，cd进去，npm install  
3.此时将ioo中文件全部覆盖到io文件夹  
之后可以在自己的ide中编辑了

#### 在_posts 文件夹中新建md文档编辑就好
md文件头：  

    ---
    title: 标题
    
    tags: 
        - 标签1  
        - 标签2  
        
    categories: 
        - 分类1   
        - 分类2  
    
    date: 2018-08-008 13:00:00
    ---
    
    index页显示简介内容
    <!-- more -->   //简介-正文分割符
    
文件头根据需要可加入别的属性  
#### 发布
    $ hexo generate     //将md文件生成需要部署的html静态页面
    $ hexo server       //本地启动查看 [http://localhost:4000/](http://localhost:4000/)
    $ hexo deploy       //发布到git地址
生成发布可直接简写：  

    $ hexo g -d
    $ hexo d -g

[发布细节](https://hexo.io/docs/deployment.html)  
可以多定义几个deloy，deloy命令后会依次来repo,
同时一起更新到github,gitlee,码云等。
前提要创建好repo，对应站点定义好SSH key。


## 主题轻改 Theme-Next
### 基本信息 baseInfo
#### 站点设置 siteInfo
#### 侧边栏信息 authorInfo

### 首页样式 viewIndex
#### 博文块样式 blockView
使用Gemini主题下修改
#### 首页文章标题与文章简介的距离
    默认值60
    更改css样式 margin{上右下左}/{上右下}/{上下-右左}
    src/setNext.md
    位置：themes/next/source/css/_common/components/post/post-meta.styl
    行号：02
#### 首页文章标题-对齐
    位置：themes/next/source/css/_common/components/post/post-title.styl
    行号：02
    默认：center
    改为：left
#### 阅读全文按钮调整
    位置：themes/next/source/css/_variables/base.styl
    行号：164-165
    默认： $btn-default-border-width       = 2px
          $btn-default-border-color       = $black-deep
    
    对齐-边距：
    位置：themes/next/source/css/_common/components/post/post-button.styl
    添加：text-align: left;    //对齐
          margin-top: 4px;      //与简介边距$Ans*2
    
    按钮边距：
    位置：themes/next/source/css/_common/components/buttons.styl
    修改：padding: 0 0px;
    默认：padding: 0 2px;
#### 底层动画   cssFlash
#### 右上Github 
#### index页面加载条
这个如果用的是比较新的NexT主题，只需要在配置文件里面进行修改就可以了。
旧的话，就需要对/next/layout/_partials/head.swig文件做些修改，添加对应的代码。  
```
    <script src="//cdn.bootcss.com/pace/1.0.2/pace.min.js"></script>
    <link href="//cdn.bootcss.com/pace/1.0.2/themes/pink/pace-theme-flash.css" rel="stylesheet">
    <style>
    .pace .pace-progress {
        background: #1E92FB; /*进度条颜色*/
        height: 3px;
    }
    .pace .pace-progress-inner {
         box-shadow: 0 0 10px #1E92FB, 0 0 5px     #1E92FB; /*阴影颜色*/
    }
    .pace .pace-activity {
        border-top-color: #1E92FB;    /*上边框颜色*/
        border-left-color: #1E92FB;    /*左边框颜色*/
    }
    </style>
```

#### 标签云
    
    找到文件 next/layout/_macro/sidebar.swig, 然后添加如下内容。
    
    {% if site.tags.length > 1 %}
    <script type="text/javascript" charset="utf-8" src="/js/tagcloud.js"></script>
    <script type="text/javascript" charset="utf-8" src="/js/tagcanvas.js"></script>
    <div class="widget-wrap">
        <h3 class="widget-title">Tag Cloud</h3>
        <div id="myCanvasContainer" class="widget tagcloud">
            <canvas width="250" height="250" id="resCanvas" style="width=100%">
                {{ list_tags() }}
            </canvas>
        </div>
    </div>
    {% endif %}

#### 页尾信息 footer
   位置：\themes\next\layout\_partials\footer.swig
   搜索标签：
   <div class="author">
   <div class="powered-by">
   <div class="theme-info">
   更改以上3标签内部链接内容
   
   同时可对以上标签弱修改，可修改语言配置文件
   位置：\themes\next\languages\ 

### 其他样式 views
#### 关于 about
#### 标签 tags
#### 分类 categorise
#### 搜索 search

添加本地搜索  
安装 hexo-generator-search，在nanshanyi.github.io目录下执行以下命令：

    $ npm install hexo-generator-search --save
在   next/_config.yml添加  

    search: 
      path: search.xml
      field: post
      format: html
      limit: 10000
同时在该文件中开启search功能 local_search改为true
    
    local_search:
      enable: true

#### 自定义 ...  
### 文章样式 mainView
#### 标签优化


#### 谷歌-百度收录
#### 访问量查看
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

#### 底部版权信息
在目录 next/layout/_macro/下添加 my-copyright.swig：
```
    {% if page.copyright %}
    <div class="my_post_copyright">
      <script src="//cdn.bootcss.com/clipboard.js/1.5.10/clipboard.min.js"></script>
    
      <!-- JS库 sweetalert 可修改路径 -->
      <script src="https://cdn.bootcss.com/jquery/2.0.0/jquery.min.js"></script>
      <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
      <p><span>本文标题:</span><a href="{{ url_for(page.path) }}">{{ page.title }}</a></p>
      <p><span>文章作者:</span><a href="/" title="访问 {{ theme.author }} 的个人博客">{{ theme.author }}</a></p>
      <p><span>发布时间:</span>{{ page.date.format("YYYY年MM月DD日 - HH:MM") }}</p>
      <p><span>最后更新:</span>{{ page.updated.format("YYYY年MM月DD日 - HH:MM") }}</p>
      <p><span>原始链接:</span><a href="{{ url_for(page.path) }}" title="{{ page.title }}">{{ page.permalink }}</a>
        <span class="copy-path"  title="点击复制文章链接"><i class="fa fa-clipboard" data-clipboard-text="{{ page.permalink }}"  aria-label="复制成功！"></i></span>
      </p>
      <p><span>许可协议:</span><i class="fa fa-creative-commons"></i> <a rel="license" href="https://creativecommons.org/licenses/by-nc-nd/4.0/" target="_blank" title="Attribution-NonCommercial-NoDerivatives 4.0 International (CC BY-NC-ND 4.0)">署名-非商业性使用-禁止演绎 4.0 国际</a> 转载请保留原文链接及作者。</p>  
    </div>
    <script> 
        var clipboard = new Clipboard('.fa-clipboard');
          $(".fa-clipboard").click(function(){
          clipboard.on('success', function(){
            swal({   
              title: "",   
              text: '复制成功',
              icon: "success", 
              showConfirmButton: true
              });
            });
        });  
    </script>
    {% endif %}
```
在目录next/source/css/_common/components/post/下添加my-post-copyright.styl：
```
    .my_post_copyright {
      width: 85%;
      max-width: 45em;
      margin: 2.8em auto 0;
      padding: 0.5em 1.0em;
      border: 1px solid #d3d3d3;
      font-size: 0.93rem;
      line-height: 1.6em;
      word-break: break-all;
      background: rgba(255,255,255,0.4);
    }
    .my_post_copyright p{margin:0;}
    .my_post_copyright span {
      display: inline-block;
      width: 5.2em;
      color: #b5b5b5;
      font-weight: bold;
    }
    .my_post_copyright .raw {
      margin-left: 1em;
      width: 5em;
    }
    .my_post_copyright a {
      color: #808080;
      border-bottom:0;
    }
    .my_post_copyright a:hover {
      color: #a3d2a3;
      text-decoration: underline;
    }
    .my_post_copyright:hover .fa-clipboard {
      color: #000;
    }
    .my_post_copyright .post-url:hover {
      font-weight: normal;
    }
    .my_post_copyright .copy-path {
      margin-left: 1em;
      width: 1em;
      +mobile(){display:none;}
    }
    .my_post_copyright .copy-path:hover {
      color: #808080;
      cursor: pointer;
    }
```
修改next/layout/_macro/post.swig，在代码
```
<div>
      {% if not is_index %}
        {% include 'wechat-subscriber.swig' %}
      {% endif %}
</div>
```
之前添加增加如下代码：
```
<div>
      {% if not is_index %}
        {% include 'my-copyright.swig' %}
      {% endif %}
</div>
```
修改next/source/css/_common/components/post/post.styl文件，在最后一行增加代码：
```
@import "my-post-copyright"
```
保存重新生成即可。 
如果要在该博文下面增加版权信息的显示，需要在 Markdown 中增加copyright: true的设置，类似：
```
    ---
    title: 前端小项目：使用canvas绘画哆啦A梦
    date: 2017-05-22 22:53:53
    tags: canvas
    categories: 前端
    copyright: true
    ---
```

### 三方

#### 评论-来必力
1.登陆[官网](https://livere.com/) 
进行[注册](https://was.livere.me/register?lang=zh-cn)等事宜  
2.Next中进行设置  

Next中设置
1.首先在 _config.yml 文件中添加如下配置：  

    # Support for LiveRe comments system.
    # You can get your uid from https://livere.com/insight/myCode (General web site)
    livere_uid: your uid

#### 评论-valine
我们的评论系统其实是放在Leancloud上的，因此首先需要去注册一个账号  
[Leancloud官网](https://leancloud.cn/dashboard/data.html?appid=dU4DFkRRQqU30bvT0uXQmhSw-gzGzoHsz#/)
注册完以后需要创建一个应用，名字可以随便起，然后 进入应用->设置->应用key  
获取你的appid 和 appkey  

Next中已经内置，在_config中设置开启

    valine:
      enable: true
      appid:  your app id
      appkey: your app key
      notify: false # mail notifier , https://github.com/xCss/Valine/wiki
      verify: false # Verification code
      placeholder: ヾﾉ≧∀≦)o来啊，快活啊! 
      guest_info: nick,mail,link
      pageSize: 10


#### SEO优化
    
#####站点地图
站点地图即sitemap， 是一个页面，上面放置了网站上需要搜索引擎抓取的所有页面的链接。
站点地图可以告诉搜索引擎网站上有哪些可供抓取的网页，以便搜索引擎可以更加智能地抓取网站。  
生成站点地图
安装百度和Google的站点地图生成插件：
    
    npm install hexo-generator-sitemap --save       //google
    npm install hexo-generator-baidu-sitemap --save       
#####修改配置文件
修改站点配置文件_config.yml，添加以下内容：

    # 自动生成sitemap
    sitemap:
      path: sitemap.xml
    baidusitemap:
      path: baidusitemap.xml    
在hexo g后，public目录下会有sitemap.xml和baidusitemap.xml两个文件，
这就是生成的站点地图。  
前往百度提交入口和谷歌提交入口，进行文件验证:  
下载他他们的验证文件html,
在_config文件中找到skip_render属性，存入  
    
    //避免被hexo渲染
    skip_render: [googleb2b4a4dcdb4a9ac4.html, baidu_verify_R6YFRwbiQw.html]
重新渲染文件： hexo clean hexo d -g
再进行站长验证.  
验证成功后前往各自站点提交sitemap


#### 多媒体
插件
npm install hexo-tag-aplayer    //音频
npm install hexo-tag-dplayer    //视频
##### 视频
md中插入  

    {   % dplayer
         "url=http://home.ustc.edu.cn/~mmmwhy/GEM.mp4"  
         "pic=http://home.ustc.edu.cn/~mmmwhy/GEM.jpg" 
         "loop=yes" 
         "theme=#FADFA3" 
         "autoplay=false" 
         "token=tokendemo" 
    %}
key的属性有：
    
    dplayer options:
        'autoplay', 'loop', 'screenshot','hotkey', 'mutex', 'dmunlimited' : bool options, use "yes" "y" "true" "1" "on" or just without value to enable
        'preload', 'theme', 'lang', 'logo', 'url', 'pic', 'thumbnails', 'vidtype', 'suburl', 'subtype', 'subbottom', 'subcolor', 'subcolor', 'id', 'api', 'token', 'addition', 'dmuser' : string arguments
        'volume', 'maximum' : number arguments
    container options:
        'width', 'height' : string, used in container element style
    other:
        'code' : value of this key will be append to script tag
    

##### 音频


