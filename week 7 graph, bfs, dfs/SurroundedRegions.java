package search;

import java.util.*;

/**
 * BFS, Union Find
 * 
 * <pre>
X X X X
X O O X
X X O X
X O X X

to

X X X X
X X*X*X
X X X*X
X O X X
 * </pre>
 */
public class SurroundedRegions {
	private static final char TEMP = '\u0000';
	/**
	 * <pre>
	 * 用BFS/DFS （call stack）来做
	 * https://discuss.leetcode.com/topic/17224
	 * https://discuss.leetcode.com/topic/18706
	 * 
	 * 英文描述算法：
	 * 1. 把所有edge的'O'翻转->TEMP, 做DFS（or BFS）（连通域）
	 * 		DFS中：
	 * 		0. 自己如果不是'O'就不翻转了
	 * 		1. 翻转'O'->TEMP
	 *		2. 对所有【还在棋盘上】的【邻居】，如果他们【不是edge边上】的话，继续DFS向深处进发
	 * 2. 翻转所有'O'->'X'
	 * 3. 翻转所有TEMP->'O'
	 * </pre>
	 */
	public void solve(char[][] board) {
		if (board.length == 0 || board[0].length == 0)
			return;
		// 1. 在edge的时候，翻转'O'->'.', 做DFS（or BFS）
		for (int x = 0; x < board.length; x++)
			for (int y = 0; y < board[0].length; y++)
				if (atEdge(board, x, y) && board[x][y] == 'O')
					convertEdge_O_to_TEMP_DFS(board, x, y);
		// 2. 翻转所有'O'->'X'
		convertFromTo(board, 'O', 'X');
		// 3. 转回来'.'->'O'
		convertFromTo(board, TEMP, 'O');
	}
	private void convertFromTo(char[][] board, char from, char to) {
		for (int x = 0; x < board.length; x++)
			for (int y = 0; y < board[0].length; y++)
				if (board[x][y] == from)
					board[x][y] = to;
	}
	private void convertEdge_O_to_TEMP_DFS(char[][] board, int x, int y) {
		if (board[x][y] != 'O')
			return;
		// 1. convert
		board[x][y] = TEMP;
		// 2. go to near 4 (可以用其他的解法）:x++,x--,y++,y--
		for (int[] tuple : new int[][]{{x + 1, y}, {x - 1, y}, {x, y + 1},
				{x, y - 1}})
			if (withinBoard(board, tuple[0], tuple[1]) // 1.首先确定还在棋盘上
					&& !atEdge(board, tuple[0], tuple[1]) // 2.然后确定不是edge-不然的话和外面loop的重复了,会导致leetcode:stackOverFlowError
					&& board[tuple[0]][tuple[1]] == 'O') // 3.最后确认这是一个'O'
				convertEdge_O_to_TEMP_DFS(board, tuple[0], tuple[1]); // →前往下一层
	}
	private boolean atEdge(char[][] board, int x, int y) {
		return x == 0 || x == board.length - 1 || y == 0
				|| y == board[0].length - 1;
	}
	private boolean withinBoard(char[][] board, int x, int y) {
		return 0 <= x && x < board.length && 0 <= y && y < board[0].length;
	}
}

class 太复杂的写法 {

