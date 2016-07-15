package medium;

import easy.ListNode;

/**
 * https://leetcode.com/problems/odd-even-linked-list/
 * 
 */
public class OddEvenLinkedList {
	/**
	 * 2016-7-13 自己手写解法，按照
	 * https://leetcode.com/problems/copy-list-with-random-pointer/ 的思路
	 * 
	 * 优化： https://discuss.leetcode.com/topic/34292
	 * 
	 * <pre>
	Given 1->2->3->4->5->NULL,
	return {1->3->5}->{2->4}->NULL.
	
	计划：跳着进行move：一个oddHead和一个evenHead，然后之后把oddTail连上evenTail。
	类似copy list with random pointer
	
	一开始：
	1 => 2 => 3 => 4 => 5 => null
	
	第一圈后：
	┌--------┐
	↑         ↓
	1    2    3 => 4 => 5 => null
		 ↓         ↑
		 └--------┘
	
	然后就有：
	1 => 3 => 5 => X
	2 => 4 => X
	这时候c1 = 5, c2 = null
	=> 于是c1.next = h2 ---- 全剧终。(case 1)
	
	万一长度是even (n%2==0)呢？
	
	1. 一开始：
	1 => 2 => 3 => 4 => null
	
	2. 第一圈后：
	┌--------┐
	↑         ↓
	1    2    3 => 4 => null
		 ↓         ↑
		 └--------┘
	
	3. 第二圈：c1=3, c2 = 4
	c1.next = c2.next: c1.next = null了 (1 => 3 => X)
	c2.next = c1.next: c2.next继续是null (2 => 4 => X)
	然后移动, 
	c1 = c1.next : c1 = null
	c2 = c2.next : c2 = null
	这时候结束出来你啥也不知道
	
	所以这时候在c1.next的时候是null的时候，直接走c1.next = h1，然后让c1 = null. (case 2)
	 * </pre>
	 * 
	 */

	public static ListNode oddEvenList(ListNode head) {
		if (head == null)
			return null;
		final ListNode evenHead = head.next;
		ListNode c1 = head, c2 = head.next;
		for (; c1.next != null && c2.next != null; c1 = c1.next, c2 = c2.next) {
			/**
			 * <pre>
			 * 把c1,c2 check null 改成 c1.next和c2.next: 
			 * 也就是在c2=c2.next后，c2.next=null的时候会退出；
			 * 		意思就是在 (case 2)里c1=3, c2=4的时候就退出了， 这时候c1是odd tail，c2是 even tail;
			 *  	这样中间就不用加那段return code了。
			 * </pre>
			 */
			c1.next = c1.next.next;
			c2.next = c2.next.next;
		}
		// if (c1 != null) // c1 is tail of odd (case 1) // 要不然会在里面return
		c1.next = evenHead;
		return head;
	}

	/**
	 * <pre>
	 * public static ListNode oddEvenList初始手稿(ListNode head) {
	 * 	final ListNode evenHead = head.next;
	 * 	ListNode c1 = head, c2 = head.next;
	 * 	for (; c1 != null && c2 != null; c1 = c1.next, c2 = c2.next) {
	 * 		c1.next = c1.next.next;
	 * 		if (c1.next == null) { // c1 is the tail of odd (case 2)
	 * 			c1.next = evenHead;
	 * 			return head;
	 * 		}
	 * 		c2.next = c2.next.next;
	 * 	}
	 * 	// if (c1 != null) // c1 is tail of odd (case 1) // 要不然会在里面return
	 * 	c1.next = evenHead;
	 * 	return head;
	 * }
	 * </pre>
	 */
}
