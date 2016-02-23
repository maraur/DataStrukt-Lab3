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
        System.out.println("add " + x); //todo remove
        if (x == null) {
            throw new NullPointerException();
        }
        if (root == null) {
            root = new Node(x);
            size++;
            return true;
        }
        Node next = root;
        boolean keepLooping = true;
        System.out.println("1 entered loop");//todo remove
        while(keepLooping) {
            int c = next.elt.compareTo(x);
            System.out.println(c);
            if(c > 0){
                System.out.println("1 smaller than next"); //todo remove
                if(next.getLeft() == null){
                    Node newNode = new Node(x);
                    next.setLeft(newNode);
                    System.out.println(newNode.isRight());
                    size++;
                    keepLooping = false;
                    splay(newNode);
                }else{
                    next = next.getLeft();
                }
            }else if (c < 0){
                System.out.println("1 larger than next"); //todo remove
                if(next.getRight() == null){
                    Node newNode = new Node(x);
                    next.setRight(newNode);
                    size++;
                    keepLooping = false;
                    splay(newNode);
                }else{
                    next = next.getRight();
                }
            }else {
                System.out.println("1 equals next"); //todo remove
                return false;
            }
        }
        System.out.println("1 leaves loop");//todo remove
        return true;
    }

    @Override
    public boolean remove(Comparable x) {
        System.out.println("remove " + x); //todo remove
        boolean keepLooping = true;
        boolean doRemove = false;
        if (x == null) {
            throw new NullPointerException();
        }
        if(root == null){
            return false;
        }
        Node next = root;
        System.out.println("2 entered loop");//todo remove
        while(keepLooping){
            int c = next.elt.compareTo(x);
            if(c > 0) {
                System.out.println("2 smaller than next"); //todo remove
                if(next.getLeft() == null) {
                    keepLooping = false;
                }else{
                    next = next.getLeft();
                }
            }else if(c < 0) {
                System.out.println("2 larger than next"); //todo remove
                if(next.getRight() == null) {
                    keepLooping = false;
                }else{
                    System.out.println(next + "  " + next.getRight()); //todo remove
                    next = next.getRight();
                }
            }else{
                System.out.println("2 equals next"); //todo remove
                keepLooping = false;
                doRemove = true;
                size--;
            }
        }
        System.out.println("2 exited loop");//todo remove
        if(doRemove){
            System.out.println("removing");
            splay(next);
            if(next.left != null && next.right != null) {
                next.left.parent = null;
                next.right.parent = null;
                Node largestLeft = next.left;
                while(largestLeft.right != null){
                    largestLeft = largestLeft.right;
                }
                System.out.println("splaying largest");
                root = largestLeft;
                splay(largestLeft);
                root.setRight(next.getRight());
            }else if (next.left != null && next.right == null ){
                next.left.parent = null;
                root = next.left;
            }else if(next.left == null && next.right != null){
                next.right.parent = null;
                root = next.right;
            }else{
                root = null; //if root it has no children, just remove it
            }
        }
        return doRemove;
    }

    @Override
    public boolean contains(Comparable x) {
        System.out.println("contains " + x); //todo remove
        if (x == null) {
            throw new NullPointerException();
        }
        if(root == null){
            return false;
        }
        Node next = root;
        boolean keepLooping = true;
        boolean isInTree = false;
        System.out.println("3 entered loop");//todo remove
        while (keepLooping) {
            int c = next.elt.compareTo(x);
            if (c > 0) {
                System.out.println("3 smaller than next"); //todo remove
                if (next.getLeft() == null) {
                    keepLooping = false;
                } else {
                    next = next.getLeft();
                }
            } else if (c < 0) {
                System.out.println("3 larger than next"); //todo remove
                System.out.println(next + "  " + next.getRight()); //todo remove
                if (next.getRight() == null) {
                    keepLooping = false;
                } else {
                    next = next.getRight();
                }
            } else {
                System.out.println("3 equals next"); //todo remove
                isInTree = true;
                keepLooping = false;
            }
        }
        System.out.println("3 exited loop");//todo remove
        splay(next);
        return isInTree;
    }
    
    public void splay(Node nod){
        System.out.println("splaying"); //todo remove
        System.out.println(nod.elt + " " + nod.getParent()); //todo remove
        if(nod.getParent() == null){
            root = nod;
        }
        while(nod != root) {
            System.out.println("blwa");
            Node parent = nod.getParent();
            if( parent == root || parent.getParent() == null){ //do Zig step
                System.out.println("zig");
                root = nod; // want splay to end after this
                System.out.println(nod.isRight + " " + nod.elt + " " + nod.getParent().elt);
                if(nod.isRight()){
                    parent.setRight(nod.getLeft());
                    nod.setLeft(parent);
                }else{
                    parent.setLeft(nod.getRight());
                    nod.setRight(parent);
                }
                nod.setParent(null);
            }else {
                Node grandParent = parent.getParent();
                if(parent.isRight() == nod.isRight()){ //do zigzig

                    if(nod.isRight()){ //they are both right children
                        grandParent.setRight(parent.getLeft());
                        parent.setLeft(grandParent);
                        parent.setRight(nod.getLeft());
                        nod.setLeft(parent);
                    } else {
                        grandParent.setLeft(parent.getRight());
                        parent.setRight(grandParent);
                        parent.setLeft(nod.getRight());
                        nod.setRight(parent);
                    }
                    nod.setParent(grandParent.getParent());
                }else{ //do zigzag

                    if(nod.isRight()){
                        grandParent.setLeft(nod.getRight());
                        parent.setRight(nod.getLeft());
                        nod.setLeft(parent);
                        nod.setRight(grandParent);
                    }else{
                        grandParent.setRight(nod.getLeft());
                        parent.setLeft(nod.getRight());
                        nod.setRight(parent);
                        nod.setLeft(grandParent);
                    }
                    nod.setParent(grandParent.getParent());
                }
                if(grandParent == root){
                    root = nod;
                    nod.setParent(null);
                }
            }
        }
    }

    public class Node {
        /** The contents of the node is public */
        public Comparable elt;

        protected Node left, right, parent;
        protected boolean isRight;

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
        public boolean isRight(){
            System.out.println(this.elt + " " + parent.elt);
            return (this.elt.compareTo(parent.elt) > 0 );
        }
        //public boolean isRight(){ return isRight; }
        public void setParent(Node parent){
            if(parent != null){
                int c = this.elt.compareTo(parent.elt);
                if(c > 0){
                    parent.setRight(this);
                }else{
                    parent.setLeft(this);
                }
            }else{
                this.parent = null;
            }
        }
        public void setRight(Node nod){
            if(nod != null){
                nod.parent = this;
                nod.isRight = true;
                this.right = nod;
            }else{
                this.right = null;
            }
        }
        public void setLeft(Node nod){
            if(nod != null){
                nod.parent = this;
                nod.isRight = false;
                this.left = nod;
            }else{
                this.left = null;
            }
        }
    }
}
