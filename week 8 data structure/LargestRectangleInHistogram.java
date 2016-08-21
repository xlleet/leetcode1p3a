package stack.hard;

import java.util.*;

/**
 * https://leetcode.com/problems/largest-rectangle-in-histogram/
 */
public class LargestRectangleInHistogram {
	/**
	 * https://discuss.leetcode.com/topic/3913?
	 * 这个的C版本加了个0，但java不能加，所以在i==n的时候做手脚。
	 * 
	 * 九章的具体解答： http://www.cnblogs.com/yuzhangcmu/p/4191981.html
	 * 
	 * <pre>
	 * 一句话解答：保持一个stack with [monotone increasing] values, 然后pop when meet an element that is not greater than.
	 * 
	 * 1. for loop: add each element [iRight] -- 每个加一个
	 * 		1.1. 第二层loop: stack.NotEmpty & non greater / last one [如果A[i]比stack.top()要小]: -- 准备pop stack -
	 * 			- pop stack了之后，每个pop出来的value都比目前的大，所以以他形成的rectangle不包括iRight
	 * 			1.1.1. height = popStack，也就是现在要算的rectangle的高度
	 * 			1.1.2. width 根据stack.Emtpy?有两种情况
	 * 				1.1.2.1. stack is empty: 那width = iRight。意思就是从stack.pop左边就没有比stack.pop这个还小的了，
	 * 					- 也就是stack.pop是目前这个increasing中的最小的；自己左边要么比自己大，要么没有。
	 * 					- 而根据stack的原理，自己的右边(截止[i-1])的val都比自己大 && 自己左边也比自己大，所以可以形成i长的rectangle。所以是从current一直延长到
	 * 						- 形容： {矩形从stack.pop开始，向两侧←→延伸--[0..iRight-1],长度=iRight}
	 * 				1.1.2.2. stack not empty: 也就是左边还有比自己小的(所以不能往左←边走)
	 * 					- 那就peek一下
	 * 					- 那就是从stack.peek()+1开始，向→右侧延伸：{stack.peek+1 .. iRight-1} (因为iRight比我小)
	 * 						- width = (iRight-1)-(stack.peek+1)+1 (真实公式)
	 * 								= iRight-1-stack.peek         (简化版；网上答案)
	 * 						- 例如当stack.peek = 1, pop的=2, iRight=3的时候，就是{2..2}, len = 1
	 * 					- 所以assert:
	 * 						- A[stack.peek()] < A[stack.pop() (之前pop的)]
	 * 						- A[iRight]       < A[stack.pop() (之前pop的)]
	 * 						- 然后中间是可以有greater的：
	 * 							- pop的右边可以greater，因为是递增stack；
	 * 							- pop的左边和peek中间可以有greater，因为是递增的，所以有greater的话就吃掉了
									peek()	pop()
									  v     v
								         *    *    
								         *  * * iRight 
								      *  *  * *  v  
								      *  *  * *   
								*     *  *  * *  *  
								*  *  *  *  * *  *  
										 ^
									  in-between
	 * 这样就很清楚了。
	 * 
	 * 注意一些 i==n的ending condition的事情：
	 * - i==n的之后：
	 * 		- while loop结束后for loop也会(implicitly)终止
	 * 		- 最后一次的stack.push(n)毫无意义，因为for终止了，里面的while也不会再进去。
	 * 
	 * walkthrough example在下面有
	 * </pre>
	 */
	public static int largestRectangleArea(int[] height) {
		final int n = height.length;
		int maxArea = 0;
		Stack<Integer> stackIndex = new Stack<>();

		for (int iRight = 0; iRight <= n; iRight++) {
			System.out.println(" i = " + iRight + ",A[i]=" + (iRight == n ? -100 : height[iRight]) + ", index = "
					+ stackIndex + ", last = " + (stackIndex.isEmpty() ? "NO" : stackIndex.peek()));
			// 要保持一个[单调increasing]的stack
			// 两种情况下pop：1. i==n的时候，清空
			// 2. value[stackIndex.last] >= value[i]的时候。
			while (stackIndex.size() > 0 && (iRight == n || height[stackIndex.peek()] >= height[iRight])) {
				System.out.println("\t stack out:" + stackIndex.peek() + ", A[out] = " + height[stackIndex.peek()]);
				int h = height[stackIndex.pop()];
				int width = stackIndex.size() > 0 ? (iRight - 1) - (stackIndex.peek() + 1) + 1 : // 如果刚才pop的是最后一个
						iRight;
				maxArea = Math.max(maxArea, h * width);
				System.out.println("\t\t area:" + h * width);
			}
			stackIndex.push(iRight); // 最后一个i==n的push没有意义
		}
		return maxArea;
	}

	/**
	 * <pre>
	 * 走一遍例子:
	 * 0   1  2  3  4  5  <-   i
	 * {2, 1, 5, 6, 2, 3} <- A[i]
		         *    
		      *  *    
		      *  *    
		      *  *     *
		*     *  *  *  *
		*  *  *  *  *  *
	 * 
	 *  i = 0,A[i]=2, index = [], last = NO
	 *  i = 1,A[i]=1, index = [0], last = 0
	 * 	 stack out:0, A[out] = 2
	 * 		 area:2---把A[0]=2拿了出来，sidx=1
	 *  i = 2,A[i]=5, index = [1], last = 1
	 * 		--- 递增中，无视
	 *  i = 3,A[i]=6, index = [1, 2], last = 2
	 *  		--- 递增中，无视
	 *  i = 4,A[i]=2, index = [1, 2, 3], last = 3
	 *  --- current: A[i]={2}比previous {6}小，所以要pop
	 * 	 stack out:3, A[out] = 6
	 * 		 area:6 --- 以{6}为高的rectangle只有width={1}(自己),因为自己左边比自己小(stack.peek()),右边也比自己小(A[i])
	 * 	 stack out:2, A[out] = 5
	 * 		 area:10 --- 以{5}为高的rectangle,有width={2}(自己+右边)；peek左边还是比自己小，但右边有一个空的(不在stack里了)、大的{6}。
	 * 				--- 这个{6}你怎么知道?因为刚才刚pop，而[i]还是没变。
	 *  i = 5,A[i]=3, index = [1, 4], last = 4
	 *  i = 6,A[i]=-100, index = [1, 4, 5], last = 5
	 *  		--- 最后cleanup，i=n方便计算i-n
	 * 	 stack out:5, A[out] = 3
	 * 		 area:3 --- pop出来，然后往右算area：第一次的area的width都是1
	 * 	 stack out:4, A[out] = 2
	 * 		 area:8 --- pop出来，index = 4, 然后是[4,5,6] -- 宽=2，高=4(A[6]不存在的，只是用这个来算width)
	 * 	 stack out:1, A[out] = 1
	 * 		 area:6 --- pop出来， index=1，[1,2,3,4,5,6]，因为是最后一个所以宽
	 * 10
	 * 这里index=[]存的其实是previous 的index，看previous的value(A[previous])是否比右边的大，如果小的话就OK。
	 * </pre>
	 */
	public static void main(String[] args) {
		System.out.println(largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
	}
}
