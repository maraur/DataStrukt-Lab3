/**
 * Created by fredrik on 2016-02-19.
 */
public class SplayTreeSet<T> implements SimpleSet {
    int size;
    Node root;
    public SplayTreeSet() {
        size = 0;
    }

    /***
     *  Returns size of tree
     * @return Size of tree
     */
    @Override
    public int size() {
        return size;
    }

    /***
     * Adds an element to the splay set and rotates the tree so that it is root.
     * @param x
     * @return boolean saying whether add was successful or not
     */
    @Override
    public boolean add(Comparable x) {
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
        while(keepLooping) {
            int c = next.elt.compareTo(x);
            if(c > 0){
                if(next.getLeft() == null){
                    Node newNode = new Node(x);
                    next.setLeft(newNode);
                    size++;
                    keepLooping = false;
                    splay(newNode);
                }else{
                    next = next.getLeft();
                }
            }else if (c < 0){
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
                splay(next);
                return false;
            }
        }
        return true;
    }

    /***
     *  Removes an element from the splay set. Set is splayed after being called.
     * @param x
     * @return boolean saying whether remove was successful or not
     */
    @Override
    public boolean remove(Comparable x) {
        boolean keepLooping = true;
        boolean doRemove = false;
        if (x == null) {
            throw new NullPointerException();
        }
        if(root == null){
            return false;
        }
        Node next = root;
        while(keepLooping){
            int c = next.elt.compareTo(x);
            if(c > 0) {
                if(next.getLeft() == null) {
                    keepLooping = false;
                }else{
                    next = next.getLeft();
                }
            }else if(c < 0) {
                if(next.getRight() == null) {
                    keepLooping = false;
                }else{
                    next = next.getRight();
                }
            }else{
                keepLooping = false;
                doRemove = true;
                size--;
            }
        }
        splay(next);
        if(doRemove){
            if(next.left != null && next.right != null) {
                next.left.parent = null;
                next.right.parent = null;
                Node largestLeft = next.left;
                while(largestLeft.right != null){
                    largestLeft = largestLeft.right;
                }
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

    /***
     * Checks the set for the provided element
     * @param x
     * @return boolean saying if the element is within the set or not
     */
    @Override
    public boolean contains(Comparable x) {
        if (x == null) {
            throw new NullPointerException();
        }
        if(root == null){
            return false;
        }
        Node next = root;
        boolean keepLooping = true;
        boolean isInTree = false;
        while (keepLooping) {
            int c = next.elt.compareTo(x);
            if (c > 0) {
                if (next.getLeft() == null) {
                    keepLooping = false;
                } else {
                    next = next.getLeft();
                }
            } else if (c < 0) {
                if (next.getRight() == null) {
                    keepLooping = false;
                } else {
                    next = next.getRight();
                }
            } else {
                isInTree = true;
                keepLooping = false;
            }
        }
        splay(next);
        return isInTree;
    }

    /***
     * Rotates the set until the provided node is the root.
     * @param nod
     */
    public void splay(Node nod){
        if(nod.getParent() == null){
            root = nod;
        }
        while(nod != root) {
            Node parent = nod.getParent();
            if( parent == root || parent.getParent() == null){ //do Zig step
                root = nod; // want splay to end after this
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
                        nod.setParent(grandParent.getParent());
                        grandParent.setRight(parent.getLeft());
                        parent.setLeft(grandParent);
                        parent.setRight(nod.getLeft());
                        nod.setLeft(parent);
                    } else {
                        nod.setParent(grandParent.getParent());
                        grandParent.setLeft(parent.getRight());
                        parent.setRight(grandParent);
                        parent.setLeft(nod.getRight());
                        nod.setRight(parent);
                    }
                }else{ //do zigzag
                    if(nod.isRight()){
                        nod.setParent(grandParent.getParent());
                        grandParent.setLeft(nod.getRight());
                        parent.setRight(nod.getLeft());
                        nod.setLeft(parent);
                        nod.setRight(grandParent);
                    }else{
                        nod.setParent(grandParent.getParent());
                        grandParent.setRight(nod.getLeft());
                        parent.setLeft(nod.getRight());
                        nod.setRight(parent);
                        nod.setLeft(grandParent);
                    }
                }
                if(grandParent == root|| nod.getParent() == null){
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
            return (this.elt.compareTo(parent.elt) > 0 );
        }
        /***
         * Method for setting the parent of the node
         * @param parent
         */
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

        /***
         * Method for setting the right child of the node
         * @param nod
         */
        public void setRight(Node nod){
            if(nod != null){
                nod.parent = this;
                nod.isRight = true;
                this.right = nod;
            }else{
                this.right = null;
            }
        }

        /***
         * Method for setting the left child of the node
         * @param nod
         */
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
