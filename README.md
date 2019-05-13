# QuickItemDecorationDemo

使用方式
1、在Project的build.gradle中的对应位置添加
```java
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
2、在项目的依赖中添加
```java
dependencies {
  implementation 'com.github.GeniusLiu:QuickItemDecorationDemo:1.5.0'
}
```

3、配置全局的分割线

可以在自定义的Application中：
```java
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        QuickItemDecoration.setGlobalConfig(
                ItemDecorationConfig.GlobalConfig.getInstance(
                        new ItemDecorationConfig()
                                .create()
                                .setRecyclerMarginTop(500)
                                .setRecyclerMarginBottom(500)
                                .setRecyclerMarginTopColor(Color.RED)
                                .setRecyclerMarginBottomColor(Color.YELLOW)
                                .setMarginBottomWhenNotMatch(false)
                                .setItemDivider(new ItemDivider()
                                        .setColor(Color.BLUE)
                                        .setMarginLeft(150)
                                        .setMarginRight(0)
                                        .setWidth(10))
                                .addIgnoreViewId(R.id.emptyView)
                                .build()));
        QuickItemDecoration.setGlobalConfig(ItemDecorationConfig.GlobalConfig.getInstance(new ItemDecorationConfig().create().build()));
        
    }

}
```

4、设置全局配置后，只需要将我们的分割线add进recyclerView即可

```java
QuickItemDecoration quickItemDecoration = new QuickItemDecoration();
recyclerView.addItemDecoration(quickItemDecoration);
```

5、如果想要在某个特殊的RecyclerView列表中更改某个参数的话，可以：

```java
QuickItemDecoration quickItemDecoration = new QuickItemDecoration();
quickItemDecoration.getUpdateConfig().setRecyclerMarginTopColor(Color.BLUE).update();
recyclerView.addItemDecoration(quickItemDecoration);
``` 

这种方式只会改变update的参数，不会改变GlobalConfig中其他的参数

6、如果想要在某个特殊的RecyclerView列表中完全使用新的分割线，忽略GlobalConfig：

```java
QuickItemDecoration quickItemDecoration = new QuickItemDecoration();
quickItemDecoration.setConfig(new ItemDecorationConfig().create().setRecyclerMarginTop(100).build());
recyclerView.addItemDecoration(quickItemDecoration);
``` 

7、如果您的项目中，空视图、加载视图也占用了item位置，可以忽略给这些视图添加分割线：
```java
QuickItemDecoration quickItemDecoration = new QuickItemDecoration();
quickItemDecoration.getUpdateConfig().addIgnoreViewId(R.id.emptyView).update();
recyclerView.addItemDecoration(quickItemDecoration);
``` 

效果:
![demo](https://github.com/GeniusLiu/QuickItemDecorationDemo/blob/master/app/src/main/res/drawable/demo.png)