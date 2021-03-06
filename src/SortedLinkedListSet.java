/**
 * Created by fredrik on 2016-02-19.
 */
public class SortedLinkedListSet<E> implements SimpleSet{
    Node head;
    int size;

    public SortedLinkedListSet() {
        head = new Node(null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(Comparable x) {
        if( x == null ){
            throw new NullPointerException();
        }
        if (head.getNext() == null) {
            head.next = new Node(x);
            size++;
            return true;
        }
        Node tempNode = head;
        while(tempNode.getNext() != null){
            if(tempNode.getNext().elt.compareTo(x) == 0){
                return false;
            }
            if(tempNode.getNext().elt.compareTo(x) > 0){
                Node newNode = new Node(x);
                newNode.next = tempNode.getNext();
                tempNode.next = newNode;
                size++;
                return true;
            }
            tempNode = tempNode.getNext();
        }
        Node newNode = new Node(x);
        tempNode.next = newNode;
        size++;
        return true;
    }

    @Override
    public boolean remove(Comparable x) {
        if( x == null ){
            throw new NullPointerException();
        }
        Node tempNode = head;
        while(tempNode.getNext() != null){
            if((tempNode.getNext().elt).equals(x)){
                tempNode.next = tempNode.getNext().next;
                size--;
                return true;
            }
            tempNode = tempNode.getNext();
        }
        return false;
    }

    @Override
    public boolean contains(Comparable x) {
        boolean isInSet = false;
        Node nextNode = head;
        while(nextNode.next != null && !isInSet) {
            if(nextNode.next.elt.equals(x)) {
                isInSet = true;
            }
            nextNode = nextNode.getNext();
        }
        return isInSet;
    }

    public class Node {
        /** The contents of the node is public */
        public Comparable elt;

        protected Node next;

        Node(Comparable elt) {
            this.elt = elt;
        }

        public Node getNext() {
            return next;
        }
    }
}