	/**
	 * <pre>
	 * 基本解法：
	 * 1. 先找O的地方
	 * 	（？？'area'怎么定义？*连通*吗？）
	 * 2. 然后看旁边是不是被包('surrounded')（？？？）
	 * 		确认全部都被surrounded后，就
	 * 3. flip
	 * </pre>
	 */
	public void solve(char[][] board) {
		if (board.length == 0 || board[0].length == 0)
			return;
		boolean[][] visited = new boolean[board.length][board[0].length];

		// 1. get O area
		List<List<Tuple>> regions = new ArrayList<>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (visited[i][j])
					continue;
				if (board[i][j] == 'O')
					goGetNeighbor连通域(board, visited, i, j, regions);

				visited[i][j] = true;
			}
		}

		System.out.println("regions" + regions);
		// 2. determine if O area is surrounded
		for (List<Tuple> region : regions) {
			//
			// 1. 检查是不是有问题
			boolean atEdge = false;
			for (Tuple tuple : region) {
				if (atEdge(tuple, board)) {
					atEdge = true;
					break;
				}
			}
			if (atEdge)
				continue; // this region 有在edge的气口，无法被surrounded
			// 2. 接下来寻找临近area
			// System.out.println("2. 接下来寻找临近area");
			for (Tuple tuple : region) {
				System.out.println("现在在tuple = " + tuple);
				visited = new boolean[board.length][board[0].length];

				List<Tuple> near4 = getNear4(tuple, board);
				// System.out.println("near4" + near4);
				boolean status = false;
				for (Tuple nearNode : near4) {
					if (board[nearNode.x][nearNode.y] == 'X') {
						// boolean good = true;
						// System.out.println("好的");
					} else if (board[nearNode.x][nearNode.y] == 'O') {
						// System.out.println("不好的");
						status = verify(nearNode, visited, board);
						if (status == false) {
							// System.out.println("BREAK!" + tuple);
							break;
						} else {
							status = true;//
							board[tuple.x][tuple.y] = 'X'; // flip
						}
					}
					System.out.println(
							"结束，visited = " + Arrays.deepToString(visited));
				}
				if (status = true)
					board[tuple.x][tuple.y] = 'X'; // flip
				visited[tuple.x][tuple.y] = true;
			}

		}

		System.out.println(regions);
	}

	private boolean verify(Tuple tuple, boolean[][] visited, char[][] board) {
		if (visited[tuple.x][tuple.y])
			return true;
		List<Tuple> near4 = getNear4(tuple, board);
		for (Tuple nearNode : near4) {
			// System.out.println("verify里面，" + nearNode);
			visited[nearNode.x][nearNode.y] = true;
			if (!verify(nearNode, visited, board)) // any false is false
				return false;
		}
		return true;
	}

	private List<Tuple> getNear4(Tuple current, char[][] board) {
		List<Tuple> near4 = new LinkedList<>();
		Tuple[] near4Array = {//
				new Tuple(current.x + 1, current.y),
				new Tuple(current.x - 1, current.y),
				new Tuple(current.x, current.y + 1),
				new Tuple(current.x, current.y - 1)};
		for (Tuple tuple : near4Array)
			if (validateRange(tuple, board))
				near4.add(tuple);

		return near4;
	}

	/**
	 * 在边界的话无法被surrounded
	 */
	private boolean atEdge(Tuple tuple, char[][] board) {
		return tuple.x == 0 || tuple.x == board.length - 1 || //
				tuple.y == 0 || tuple.y == board[0].length - 1;
	}

	private void goGetNeighbor连通域(char[][] board, boolean[][] visited, int i,
			int j, List<List<Tuple>> regions) {
		List<Tuple> region = new ArrayList<>();
		region.add(new Tuple(i, j));
		if (visited[i][j])
			return;
		else
			visited[i][j] = true;
		Queue<Tuple> bfs = new LinkedList<>();
		bfs.add(new Tuple(i, j));
		// System.out.println("at " + i + ", " + j);
		while (!bfs.isEmpty()) {
			Tuple current = bfs.poll();
			// System.out.println("at " + current);
			// 0. validate
			if (!validateRange(current, board)) {
				continue;
			}
			if (board[current.x][current.y] != 'O') {
				continue; // 只寻找'O'的连通域-如果不是O，就跳过（到边界了）
			}
			if (visited[current.x][current.y]) {
				continue;
			}

			// 1. process
			visited[current.x][current.y] = true;
			region.add(current);

			// System.out.println("at " + current);
			// 2. add 4个方向 neighbor
			bfs.add(new Tuple(current.x + 1, current.y));
			bfs.add(new Tuple(current.x - 1, current.y));
			bfs.add(new Tuple(current.x, current.y + 1));
			bfs.add(new Tuple(current.x, current.y - 1));

		}
		regions.add(region);

	}

	private boolean validateRange(Tuple current, char[][] board) {
		return 0 <= current.x && current.x < board.length && //
				0 <= current.y && current.y < board[0].length;
	}
	public static void main(String[] args) {
		SurroundedRegions s = new SurroundedRegions();
		// char[][] board = {{'X', 'X', 'X'}, {'X', 'O', 'X'}, {'X', 'X', 'X'}};
		// s.solve(board);
		// System.out.println(Arrays.deepToString(board));
		// char[][] board2 = {{'O', 'O'}, {'O', 'O'}};
		// s.solve(board2);
		// System.out.println(Arrays.deepToString(board2));
		// char[][] board = {{'O', 'O', 'O'}, {'O', 'O', 'O'}, {'O', 'O', 'O'}};
		// s.solve(board);
		// System.out.println(Arrays.deepToString(board));
	}

}
class Tuple {
	public final int x;
	public final int y;
	public Tuple(int x, int y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Tuple))
			return false;
		Tuple other = (Tuple) obj;
		return other.x == x && other.y == y;
	}
}