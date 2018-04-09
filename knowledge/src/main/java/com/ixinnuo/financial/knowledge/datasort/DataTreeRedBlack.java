package com.ixinnuo.financial.knowledge.datasort;

import java.util.Map;

/**
 * 红黑树，一种平衡的二叉查找树，但在每个结点上增加一个存储位表示结点的颜色，可以是Red或Black。<br>
 * 红黑树的性质如下，保证了查找、插入、删除的时间复杂度最坏为O(log n)。
 * 1.每个结点要么是红的要么是黑的。<br>
 * 2.根结点是黑的。<br>
 * 3.每个NIL节点（叶结点的延伸节点，是不存在的）都是黑的<br>
 * 4.红色节点不能连续。（也即是，红色节点的孩子和父亲都不能是红色）<br> 	
 * 5.对于任意结点而言，其到NIL结点的每条路径都包含相同数目的黑结点。<br>
 * <p>
 * java.uitl.TreeMap 就是红黑树
 * @author 2476056494@qq.com
 *
 */
public class DataTreeRedBlack<K,V> {
	
    private static final boolean RED   = false;
    private static final boolean BLACK = true;
    private transient Entry<K,V> root;
    
    static final boolean valEquals(Object o1, Object o2) {
        return (o1==null ? o2==null : o1.equals(o2));
    }

	 /**
     * Node in the Tree.  Doubles as a means to pass key-value pairs back to
     * user (see Map.Entry).
     */

    static final class Entry<K,V> implements Map.Entry<K,V> {
        K key;
        V value;
        Entry<K,V> left;
        Entry<K,V> right;
        Entry<K,V> parent;
        boolean color = BLACK;

        /**
         * Make a new cell with given key, value, and parent, and with
         * {@code null} child links, and BLACK color.
         */
        Entry(K key, V value, Entry<K,V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        /**
         * Returns the key.
         *
         * @return the key
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value associated with the key.
         *
         * @return the value associated with the key
         */
        public V getValue() {
            return value;
        }

        /**
         * Replaces the value currently associated with the key with the given
         * value.
         *
         * @return the value associated with the key before this method was
         *         called
         */
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;

            return valEquals(key,e.getKey()) && valEquals(value,e.getValue());
        }

        public int hashCode() {
            int keyHash = (key==null ? 0 : key.hashCode());
            int valueHash = (value==null ? 0 : value.hashCode());
            return keyHash ^ valueHash;
        }

        public String toString() {
            return key + "=" + value;
        }
    }
	
	
	/**
	 * 左旋
	 * 左旋的过程是将x的右子树绕x逆时针旋转，使得x的右子树成为x的父亲，同时修改相关节点的引用。旋转之后，二叉查找树的属性仍然满足。
	 * x节点的右节点的左节点 转到x节点的右节点
	 * @param p
	 */
	private void rotateLeft(Entry<K,V> p) {
	    if (p != null) {
	        Entry<K,V> r = p.right;
	        //p节点的右节点的左节点 转到p节点的右节点
	        p.right = r.left;
	        if (r.left != null)
	            r.left.parent = p;
	        //p节点的右节点r，替代p节点，r的父节点不再指向p，而是执行p的父节点；同时子节点指向p的父节点g，g的子节点改成指向r
	        r.parent = p.parent;
	        if (p.parent == null)
	            root = r;
	        else if (p.parent.left == p)
	            p.parent.left = r;
	        else
	            p.parent.right = r;
	        //p和r的父子身份互换
	        r.left = p;
	        p.parent = r;
	    }
	}
}
