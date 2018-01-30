
# Base介绍

```
这个是经过许多项目提炼出来的代码，目前不解释，只能说是个好东西、好东西、好东西……

1、签名信息：
keystore=sign/android.jks
storePassword=android
keyAlias=android
keyPassword=android

2、添加了bugly 异常上报与更新，已经添加你的QQ为管理员，登录https://bugly.qq.com/v2/crash-reporting/dashboard/9bb43e127f?pid=1 可发布新版本与查看bug

3、添加了友盟统计，可登录http://passport.umeng.com/login

5、使用glide 加载图片 ，已经使用策略模式封装好工具类

7、上下拉刷新：https://github.com/lcodecorex/TwinklingRefreshLayout（这个稳定，bug少）

11、项目全局使用RecyclerView，适配器的二次封装使用：（支持多种布局）
博客：http://blog.csdn.net/fisher0113/article/details/51955845
github:https://github.com/fishyer/StudyRecyclerView

12、数据库：使用litepal ，轻量，简单：https://github.com/LitePalFramework/LitePal

难受，难受难受，上传的aar包不能包含有aar包，因为这个问题，导致我创建了几个版本都不能成功导入，aar包只能引用aar，不能包含aar，目前稳定版是1.0.3
```


# 使用
```java
allprojects {
    repositories {
	  ...
	  maven { url 'https://jitpack.io' }
    }
}

dependencies {
       compile 'com.github.supertaohaili:Base:1.0.11'
}
```


### Known Issues
If you have any questions/queries/Bugs/Hugs please mail @
taohailili@gmail.com