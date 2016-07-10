package easy;

public class MinDepth {
	/**
	 * 2016-7-10 快速手写
	 */
	public int minDepth(TreeNode root) {
		return helper(root, 0);
	}
	private int helper(TreeNode root, int depth) {
		if (root == null)
			return depth;
		int leftDepth = helper(root.left, depth + 1);
		int rightDepth = helper(root.right, depth + 1);
		if (root.left == null) // 这里要注意一下： 如果只有一边有路的情况下是不能说没路的，得沿着唯一的一条路走下去。
			return rightDepth;
		else if (root.right == null)
			return leftDepth;
		else
			return Math.min(leftDepth, rightDepth);
	}

}

class 其他写法_没有很大差别_不需要看 {
	/**
	 * http://www.jiuzhang.com/solutions/minimum-depth-of-binary-tree/
	 * 
	 * http://www.lintcode.com/en/problem/minimum-depth-of-binary-tree/
	 * https://leetcode.com/problems/minimum-depth-of-binary-tree/
	 */
	public int minDepthJZ(TreeNode root) {
		if (root == null)
			return 0;

		return getMin(root);
	}

	public int getMin(TreeNode root) {
		if (root == null) {
			return Integer.MAX_VALUE;
		}

		if (root.left == null && root.right == null) {
			return 1;
		}

		return Math.min(getMin(root.left), getMin(root.right)) + 1;
	}

	/**
	 * 
	 * https://leetcode.com/discuss/76318/
	 * 
	 * @param root
	 * @return
	 */
	public int minDepth(TreeNode root) {
		if (root == null)
			return 0;
		if (root.left != null && root.right != null)
			return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
		else
			return Math.max(minDepth(root.left), minDepth(root.right)) + 1;
	}

	/**
	 * https://leetcode.com/discuss/25060/my-4-line-java-solution
	 */
	public int minDepth2(TreeNode root) {
		if (root == null)
			return 0;
		int left = minDepth2(root.left);
		int right = minDepth2(root.right);
		return (left == 0 || right == 0)
				? left + right + 1
				: Math.min(left, right) + 1;
	}

	public static int minDepth3(TreeNode root) {
		if (root == null)
			return 0;
		if (root.left == null)
			return minDepth3(root.right) + 1;
		if (root.right == null)
			return minDepth3(root.left) + 1;
		else
			return Math.min(minDepth3(root.left), minDepth3(root.right)) + 1;
	}

	public int minDepth4(TreeNode root) {
		if (root == null)
			return 0;
		else if (root.left != null && root.right != null)
			return Math.min(minDepth4(root.left), minDepth4(root.right)) + 1;
		else
			// either minDepth(root.left) or minDepth(root.right) is 0 in this
			// case
			return minDepth4(root.right) + minDepth4(root.left) + 1;
	}
}