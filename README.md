# BannerView

[![JitPack](https://jitpack.io/v/HaowenLee/BannerView.svg)](https://jitpack.io/#HaowenLee/BannerView)

好用又好看的轮播图控件

## 效果图

<img src="https://github.com/HaowenLee/BannerView/blob/master/images/banner_view.gif?raw=true" width="360" alt="BannerView效果图"/>

## 使用步骤

#### 步骤 1. 将JitPack存储库添加到构建文件中

将其添加到存储库末尾的根build.gradle中：

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

#### 步骤 2. 在module的build.gradle添加依赖项

```
dependencies {
        implementation 'com.github.HaowenLee:BannerView:0.0.10'
}

```

#### 步骤 3. 在布局文件中添加BannerView 或者 BannerViewPager和IndicatorLayout，可以设置自定义属性

```
<!-- 轮播图控件（带普通指示器） -->
<me.haowen.library.BannerView
    android:id="@+id/bannerView"
    android:layout_marginTop="60dp"
    android:layout_width="match_parent"
    android:layout_height="132dp">
```

#### 步骤 4. 设置图片加载器

```
bannerView.imageLoader = object : ImageLoader() {

        override fun displayImage(context: Context, path: Any, imageView: ImageView, position: Int) {
            // Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView)
        }
    }
```

#### 步骤 5. 在Activity或者Fragment中配置BannerView

设置数据
```
bannerView.imageList = arrayListOf(...)
```

Item点击事件
```
bannerView.setOnItemClickedListener { position ->
    // do something
}
```

#### 步骤 6. （可选）增加体验
```
@Override
protected void onStart() {
    super.onStart();
    //开始轮播
    banner.startAutoPlay();
}

@Override
protected void onStop() {
    super.onStop();
    //结束轮播
    banner.stopAutoPlay();
}
```
