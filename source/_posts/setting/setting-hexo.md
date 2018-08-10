---
title: Hexo-Next 设置|备忘

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
node.js  
hexo-client         npm install hexo-cli -g  
hexo-deployer-git   npm install hexo-deployer-git --save  
hexo-md-img-plagin  npm install https://github.com/CodeFalling/hexo-asset-image --save  
github-createRep  

### 常用命令 command

Emmm Hexo 的写作教程细节->🐷More info: [Writing](https://hexo.io/docs/writing.html)

Run server|开启服务器👌

``` bash
$ hexo server
hello,hello,hello... 你好啊，你是谁，你在哪，你在干什么👀👀👀
```

生成文章的命令|Generate static files

``` bash
$ hexo generate
```

生成文件 md->html|Deploy to remote sites

``` bash
$ hexo deploy
```

完成后部署
执行下列的其中一个命令，让 Hexo 在生成完毕后自动部署网站，两个命令的作用是相同的。  
``` bash
$ hexo generate --deploy
$ hexo deploy --generate
```

> 简写
上面两个命令可以简写为
$ hexo g -d
$ hexo d -g


More info: [Deployment](https://hexo.io/docs/deployment.html)
``` bash
可以多定义几个deloy，deloy命令后会依次来repo,
同时一起更新到github,gitlee,码云等。
前提要创建好repo
以及，对应站点定义好SSH key
```




## 主题轻改 Theme-Next
### 基本信息 baseInfo
#### 站点设置 siteInfo
#### 侧边栏信息 authorInfo

### 首页样式 viewIndex
#### 博文块样式 blockView
对齐
书签
#### 底层动画   cssFlash
#### 右上Github 
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
#### 自定义 ...

### 文章样式 mainView
#### 标签优化
#### 评论引入
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

