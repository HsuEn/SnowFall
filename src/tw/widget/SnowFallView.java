package tw.widget;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

public class SnowFallView extends View {

	private static Random sRandomGen = new Random();

	private int snow_flake_count = 10;
	private final List<Drawable> drawables = new ArrayList<Drawable>();
	private int[][] coords;
	private final Context mContext;

	public SnowFallView(Context context) {
		super(context);
		setFocusable(true);
		setFocusableInTouchMode(true);
		mContext = context;
		// snow_flake = context.getResources().getDrawable(R.drawable.a1_snow);
	}

	public SnowFallView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(true);
		setFocusableInTouchMode(true);
		mContext = context;

	}

	@Override
	protected void onSizeChanged(int width, int height, int oldw, int oldh) {
		super.onSizeChanged(width, height, oldw, oldh);

		Interpolator interpolator = new LinearInterpolator();
		snow_flake_count = Math.max(width, height) / 60;
		coords = new int[snow_flake_count][];
		drawables.clear();
		for (int i = 0; i < snow_flake_count; i++) {
			Animation animation = new TranslateAnimation(0, height / 10
					- sRandomGen.nextInt(height / 5), 0, height + 30);
			animation.setDuration(10 * height + sRandomGen.nextInt(5 * height));
			animation.setRepeatCount(-1);
			animation.initialize(10, 10, 10, 10);
			animation.setInterpolator(interpolator);

			coords[i] = new int[] { sRandomGen.nextInt(width - 30), -60 };

			int size = 37 * height / 1000 + sRandomGen.nextInt(13);
			Drawable snow_flake = mContext.getResources().getDrawable(R.drawable.snow);
			snow_flake.setBounds(0, 0, size, size);
			drawables.add(new AnimateDrawable(snow_flake, animation));
			animation.setStartOffset(sRandomGen.nextInt(20 * height));
			animation.startNow();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		for (int i = 0; i < snow_flake_count; i++) {
			Drawable drawable = drawables.get(i);
			canvas.save();
			canvas.translate(coords[i][0], coords[i][1]);
			drawable.draw(canvas);
			canvas.restore();
		}
		invalidate();
	}

}
