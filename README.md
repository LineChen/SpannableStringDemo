# SpannableStringDemo
TextView使用SpannableString设置复合文本


## 介绍

	使用SpannableString实现类似新浪微博、QQ空间等帖子显示。

主要包括：
- **话题显示（#舌尖上的大连#）,可以点击话题**
- **表情显示（[大笑]）**
- **@好友(@小明) ,可以点击好友**


	工程中封装了两个类，SpanUtils和ExpressionConvertUtil。SpanUtils主要操作SpannableString，实现关键字颜色变换、话题显示、表情显示、@好友显示等方法。ExpressionConvertUtil主要实现表情加载、匹配等方法。

## 效果图

![截图](https://github.com/LineChen/SpannableStringDemo/blob/master/screenshot/sh.gif)