package com.think.game;

import java.lang.reflect.Method;

import android.util.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.think.game.f.FGame;
import com.think.game.f.FGameUtil;

/**
 * FGame��������
 * 
 * @author gudh
 * @date 2013-11-12
 */
public class FGameAct implements ApplicationListener {

	// �洢������ɫ����
	private static Texture[] texts;
	private static Image[] rects;

	// ��Ϸ����
	public FGameParameter para;

	// ��ͼ��Դ
	private SpriteBatch batch;
	private Stage stage;
	private BitmapFont font;
	private Skin skin;

	// ��Ϸʵ��
	private FGame fgame;

	// �ؼ�
	Label lab;
	TextButton btn1;
	TextButton btn2;
	// �洢ÿ������
	private Image[][] gameArea;

	// ��־�Ƿ��һ�μ���
	private boolean isInit = false;

	public FGameAct() {
		// ��ʼ����Ϸʵ��
		fgame = new FGame(10, 16, 5, 25);
	}

	/**
	 * ��ʼ����Դ��Ϣ
	 */
	private void initproperties() {
		// ��ʼ����ɫ
		int length = FGameUtil.getAllColors().length;
		texts = new Texture[length];
		rects = new Image[length];

		for (int i = 0; i < length; i++) {
			Pixmap p = new Pixmap(2, 2, Format.RGBA8888);
			p.setColor(FGameUtil.getAllColors()[i]);
			p.fillRectangle(0, 0, 2, 2);
			texts[i] = new Texture(p, false);
			rects[i] = new Image(texts[i]);
		}

		// ��ʼ��font��skin�ļ�
		font = new BitmapFont(Gdx.files.internal("default.fnt"),
				Gdx.files.internal("default.png"), false);
		skin = new Skin(Gdx.files.internal("uiskin.json"));

	}

	/**
	 * ���ÿؼ���λ��
	 * @param obj
	 * @param bound
	 */
	private void setBound(Object obj, Rectangle bound) {
		try {
			Method posMethod = obj.getClass().getMethod("setPosition",
					float.class, float.class);
			posMethod.invoke(obj, bound.getX(), bound.getY());

			Method sizeMethod = obj.getClass().getMethod("setSize",
					float.class, float.class);
			sizeMethod.invoke(obj, bound.getWidth(), bound.getHeight());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����x,y��ȡImage�������ú�λ�úʹ�С
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private Image getNewImageByPos(int x, int y) {
		int index = fgame.getRcs()[x][y];
		Image m = new Image(texts[index]);
		// m������Ҫcloneһ��

		float xx = para.getGameBound().getX() + x * para.getRectSize()[0];
		float yy = para.getGameBound().getY() + y * para.getRectSize()[1];
		m.setPosition(xx, yy);
		m.setSize(para.getNodeSize()[0], para.getNodeSize()[1]);
		return m;
	}

	/**
	 * ����ĳ��λ�õ���ɫ
	 * 
	 * @param x
	 * @param y
	 */
	private void updatePos(int x, int y) {
		int index = fgame.getRcs()[x][y];
		gameArea[x][y].setDrawable(new TextureRegionDrawable(new TextureRegion(
				texts[index])));
	}

	/**
	 * ���µ÷�
	 * 
	 * @param score
	 */
	private void updateScore(int score) {
		lab.setText("Come on!\n\nScore: " + score);
	}

	public void playOnce(int x, int y) {
		int res = fgame.click(x, y);
		Log.i("PlayResult", "����Ϸ�����" + res);
		if (res == 2) {
			for (int i = 0; i < fgame.getRows(); i++) {
				for (int j = 0; j < fgame.getCols(); j++) {
					updatePos(i, j);
				}
			}
			updateScore(fgame.getScore());
		}
	}

	/**
	 * ��ʼ����Ϸ��Ϣ
	 */
	private void initGame(int rows, int cols) {
		// ���ز���
		para = FGameParameter.getParaInstance(fgame.getRows(), fgame.getCols());

		batch = new SpriteBatch();

		stage = new Stage(para.getScreenWidth(), para.getScreenHeight(), true,
				batch);

		btn1 = new TextButton("Start", skin);
		setBound(btn1, para.getBtn1Bound());
		stage.addActor(btn1);

		btn2 = new TextButton("Pause", skin);
		setBound(btn2, para.getBtn2Bound());
		stage.addActor(btn2);

		lab = new Label("Come on!\n\nScore�� 0", new LabelStyle(font, Color.RED));
		setBound(lab, para.getLabelBound());
		stage.addActor(lab);

		gameArea = new Image[para.getRows()][para.getCols()];
		for (int i = 0; i < para.getRows(); i++) {
			for (int j = 0; j < para.getCols(); j++) {
				gameArea[i][j] = getNewImageByPos(i, j);
				stage.addActor(gameArea[i][j]);
			}
		}
	}

	@Override
	public void create() {
		if (!isInit) {
			// ��һ�μ���ʱ��ʼ����Դ��Ϣ
			initproperties();
			isInit = true;
		}

		// ��ʼ����Ϸ
		initGame(23, 32);

		// ����¼�
		Gdx.input.setInputProcessor(new FGameInputProcessor(this));
	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// ��ͼ
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}
}