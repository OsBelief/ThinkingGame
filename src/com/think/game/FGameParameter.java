package com.think.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

/**
 * �洢FGame����Ϸ����
 * 
 * @author gudh
 * @date 2013-11-12
 */
public class FGameParameter {

	private int rows;
	private int cols;

	private int screenWidth;
	private int screenHeight;

	private Rectangle btn1Bound;
	private Rectangle btn2Bound;
	private Rectangle labelBound;

	private Rectangle gameBound;
	private int[] nodeSize;
	private int[] rectSize;

	private static FGameParameter para;

	private FGameParameter() {
	}

	public static FGameParameter getParaInstance(int rows, int cols) {
		if (para == null) {
			para = new FGameParameter();

			para.rows = rows;
			para.cols = cols;

			para.screenWidth = Gdx.graphics.getWidth();
			para.screenHeight = Gdx.graphics.getHeight();

			para.btn1Bound = new Rectangle(20, 20, 100, 100);
			para.btn2Bound = new Rectangle(140, 20, 100, 100);
			para.labelBound = new Rectangle(260, 20, 120, 100);

			para.gameBound = new Rectangle(20, 150, 420, 672);
			para.nodeSize = new int[] { 40, 40 };
			para.rectSize = new int[] { 43, 43 };
		}
		return para;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public Rectangle getBtn1Bound() {
		return btn1Bound;
	}

	public Rectangle getBtn2Bound() {
		return btn2Bound;
	}

	public Rectangle getLabelBound() {
		return labelBound;
	}

	public Rectangle getGameBound() {
		return gameBound;
	}

	public int[] getNodeSize() {
		return nodeSize;
	}
	
	public int[] getRectSize() {
		return rectSize;
	}
}
