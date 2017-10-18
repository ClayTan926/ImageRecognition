package cn.thxy.imagerecognition.ratio;

import android.animation.TypeEvaluator;
import android.content.Context;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.List;


public class EnterRatioFrameEvaluator implements TypeEvaluator {

    public static final int OFFSET_DISTANCE = 80;

    private Context mContext;

    private int mOffsetDistance;

    public EnterRatioFrameEvaluator(Context context){
        this.mContext = context;
        mOffsetDistance = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,OFFSET_DISTANCE,mContext.getResources().getDisplayMetrics());
    }


    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        List<RatioFrame> startRatioFrameList = (List<RatioFrame>) startValue;
        List<RatioFrame> endRatioFrameList = (List<RatioFrame>) endValue;
        List<RatioFrame> ratioFrameList = new ArrayList<>();

        for (int i = 0; i < endRatioFrameList.size(); i++) {
            RatioFrame endRatioFrame = endRatioFrameList.get(i);
            RatioFrame startRatioFrame = startRatioFrameList.get(i);
            //计算left,top,right,bottom
            double t = ( -2 * Math.pow(fraction,2) + 2 * fraction);//倾斜变化率
            int temp = (int)((mOffsetDistance) * t);
            double rightAngle = Math.PI / 2;
            int moveX = 0,moveY = 0;
            moveX = (int)(temp * Math.cos(endRatioFrame.mAngle));
            moveY = (int)(temp * Math.sin(endRatioFrame.mAngle));

            int left = (int)(startRatioFrame.mLeft + ((endRatioFrame.mLeft - startRatioFrame.mLeft) * fraction) - moveX);
            int top = (int)(startRatioFrame.mTop + ((endRatioFrame.mTop - startRatioFrame.mTop) * fraction) - moveY) ;
            int right = (int)(startRatioFrame.mRight + ((endRatioFrame.mRight - startRatioFrame.mRight) * fraction) - moveX);
            int bottom = (int)(startRatioFrame.mBottom + ((endRatioFrame.mBottom - startRatioFrame.mBottom) * fraction) - moveY) ;
            ratioFrameList.add(new RatioFrame(left,top,right,bottom));
        }
        return ratioFrameList;
    }
}
