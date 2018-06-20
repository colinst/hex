---
title: Hexo 引用图片 

tags: 
    - hexo 
    - img 
    - setting  
    
categories: 
    - setting
    - blog
    - plus
    
description: 传统句法中的图片引用无法在Hexo中使用，需要加料

date: 2017-02-010 13:00:00
---


修改_config.yml配置文件post_asset_folder项为true。  

```
hexo new "file" 
```

此时创建文章后，会出现一个与文章文件同名的文件夹。
将图片文件复制到文件夹中。(img.png)  

### 直接引用
前提为，你的hexo版本是hexo3以上
```
{%  asset_img img.png  img的图片信息  %}
```

### hexo插件方法
安装
```
npm install https://github.com/CodeFalling/hexo-asset-image --save
```
等待一段时间后安装完成后，使用传统方法引用就好
```
![logo](file/img.png)
```
其中[]中内容为为图片标题信息