package cn.gavinliu.android.lib.drawable;import android.content.res.Resources;import android.graphics.Bitmap;import android.graphics.Canvas;import android.graphics.ColorFilter;import android.graphics.Paint;import android.graphics.PorterDuff;import android.graphics.PorterDuffColorFilter;import android.graphics.drawable.BitmapDrawable;import android.support.v7.graphics.Palette;import android.util.DisplayMetrics;import android.view.Gravity;/** * Created by GavinLiu on 2015-07-02 */public class BlurDrawable extends BitmapDrawable {    private Bitmap bitmap;    private Paint paint;    private Bitmap blurredBitmap;    private Paint blurPaint;    private ColorFilter colorFilter;    private int blurSize;    private Gravity blurGravity;    private int mBitmapWidth, mBitmapHeight;    public BlurDrawable(Resources res, Bitmap bitmap) {        super(res, bitmap);        this.bitmap = bitmap;        int mTargetDensity = DisplayMetrics.DENSITY_DEFAULT;        mBitmapWidth = bitmap.getScaledWidth(mTargetDensity);        mBitmapHeight = bitmap.getScaledHeight(mTargetDensity);        BlurManager mBlurManager = new BlurManager(bitmap);        blurredBitmap = mBlurManager.process((int) (mBitmapHeight * 0.15));        paint = new Paint();        blurPaint = new Paint();        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {            @Override            public void onGenerated(Palette palette) {                Palette.Swatch swatch = palette.getMutedSwatch();                if (swatch == null) {                    swatch = palette.getLightMutedSwatch();                }                if (swatch == null) {                    swatch = palette.getDarkVibrantSwatch();                }                if (swatch == null) {                    swatch = palette.getDarkMutedSwatch();                }                colorFilter = new PorterDuffColorFilter((swatch.getRgb() & 0xCCFFFFFF), PorterDuff.Mode.SRC_OVER);                invalidateSelf();            }        });    }    @Override    public void draw(Canvas canvas) {        canvas.save();        canvas.clipRect(0, 0, bitmap.getWidth(), 2 * bitmap.getHeight() / 3);        canvas.drawBitmap(bitmap, null, getBounds(), paint);        canvas.restore();        canvas.save(Canvas.CLIP_SAVE_FLAG);        canvas.clipRect(0, 2 * bitmap.getHeight() / 3, bitmap.getWidth(), bitmap.getHeight());        blurPaint.setColorFilter(colorFilter);        canvas.drawBitmap(blurredBitmap, null, getBounds(), blurPaint);        canvas.restore();    }    @Override    public void setAlpha(int i) {    }    @Override    public void setColorFilter(ColorFilter colorFilter) {    }    @Override    public int getOpacity() {        return 0;    }}