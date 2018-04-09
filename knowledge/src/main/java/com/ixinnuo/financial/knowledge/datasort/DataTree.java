package com.ixinnuo.financial.knowledge.datasort;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.math.RandomUtils;

/**
 * 树形结构，N个节点的集合，【一颗倒立生长的树】几个概念定义如下：
 * <p>
 * 根，没有父节点的节点<br>
 * 边，父子节点之间的连线，N-1条边 <br>
 * 树叶，没有子节点的节点<br>
 * 路径，从节点n1到其子节点nk两个节点之间的节点序列，路径是唯一的，边数就是长度<br>
 * 深度，节点到跟的路径长度，所以根的深度为0，往根的方向找<br>
 * 高度，节点到最远树叶节点的路径长度，所有树叶节点的高度都为0，往根的反方向找<br>
 * <p>
 * 二叉查找数，适用范围查找，查最小-最大<br>
 * 树中的每个节点，左子树所有项比它小，右子树所有项比它大，比较接口comparable<br>
 * 查找的时间复杂度O(logN)<br>
 * 二叉查找数的性质<br>
 * 1.左子树都比节点小<br>
 * 2.右子树都比节点大<br>
 * 3.左右子树分别为二叉树<br>
 * 4.没有相等的节点<br>
 * <p>
 * 
 * @author 2476056494@qq.com
 * @param <T>
 *
 */
public class DataTree<T extends Comparable<T>> {

	/**
	 * 表示一个二叉查找数的节点，应该是key，value形式，key是排序的，value存储，这里直接用value
	 * 
	 * @author 2476056494@qq.com
	 *
	 * @param <T>
	 */
	private static class BinaryNode<T> {
		T element;
		AtomicInteger count = new AtomicInteger();// 记录相同元素出现的次数
		BinaryNode<T> left;
		BinaryNode<T> right;

		BinaryNode(T element) {
			this(element, null, null);
		}

		BinaryNode(T element, BinaryNode<T> left, BinaryNode<T> right) {
			this.element = element;
			this.left = left;
			this.right = right;
			count.incrementAndGet();// 实例化对象，记为1次
		}

	}

	/**
	 * 定义root节点
	 */
	private BinaryNode<T> root;

	public DataTree(BinaryNode<T> root) {
		this.root = root;
	}

	/**
	 * 查找二叉树是否包含特定元素
	 * 
	 * @param element
	 *            特定元素
	 * @param root
	 *            查找二叉树的root节点
	 * @return true包含，false不包含
	 */
	public boolean contains(T element, BinaryNode<T> root) {
		// 节点不存在则不包含
		if (null == root) {
			return false;
		}
		int compareTo = element.compareTo(root.element);
		// 比根节点小，继续查找左子树
		if (compareTo < 0) {
			return contains(element, root.left);
			// 比根节点大，继续查找右子树
		} else if (compareTo > 0) {
			return contains(element, root.right);
		} else {
			return true;
		}
	}

	/**
	 * 二叉查找数中的最小元素，只要左节点存在，就向左进行，终点就是最小元素，递归实现
	 * 
	 * @param root
	 * @return
	 */
	public BinaryNode<T> findMin(BinaryNode<T> root) {
		if (root == null) {
			return null;
		} else if (root.left == null) {
			return root;
		} else {
			return findMin(root.left);
		}
	}

	/**
	 * 二叉查找数中的最大元素，只要右节点存在，就向右进行，终点就是最大元素，循环实现
	 * 
	 * @param root
	 * @return
	 */
	public BinaryNode<T> findMax(BinaryNode<T> root) {
		if (root == null) {
			return null;
		}
		// 循环查找，变量替换
		while (root.right != null) {
			root = root.right;
		}
		return root;

	}

	public BinaryNode<T> insert(T element, BinaryNode<T> root) {
		// 空节点，则创建，并返回这个节点
		if (root == null) {
			// 插入新值
			return new BinaryNode<T>(element);
		}
		int compareTo = element.compareTo(root.element);
		if (compareTo < 0) {
			// 比节点小，继续往左侧插入，并且左节点指向这个新插入的节点
			root.left = insert(element, root.left);
		} else if (compareTo > 0) {
			// 反之往右侧插入
			root.right = insert(element, root.right);
		} else {
			// 重复节点,什么也不做，也可以加+1
			// root.count.incrementAndGet();
		}
		// 插入已存在的值
		return root;
	}

