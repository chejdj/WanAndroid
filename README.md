## WanAndroid
#### 项目介绍
**一款每日推荐优质文章的APP,该项目是鸿洋大佬推荐Andorid开发者的一个开源项目，项目中的API为鸿洋大佬提供([API直通车](https://www.wanandroid.com/blog/show/2))，此项目基于Java+Material Design+MVP+RxJava2+Retrofit等一些主流框架搭建而成**  
### 项目背景  
首先介绍一下自己，自己是一个大学即将毕业的Andorid初级开发者，在找工作的时候，看过一些面经，但是忘记快又理解不深刻，于是想要拿一个项目练练手，加深一下对知识点的理解，以及对于Andorid开发整个流程的梳理，这个项目希望大家一起学习，探讨,尤其是对于Android新手如何写代码的通病以及如何写出高效简洁的代码(希望有大佬指点)，欢迎issue，star。  
### 整体模块
此项目的整体框架如下：
* 首页模块：轮番广告显示,最新文章推送
* 知识体系模块 
* 公众号模块
* 开源项目模块
* 收藏模块
* 登录注册模块
* 文章搜索模块
* WebView文章浏览模块
* 数据库本地缓存模块
### 第三方开源库
* [Objectbox](https://github.com/objectbox/objectbox-java)(数据库框架)
* [Glide](https://github.com/bumptech/glide)(图片加载框架)
* [Retrofit](https://github.com/square/retrofit)(网络请求框架)
* [Rxjava2](https://github.com/ReactiveX/RxJava)(异步请求或者基于事件的框架)
* [Butterknife](https://github.com/JakeWharton/butterknife)(注解绑定UI控件框架)
* [Banner](https://github.com/youth5201314/banner)(一种广告图片轮播图控件)
* [Floatingactionbutton](https://github.com/Clans/FloatingActionButton)(MaterialDesign格式的浮动按钮)
* [Flowlayout](https://github.com/hongyangAndroid/FlowLayout)(Android流式布局)
* [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)(一个强大的RecyclerView适配器)
* [EventBus](https://github.com/greenrobot/EventBus)(事件通知框架)
* [Bugly](https://bugly.qq.com/v2/)(Bug监控框架)
* [RecyclerView]()(Andorid官方list控件)
* [design]()(Android官方库，包含多种MaterialDesign控件)  
### 参考项目以及文章  
* [hurshi大神的wanandroid](https://github.com/hurshi/wanandroid)(此项目的UI是参考，模仿hurshi大神的玩Andorid项目,感谢)
* [给初学者的RxJava2.0教程系列](https://www.jianshu.com/p/464fa025229e)
* [Android冷启动解决方法](http://saulmm.github.io/avoding-android-cold-starts)  
* [今日头条屏幕适配方案](https://mp.weixin.qq.com/s/sjI-gDx3PaCskxh_vj6vpA)
* [WebView性能、体验分析与优化](https://mp.weixin.qq.com/s?__biz=MjM5NjQ5MTI5OA==&mid=2651746383&idx=2&sn=9b8f8ec2adf7c13934bfb9891eae4d81&chksm=bd12a9028a652014ab8b89ff996cf7b53e8d40bfbcd64725c7c82df72515669fcf5267272ccf&scene=38#wechat_redirect)(这一块此项目后续需要改进)
* [Android MVP架构从入门到精通-真枪实弹](https://www.wanandroid.com/blog/show/2440)
* [你真的会用Retrofit2吗?Retrofit2完全教程](https://www.jianshu.com/p/308f3c54abdd)  
### 版本更新说明  

```
v2.2.0 Fragment的懒加载和内存泄漏问题
v2.1.0 引入滴滴的哆啦A梦,修改一些Bugly的上Bug
v2.0.0 加入微信分享+修改了部分Bug  
v1.4.2 更新部分界面，以及无网络及网络差的友好显示，及Bugly上面的Bug  
v1.3.0 修改首页Frament之间的切换方式以及FragmentStatePagerAdapter的Bug    
v1.2.0 修改Android6.0动态权限申请  
v1.1.0 初始版本
```  

### 项目一览  
![启动页](https://raw.githubusercontent.com/chejdj/WanAndroid/master/image/start.png?token=AQ4MyR7l6VVBBAtmu58KP5JDaqCvBeeuks5cmY9GwA%3D%3D)![首页](https://raw.githubusercontent.com/chejdj/WanAndroid/master/image/home.png?token=AQ4MyRfQ4zg_fKBxyoT20J_lbKqK5yA1ks5cmY9fwA%3D%3D)![知识体系](https://raw.githubusercontent.com/chejdj/WanAndroid/master/image/knowledgeArch.png?token=AQ4MyeEP5r7eQBbuzhnjY47bZ4TtVG7cks5cmY97wA%3D%3D)  

![详情知识体系文章](https://raw.githubusercontent.com/chejdj/WanAndroid/master/image/detail_knowledge.png?token=AQ4MyVOiuSZondN8kyVxxUvM53Sy3av7ks5cmY-SwA%3D%3D)![微信公众号](https://raw.githubusercontent.com/chejdj/WanAndroid/master/image/wechat.png?token=AQ4MyczK2p_uOBaMH3fHpijwmpeCAj79ks5cmZLqwA%3D%3D)![项目](https://raw.githubusercontent.com/chejdj/WanAndroid/master/image/project.png?token=AQ4MySYP3LhA_S0m-99uQl05v7hJvdLWks5cmY_JwA%3D%3D)  

![登录](https://raw.githubusercontent.com/chejdj/WanAndroid/master/image/login.png?token=AQ4MyaDqXbyvT1_7QGZ9tnTuylsr25UZks5cmY_rwA%3D%3D)![我的](https://raw.githubusercontent.com/chejdj/WanAndroid/master/image/me.png?token=AQ4MyXe7t_GjrtN2f6fH4TblvcbBSHIxks5cmZACwA%3D%3D)![搜索热词](https://raw.githubusercontent.com/chejdj/WanAndroid/master/image/search_hot_keys.png?token=AQ4MySHXivZbbxGsRxKDT28I9F15fbeMks5cmZAdwA%3D%3D)  

![搜索](https://raw.githubusercontent.com/chejdj/WanAndroid/master/image/search.png?token=AQ4MySH6FlcA90BGrtyjntteyjqyn54hks5cmZBtwA%3D%3D)![文章](https://raw.githubusercontent.com/chejdj/WanAndroid/master/image/article.png?token=AQ4MyZ4y36FW7ccDsmyNSgHvBz7Ye3QRks5cmZCFwA%3D%3D)  
### 项目资源  
1. [APP下载地址](https://www.pgyer.com/nP6h)，二维码  
![二维码](https://raw.githubusercontent.com/chejdj/WanAndroid/master/image/apk_download.png)  
(二维码的APK永远都是最新的，release上的重大更新才发版)  
2. 项目地址: https://github.com/chejdj/WanAndroid









