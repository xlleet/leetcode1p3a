package traverse;

import java.util.*;

import easy.TreeNode;

/**
 * https://www.wikiwand.com/en/Tree_traversal
 */
public class PostOrder {
	/**
	 * https://leetcode.com/problems/binary-tree-postorder-traversal/
	 * 
	 * 这个比较简单 https://discuss.leetcode.com/topic/2919 ;
	 * https://discuss.leetcode.com/topic/7427
	 * 
	 * 
	 * @see leetcode.com/discuss/71943/?pre和in和post的iterative；这个比较复杂，用了多pointer，
	 *      但似乎有很好的模板。
	 */
	public static List<Integer> postorderTraversal_Iter(TreeNode root) {
		LinkedList<Integer> result = new LinkedList<Integer>(); // LinkedList.addFirst()
		Deque<TreeNode> stack = new LinkedList<TreeNode>();
		stack.push(root);
		while (!stack.isEmpty()) {
			TreeNode current = stack.pop();
			if (current == null)
				continue;
			// [1]注意是addFirst加到头……其实也就是加到尾的反过来；是最深的(以left-most-DFS而言)在最前面，最浅的在最后面。
			result.addFirst(current.val);
			// [2]注意是先left再right,因为会被反过来;也就是会先去right，但结果会是left和deep的在前面。(pre的是left和non-deep的在前面)
			stack.push(current.left);
			stack.push(current.right);
		}
		return result;
	}
}
