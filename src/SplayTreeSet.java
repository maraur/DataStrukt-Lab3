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
        if (x == null) {
            throw new NullPointerException();
        }
        Node next = root;
        boolean keepLooping = true;
        boolean isInTree = false;
        while (keepLooping) {
            if (next.elt.compareTo(x) > 0) {
                if (next.getLeft() == null) {
                    keepLooping = false;
                } else {
                    next = next.getLeft();
                }
            } else if (next.elt.compareTo(x) < 0) {
                if (next.getRight() == null) {
                    keepLooping = false;
                } else {
                    next = next.getRight();
                }
            } else {
                isInTree = true;
            }

        }
        rearrangeTree(next);
        return isInTree;
    }
    private void rearrangeTree(Node nod){
        while(nod != root){
            if(nod.getParent() == root){
                zig(nod);
            } else if((nod.getParent().left == nod && nod.getParent().getParent().left == nod.getParent())
                    || (nod.getParent().right == nod && nod.getParent().getParent().right == nod.getParent())){
                zigZig(nod);
            }else{
                zigZag(nod);
            }
        }
    }
    private void zig(Node nod){ //seems capable of determining rotation?
        if(nod.getParent() == root){ //might not be the best place to handle?
            nod = root;
        }
        Node parent = nod.getParent();
        nod.parent = parent.parent;
        parent.parent = nod;
        if (nod == parent.getLeft()){
            parent.left = nod.right;
            nod.right = parent;
        } else {
            parent.right = nod.left;
            nod.left = parent;
        }
    }
    private void zigZig(Node nod){
        zig(nod.getParent());
        zig(nod);
    }
    private void zigZag(Node nod){
        zig(nod);
        zig(nod.getParent());
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
