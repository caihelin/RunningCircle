package com.example.asus.runningcircle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by codekk on 2016/5/23.
 * Email:  645326280@qq.com
 */
public class RunningCircle extends View {
    private Paint paint;
    private BitmapShader bitmapShader;
    private Bitmap bitmap;
    private int radius;
    private float scaleRatio;
    private int viewWidth;
    private Matrix matrix;
    private int directions;
    public static final int CW = 0;
    public static final int ACW = 1;
    private int timeDelta;
    private int rotateAngle = 0;
    private int bitmapWidth;
    private Thread startThread;
    private int imgSrc;
    private boolean isRunning = true;  //this default value is false,for the view is running!!
    private OnRunningListener listener;
    //定义接口
    public interface OnRunningListener{
        void onStart();
        void onStop();
    }

    public RunningCircle(Context context) throws ImgSrcException {
        super(context);
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.RunningCircle);
        imgSrc = typedArray.getResourceId(R.styleable.RunningCircle_imgSrc, -1);
        directions = typedArray.getInteger(R.styleable.RunningCircle_direction, CW);
        timeDelta = typedArray.getInteger(R.styleable.RunningCircle_timeDelta, 80);
        typedArray.recycle();
        inits();
    }

    public RunningCircle(Context context, AttributeSet attrs) throws ImgSrcException {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RunningCircle);
        imgSrc = typedArray.getResourceId(R.styleable.RunningCircle_imgSrc, -1);
        directions = typedArray.getInteger(R.styleable.RunningCircle_direction, CW);
        timeDelta = typedArray.getInteger(R.styleable.RunningCircle_timeDelta, 80);
        typedArray.recycle();
        inits();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        viewWidth = Math.min(width, height);    //set the view's height and width were equal forced!!
        setMeasuredDimension(viewWidth, viewWidth);
        radius = viewWidth / 2;
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int result = 0;
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 1000;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(size, result);
            }
        }
        return result;
    }


    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int result = 0;
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 1000;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(size, result);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bitmapWidth = Math.min(bitmap.getWidth(), bitmap.getHeight());
        scaleRatio = viewWidth * 1.0f / bitmapWidth;   //计算图片缩放比例
        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
        canvas.drawCircle(radius, radius, radius, paint);
        startRunning(directions);
    }

    private void startRunning(final int type) {
        startThread = new Thread(new Runnable() {
            @Override
            public void run() {
                matrix.setScale(scaleRatio, scaleRatio);
                matrix.preRotate(rotateAngle, bitmapWidth / 2, bitmapWidth / 2);
                if (type == ACW && isRunning) {
                    rotateAngle--;
                    if (rotateAngle == 0) {
                        rotateAngle = 360;
                    }
                } else if (isRunning) {   //设置为CW或者不设置（即默认）是顺时针
                    rotateAngle++;
                    if (rotateAngle == 360) {
                        rotateAngle = 0;
                    }
                }
                postInvalidate();
                try {
                    Thread.sleep(timeDelta);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        });
        startThread.start();
    }

    private void inits() throws ImgSrcException {
        paint = new Paint();
        matrix = new Matrix();
        paint.setAntiAlias(true);
        if (imgSrc != -1) {
            bitmap = BitmapFactory.decodeResource(getResources(), imgSrc);
        }else{
            throw new ImgSrcException("你需要通过使用imgSrc标签属性来给这个RunningCircle设置图片资源!");
        }
        if (directions == CW) {  //顺时针旋转
            rotateAngle = 1;
        } else if (directions == ACW) {
            rotateAngle = 259;
        }
    }

    public void start() {
        isRunning = true;
        if(listener!=null){
            listener.onStart();
        }
    }

    public void stop() {
        isRunning = false;
        if(listener!=null){
            listener.onStop();
        }
    }

    public void setRunningListener(OnRunningListener listener){
        this.listener=listener;
    }

    class ImgSrcException extends Exception{
        public ImgSrcException(String message){
            super(message);
        }
    }

    public void setImgSrc(int imgSrc1){
        imgSrc=imgSrc1;
        bitmap = BitmapFactory.decodeResource(getResources(), imgSrc);
        invalidate();
    }

    public void setTimeDelta(int delta){
        timeDelta=delta;
        invalidate();
    }

    public void setDirections(int directions) {
        this.directions = directions;
        invalidate();
    }
}
