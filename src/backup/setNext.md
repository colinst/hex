## Next 自定义更改
使用Gemini主题下修改
### 首页文章标题与文章简介的距离
默认为60
更改css样式 margin{上右下左}/{上右下}/{上下-右左}
src/setNext.md
位置：themes/next/source/css/_common/components/post/post-meta.styl
行号：02


### 首页文章标题-对齐
位置：themes/next/source/css/_common/components/post/post-title.styl
行号：02
默认：center
改为：left

### 阅读全文按钮调整
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
