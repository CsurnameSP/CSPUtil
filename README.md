1、在项目的build.gradle里面添加maven支持
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
2、在app的gradle下的dependencies中添加依赖：

  implementation 'com.github.CsurnameSP:CSPUtil:1.0.0'
