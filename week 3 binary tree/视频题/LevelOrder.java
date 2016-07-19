public class LevelOrder {
	/**
	 * BFS, Queue <br>
	 * https://discuss.leetcode.com/topic/7647
	 * www.jiuzhang.com/solutions/binary-tree-level-order-traversal
	 * 
	 * 类似解法：可以用一个null来标记这一行的结束。https://discuss.leetcode.com/topic/10469
	 * 
	 * @time n, visit each
	 * @space n (extra as queue)
	 */
	public List<List<Integer>> levelOrder(TreeNode root) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		if (root == null)
			return result;
		Queue<TreeNode> bfs = new LinkedList<TreeNode>();
		bfs.add(root);
		ArrayList<Integer> currentLevelNode;
		while (!bfs.isEmpty()) {
			// add level when done
			final int currentLevelSize = bfs.size(); // todo changing

			currentLevelNode = new ArrayList<Integer>();
			for (int i = 0; i < currentLevelSize; i++) { // at this level
				TreeNode cur = bfs.poll();
				currentLevelNode.add(cur.val);
				addIfNotNull(bfs, cur.left);
				addIfNotNull(bfs, cur.right);
			}
			result.add(currentLevelNode);
		}
		return result;
	}
	private <T> void addIfNotNull(Queue<T> list, T object) {
		if (object != null)
			list.add(object);
	}
}
