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
        while(keepLooping) {
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
        boolean keepLooping = true;
        boolean isRemoved = false;
        if (x == null || root == null) {
            throw new NullPointerException();
        }
        Node next = root;
        while(keepLooping){
            if(next.elt.compareTo(x) == 0) {
                    keepLooping = false;
                    isRemoved = true;
                    size--;
            }else{
                isRemoved = false;
            }
        }
        if(isRemoved){
            rearrangeTree(next);
            if(next.left != null && next.right != null) {
                next.left.parent = null;
                next.right.parent = null;
                Node largestLeft = next.left;
                while(largestLeft.right != null){
                    largestLeft = largestLeft.right;
                }
                rearrangeTree(largestLeft);
                root = largestLeft;
                root.right = next.right;
            }else if (next.left != null && next.right == null ){
                next.left.parent = null;
                root = next.left;
            }else if(next.left == null && next.right != null){
                next.right.parent = null;
                root = next.right;
            }else{
                root = null; //if root has no children, just remove it
            }
        }
        return isRemoved;
    }

    @Override
    public boolean contains(Comparable x) {
        if (x == null || root == null) {
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
    /**
     * Problem here is row 135/136 where a nullpointer is thrown. Antagligen för att någon av alla jämförelser krånglar
     * typ nod.getParent().left == nod kommer ju ge nullpointer on noden är en höger nod och föräldern inte har något vänsterbarn.
     * Antingen måste vi behålla om det är ett höger eller vänsterbarn men jag vet inte var det ska läggas till i så fall, vi kan
     * kontrollera alla mot null också med det känns fruktansvärt ineffektivt och i så fall är det nog smartast att komma på ett
     * bättre villkor. Kanske bryta ner i fler steg?
     */
    private void rearrangeTree(Node nod){     //todo FIX
        while(nod != root){
            if(nod.getParent() == root){
                zig(nod);
            } else if((nod.getParent().left == nod && nod.getParent().getParent().left == nod.getParent()) //this row seemingly disrupts the program
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
