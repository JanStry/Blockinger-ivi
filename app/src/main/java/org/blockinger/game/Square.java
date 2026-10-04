package org.blockinger.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Square {

	public static final int type_empty = 0;
	public static final int type_blue = 1;
	public static final int type_orange = 2;
	public static final int type_yellow = 3;
	public static final int type_red = 4;
	public static final int type_green = 5;
	public static final int type_magenta = 6;
	public static final int type_cyan = 7;

	private int type;

	private Paint paint;
	private Paint glowPaint;
	private Paint borderPaint;
	private Paint highlightPaint;

	private Bitmap bm;
	private Bitmap phantomBM;

	private Canvas canv;
	private Canvas phantomCanv;

	private int squaresize;
	private int phantomAlpha;

	public Square(int type, Context c) {

		this.type = type;

		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


		glowPaint.setStyle(Paint.Style.STROKE);

		borderPaint.setStyle(Paint.Style.STROKE);

		highlightPaint.setStyle(Paint.Style.STROKE);

		phantomAlpha = c.getResources()
				.getInteger(R.integer.phantom_alpha);

		squaresize = 0;

		switch (type) {

			case type_blue:
				paint.setColor(c.getResources()
						.getColor(R.color.square_blue));
				break;

			case type_orange:
				paint.setColor(c.getResources()
						.getColor(R.color.square_orange));
				break;

			case type_yellow:
				paint.setColor(c.getResources()
						.getColor(R.color.square_yellow));
				break;

			case type_red:
				paint.setColor(c.getResources()
						.getColor(R.color.square_red));
				break;

			case type_green:
				paint.setColor(c.getResources()
						.getColor(R.color.square_green));
				break;

			case type_magenta:
				paint.setColor(c.getResources()
						.getColor(R.color.square_magenta));
				break;

			case type_cyan:
				paint.setColor(c.getResources()
						.getColor(R.color.square_cyan));
				break;

			case type_empty:
				return;

			default:
				paint.setColor(c.getResources()
						.getColor(R.color.square_error));
				break;
		}
	}


	public void reDraw(int ss) {

		if (type == type_empty)
			return;

		squaresize = ss;

		bm = Bitmap.createBitmap(
				ss,
				ss,
				Bitmap.Config.ARGB_8888
		);

		phantomBM = Bitmap.createBitmap(
				ss,
				ss,
				Bitmap.Config.ARGB_8888
		);

		canv = new Canvas(bm);
		phantomCanv = new Canvas(phantomBM);

		drawNeonBlock(canv, false);
		drawNeonBlock(phantomCanv, true);
	}


	private void drawNeonBlock(Canvas canvas, boolean phantom) {


		float padding = Math.max(1, squaresize * 0.075f);


		float radius = Math.max(2, squaresize * 0.18f);


		RectF rect = new RectF(
				padding,
				padding,
				squaresize - padding,
				squaresize - padding
		);



		int baseColor = paint.getColor();

		glowPaint.setStyle(Paint.Style.STROKE);


		glowPaint.setColor(
				Color.argb(
						phantom ? phantomAlpha / 3 : 70,
						Color.red(baseColor),
						Color.green(baseColor),
						Color.blue(baseColor)
				)
		);

		glowPaint.setStrokeWidth(
				Math.max(3, squaresize * 0.18f)
		);

		canvas.drawRoundRect(
				rect,
				radius,
				radius,
				glowPaint
		);



		glowPaint.setColor(
				Color.argb(
						phantom ? phantomAlpha / 2 : 120,
						Color.red(baseColor),
						Color.green(baseColor),
						Color.blue(baseColor)
				)
		);

		glowPaint.setStrokeWidth(
				Math.max(2, squaresize * 0.10f)
		);

		canvas.drawRoundRect(
				rect,
				radius,
				radius,
				glowPaint
		);




		paint.setStyle(Paint.Style.FILL);

		paint.setAlpha(
				phantom ? phantomAlpha : 255
		);

		canvas.drawRoundRect(
				rect,
				radius,
				radius,
				paint
		);




		borderPaint.setStyle(Paint.Style.STROKE);

		borderPaint.setStrokeWidth(
				Math.max(1.5f, squaresize * 0.035f)
		);

		borderPaint.setColor(
				Color.argb(
						phantom ? phantomAlpha : 150,
						0,
						0,
						0
				)
		);

		canvas.drawRoundRect(
				rect,
				radius,
				radius,
				borderPaint
		);


		float inner = Math.max(2, squaresize * 0.055f);

		RectF innerRect = new RectF(
				padding + inner,
				padding + inner,
				squaresize - padding - inner,
				squaresize - padding - inner
		);

		highlightPaint.setStyle(Paint.Style.STROKE);

		highlightPaint.setStrokeWidth(
				Math.max(1, squaresize * 0.025f)
		);

		highlightPaint.setColor(
				Color.argb(
						phantom ? phantomAlpha / 2 : 150,
						255,
						255,
						255
				)
		);

		canvas.drawRoundRect(
				innerRect,
				Math.max(1, radius - inner),
				Math.max(1, radius - inner),
				highlightPaint
		);




		Paint topHighlight = new Paint(Paint.ANTI_ALIAS_FLAG);

		topHighlight.setStyle(Paint.Style.STROKE);

		topHighlight.setStrokeWidth(
				Math.max(1, squaresize * 0.018f)
		);

		topHighlight.setColor(
				Color.argb(
						phantom ? phantomAlpha / 3 : 100,
						255,
						255,
						255
				)
		);

		RectF topRect = new RectF(
				padding + inner * 1.5f,
				padding + inner * 1.5f,
				squaresize - padding - inner * 1.5f,
				squaresize - padding - inner * 1.5f
		);

		canvas.drawRoundRect(
				topRect,
				Math.max(1, radius - inner * 1.5f),
				Math.max(1, radius - inner * 1.5f),
				topHighlight
		);
	}


	public Square clone(Context c) {
		return new Square(type, c);
	}


	public boolean isEmpty() {
		return type == type_empty;
	}


	public void draw(
			int x,
			int y,
			int squareSize,
			Canvas c,
			boolean isPhantom) {

		if (type == type_empty)
			return;

		if (squareSize != squaresize)
			reDraw(squareSize);

		if (isPhantom) {

			c.drawBitmap(
					phantomBM,
					x,
					y,
					null
			);

		} else {

			c.drawBitmap(
					bm,
					x,
					y,
					null
			);
		}
	}
}
