package search;

import java.util.*;

/**
 * https://leetcode.com/problems/reconstruct-itinerary/
 */
public class ReconstructItinerary {

	/**
	 * https://discuss.leetcode.com/topic/36370
	 * 
	 * 每次都换from和to
	 * 
	 * 但是为什么能解决我的code 的那种bug，还是不得而知；是因为是倒过来的吗？
	 */
	public static List<String> findItinerary(String[][] tickets) {
		Map<String, PriorityQueue<String>> map = getMap(tickets);
		List<String> route = new LinkedList<>(); // 因为要加头所以只能linkedlist
		Stack<String> stack = new Stack<>();
		stack.push("JFK");
		while (!stack.empty()) {
			String from = stack.peek();
			PriorityQueue<String> to = map.get(from);
			while (map.containsKey(from) && !to.isEmpty()) {
				stack.push(to.poll());
				from = stack.peek();
				to = map.get(from);
			}
			route.add(0, stack.pop()); // 加到头
		}
		return route;
	}

	protected static Map<String, PriorityQueue<String>> getMap(
			String[][] tickets) {
		Map<String, PriorityQueue<String>> map = new HashMap<>();
		for (String[] ticket : tickets) { // 0: from, 1: to
			// if (!map.containsKey(ticket[0])) // 不用JAVA8的写法
			// map.put(ticket[0], new PriorityQueue<>());
			// map.get(ticket[0]).add(ticket[1]);
			map.computeIfAbsent(ticket[0], k -> new PriorityQueue<String>())
					.add(ticket[1]);
		}
		return map;
	}
}
class 我失败的greedy解法 extends ReconstructItinerary {
	/**
	 * <pre>
	 * 每次随便抓一个
	 * 出错： {{"JFK", "KUL"}, {"JFK", "NRT"}, {"NRT", "JFK"}}
	 * 		--有两个同出发点，并且是lexical order大的那个的出发点才是正确的（小的是后面走）的时候；我这个写法会自动【先】走lexical小的，无论是不是能最后走通。
	 * </pre>
	 */
	public static List<String> findItinerary(String[][] tickets) {
		Map<String, PriorityQueue<String>> map = getMap(tickets);

		List<String> result = new ArrayList<>();
		result.add("JFK");

		while (!map.isEmpty()) {
			String from = result.get(result.size() - 1);
			PriorityQueue<String> to_s = map.get(from);
			if (to_s == null || to_s.isEmpty()) {
				System.out.println("PROBLEM!");
				break;
			} else
				result.add(to_s.poll());
			if (to_s.isEmpty())
				map.remove(from);
		}

		return result;
	}

	public static void main(String[] args) {
		Map<String[][], List<String>> testCases = new HashMap<>();
		testCases.put(
				new String[][]{{"MUC", "LHR"}, {"JFK", "MUC"}, {"SFO", "SJC"},
						{"LHR", "SFO"}},
				Arrays.asList(new String[]{"JFK", "MUC", "LHR", "SFO", "SJC"}));
		testCases.put(
				new String[][]{{"JFK", "SFO"}, {"JFK", "ATL"}, {"SFO", "ATL"},
						{"ATL", "JFK"}, {"ATL", "SFO"}},
				Arrays.asList(new String[]{"JFK", "ATL", "JFK", "SFO", "ATL",
						"SFO"}));

		testCases.put(
				new String[][]{{"JFK", "KUL"}, {"JFK", "NRT"}, {"NRT", "JFK"}},
				Arrays.asList(new String[]{"JFK", "NRT", "JFK", "KUL"}));

		for (String[][] input : testCases.keySet()) {
			List<String> expectedoutput = testCases.get(input);
			List<String> output = findItinerary(input);
			System.out.println(
					"test case  = " + Arrays.deepToString(input) + " pass "
							+ (output.equals(expectedoutput) ? "YES" : output));

		}

	}
}