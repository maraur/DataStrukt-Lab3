/**
 * Created by fredrik on 2016-02-19.
 */
public class SplayTreeSet<T> implements SimpleSet {
    int size;
    Node root;
    public SplayTreeSet() {
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(Comparable x) {
        if (x == null) {
            throw new NullPointerException();
        }
        if (root == null) {
            root = new Node(x);
        }
        Node next = root;
        boolean keepLooping = true;
        while(keepLooping) { //Bad but can't find anything smarter
            if(next.elt.compareTo(x) > 0){
                if(next.getLeft() == null){
                    next.left = new Node(x);
                    size++;
                    keepLooping = false;
                    next = next.left;
                }else{
                    next = next.getLeft();
                }
            }else if (next.elt.compareTo(x) < 0){
                if(next.getRight() == null){
                    next.right = new Node(x);
                    size++;
                    keepLooping = false;
                    next = next.right;
                }else{
                    next = next.getRight();
                }
            }else {
                return false;
            }

        }
        rearrangeTree(next);
        return true;
    }

    @Override
    public boolean remove(Comparable x) {
        //todo implement
        size--;
        return false;
    }

    @Override
    public boolean contains(Comparable x) {

        //todo implement
        return false;
    }
    private void rearrangeTree(Node nod){
        /**
         * Loops and decides parents so it calls the appropriate zig, zigzig or zigzag
         */
    }
    private void zig(Node nod){
        /**
         * Exchange for rotateLeft/rotateRight?
         */
    }
    private void zigZig(Node nod){
        /**
         * should be able to know if left-left or right-right
         * call Zig twice
         */
    }
    private void zigZag(Node nod){
        /**
         * should be able to know if left-right or right-left
         * call Zig twice
         */
    }

    public class Node {
        /** The contents of the node is public */
        public Comparable elt;

        protected Node left, right, parent;

        Node(Comparable elt) {
            this.elt = elt;
        }
        public Node getParent(){
            return parent;
        }
        public Node getLeft() {
            return left;
        }
        public Node getRight(){
            return right;
        }
    }
}
