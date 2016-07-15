package easy;

/**
 * https://leetcode.com/problems/intersection-of-two-linked-lists/
 */
public class Intersection {
	/**
	 * 解法2：<br>
	 * https://discuss.leetcode.com/topic/5527
	 * https://discuss.leetcode.com/topic/28067
	 * 
	 * <pre>
	 * 例子
	 * 
	A:           a1 → a2
	               		↘
	                  	  c1 → c2
	               		↗            
	B:     b1 → b2 → b3
	
	len(A) = 4 = n1
	len(B) = 5 = n2
	假设 n1 < n2
	如果A走完了就回B, B走完了就回A的话，
	A回B的时候，B距离终点还有 (n2-n1) 步；这里正好diff=n2-n1
	然后等B再走完这(diff) 步、回到headA的时候，A已经在headB前走了(diff) 步了。
	也就是说，这时候就和刚才的naive解法一样了。
	
	考虑：极端情况：一个的长度是另一个的2倍+？ --也没事，因为不会走好几圈的。
	
	A:            	   a1
	               		↘
	                 	  c1
	               		↗            
	B:    b1→b2→b3→b4
	
	走步		cur1		cur2	/
	0		a1		b1	
	1		c1		b2	
	2		b1~		b3		注意这里cur1来了b1了
	3		b2		b4	
	4		b3		c1	
	5		b4		a1~		注意这里cur2也换了，全剧终
	6		c1!		c1!		BOOM
	 * 
	 * </pre>
	 */
	public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
		ListNode cur1 = headA, cur2 = headB; // 之所以不叫A，B是因为过阵子会换

		while (cur1 != cur2 && cur1 != null && cur2 != null) {
			cur1 = cur1.next;
			cur2 = cur2.next;

			// 碰到后就结束
			// 这里必须要放在中间，不然没相交的情况要走很久。
			// 没相交的情况：走完(n1+n2)步后，两个都是null，退出。
			if (cur1 == cur2)
				return cur1;

			// 跑完后回到另一边；两个都重新assign过了以后，就距离交接点一样远了。
			// Once both of them go through reassigning,
			// they will be equi-distant from the collision point.
			if (cur1 == null)
				cur1 = headB;
			if (cur2 == null)
				cur2 = headA;
		}
		return cur1 == cur2 ? cur1 : null;
	}
}

class 基本解法_getSize {
	/**
	 * 2016-7-12 自己写 公车上想出来
	 * 
	 * 解法1 先拿length，然后长的那个走abs(len1-len2)，然后两个一起走，就走到了
	 */
	public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
		final int sizeA = getSize(headA);
		final int sizeB = getSize(headB);
		final int diffSize = Math.abs(sizeA - sizeB);

		for (int i = 0; i < diffSize; i++)
			if (sizeA > sizeB)
				headA = headA.next;
			else
				headB = headB.next;
		while (headA != headB && headA != null && headB != null) {
			headA = headA.next;
			headB = headB.next;
		}
		return headA == headB ? headA : null;
	}
	private int getSize(ListNode head) {
		int size = 0;
		for (ListNode cur = head; cur != null; cur = cur.next, size++);
		return size;
	}
}