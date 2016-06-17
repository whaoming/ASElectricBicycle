#随车行App

----------
##效果图
先上图看看

##项目介绍
###背景
开发这款应用的目的是为了适配学校的另外一个创业项目，那个项目开发了一辆代步车(像自行车又像小时候玩的滑板)。所以此app是为了实现线上车友交流而开发的。
###项目特点
 - MateriaDesign的主题风格设计
 - 大量使用V7包的新控件，完成各种绚丽的动画效果
 - 集成百度地图api，实现地图，导航，地点查询，路线查询等功能
 - 集成环信IM，实现好友聊天系统
 - 实现勾搭功能，实时获取附近的人信息
 - 增强版Retrofit+RxJava的网络访问框架
 - 整个项目使用MVP的结构，所有网络请求处于RxAndroid的工作流中



##技术特点
###MVP基础框架
lz自己封装的一个实用性很强的mvp基础框架
> https://github.com/122627018/BaseMVP

###增强版Retrofit+RxJava网络访问框架
在本项目中，P与M的数据交互基本都是发生在RxAndroid的工作流中，那么怎么去处理流中的异常事件呢？具体：
> 优雅地处理服务器返回的错误和客户端访问网络过程中产生的错误
> https://github.com/122627018/Retorfit_RxJava_Exception

###动画效果
通过自定义的CoordinatorLayout behavior实现控件联动

![这里写图片描述](http://img.blog.csdn.net/20160617115953014)

双层CoordinatorLayout嵌套(在控件的底部其实还有一个snackBar一起联动)，但是自定义控件的behavior实现方式跟snackBar完全不相同


##其他
###服务器代码
服务器使用JavaWeb+Tomcat7.0，完全自己编写，使用servlet回应，与客户端的交互采用json的数据格式
(服务器也需要集成环信的im，客户端的用户注册功能是由服务器来完成的)
![这里写图片描述](http://img.blog.csdn.net/20160617111507607)
> 传送门：https://github.com/122627018/ElectricBicycleServer





