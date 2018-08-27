# QuickItemDecorationDemo

使用方式
1、在Project的build.gradle中的对应位置添加
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
2、在项目的依赖中添加
dependencies {
  implementation 'com.github.GeniusLiu:QuickItemDecorationDemo:1.0.0'
}
