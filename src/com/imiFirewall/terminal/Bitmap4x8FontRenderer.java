package com.imiFirewall.terminal;

import com.imiFirewall.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;



class Bitmap4x8FontRenderer extends BaseTextRenderer {
  private final static int kCharacterWidth = 4;

  private final static int kCharacterHeight = 8;

  private Bitmap mFont;

  private int mCurrentForeColor;

  private int mCurrentBackColor;

  private float[] mColorMatrix;

  private Paint mPaint;

  private static final float BYTE_SCALE = 1.0f / 255.0f;

  public Bitmap4x8FontRenderer(Resources resources, int forePaintColor, int backPaintColor) {
    super(forePaintColor, backPaintColor);
    mFont = BitmapFactory.decodeResource(resources, R.drawable.atari_small);
    mPaint = new Paint();
    mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
  }

  public int getCharacterWidth() {
    return kCharacterWidth;
  }

  public int getCharacterHeight() {
    return kCharacterHeight;
  }

  public void drawTextRun(Canvas canvas, float x, float y, int lineOffset, char[] text, int index,
      int count, boolean cursor, int foreColor, int backColor) {
    setColorMatrix(mForePaint[foreColor & 7], cursor ? mCursorPaint : mBackPaint[backColor & 7]);
    int destX = (int) x + kCharacterWidth * lineOffset;
    int destY = (int) y;
    Rect srcRect = new Rect();
    Rect destRect = new Rect();
    destRect.top = (destY - kCharacterHeight);
    destRect.bottom = destY;
    for (int i = 0; i < count; i++) {
      char c = text[i + index];
      if ((cursor || (c != 32)) && (c < 128)) {
        int cellX = c & 31;
        int cellY = (c >> 5) & 3;
        int srcX = cellX * kCharacterWidth;
        int srcY = cellY * kCharacterHeight;
        srcRect.set(srcX, srcY, srcX + kCharacterWidth, srcY + kCharacterHeight);
        destRect.left = destX;
        destRect.right = destX + kCharacterWidth;
        canvas.drawBitmap(mFont, srcRect, destRect, mPaint);
      }
      destX += kCharacterWidth;
    }
  }

  private void setColorMatrix(int foreColor, int backColor) {
    if ((foreColor != mCurrentForeColor) || (backColor != mCurrentBackColor)
        || (mColorMatrix == null)) {
      mCurrentForeColor = foreColor;
      mCurrentBackColor = backColor;
      if (mColorMatrix == null) {
        mColorMatrix = new float[20];
        mColorMatrix[18] = 1.0f; // Just copy Alpha
      }
      for (int component = 0; component < 3; component++) {
        int rightShift = (2 - component) << 3;
        int fore = 0xff & (foreColor >> rightShift);
        int back = 0xff & (backColor >> rightShift);
        int delta = back - fore;
        mColorMatrix[component * 6] = delta * BYTE_SCALE;
        mColorMatrix[component * 5 + 4] = fore;
      }
      mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
    }
  }
}
