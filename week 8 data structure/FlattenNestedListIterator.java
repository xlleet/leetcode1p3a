package medium;
import java.util.*;

/**
 * <pre>
 * https://leetcode.com/problems/flatten-nested-list-iterator/
 * 
 * 2016-8-15 培训8-作业
 * 
 * 1. 类似题NestedIterator:，也是用dfs：
 * 		https://leetcode.com/problems/nested-list-weight-sum/ 
 * 2. 类似题Iterator: 一样是两种解法，一种是new()的时候全部做完，一种是一边next()一边做。
 * 		https://leetcode.com/problems/binary-search-tree-iterator/
 * 
 * 上课提醒事项：
 * 1. The user might call hasNext() without calling next()
 * 2. The user might call next() without calling hasNext();
 * 
 * 解法1：stack，需要push(list.reverse())
 * 解法2：stack & iter，需要nextInt
 * 解法3：stack & list-iter, 需要previous
 * </pre>
 */
public class FlattenNestedListIterator {
	/**
	 * <pre>
	 * // 需要处理[[]]的情况: 
	 * // [[1],[]] is not [1,null] but [1]
	 * // 处理 [[-1],[],[]] ： 好多个emptyList
	 * https://discuss.leetcode.com/topic/42042/? - 用的是这个
	 * 
	 * 重点不是stack，重点是同进同出(可以用deque只加头)
	 * 
	 * 一句话解答：
	 * - 其实还是DFS with explicit stack
	 * 0. init就是add reverse to stack：这样第一个element第一个出。
	 * 1. next就只需要pop就好了
	 * 2. hasNext()比较长；确认的是next()之前一定会有一次hasNext() call
	 * 
	 * hasNext():
	 * 	1. stackEmpty就退
	 * 	2. curr=peek()如果是int就ok，不是就pop掉(移除cur)，然后把cur的list.reverse()加进去(例如list=[1,2,3]，这样加到stack里希望第一个出来的是1)
	 * 		- 但不能用queue，因为这时候current的sublist的优先级比后面的高；
	 * 			- 也就是[[1,2],3,4]的时候到[1,2]先处理[1,2]，而不是把[1,2]加到[3][4]后面
	 * 		- 可以Deque
	 * </pre>
	 */
	public class NestedIterator implements Iterator<Integer> {
		Stack<NestedInteger> stack;
		public NestedIterator(List<NestedInteger> nestedList) {
			stack = new Stack<>();
			addAllReverse(nestedList);
		}
		public Integer next() {
			return stack.pop().getInteger();
		}
		public boolean hasNext() {
			if (stack.isEmpty())
				return false;
			NestedInteger current = stack.peek();
			if (current.isInteger())
				return true;
			// else is list: remove this and add later
			stack.pop();
			addAllReverse(current.getList());
			return hasNext(); // can be written as while(!stack.isEmpty()){}
		}
		private void addAllReverse(List<NestedInteger> list) {
			for (ListIterator<NestedInteger> iterator = list.listIterator(list.size()); iterator.hasPrevious();)
				stack.add(iterator.previous());
			// Colletions.reverse(list); stack.add(list); 缺点是会modify input
		}
	}
}
/**
 * <pre>
 * 一句话解答：反正就用iter。相当于nested stack，也就是stack里的还没有被flatten。
 * 然后这样的话，后来加上来的东西也会堆上去。然后iter会保存[我走到哪里]了这个东西，很方便。
 * 
 * (具体解法里，注意(!iter.hasNext)=>stack.pop => (移动到下一个)
 * 		- 然后iter赋值给nextInt：如果是iter就OK，不是的话就push.iter.Cur
 * 
 * - 还有用nextInt也比较麻烦
 * - 然后一定要先call一次hasNext
 * 
 * 2016-8-20 上课解法
 * https://discuss.leetcode.com/topic/44983/? 一个stack，一个nextInt上课用的解法。
 * https://discuss.leetcode.com/topic/41870/? 类似stack，不用nextInt，但用listIter -- 可以previous()
 * </pre>
 */
class 解法2_上课讲的一用iterator {
	public class NestedIterator implements Iterator<Integer> {
		private Stack<Iterator<NestedInteger>> stack;
		private NestedInteger nextInt;
		public NestedIterator(List<NestedInteger> nestedList) {
			stack = new Stack<>();
			stack.push(nestedList.iterator());
		}
		public Integer next() {
			hasNext();
			Integer ret = nextInt.getInteger();
			nextInt = null;
			return ret;
		}
		public boolean hasNext() {
			if (nextInt != null)
				return true;
			while (!stack.isEmpty()) {
				Iterator<NestedInteger> iter = stack.peek();
				if (!iter.hasNext()) {// empty iter，那就pop掉。
					stack.pop();
					continue;
				}
				nextInt = iter.next();
				if (nextInt.isInteger())
					return true;
				else
					stack.push(nextInt.getList().iterator()); // 还要再改一波nextInt,先把iter放进去
			}
			return false;
		}
	}
}

/**
 * <pre>
 * 2016-8-15 随手写的解法1，10分钟写完；
 * 
 * 一句话解答：DFS， recursive;
 * - 用一个LinkedList保存iterator
 * 		- next就只result.getNext()
 * 		- hasNext就是list.NotEmpty
 * - dfs过程：简单无脑
 * 		- 如果是int,那就add to result
 * 		- 如果不是int，那就是NestedList，那就dfs(this)
 * 		- 记得input也是一个list哦
 * - 因为dfs实际上是用stack的，所以99%可以用stack来提高（？改写？)
 * 		7ms, beats 79.73%
 * </pre>
 */
class 随手写的解法1_DFS_recursive_getAllWhenInit {
	public class NestedIterator implements Iterator<Integer> {
		private LinkedList<Integer> result = new LinkedList<>();
		public NestedIterator(List<NestedInteger> nestedList) {
			dfs(nestedList);
		}
		@Override
		public Integer next() {
			return result.removeFirst();
		}
		private void dfs(List<NestedInteger> nestedList) {
			for (NestedInteger current : nestedList)
				if (current.isInteger())
					result.add(current.getInteger());
				else
					dfs(current.getList());
		}
		@Override
		public boolean hasNext() {
			return result.size() > 0;
		}
	}
}

interface NestedInteger {
	public boolean isInteger(); // only 2, no null
	public Integer getInteger();
	public List<NestedInteger> getList();
}