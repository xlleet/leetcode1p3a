package medium;

import java.util.*;

import easy.TreeNode;

/**
 * https://leetcode.com/problems/sum-root-to-leaf-numbers/
 */
public class SumRootToLeafNumbers {
	/**
	 * @2016-7-10 自己手写
	 * 
	 * @进一步优化：不存path，只存值; space to O(1).
	 * @pass leetcode最优速度。(因为如果用List<>的话一定会变慢)
	 * 
	 * @int [1] 的灵感来自于另一个path sum any的题
	 *      leetcode.com/problems/binary-tree-maximum-path-sum/
	 */
	public int sumNumbers(TreeNode root) {
		int[] result = new int[1];
		dfsGetAllPaths(root, new int[1], result);
		return result[0];
	}
	private void dfsGetAllPaths(TreeNode root, int[] pathSum, int[] result) {
		if (root == null)
			return;
		if (root.left == null && root.right == null) {
			result[0] += pathSum[0] * 10 + root.val;
			return;
		}
		pathSum[0] = pathSum[0] * 10 + root.val; // add current val
		dfsGetAllPaths(root.left, pathSum, result);
		dfsGetAllPaths(root.right, pathSum, result);
		pathSum[0] /= 10; // from 21 to 2, just removed 1
	}
}

class 第一步优化 {

	/**
	 * @2016-7-10 自己手写
	 * 
	 * @space O(n) 但是稍微有所提高；worst case (tree=link-list的时候还是需要n的space来存path.
	 * @time O(n)
	 * 
	 * @优化：把compute path移动到内部，这样就不需要n的space，只需要path length的space。
	 * 
	 * @进一步优化：不存path，只存值; space to O(1).
	 */
	public int sumNumbers(TreeNode root) {
		int[] result = new int[1];
		dfsGetAllPaths(root, new ArrayList<Integer>(), result);
		return result[0];
	}
	private void dfsGetAllPaths(TreeNode root, List<Integer> currentPath,
			int[] result) {
		if (root == null)
			return;
		if (root.left == null && root.right == null) {
			int pathNumSum = sumPath(currentPath) * 10 + root.val; // 算上目前这一层
			result[0] += pathNumSum;
			return;
		}
		currentPath.add(root.val);
		dfsGetAllPaths(root.left, currentPath, result);
		dfsGetAllPaths(root.right, currentPath, result);
		currentPath.remove(currentPath.size() - 1);
	}
	private int sumPath(List<Integer> path) {
		int pathNumSum = 0;
		for (int value : path) {
			pathNumSum *= 10; // from 2 to 20
			pathNumSum += value; // 2->1, value = 1: 20 + 1 = 21.
		}
		return pathNumSum;
	}
}

class 自己写法dfsGetAllPath {
	/**
	 * @2016-7-10 自己写出; low pass, 5%
	 * 
	 * @space O(n) 这里n表示有多少path
	 * 
	 * @time O(n) 每个note不重复的visit；因为是DFS
	 * 
	 *       <pre>
	 *        1
		     / \
		    /   \
		   /     \
		  2       3
		 / \     / \
		4   5   6   7
		例如这个题
	 *       </pre>
	 * 
	 * @优化：可能需要不存，而是一直记着数字，加数字。
	 */
	public int sumNumbers(TreeNode root) {
		List<List<Integer>> allPaths = new ArrayList<>();
		dfsGetAllPaths(root, allPaths, new ArrayList<Integer>());
		int result = 0;
		for (List<Integer> path : allPaths) {
			int pathVal = 0;
			for (int value : path) {
				pathVal *= 10; // from 2 to 20
				pathVal += value; // 2->1, value = 1: 20 + 1 = 21.
			}
			result += pathVal;
		}
		return result;
	}

	private void dfsGetAllPaths(TreeNode root, List<List<Integer>> allPaths,
			List<Integer> currentPath) {
		if (root == null)
			return; // 这里不加因为会重复: 例如root左右都没有，这样的话left和right都会来add一遍。
		if (root.left == null && root.right == null) {
			currentPath.add(root.val);
			allPaths.add(new ArrayList<>(currentPath));
			currentPath.remove(currentPath.size() - 1);
			return;
		}
		currentPath.add(root.val);
		dfsGetAllPaths(root.left, allPaths, currentPath);
		dfsGetAllPaths(root.right, allPaths, currentPath);
		currentPath.remove(currentPath.size() - 1);
	}
}
