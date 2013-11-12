package com.think.game;

import android.util.Log;

import com.badlogic.gdx.InputProcessor;

/**
 * FGame�¼�����
 * @author gudh
 * @date 2013-11-12
 */
public class FGameInputProcessor implements InputProcessor {

	public FGameAct fGame;

	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int pw;
	private int ph;

	private int height;

	public FGameInputProcessor(FGameAct fGame) {
		this.fGame = fGame;
		FGameParameter para = fGame.para;
		x1 = (int) para.getGameBound().getX();
		y1 = (int) para.getGameBound().getY();
		x2 = (int) para.getGameBound().getWidth() + x1;
		y2 = (int) para.getGameBound().getHeight() + y1;
		pw = para.getRectSize()[0];
		ph = para.getRectSize()[1];

		height = para.getScreenHeight();
	}

	/**
	 * ��ӦTouchUp�¼�
	 * 
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 */
	public boolean actEventTouchUp(int arg0, int arg1, int arg2, int arg3) {
		// y��ת��
		arg1 = height - arg1;
		Log.d("Event", "ת���������:" + arg0 + " " + arg1);

		if (arg0 < x1 || arg0 > x2 || arg1 < y1 || arg1 > y2) {
			Log.i("Event", "������Ϸ��");
			// ������ʼ��ť

		} else {
			int x = (arg0 - x1) / pw;
			int y = (arg1 - y1) / ph;
			Log.i("Event", "��� �� " + x + " " + y);
			fGame.playOnce(x, y);
			return true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		String str = "Up " + arg0 + " " + arg1 + " " + arg2 + " " + arg3;
		Log.d("Event", str);
		return actEventTouchUp(arg0, arg1, arg2, arg3);
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		String str = "Drag " + arg0 + " " + arg1 + " " + arg2;
		Log.d("Event", str);
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		String str = "Down " + arg0 + " " + arg1 + " " + arg2 + " " + arg3;
		Log.d("Event", str);
		return false;
	}

	@Override
	public boolean keyDown(int arg0) {
		String str = "KeyDown " + arg0;
		Log.d("Event", str);
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		String str = "KeyTyped " + arg0;
		Log.d("Event", str);
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		String str = "KeyUp " + arg0;
		Log.d("Event", str);
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		String str = "MouseMove " + arg0 + " " + arg1;
		Log.d("Event", str);
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		String str = "Scrolled " + arg0;
		Log.d("Event", str);
		return false;
	}
}