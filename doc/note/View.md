## DecorView

> getWindow().getDecorView()的方法可以获取到decorView，decorView是什么呢？
decorView是window中的最顶层view，可以从window中获取到decorView，然后decorView有个getWindowVisibleDisplayFrame方法可以获取到程序显示的区域，包括标题栏，但不包括状态栏。

<br/>

```java
  Rect rect = new Rect();  
            /* 
             * getWindow().getDecorView()得到的View是Window中的最顶层View，可以从Window中获取到该View， 
             * 然后该View有个getWindowVisibleDisplayFrame()方法可以获取到程序显示的区域， 
             * 包括标题栏，但不包括状态栏。 
             */  
            getWindow().getDecorView().getWindowVisibleDisplayFrame(rect); 

             1.获取状态栏高度：  
             根据上面所述，我们可以通过rect对象得到手机状态栏的高度 
             int statusBarHeight = rect.top; 

             2.获取标题栏高度： 
             getWindow().findViewById(Window.ID_ANDROID_CONTENT); 
             该方法获取到的View是程序不包括标题栏的部分，这样我们就可以计算出标题栏的高度了。 
             int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();    
             //statusBarHeight是上面所求的状态栏的高度    
             int titleBarHeight = contentTop - statusBarHeight   
```

#### 实现的大致思路

1. 首先需要明白什么是DecorView，他是android中界面的根布局。其实android的activity界面整个就是一个控件树，DecorView是根节点，DecorView的孩子节点就是一个LinearLayout，这个LinearLayout的孩子系节点就包括状态栏 + 和我们自己写的布局
2. DecorView是FramLayout的子类（可以从源码中看到）
3. 既然DecorView是根节点，而且还是FrameLayout，所以我们可以把我们自己的布局 添加到DecorView 或者 从DecorView移除，这样就模拟出了一个Dialog的效果~~ ，当然这个Dialog的样式，动画就可以自己想怎么写就怎么写了撒
4. 通过activity.getWindow().getDecorView()可以获得DecorView
