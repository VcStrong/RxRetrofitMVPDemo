# rxjava2+retrofit2


<img src="https://github.com/VcStrong/KotlinMVPDemo/blob/master/image/1.jpg" width="300" align=center /><img src="https://github.com/VcStrong/KotlinMVPDemo/blob/master/image/2.jpg" width="300" align=center /><img src="https://github.com/VcStrong/KotlinMVPDemo/blob/master/image/3.jpg" width="300" align=center /><img src="https://github.com/VcStrong/KotlinMVPDemo/blob/master/image/4.jpg" width="300" align=center />
<br/>
<br/>

kotlin版mvp参见：https://github.com/VcStrong/KotlinMVPDemo.git<br/>

## 1.mvp-V1
mvp乞丐版已知bug：请求异常会崩溃

## 2.mvp-V2
MVP-V2.0<br/>
增加MD5,工具类，Activity和Application基类<br/>
封装retrofit异常<br/>
修改回调接口<br/><br/>
#### 2018-12-30<br/>
修复rxjava观察者请求结果泛型报错问题

#### 2019-01-04<br/>
1.免测量的ScrollView:android.support.v4.widget.NestedScrollView<br/>
2.仿朋友圈的技术分析：<br/>
 - 测量GridView：参照ScrollView嵌套GridView<br/>
 - 如果有图片，则显示GridView，没图片则隐藏GONE<br/>
 - 如果有多张图片，按照一定的逻辑切换Griview的列数，在循环复用的时候切换gridview的adpter在创建的时候设置上，循环复用的时候，得到adpter清空数据，添加新数据刷新<br/>
3.页面布局关于长短页面的压盖问题<br/>

#### 2019-01-07<br/>
1.topdemo是顶部浮动效果（吸顶效果）测试<br/>
```
引入依赖：
implementation 'com.gavin.com.library:stickyDecoration:*'

使用：
PowerfulStickyDecoration decoration = PowerfulStickyDecoration.Builder
                .init(new PowerGroupListener() {
                    @Override
                    public String getGroupName(int position) {
                        //获取组名，用于判断是否是同一组
                        return myAdapter.getItem(position).getBrandId();
                    }

                    @Override
                    public View getGroupView(int position) {
                        //获取自定定义的组View
                            View view = getLayoutInflater()
                                    .inflate(R.layout.list_title, null, false);
                            ((TextView) view.findViewById(R.id.title))
                                    .setText(myAdapter.getItem(position).getBrand());
                            return view;
                    }
                })
                .setGroupHeight(getResources().getDimensionPixelSize(R.dimen.dip_40))//设置高度
                .build();
recyclerView.addItemDecoration(decoration);
```
<br/>

## 3.mvp-V3
TopDemo可视情况移除<br/>
重大升级：针对公司级大项目进行框架升级，V3版本停留于单工程形式，接下来的V4版本将组件化分层（请期待）
#### 2019-06-09<br/>
注：WDPresenter以下简称BP;NetworkManager为Retrofit网络工具类<br/>
1.修复rxjava无法停止请求的问题，BP中改为Dispose对象停止访问网络；<br/>
2.将IRquest接口每次请求都要新建的问题修复，放到Presenter构造方法中，提高对象使用率；<br/>
3.简化BP中异常处理类。。哈哈，抱歉，其实我学会使用Rxjava的onErrorReturn方法了，所以之前V2版本中BP异常处理就要被抛弃了<br/>
4.重点：对BP和DataCall进行重大升级：
 - ①BP可以根据业务选择不同的请求接口，例：
请求我们服务器用的IAppRequest，请求经纬度换算的百度接口，使用IBaiduRequest，
Presenter层继承BP，重写getRequestType()方法用于操作NetworkManager中不同域名的Retrofit；
 - ②BP根据结果不同，选择不同的Consumer进行处理，这里写法比较灵活，遵循基类不能经常修改的原则，
我暴露了getConsumer(Object...args)方法，你可以直接重写此方法修改Consumer用于操作返回值；
如果某个业务的Consumer被大量用到，你可以修改BP中getConsumer方法，然后重写getResponseType()即可。
例：我们项目接口有50个，由于和新浪微博深度合作资源共享，我们接入新浪微博的20个接口，项目用到百度定位，用了两个百度接口；
 - ③DataCall重写，由于某些业务需要通过请求值进行判断，这里我把请求值又回传给了页面层。<br/>
 
## 4.mvp-V4
TopDemo已经移除<br/>
重大升级：模块化MVP框架，应对多业务多小组大项目开发<br/>
1.ButterKnife使用，子module中的build.gradle必须包含：
```
apply plugin: 'com.jakewharton.butterknife'

annotationProcessor 'com.jakewharton:butterknife-compiler:10.1.0'
```

ButterKnife使用中的坑我已经帮各位踩过了，随便查看项目中的某一个Activity代码你都会发现，@BindView使用了R2（R2只适用于ButterKnife自己的类使用），
但是方法中所有资源使用R，这个坑很恶心的。<br/>
2.Arouter的引入和使用，网上博客千千万，最好参照github:https://github.com/alibaba/ARouter;<br/>
3.v4使用androidX，替换了appcompat.support，因为ButterKnife10.*之后使用了androidX。<br/>
4.暂时没有要普及的了，感谢自己。

## 2019-07-18 
注：WDPresenter以下简称BP;NetworkManager为Retrofit网络工具类<br/>
1.BP中实现了模块的请求切换和结果统一封装回调，继承BP之后只需要写业务逻辑和调用请求，参见任意*Presenter <br/>
2.建议每个模块加入自己的请求接口，参照common包中的IAppRequest <br/>
3.业务上包含：登录，退出登录，上传多图，recyclerview仿朋友圈列表<br/>

## 5.mvp-V5（模块组件打包） 质量的提高来自不断地追求
由于对组件和模块的概念有了更深的了解，参考了网上的组件化教程，实践总结利弊之后，决定自己写一套优秀高效率的组件运行gradle；<br/>
我先总结一下网友的组件运行方案，然后说一下我的，方便各位采纳和推广我的打包方式，哈哈哈哈哈.....<br/>
网友的方案：①根据判断修改每个module的gradle中application/library,②根据判断选择使用的AndroidManifest.xml文件;<br/>
网友方案的优点：更改groovy变量控制打包条件，但是缺点是：不方便某几个模块联调测试，实际公司场景都是需要某几个模块业务联调完成运行。<br/>
既然release版本是（dependencies）引入所有模块打包，那我如果根据gradle配置动态改变模块的引入，
分分钟就能解决一个模块或者多个模块打包的问题了，而且不需要对module的类型进行任何的修改。具体方式如下：<br/>
1.项目根目录新建了config.gradle存放系统变量；<br/>
2.项目根目录build.gradle动态改变app（module）对模块的引入；<br/>
3.所有选中的模块可根据自己要求看看是否需要改变AndroidManifest.xml的引入，仿照open_main模块中的sourceSets;<br/>
注：请认真查看config.gradle中的变量备注<br/>

## 2019-10-28
1.修改http适配文件，适配9.0系统，方便体验。


### 框架包含以下
- androidx：这个系列的jar包和appcompat.support对立的，参见谷歌官方文档
- retrofit2+rxjava2
- greendao：数据库如果要加密，请配合SqlCipher使用，参见：https://blog.csdn.net/VcStrong/article/details/82972043
- MZBanner：banner如果不需要刻意去掉。
- fresco：图片加载
- xRecyclerView
- easypermissions：权限申请比较好用
- Arouter

