# 随车行App
   
根据摩拜单车的理念开发的一款app，app通过蓝牙与车子进行连接，在行走过程app可以提供车子信息实时展示的功能，在此功能之上，app还提供当前位置信息，并以当前位置为基点提供附近用户位置并可进行好友的添加，聊天等。app还提供了一套hybrid框架用于功能的扩展,通过原生提供的一些接口，可以通过H5快速增加某些特定模块.   


## 更新日志
#### 2017-2-2 
 - 通信加密  
  - RSA+AES   
  - Android aes密钥通过rsa公钥加密，放在http请求头中，rsa公钥由服务器生成放在assets中,rsa密钥存放在服务器  
 - 加入驾驶模式
  - 通过特定的协议，app使用蓝牙模块与STC进行通信，从而实时对蓝牙小车的速度进行展示(暂时只显示PWM的占空比，测速模块还在快递路上)  
  - [硬件模块传送门](https://github.com/whaoming/SCMAndroidCommunicate "悬停显示4")，主要通过k1，k2按键来改变PWM从而达到调速效果，蓝牙模块使用HC-06，协议也是我自己制定的，具体可以进去看看  
 
 
## guide  
 -  [预览](#yulan)
 - [支持功能](#gongneng)  
 - [运行环境](#yunxinghuanjing)  
 - [模块](#mokuai)  
 - [运行环境](#yunxinghuanjing)  
 - [搭建说明](#yunxinghuanjing)  
 - [bug提交](#yunxinghuanjing)  
 - [关于我](#aboutme)    


<h2 id="yulan">预览</h2>  
### native部分  

| 启动界面        | 首页一角           | 好友界面  |   
|:-------------:|:-------------:|:-------------:|  
![image](https://github.com/whaoming/aboutme/blob/master/image/%E5%90%AF%E5%8A%A8%E7%95%8C%E9%9D%A2.gif?raw=true)     | ![image](https://github.com/whaoming/aboutme/blob/master/image/%E9%A6%96%E9%A1%B5%E9%99%84%E8%BF%91%E7%9A%84%E4%BA%BA%E5%8A%A8%E7%94%BB.gif?raw=true) | ![image](https://github.com/whaoming/aboutme/blob/master/image/%E5%A5%BD%E5%8F%8B%E7%95%8C%E9%9D%A2+%E6%9F%A5%E6%89%BE%E5%A5%BD%E5%8F%8B%E6%93%8D%E4%BD%9C.gif?raw=true)    
| 个人头像修改 | 图片选择  | 聊天界面 |  
![image](https://github.com/whaoming/aboutme/blob/master/image/%E5%A4%B4%E5%83%8F%E4%BF%AE%E6%94%B9.gif?raw=true) | ![image](https://github.com/whaoming/aboutme/blob/master/image/%E5%9B%BE%E7%89%87%E9%80%89%E6%8B%A9%E9%A1%B5%E9%9D%A2.png?raw=true) | ![image](https://github.com/whaoming/aboutme/blob/master/image/chating.gif)  
### hybrid部分
ps：本来觉得hybrid部分不用传上来的，因为全部都是h5写的，但是想展示下原生部分，由下图可以看到标题栏还有浮动按钮都是原生的，是以JsBridge为桥梁来做到html与原生的双向通信，从而达到js中也能控制原生ui的展示，也就是说我可以通过html来修改app中的控件，从而达到这个模块可以热更新的效果(但是现在这个方案还不太成熟，我还在不断完善中)  

| 信息列表       | 详情           | 发布界面  |  
|:-------------:|:-------------:|:-------------:|  
![image](https://github.com/whaoming/aboutme/blob/master/image/web%E5%88%97%E8%A1%A8%E9%A1%B5%E9%9D%A2.png?raw=true)     | ![image](https://github.com/whaoming/aboutme/blob/master/image/web_%E5%85%B7%E4%BD%93%E9%A1%B5%E9%9D%A2.png?raw=true) | ![image](https://github.com/whaoming/aboutme/blob/master/image/web_%E5%8F%91%E8%A1%A8%E9%A1%B5%E9%9D%A2.png?raw=true)    
| 查看个人发布信息 |   
![image](https://github.com/whaoming/aboutme/blob/master/image/web_%E4%B8%AA%E4%BA%BA%E9%A1%B5%E9%9D%A2.png) |   
### 硬件通信部分  
实现了STC与android通过蓝牙通信从而达到信息交互，可以看看我的文章：[SCMAndroidCommunicate](https://github.com/whaoming/SCMAndroidCommunicate "悬停显示4")，只要遵守我制定好的数据协议，便可以与app的驾驶模块进行交互  

![这里写图片描述](https://raw.githubusercontent.com/whaoming/aboutme/master/image/201702022059.BMP)![这里写图片描述](https://raw.githubusercontent.com/whaoming/aboutme/master/image/2017020220591.BMP)   
[在线演示视频观看](http://v.youku.com/v_show/id_XMjQ5MTgyMTAwMA==.html "悬停显示")(模块在线演示地址)  

<h2 id="gongneng">功能</h2>
已经差不多可以媲美一个成熟的app了，该有的功能都有，下面拿几个平常开源项目可能比较少关注的点提一提：</br>
 - 用户模块：支持好友的聊天，表情图片的发送，添加好友，同意好友请求等，离线消息接收，多处登陆(挤下线)，消息多种提醒方式。
 - 自动登陆：双token机制保证了用户在本机app中只需登陆一次，即可做到以后都自动登陆
 - 位置系统：服务器端使用geo编码来实现附近的人，每次登陆都会自动上传自己当前的位置并展示出附近的人。足迹功能：在移动端用户也可以主动记录自己当前的位置和发表动态与上传照片，服务器会根据时间来判断是否符合发表的条件，用户可以查看自己的足迹(百度地图的覆盖物有点小问题，这个功能很快可以完善，现在只有一个雏形) 
 - 硬件通信模块：与单片机通过蓝牙通信，达到双工通信，app实时显示蓝牙小车速度信息  
 
 
 
<h2 id="yunxinghuanjing">运行环境</h2>
服务器框架ssh(有点笨重，将考虑换成node.js)，然后图片服务器本来自己有写了一个，但是后面还是用回阿里云的oss，因为我的云服务器是腾讯云的学生机，硬盘容量不忍直视啊，阿里云oss对于我这种独立户来说基本等于免费，关于app中模块的前端框架是使用阿里的SuiMobile，感觉对于我这种不是很精通前端的人来说简直就是神器：</br> 
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
 - 单片机：STC89C51，c语言   
 
 
<h2 id="mokuai">模块</h2>  
### 第三方框架
 - Retrofit+Rxjava
 - Glide
 - Materia Design
 - 即时通讯|推送：环信sdk
 - 百度地图sdk
 - 图片上传：阿里云oss
 - 本地图片选取：[GalleryPick](https://github.com/YancyYe/GalleryPick "悬停显示")，自带图片裁剪，对activity的入侵比较小 
 - easeui：环信sdk的工具类
 - JsBridge：webview与js沟通的桥梁(正在研究新方案)  
 - (已去除) ~~内存泄漏检测 LeakCommpany~~
 
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
 
### 硬件通信协议  
在项目的实际应用中，二者之间传递的信息类型太多了，比如实时速度，电量，还有车子灯光打开，或者修改车子密码等等信息，那么单片机或者app要怎么去判断传递过来的是哪种信息呢？那么我们就必须去制定一套数据协议，这里看看我的方案，协议规定：

| 包头   | 类型位 | 数据位  | 数据位   |  结束位   |  
|:-----:|:-----:|:------:|:-------:|:--------:|  
 0xFF | 0x** | 0x** | 0x** | 0xFF |  
 
 
那么我们的数据位可以分别代表高二位和低二位，那么通常情况下这种方案就可以满足我们的需求了。举个例子：  

| 类型位  | 数据位 | 数据位  | 功能 |  
|:------:|:-----:|:------:|:-------:|  
 0X00 | 0X02 | 0X00 | 前进 |  
 0X00 |    0X01 | 0X00 | 后退 |  
 0X00 |  0X03 | 0X00 |  左转 |  
 0X00   |    0X04 | 0X00   | 右转 |  
 0X00     |  0X00 | 0X00  |  停止 |  
 0X02     | 0x00| 0X01   |  车灯亮 |  
 0X02    | 0x00|  0X02   |  车灯灭 |  
 0X03   |  雷达数据高位 | 雷达数据低位 |  发送雷达数据 |    
 [详情看我写的另外一个硬件交互的项目](https://github.com/whaoming/SCMAndroidCommunicate "悬停显示3") 
 
<h2 id="aboutme">关于我</h2>  
 - 邮箱：122627018@qq.com
 

