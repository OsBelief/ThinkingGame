package com.think.game.f;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.util.Log;
import android.util.SparseArray;

/**
 * @author gudh
 * @date 2013-11-11
 */
public class FGame {

	private int rows = 10;
	private int cols = 10;
	private int colorCount = 5;
	private int rcs[][] = new int[rows][cols];

	private int emptyCount = 10;
	private int score = 0;

	public FGame(int rows, int cols, int colorCount, int emptyCount) {
		this.rows = rows;
		this.cols = cols;
		this.rcs = new int[rows][cols];
		this.colorCount = colorCount;
		this.emptyCount = emptyCount;
		// ��ʼ����Ϸ����
		initGame();

		this.score = 0;
	}

	public void printLayout(){
		System.out.println("=======================");
		for(int j = rows - 1; j >= 0; j--){
			for(int i = 0; i < cols; i++){
				System.out.print(rcs[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("=======================");
	}
	
	/**
	 * ��ʼ����Ϸ���֣��հ׺ͷǿհ׵�
	 */
	private void initGame() {
		Set<String> emptySet = FGameUtil.getEmptyPosSet(rows, cols, emptyCount);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (emptySet.contains(new StringBuilder().append(i).append("_")
						.append(j).toString())) {
					rcs[i][j] = 0;
					Log.d("InitColor", FGameUtil.getColor(0).toString());
				} else {
					rcs[i][j] = FGameUtil.getNextColorIndex(colorCount);
					Log.d("InitColor", FGameUtil.getColor(rcs[i][j]).toString());
				}
			}
		}
	}

	/**
	 * ���X��Yλ�ã��������ؽ��
	 * 
	 * @param x
	 * @param y
	 * @return -1 ��������Ϸ(����ȷ������)��0���Ϊ�ǿմ���1���û���κ�������2������������÷�
	 */
	public int click(int x, int y) {
		if (x < 0 || x >= cols || y < 0 || y > rows) {
			return -1;
		}
		if (rcs[x][y] != 0) {
			return 0;
		}

		// ��ȡ����ǿսڵ㼯
		List<int[]> nearNodes = getNearNotEmptyNodes(x, y);
		// ������Ա������Ľڵ�
		List<int[]> clearNodes = getNeedClearNodes(nearNodes);

		if (clearNodes == null) {
			return 1;
		}

		// ����Ҫ�����Ľڵ�
		clearMatchedNodes(clearNodes);

		return 2;
	}

	/**
	 * ��ȡ�ڵ�x,y���������������һ���ǿսڵ㼯������Ǳ�Ե�Ļ��Ͳ���ӵ������
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private List<int[]> getNearNotEmptyNodes(int x, int y) {
		List<int[]> nodes = new ArrayList<int[]>(4);
		int tx = x;
		int ty = y;

		// ��ߵĽڵ�
		do {
			tx--;
		} while (tx > 0 && rcs[tx][ty] == 0);
		if (tx >= 0) {
			nodes.add(new int[] { tx, ty });
		}

		// �ұߵĽڵ�
		tx = x;
		ty = y;
		do {
			tx++;
		} while (tx < cols - 1 && rcs[tx][ty] == 0);
		if (tx < cols) {
			nodes.add(new int[] { tx, ty });
		}

		// �±ߵĽڵ�
		tx = x;
		ty = y;
		do {
			ty--;
		} while (ty > 0 && rcs[tx][ty] == 0);
		if (ty >= 0) {
			nodes.add(new int[] { tx, ty });
		}

		// �ϱߵĽڵ�
		tx = x;
		ty = y;
		do {
			ty++;
		} while (ty < rows - 1 && rcs[tx][ty] == 0);
		if (ty < rows) {
			nodes.add(new int[] { tx, ty });
		}

		return nodes;
	}

	/**
	 * ������Щ�ڵ���Ա������������ؿ��Ա������Ľڵ㣬���û�пɱ������ķ���null
	 * 
	 * @param nodes
	 * @return
	 */
	private List<int[]> getNeedClearNodes(List<int[]> nodes) {

		// �ڵ���࣬����ͬ��ɫ�Ľڵ���ൽͬһ��KEY����
		SparseArray<List<int[]>> classNodes = new SparseArray<List<int[]>>(4);
		// ��key(��ɫ)��value(�洢����ɫ����������int[]��list)
		for (int[] node : nodes) {
			int color = getRC(node);
			if (classNodes.get(color) == null) {
				classNodes.put(color, new ArrayList<int[]>(4));
			}
			classNodes.get(color).add(node);
		}

		// ���ݷ����ж���Щ�ڵ���Ա�����
		List<int[]> needClearNodes = null;
		for (int i = 1; i < colorCount + 1; i++) {
			if (classNodes.indexOfKey(i) >= 0) {
				if (classNodes.get(i).size() > 1) {
					// ���Ϊnull���ʼ��һ��
					if (needClearNodes == null) {
						needClearNodes = new ArrayList<int[]>(4);
					}
					needClearNodes.addAll(classNodes.get(i));
				}
			}
		}

		return needClearNodes;
	}

	/**
	 * ����ָ���Ľڵ�����
	 * 
	 * @param clearNodes
	 */
	private void clearMatchedNodes(List<int[]> clearNodes) {
		int base = 1;
		for(int[] node : clearNodes){
			setRC(node, 0);
			score += base;
			base++;
		}
	}
	
	private void setRC(int[] rc, int value) {
		rcs[rc[0]][rc[1]] = value;
	}

	private int getRC(int[] rc) {
		return rcs[rc[0]][rc[1]];
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public int getColorCount() {
		return colorCount;
	}

	public int[][] getRcs() {
		return rcs;
	}

	public int getEmptyCount() {
		return emptyCount;
	}

	public int getScore() {
		return score;
	}
}