	/**
	 * 插入，非递归实现
	 * @param element
	 * @param root
	 * @return
	 */
	public BinaryNode<T> insert2(T element, BinaryNode<T> root) {
		Stack<BinaryNode<T>> stack = new Stack<DataTree.BinaryNode<T>>();
		BinaryNode<T> binaryNode = new BinaryNode<T>(element);
		if(root == null){
			return binaryNode;
		}
		stack.push(root);
		while (stack.peek() != null) {
			int compareTo = element.compareTo(stack.peek().element);
			if (compareTo < 0) {
				// 比节点小，继续往左侧插入
				stack.push(stack.peek().left);
			} else if (compareTo > 0) {
				// 反之往右侧插入
				stack.push(stack.peek().right);
			} else {
				return binaryNode;//重复节点
			}
		}
		// 插入的节点一定是叶子,找到它的父节点即可
		stack.pop();//取出第一个null
		BinaryNode<T> parent = stack.pop();//取出第二个节点，作为新插入节点的父节点
		int compareTo = element.compareTo(parent.element);
		if(compareTo > 0){
			parent.right = binaryNode;
		}else{
			parent.left = binaryNode;
		}
		return binaryNode;

	}

	/**
	 * 删除,并返回此节点<br>
	 * 只有一个子节点的情况下，直接顶上<br>
	 * 两个子节点都存在的情况下，从左子树选一个最大的(不可能比父节点大)，<br>
	 * 或右子树选一个最小的（不可能比父节点小），一定是叶子节点<br>
	 * 
	 * @param element
	 * @param root
	 * @return
	 */
	public BinaryNode<T> remove(T element, BinaryNode<T> root) {
		if (root == null) {
			return null;
		}
		int compareTo = element.compareTo(root.element);
		if (compareTo < 0) {
			root.left = remove(element, root.left);
		} else if (compareTo > 0) {
			root.right = remove(element, root.right);
		} else if (root.right != null && root.left != null) {
			// 移除元素，这里就是把节点的元素指向右侧最小的元素
			root.element = findMin(root.right).element;
			// 递归的删除右侧最小的元素
			root.right = remove(element, root.right);
		} else {
			// 一个子节点，或者叶子节点
			root = (root.left != null) ? root.left : root.right;
		}
		return root;
	}

	/**
	 * 先序遍历 <br>
	 * 节点 - 左孩子 - 右孩子 <br>
	 * 1)访问结点P，并将结点P入栈; <br>
	 * 2)判断结点P的左孩子是否为空，若为空，则取栈顶结点的右孩子置为当前的结点P，循环至1);若不为空， 则将P的左孩子置为当前的结点P; <br>
	 * 3)直到P为NULL并且栈为空，则遍历结束。
	 * 
	 * @param t
	 */
	public void preOrderPrint(BinaryNode<T> t) {
		Stack<BinaryNode<T>> s = new Stack<BinaryNode<T>>();
		while (t != null || !s.empty()) { // 只要满足节点非空或值栈为非空，就要不断遍历

			while (t != null) { // 压入所有左孩子节点，压入前输出
				// 【1】打印出中
				System.out.print(t.element + "\t");
				// 【2】压入中，先进后出
				s.push(t);
				// 【3】往左遍历，继续压入桟
				t = t.left;
			}
			if (!s.empty()) {
				// 左孩子都搞定了，从栈顶取出一个节点，指向它的右孩子，继续前面while循环
				t = s.pop();
				t = t.right;
			}
		}
	}

	/**
	 * 中序遍历，从小到大<br>
	 * 左孩子 - 根结点 - 右孩子<br>
	 * 访问任意一节点p，若其左孩子非空，p入栈，且p的左孩子作为当前节点，然后在对其进行同样的处理<br>
	 * 若其左孩子为空，则输出栈顶点元素并进行出栈操作，访问该栈顶的节点的右孩子 直到p为null并且栈为空则遍历结束
	 * 
	 * @param t
	 */
	public void inOrderPrint(BinaryNode<T> t) {
		Stack<BinaryNode<T>> s = new Stack<BinaryNode<T>>();
		while (t != null || !s.empty()) {
			while (t != null) { // 和前序的非递归有点像
				s.push(t);
				t = t.left; // 一路指向左孩子
			}
			if (!s.empty()) {
				t = s.pop(); // 取栈顶元素并输出，并指向右孩子
				// 打印元素
				System.out.print(t.element + "\t");
				t = t.right;
			}
		}
	}

	public static void main(String[] args) {
		// 初始化根节点
		BinaryNode<Integer> root = new BinaryNode<Integer>(100);
		// 初始化二叉查找数
		DataTree<Integer> tree = new DataTree<Integer>(root);
		int[] all = { 50, 150, 25, 175, 10, 190, 0, 200 };
		for (int i = 0; i < all.length; i++) {
			tree.insert2(all[i], tree.root);
		}
		tree.preOrderPrint(root);
		System.out.println("\n=================");
		tree.inOrderPrint(root);

	}

}
