# RunningCircle

## 一个可以自己转动的View

 最近迷上了网易云音乐（非广告植入），发现它的听歌界面会有封面转动（好吧，我承认现在大部分听歌软件都有这个的） <br> 
 
 并且在歌曲播放中和暂停时都会有唱针的移动，感觉不错，所以想做一个类似的空间，顺便学习一下自定义View
 
 
 唱针那个还没写，首先先写了这个可以自己转动的View，支持在转动切换图片资源、支持转动方向、支持定义转动速度。还有一些
 
 控制，比如开始和停止控制，当然肯定少不了监听器了。下面正题开始
 

### 要使用该控件，首先你需要在布局文件中写如下声明：
 
 <com.example.asus.runningcircle.RunningCircle
        android:id="@+id/circleView"
        android:layout_width="200dp"
        android:layout_height="180dp"
        android:layout_marginLeft="60dp"
        run:direction="CW"
        run:timeDelta="800"
        run:imgSrc="@drawable/jay_jay"/>
        
其中 run 是自己引进的命名空间：xmlns:run="http://schemas.android.com/apk/res-auto"

###　你可以在创建该View的时候设置如下属性：
        
         run:direction="CW"  //该属性使用枚举类型，分别为 CW(即顺时针转动)、 ACW（即逆时针转动）
         
         run:timeDelta="800"   //设置转动速度，单位为ms。
         
         run:imgSrc="@drawable/jay_jay"  //设置转动的图片资源
         
  PS：关于上面的必要的解释：
       
       关于 timeDelta ：我是用的Thread.sleep(timeDelta)来控制的时间变化，可能会有不妥（毕竟新手，还望指出）,所以在时
                        间控制上会显得不是很精准，因为系统本身Thead.sleep()方法休眠的时间就不是那么精准
       关于 imgSrc    ：你可以在创建该View 的时候传入一个Drawable资源编号来设置默认的图片（封面），当然，你有可能会忘
                        了去加这个属性，此时会在RunningCircle中抛出一个自定义异常（ImgSrcException）会提示你应该去设置
                        图片资源，当然，你可以在以后通过代码 runningCircle.setImgSrc(int) 来切换新的图片，该图片可以是
                        从网上加载的新的应该显示在这儿的图片。
                        并且该控件支持将你传入的图片 `自动缩放` 为该控件大小，以保证图片的 `最大化显示加载` ！
        
        
### 好了，现在回到代码中：

#### 你需要先通过findViewById(int resId)来找到该View

      runningCircle = (RunningCircle) findViewById(R.id.circleView);
      
      
### 然后，你可以做好多事情：
      
#### 我想要控制该View的开始和暂停：
      
        runningCircle.start();   
        
        runningCircle.stop();
        
#### 我想让该View变一下转动的方向：
       
        runningCircle.setDirections(RunningCircle.ACW);   
        
        //其中 ACW 表示逆时针转动， CW表示顺时针转动
        
#### 我还想让该View在转动中切换图片资源：
      
         runningCircle.setImgSrc(R.drawable.jay_fantexi);
        
#### 你还想干什么？来来来，讲给我听啊，我不打你，我是爱你的！！
     
     
#### 最后，少不了监听器啊：
     
        runningCircle.setRunningListener(new RunningCircle.OnRunningListener() {
            @Override
            public void onStart() {
                Toast.makeText(MainActivity.this, "Start Running!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStop() {
                Toast.makeText(MainActivity.this, "Stop Running!", Toast.LENGTH_SHORT).show();
            }
        });
    

### 这样就可以了，当然，国际惯例，还是要放张图的，没图你们是不会看到最后的，别问我怎么知道的(@_@;)：

     
     
![](https://github.com/youngkaaa/RunningCircle/raw/master/app/src/screens/demo.gif)  

      
         
