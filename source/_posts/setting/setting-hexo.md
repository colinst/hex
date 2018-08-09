---
title: GithubPages-Hexo 使用设置|细则备注

tags: 
    - hexo 
    - setting  

categories: 
    - setting
    - blog

description: GithubPages-Hexo 使用设置|细则备注 🐳🐋🐟🐳🐋🐟🐳🐋🐳🐋🐟🐳🐋🐟🐳🐋  

date: 2017-01-010 13:00:00
---

 
 嗨，Hello
  
  代码基地[github/hongxii](https://github.com/colinst)!
  个人网站域名[gitmmp.com](http://gitmmp.com)!
  欢迎来到我的博客 [Blog.gitmmp.com](https://colinst.github.io)!  
  🐳🐋🐟🐳🐋🐟🐳🐋🐳🐋🐟
 
 啦啦啦~  
 吾日三省吾身：早上吃什么，中午吃什么，晚上吃什么...    
 吾日三省吾身2：我是谁，我在哪，我在干什么...    
 吾日三省吾身3：早上干什么，中午干什么，晚上干什么...  
 吾日三省吾身4：今天干什么，明天干什么，后天干什么...  
 吾日三省吾身5：这周干什么，下周干什么，下下周干什么...  
 吾日三省吾身6：鱼🐟，好大的鱼🐟，虎纹鲨鱼🐯🐟...  
 吾日三省吾身7：什么🐟🐟🐟，什么🐟什么🐟🐟，🐟🐟🐟什么🐟🐟🐟什么🐟🐟🐟🐟🐟🐟🐟🐟🐟...  
 吾日三省吾身8：🐳🐋🐟🐠🦐🐡🦑🐬🐬🦈🐙🐟🐠🦑🐬🦈🐙🐟🐠🦈🐙🐟🐠🐳🐋🐟🐠🦐🦐🐡🦑🐬
 
<!-- more -->

## 🐟🐟二级大鱼

### 🐟🐟🐟做一只小小鱼

``` bash
$ hexo new "My New Post"
一脸黑人问号👤？？？
emmm 这是hexo新建项目命令
```

Emmm Hexo 的写作教程细节->🐷More info: [Writing](https://hexo.io/docs/writing.html)

### Run server|开启服务器👌

``` bash
$ hexo server
hello,hello,hello... 你好啊，你是谁，你在哪，你在干什么👀👀👀
```

More info: [Server](https://hexo.io/docs/server.html)

### 生成文章的命令|Generate static files

``` bash
$ hexo generate
```

More info: [Generating](https://hexo.io/docs/generating.html)

### 生成文件 md->html|Deploy to remote sites

``` bash
$ hexo deploy
```

### 完成后部署
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


## Will Change
### 在文章底部增加版权信息
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

