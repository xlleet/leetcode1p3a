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
 * </pre>
 */
public class NestedIterator implements Iterator<Integer> {
	private LinkedList<Integer> result;
	public NestedIterator(List<NestedInteger> nestedList) {
		result = new LinkedList<>();
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

