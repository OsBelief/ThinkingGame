package com.think.game.f;

import java.util.HashMap;
import java.util.Scanner;

/**
 * 测试方法
 * @author gudh
 * @date 2013-11-11
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FGame game = new FGame(10, 10, 3, 10);
		game.printLayout();

		String c = "";
		Scanner scan = new Scanner(System.in);
		do {
			System.out.print("> ");
			c = scan.nextLine();
			System.out.println("input: " + c);
			String infos[] = c.split(" ");
			if (infos.length == 2) {
				int r = game.click(Integer.parseInt(infos[0]),
						Integer.parseInt(infos[1]));
				System.out.println("click status:" + r);
			} else {
				System.out.println("input error");
			}
			game.printLayout();
		} while (!c.equals("exit"));
		scan.close();
	}
	/**
	 * 判断是否有可以消除的小方块,true没有,false有
	 * @param rcs
	 * @return 
	 */
	public static boolean checkGameOver(int[][] rcs) {
		int rows = rcs.length;
		int cols = rcs[0].length;
		HashMap<Integer, Integer> exitHash = new HashMap<Integer, Integer>();
		boolean flag = true;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				if(rcs[i][j] == 0) {
					int tx = j;
					int ty = i;
					// 左边的节点
					do {
						--tx;
					} while (tx > 0 && rcs[tx][ty] == 0);
					if (tx >= 0) {
						exitHash.put(rcs[tx][ty], 0);
					}
					// 右边的节点
					tx = j;
					ty = i;
					do {
						++tx;
					} while (tx < cols - 1 && rcs[tx][ty] == 0);
					if (tx < cols) {
						if(exitHash.get(rcs[tx][ty]) == null) {
							exitHash.put(rcs[tx][ty], 0);
						} else {
							flag = false;
							break;
						}
					}
					// 上边的节点
					tx = j;
					ty = i;
					do {
						--ty;
					} while (ty > 0 && rcs[tx][ty] == 0);
					if (ty >= 0) {
						if(exitHash.get(rcs[tx][ty]) == null) {
							exitHash.put(rcs[tx][ty], 0);
						} else {
							flag = false;
							break;
						}
					}
					// 下边的节点
					tx = j;
					ty = i;
					do {
						++ty;
					} while (ty < rows - 1 && rcs[tx][ty] == 0);
					if (ty < rows) {
						if(exitHash.get(rcs[tx][ty]) == null) {
							exitHash.put(rcs[tx][ty], 0);
						} else {
							flag = false;
							break;
						}
					}
					exitHash.clear();
				}
			}
		}
		return flag;
	}
}
