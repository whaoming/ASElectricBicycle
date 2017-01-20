# 随车行App

描述(包括功能和技术亮点)

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
服务器框架ssh(有点笨重，将考虑换成node.js)，然后图片服务器本来自己有写了一个，但是后面还是用回阿里云的oss，因为我的服务器是腾讯云的学生机，硬盘容量不忍直视啊，阿里云oss对于我这种独立户来说基本等于免费，然后android的不用说，关于app中webview展示的框架是使用阿里的SuiMobile，感觉对于我这种不是很精通前端的人来说简直就是神器
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
### 第三方框架
 - 网络访问：Retrofit+Rxjava
 - 即时通讯|推送：环信sdk
 - 地图：百度地图sdk
 - 图片上传：阿里云oss
 - Glide
 - Materia Design

### 错误信息处理模块  
### 图片压缩缓存模块  
### 全局缓存  
### RecyclerView  
### 其他
 - 本地图片选取：
# 作者介绍



