package search;

import java.util.*;

/**
 * https://leetcode.com/problems/course-schedule/
 * 
 * 就业培训第七周-graph/dfs/bfs
 */
/**
 * <pre>
 * 注意关于cycle dection:
 * 如果是undirected，那么只要之前visit到就行了
 * 如果是directed，那么是【如果to曾经是一个from】那就会有cycle。
 * </pre>
 */
public class CourseSchedule {
	/**
	 * 解法1 BFS with DAG.
	 * 
	 * 运用的定理：一个DAG，如果删除source+所有的out going edges（然后source本身就不存在incoming
	 * edge），然后接下来又会存在这样的source；一波接一波，如果是DAG的话会最后把所有的都移除了。
	 * 
	 * 基于 https://discuss.leetcode.com/topic/15762 （BFS with DAG, not toposort)
	 * https://discuss.leetcode.com/topic/13854
	 */
	public boolean canFinish(int numCourses, int[][] prerequisites) {
		// 0. setup
		List<List<Integer>> graph = getGraph(numCourses, prerequisites);
		int[] indegrees = getIndegree(numCourses, prerequisites); // Map<Node,int>
		// 1. find all sources in toposort (source:indegree=0) and remove them
		Queue<Integer> queue = new LinkedList<>();
		for (int node = 0; node < numCourses; node++)
			if (indegrees[node] == 0)
				queue.add(node);
		// 2. remove each source in queue
		while (!queue.isEmpty()) {
			int source = queue.poll();
			List<Integer> outgoingNode = graph.get(source);
			if (outgoingNode == null)
				continue;
			for (int to : outgoingNode) {
				indegrees[to]--; // visited
				if (indegrees[to] == 0) // you are now source
					queue.add(to);
			}
		}
		// 3. 结束。如果这时候删除到了所有的from，那就是个DAG,所以没有cycle。
		for (int indegree : indegrees)
			if (indegree != 0)
				return false;
		return true; // return 另一种写法：maintain一个0_indegree_count,为n的时候就是true。
	}

	private int[] getIndegree(int nNodes, int[][] edges) {
		int[] indegree = new int[nNodes];
		for (int[] edge : edges) {
			final int to = edge[1];
			indegree[to]++;
		}
		return indegree;
	}

	/**
	 * // 用的是dense_graph;用map的话会TLE。
	 */
	protected static List<List<Integer>> getGraph(int numNode, int[][] edges) {
		List<List<Integer>> graphAdjanList = new ArrayList<>(numNode);
		for (int i = 0; i < numNode; i++) // init
			graphAdjanList.add(new ArrayList<Integer>());
		for (int[] edge : edges) {
			final int from = edge[0], to = edge[1];
			graphAdjanList.get(from).add(to);
		}
		return graphAdjanList;
	}

	/**
	 * 这里的的list是to->from
	 */
	protected static List<List<Integer>> getGraphReverse(int numNode,
			int[][] edges) {
		List<List<Integer>> graphAdjanListReverse = new ArrayList<>(numNode);
		for (int i = 0; i < numNode; i++)
			graphAdjanListReverse.add(new ArrayList<Integer>());
		for (int[] edge : edges) {
			final int from = edge[0], to = edge[1];
			graphAdjanListReverse.get(to).add(from); // to → from
		}
		return graphAdjanListReverse;
	}
}
/**
 * https://discuss.leetcode.com/topic/15762/java-dfs-and-bfs-solution/2
 */
class TopoSort_DFS extends CourseSchedule {
	/**
	 * 解法2. https://discuss.leetcode.com/topic/15762 DFS的解法
	 * 
	 * 为什么要graph reverse？
	 */
	public boolean canFinish(int numCourses, int[][] prerequisites) {
		final List<List<Integer>> graphReverse = getGraphReverse(numCourses,
				prerequisites);

		boolean[] dfs_visited = new boolean[numCourses];
		for (int node = 0; node < numCourses; node++)
			if (dfs_hasCycle(graphReverse, dfs_visited, node)) // 检查是否通（false：有cycle）
				return false;

		return true;
	}

	/**
	 * 这里return的true/false是什么意思？——是是否有cycle，也就是是否是dfs_visited的意思
	 */
	private boolean dfs_hasCycle(final List<List<Integer>> graphReverse,
			boolean[] dfs_visited, int root) {
		if (dfs_visited[root])
			return true;
		dfs_visited[root] = true; // 标记current
		List<Integer> incomingNodes = graphReverse.get(root);
		if (incomingNodes != null)
			for (int neighbor : incomingNodes)
				if (dfs_hasCycle(graphReverse, dfs_visited, neighbor)) // 做DFS，往深处找，看有没有cycle。
					return true;
		dfs_visited[root] = false; // 结束visited
		return false; // didn't find cycle
	}

}

class 自己写但是TLE_20160729 extends CourseSchedule {
	/**
	 * @{2016-07-29 10.40 AM}写完但是TLE
	 * 
	 * 对每个i进行bfs（待优化），如果i的neighbor里有cycle就false，否则继续loop； 消耗是O(n(n+m)),进行n次BFS
	 */
	public boolean canFinish(int numCourses, int[][] prerequisites) {
		List<List<Integer>> graphAdjanList = getGraph(numCourses,
				prerequisites);
		// O(n(n+m)) 做N次DFS
		for (int i = 0; i < numCourses; i++) {
			Deque<Integer> dfs = new LinkedList<>(); // 找loop要dfs嘛？
			Set<Integer> visited = new HashSet<>();
			dfs.add(i);
			visited.add(i);
			while (!dfs.isEmpty()) {
				int currentFrom = dfs.pop();
				if (visited.contains(currentFrom) && currentFrom != i)
					continue;
				else
					visited.add(currentFrom);
				List<Integer> neighbors = graphAdjanList.get(currentFrom);
				if (neighbors != null)
					for (Integer to : neighbors) {
						dfs.add(to);
						if (to == i)
							return false; // 要to==i的时候才说有cycle；不能说visited过就是有cycle（例如[0,1],[1,2],[0,2]不算有cycle)
					}
			}
		}
		return true;
	}
	/**
	 * <pre>
	 * public static void main(String[] args) {
	 * 	System.out.println(canFinish1(3, new int[][]{{1, 0}, {2, 0}}) == true);
	 * 	System.out.println(canFinish1(2, new int[][]{{1, 0}, {0, 1}}) == false);
	 * 	// // 3, [[0,1],[0,2],[1,2]] , true
	 * 	System.out.println(
	 * 			canFinish1(3, new int[][]{{0, 1}, {0, 2}, {1, 2}}) == true);
	 * 	System.out.println(canFinish1(8, new int[][]{{1, 0}, {2, 6}, {1, 7},
	 * 			{6, 4}, {7, 0}, {0, 5}}) == true); //
	 * 	// 不用visited，直接问to==i与否；因为这次是寻找一个从i开始的path。—
	 * 	int[][] a = {{0, 1}, {3, 1}, {1, 3}, {3, 2}};
	 * 	System.out.println(canFinish1(4, a) == false);
	 * }
	 * 
	 * </pre>
	 */
}
