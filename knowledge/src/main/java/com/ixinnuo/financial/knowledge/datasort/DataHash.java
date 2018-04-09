package com.ixinnuo.financial.knowledge.datasort;
/**
 * 散列
 * @author 2476056494@qq.com
 *
 */
public class DataHash {

	/**
	 * 分离链接散列
	 * 散列到同一个值的所有元素保留到一个表中
	 * 缺点，使用一些链表，分配地址需要时间
	 */
	public void separateChaining(){
		
	}
	
	/**
	 * 线性探测散列，不需要链表，直接按照f(i)=i，把数据放入一个大的散列表内，
	 * 遇到冲突的向下尝试，直到找出空的单元为止
	 * 缺点：数据容易聚集，比如好多数字带9
	 * 
	 */
	public void fx(){
		
	}
}
