/**
 * https://discuss.leetcode.com/topic/9936
 * 
 * <pre>
 *  例子
 *     
	  1
	 / \
	2   5
   / \   \
  3   4   6
  
  一上来root = 1
  切断1->2 (root.left = null), 变成：
	  1
	 × \
	2   5
   / \   \
  3   4   6
  然后继续去切断left:
  	root = 2, 切断左
	  1
	 × \
	2   5
   × \   \
  3   4   6
  继续往left=3走
  	root = 3：到达leaf。 左边本来就是null，右边也是null。
  	这时候root.right = left (都是null)
  	所以在leaf这里其实啥也不会做(if (root.left == null && root.right == null) { do nothing })
	于是回到了root=2, left=3, right=4
		往right走，也是什么都不会发生。
		这时候，就来merge了： root.right (4) = left (3)
		
				  1		这里root=2, left=3, right = 4 (已经飞出去)
				   \
				2   5
		新加上-> \   \
			      3   6
			      4 <- 原来的root.right断开了
		然后找到2的最右边的node——正好就是之前的oldLeft (3)。
		于是 {最右.right = oldRight} (也就是连接上)
		于是：
				  1
				   \
				2   5
			     \   \
			      3   6
			       \  <- 连接上
			        4
			        
这时候root=2的任务完成了，也就是main root(1)的left subtree已经做完了。
回到root = 1, oldLeft = 2, oldRight = 5.
然后root.right = oldLeft，也就是把刚才连接好的部分连起来：

       root-> 1            这里断开了(原来是1->5)的	  
连接上这里了->  \  	         
	oldLeft->	2	 	    5 <- oldRight
			     \  		 	 \
			      3 	  		  6
			       \  
			        4
		然后root找最右的——正好是4.
		然后{最右.right = oldRight}，就连上了。
 * </pre>
 */
class RecusiveLC2 {
	public void flatten(TreeNode root) {
		if (root == null)
			return;

		TreeNode oldLeft = root.left;
		TreeNode oldRight = root.right;

		root.left = null; // 砍断root->left这一条，但是root.left已经存在[left]里了。

		flatten(oldLeft);
		flatten(oldRight);

		// merge left & right
		root.right = oldLeft;
		getRightMostNode(root).right = oldRight;
	}

	private TreeNode getRightMostNode(TreeNode root) {
		TreeNode rightMostNode = root;
		for (; rightMostNode.right != null; rightMostNode = rightMostNode.right); // 去最右→right-most
		return rightMostNode;
	}
}