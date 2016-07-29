class FooBar {
	/**
	 * http://www.jiuzhang.com/solutions/backpack-iii/
	 *
	 * infinite amount; http://mat.gsia.cmu.edu/classes/dynamic/node5.html
	 * 
	 * <pre>
	 * 装不满的情况：
	 * weight=[2,4]
	 * values=[3,5]
	 * (item 0 = (2,3), item 1 = (4,5))
	 * backpackSize = 5
	 * 这时候dp:
	 * index(weight),	value (max val at index)		来源于
	 * 0			0	来源于init
	 * 1			0	来源于init
	 * 2			3	来源于j=2,dp[j] = dp[j-weight['item0']] + values['item0']
	 * 3			3	来源于j=3,这时候意思是从index=1的情况下加了一个item0.
	 * 					(注意这里index=1代表了weight=1，但是根本不存在weight=1的item——所以value是0.
	 * 						所以这里假设存在 item_null = {'value':0, 'weight':1}这种的存在，这样最后就不用返回max(dp)了。
	 * 4			6	从{'weight':2, 'value':3}的state, 加了一个{'weight':2, 'value':3}的item过来达成。
	 * 					当然也有第二波：
	 * 5			6
	 * 
	 * [0, 0, 3, 3, 6, 6]
	 * 
	 * </pre>
	 * 
	 * weight.length == values.length
	 * 
	 */
	public static int backPackIII(int[] weights, int[] values,
			int backpackSize) {
		// 对于item k, weight[k] 是他的weight (size), values[k]是他的价值（我们想找最大价值）
		int[] dp = new int[backpackSize + 1]; // dp[i] -> max value at size i
		for (int i = 0; i < weights.length; i++)// 每种item
			for (int j = weights[i]; j <= backpackSize; j++) // 先装一个这种item
				dp[j] = Math.max(dp[j], dp[j - weights[i]] + values[i]);
		// 后半部分： dp[j - weight[i]] 是【在size=j - weight[i]的时候的最大价值】
		// + values[i]： 假设从j的时候再放进一个i，怎么样
		// note：第一个iteratation的时候，因为j=weight[i]，所以dp[j - weight[i]]=dp[0]
		return dp[backpackSize]; // 在最后的最大价值，包括装不满的情况。
	}

	/**
	 * <pre>
	 * 例子：如果item.weight=2,
	 * 那么
	 * 	dp[2]=item.val, 
	 * 	dp[3]=dp[1]+item.val，
	 * 		而dp[1]=dp[0]（假设没有weight=1的item)
	 * 	于是就有
	 * 	dp[3]=item.val = dp[2]
	 * 之所以这样是因为dp定义为【在backpackSize=i的时候,dp[i]表示他可以装的最大值】
	 * 		而不是【一定要**正好用够**i的weight】——可以富余。
	 * 
	 * 坏处是要++很多
	 * 好处是可以直接返回dp.last()，而不是返回max(dp).
	 * </pre>
	 */
	public static int backPackIII具体化(int[] weights, int[] values,
			int backpackSize) {
		背包Item[] items = 背包Item.get背包s(weights, values);
		int[] dp = new int[backpackSize + 1];
		for (背包Item item : items)
			for (int curWeight = item.weight; curWeight <= backpackSize; curWeight++)
				// 注意这里用curWeight++而不是curWeight+=item.weight,意思就是包括装不满的情况。详见顶端note
				dp[curWeight] = Math.max(dp[curWeight],
						dp[curWeight - item.weight] + item.value);
		return dp[backpackSize];
	}
}

class 背包Item {
	public int value;
	public int weight;

	public static 背包Item[] get背包s(int[] weight, int[] values) {
		final int n = weight.length;
		背包Item[] items = new 背包Item[n];
		for (int i = 0; i < n; i++) {
			items[i] = new 背包Item();
			items[i].value = values[i];
			items[i].weight = weight[i];
		}
		return items;
	}

}