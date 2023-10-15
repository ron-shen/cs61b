package lab9;

import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) return null;
        int cmp = p.key.compareTo(key);
        if      (cmp < 0) return getHelper(key, p.right);
        else if (cmp > 0) return getHelper(key, p.left);
        else              return p.value;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if(p == null){
            size += 1;
            return new Node(key, value);
        }
        int cmp = p.key.compareTo(key);
        if(cmp < 0) {
            p.right = putHelper(key, value, p.right);
        }
        else if(cmp > 0) {
            p.left = putHelper(key, value, p.left);
        }
        else {
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        //if size is unchanged -> can't find the node
        //otherwise -> can find the node
        int originalSize = size;
        Node res = removeHelper(key, value, root);
        return originalSize == size ? null : value;
    }

    private Node removeHelper(K key, V value, Node p){
        //node doesn't exist
        if(p == null){
            return null;
        }
        int cmp = p.key.compareTo(key);
        if(cmp < 0) p.right = removeHelper(key, value, p.right);
        else if(cmp > 0) p.left = removeHelper(key, value, p.left);
        else {
            size -= 1;
            //one child or no child
            if(p.left == null) return p.right;
            if(p.right == null) return p.left;
            //2 children
            Node replaceNode = max(p.left);
            //deleteMax: connect left subtree of replaceNode to its previous parent node
            //assignment: connect left ptr of replaceNode to p's left node
            replaceNode.left = deleteMax(p.left);
/*            replaceNode.left = p.left;*/
            replaceNode.right = p.right;
            return replaceNode;
        }
        return p;
    }

    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        return x;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    //max node in subtree with root node p
    private Node max(Node p){
        if(p == null) return null;
        if(p.right == null) return p;
        return max(p.right);
    }

    public static void main(String[] args) {
        BSTMap<Integer, Integer> b = new BSTMap<>();
        b.put(20, 2);
        b.put(25, 2);
        b.put(15, 2);
        b.put(10, 2);
        b.put(18, 2);
        b.put(8, 2);
        b.put(12, 2);
        b.put(11, 2);
/*        b.put(3, 3);*/
/*        b.remove(7, 1);
        b.remove(10, 2);*/
        b.remove(15,2);
    }
}
