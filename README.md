# 随车行App

描述(包括功能和技术亮点)

(最近在琢磨方案，把服务器的web模块分离出来，就是我想做一个以定位，好友，xx模块为主，然后其他模块为辅的app，所以web模块就是所谓的其他模块，我可以使用h5编写好，然后通过主服务器暴露的接口来实现数据同步(类似微信的授权)，也就是说，我的web模块可以任意，我可以做一个二手交易系统，或者例如滴滴打车系统，然后主模块会把账号信息通过授权的方式给你，这样的话，当我们要开发新的模块的时候，只需要取得主模块的接口即可，这样还有一个好处，就是当我们的主模块足够完善的时候，每次更新我们只需要通过增量更新h5信息，而不用更新整个app。由于自己的js水平不是很高，所以一直卡在js这一块，有木有有兴趣的小伙伴来一起搞一搞)

----------
## guide
预览  
支持功能  
运行环境  
libary   
主模块服务器  
hybrid模块服务器  
搭建说明  
更新日志  
bug提交  
关于我  


## 预览
各个模块po图描述

## 运行环境 
服务器框架ssh(有点笨重，将考虑换成node.js)，然后图片服务器本来自己有写了一个，但是后面还是用回阿里云的oss，因为我的云服务器是腾讯云的学生机，硬盘容量不忍直视啊，阿里云oss对于我这种独立户来说基本等于免费，关于app中模块的前端框架是使用阿里的SuiMobile，感觉对于我这种不是很精通前端的人来说简直就是神器。

1.服务器 
 - MyEclipse 10.7 + Tomcat7
 - J2EE:structs2+hibernate+spring
 - MySQL5.0 
 
2.图片服务器 
 - 阿里云oss
 - node.js    
 
3.android 
 - android studio 1.0+
 - android sdk r16+  
 
4.其他   
 - hybrid前端框架：SuiMobile
 - 交互的方案与框架：


## 模块  

### 错误信息处理模块  
 自己写的一个RxJava风格的网络访问异常处理机制：
 - 识别网络访问过程中的各种异常和错误
 - 根据与服务器约定好的错误码进行友好的信息提示
 - 不入侵view层，大大降低耦合度
 - 密钥过期处理：当发现token过期会自动向服务器索取token并重新发起之前失败的那个请求
 - 了解更多：[传送门](http://blog.csdn.net/qq122627018/article/details/51689891 "悬停显示")  
 
### 图片压缩缓存模块
 这个模块暂时只用于webview中图片处理相关，因为在native中有glide的存在了，完全没有必要再用自己的 
 基本原理：CacheManager会先根据图片url去md5为key去检查本地二重缓存(内存缓存和硬盘缓存)，当发现没有的时候再从网络去读取，然后压缩，存储，再让webview去加载，特点：  
 - 利用RxJava的多个操作符完成缓存的层级检查
 - 内存和硬盘存储的算法都是采用LRU算法
 - 在webview中发挥这个模块作用的地方有俩个：1.当加载网络图片的时候  2.当从手机本地选取大量图片加载到webview中的时候  
 - [github地址](https://github.com/whaoming/WebViewCacheModule "悬停显示1")，guthub的图片显示有点不正常，也可以去  [CSDN地址](http://blog.csdn.net/qq122627018/article/details/53351781 "悬停显示2") 看看  
 
### 全局缓存  
整个app各个模块都有独立的缓存管理器(DiskLruCache)，在各种弱网络的环境下都能取出缓存中的数据提前进行展示，用户体验棒棒哒。每个模块都可以指定缓存文件大小的最大值，根据LRU算法可以设置定时自动清理，当然也可以由用户手动进行缓存的清理。

### RecyclerView
用到俩个开源项目：[Othershe的RecyclerViewAdapter](https://github.com/Othershe/RecyclerViewAdapter "悬停显示3")和[LinHongHong的PullToRefreshRecyclerView](https://github.com/HomHomLin/Android-PullToRefreshRecyclerView "悬停显示4")
把这俩个轮子合在了一起，不知道会不会翻车，反正现在用着是挺好的。
当时太纠结了，PullToRefreshRecyclerView有SwipeRefreshLayout(太喜欢这个控件了)，而RecyclerViewAdapter的公共view处理我又太喜欢了，结果就自己动手把这PullToRefreshRecyclerView的多余功能删掉，只保留SwipeRefreshLayout和header部分。然后adapter加入loading监听，特点：
 - 一键设置空数据提示，网络错误提示，重新加载提示的view
 - SwipeRefreshLayout嵌套recycleView，效果跟知乎首页一样
 - 具备下拉刷新和上拉加载更多的功能
 - (地址下次po上，不知道是何原因那个demo一直push不上来)  
 
### 第三方框架
 - 网络访问：Retrofit+Rxjava
 - Glide
 - Materia Design
 - 即时通讯|推送：环信sdk
 - 地图：百度地图sdk
 - 图片上传：阿里云oss
 - 本地图片选取：[GalleryPick](https://github.com/YancyYe/GalleryPick "悬停显示")，自带图片裁剪，对activity的入侵比较小 
 - easeui：环信sdk的工具类
 - JsBridge：webview与js沟通的桥梁(正在研究新方案)  


 
# 作者介绍
 - 邮箱：122627018@qq.com
 

