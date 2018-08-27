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
  implementation 'com.github.GeniusLiu:QuickItemDecorationDemo:1.0.0'
}
```

3、在自己写好的adapter中
```java
QuickItemDecoration quickItemDecoration = new QuickItemDecoration(new ItemDivider()
                .setColor(0xFFFF0000)
                .setWidth(10)
                .setMarginLeft(15)
                .setMarginTop(15)
                .setMarginBottom(40)
                .setMarginRight(100)
                .setItemGridBackgroundColor(0xFF0000FF)
                , 500
                , 200,
                0xFF00FF00, false);
recyclerView.addItemDecoration(quickItemDecoration);
```

效果:
![demo](https://github.com/GeniusLiu/QuickItemDecorationDemo/blob/master/app/src/main/res/drawable/demo.png)