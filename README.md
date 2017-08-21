# JsonCache

[![](https://jitpack.io/v/rjlatgithub/JsonCache.svg)](https://jitpack.io/#rjlatgithub/JsonCache)
[![Travis](https://img.shields.io/travis/rust-lang/rust.svg)]()
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![docs](https://img.shields.io/badge/docs-30%25-green.svg)]()
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)]()

这是一个基于FastJson和字符串压缩实现的数据缓存框架，支持内存和磁盘两级缓存。
支持缓存基本数据类型和对象类型，存取操作都支持同步和异步两种模式，并且有丰富的配置。


<img src="https://github.com/rjlatgithub/JsonCache/raw/master/imgs/JsonCache.png" style="max-width:100%;width:100%;" />

# Introduction

目前大多数缓存框架都是用Java序列化的方式实现的持久化存储，我们自己公司的项目也是这么做的，功能全面且效率也高，但是有一个小问题，如果数据结构发生变化，尤其是原字段的数据类型发生变化，在加载缓存时会出现异常，这种情况即便写了序列号也没有用。还有一个小问题，就是缓存文件略微有点大。于是在和同事交流过程中我有了一个新想法，能不能用json实现缓存？经过一番尝试是可行的。大致流程如下：

 - 对象bean -> Json -> String -> 压缩String -> 存储
 - 读取 -> 压缩String -> String -> Json -> 对象bean

这种方式和序列化方式相比，它的缓存文件要小，尤其是数据较大并且重复度高的时候，它的缓存文件要比序列化文件小几十倍，当然这种情况比较极端，通常文件小两倍左右，这要归功于强大的字符串压缩算法。并且即使数据结构发生变化也不会出现异常。

但为什么上图说它“毫无卵用”呢，是因为效率要比序列化方式低好几倍，这点还需要优化。

# Compile
```
// 添加仓库
allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```

```
// 添加编译
compile 'com.github.rjlatgithub:JsonCache:v1.0.1'
```

# Configuration
```
// 默认配置
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JsonCache.getInstance().init(getApplicationContext());
    }
}
```

```
// 自定义配置
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Configuration configuration = new Configuration();
        configuration.setCacheDir("//sdcard/cache/");
        configuration.setMemoryCacheCount(20);
        configuration.setMemoryCacheTime(Configuration.DAY);
        configuration.setDiskCacheCount(100);
        configuration.setDiskCacheTime(Configuration.MONTH + Configuration.DAY * 5);
        JsonCache.getInstance().init(getApplicationContext(), configuration);
    }
}
```

# Usage
```
// 同步存取
User user = new User("张三", 12);
JsonCache.getInstance().saveObject("user", user);

User user = JsonCache.getInstance().loadObject("user", User.class);
```

```
// 异步存取
User user = new User("张三", 12);
JsonCache.getInstance().saveObjectAsync("user", user);

JsonCache.getInstance().loadObjectAsync("user", User.class, new ICallback<User>() {
    @Override
    public void onResult(User user) {
        Toast.makeText(MainActivity.this, user.toString(), Toast.LENGTH_LONG).show();
    }
});
```

# Author
- 邮箱:823668704@qq.com
- 简书:http://www.jianshu.com/u/26ef80e64974
