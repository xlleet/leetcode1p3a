package priority_queue;
import java.util.*;

/**
 * https://leetcode.com/problems/top-k-frequent-elements/
 * 
 * Your algorithm's time complexity must be better than O(n log n), where n is
 * the array's size.
 * 
 */
public class TopKFrequentElements {
	// 要不要看bucket-sort?询问中。http://www.1point3acres.com/bbs/thread-199675-1-1.html
	// https://discuss.leetcode.com/topic/44237/java-o-n-solution-bucket-sort
	// http://www.cnblogs.com/grandyang/p/4606710.html 这里的解法2
}
/**
 * 看到这种需要memorize / top-k的，你就用PQ，最无脑的
 * 
 * O((n+k)logn) = O(nlogn) (k < n)
 */
class PriorityQueue_直白的写法 {
	/**
	 * <pre>
	 * 直白的写法： priority queue
	 * 
	 * 1. get counter 计数器 O(n)
	 * 2. get PQ descending with value(freq)↓
	 * 3. add all entry to PQ; O(nlogn)
	 * 4. get top k result; O(klogn)
	 * </pre>
	 */
	public List<Integer> topKFrequent(int[] nums, int k) {
		// 1. get counter 计数器 O(n)
		Map<Integer, Integer> counter = getCounter(nums);// num => freq
		// 2. get PQ descending with value(freq)↓
		PriorityQueue<Map.Entry<Integer, Integer>> pQueue = getPQ();
		// 3. add all entry to PQ; O(nlogn)
		pQueue.addAll(counter.entrySet());
		// 4. get top k result; O(klogn)
		List<Integer> result = new LinkedList<>();
		for (int i = 0; i < k; i++)
			result.add(pQueue.poll().getKey());
		return result;
	}

	/**
	 * <pre>
	 * PQ's Comparator syntax:
	 * PriorityQueue<>
	 * (
	 * 	Comparator()
	 * 	{
	 * 		public int compare(e1, e2) {}
	 * 	}
	 * );
	 * </pre>
	 * 
	 */
	private PriorityQueue<Map.Entry<Integer, Integer>> getPQ() {
		PriorityQueue<Map.Entry<Integer, Integer>> pQueue = new PriorityQueue<> //
		(//
				new Comparator<Map.Entry<Integer, Integer>>() //
				{
					public int compare(Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) {
						return -(e1.getValue() - e2.getValue()); // descending
					}
				}//
		);
		return pQueue;
	}

	private Map<Integer, Integer> getCounter(int[] nums) {
		Map<Integer, Integer> counter = new HashMap<>(); // num => freq
		for (int val : nums) {
			// if (!counter.containsKey(val)) counter.put(val, 0);
			// counter.put(val, counter.get(val) + 1);
			counter.put(val, counter.getOrDefault(val, 0) + 1); // 新写法，灵感：https://discuss.leetcode.com/topic/44237/
		}
		return counter;
	}
}