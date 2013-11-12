package com.think.game.f;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.badlogic.gdx.graphics.Color;

/**
 * 
 * @author gudh
 * @date 2013-11-11
 */
public class FGameUtil {

	final static Color colors[] = { Color.CLEAR, Color.RED,
			Color.YELLOW, Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN,
			Color.PINK };

	private final static Random random = new Random();

	public static Color[] getAllColors(){
		return colors;
	}
	
	/**
	 * ��ȡ��һ�������ɫ�±꣬������0
	 * 
	 * @return
	 */
	static int getNextColorIndex(int colorCount) {
		return random.nextInt(colorCount) + 1;
	}

	static Color getColor(int index) {
		return colors[index];
	}

	/**
	 * ��rows�к�cols���У����ȡ��count���ڵ�λ�ã���{row, col}��ʾ
	 * 
	 * @param rows
	 * @param cols
	 * @param count
	 * @return
	 */
	static List<int[]> getEmptyPos(int rows, int cols, int count) {
		List<int[]> emptyPos = new ArrayList<int[]>(count);
		int[] temp;
		for (int i = 0; i < count; i++) {
			boolean exist;
			do {
				exist = false;
				temp = new int[] { random.nextInt(rows), random.nextInt(cols) };
				// �ж��Ƿ��Ѿ�����
				for (int[] th : emptyPos) {
					if (th[0] == temp[0] && th[1] == temp[1]) {
						// ����Ѿ������������������
						exist = true;
						break;
					}
				}
			} while (exist);
			emptyPos.add(temp);
		}
		return emptyPos;
	}

	/**
	 * ��rows�к�cols���У����ȡ��count���ڵ�λ�ã���row_col�ַ�����ʾ
	 * 
	 * @param rows
	 * @param cols
	 * @param count
	 * @return
	 */
	static Set<String> getEmptyPosSet(int rows, int cols, int count) {
		Set<String> sets = new HashSet<String>(count);

		while (sets.size() < count) {
			sets.add(new StringBuilder().append(random.nextInt(rows))
					.append("_").append(random.nextInt(cols)).toString());
		}

		return sets;
	}
}
