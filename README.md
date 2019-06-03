# rxjava2+retrofit2
### 1.mvp-V1
mvp乞丐版已知bug：请求异常会崩溃

### 2.mvp-V2
MVP-V2.0
增加MD5,工具类，Activity和Application基类
封装retrofit异常
修改回调接口
- 2018-12-30
修复rxjava观察者请求结果泛型报错问题

- 2019-01-04
1.免测量的ScrollView:android.support.v4.widget.NestedScrollView
2.仿朋友圈的技术分析：
 - 测量GridView：参照ScrollView嵌套GridView
 - 如果有图片，则显示GridView，没图片则隐藏GONE
 - 如果有多张图片，按照一定的逻辑切换Griview的列数，在循环复用的时候切换gridview的adpter在创建的时候设置上，循环复用的时候，得到adpter清空数据，添加新数据刷新
3.页面布局关于长短页面的压盖问题

- 2019-01-07
1.topdemo是顶部浮动效果（吸顶效果）测试
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
