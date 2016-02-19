/**
 * Created by fredrik on 2016-02-19.
 */
public class SortedLinkedListSet<E> implements SimpleSet{
    Node head;
    //int size;

    public SortedLinkedListSet() {
        head = new Node(null);
        //size = 0;
    }

    @Override
    public int size() {
        Node tempNode = head;
        int listSize = 0;
        while(tempNode.getNext() != null){
            tempNode = tempNode.getNext();
            listSize++;
        }
        return listSize;
        //return size;
    }

    @Override
    public boolean add(Comparable x) {
        if( x == null ){
            throw new NullPointerException();
        }
        if (head.getNext() == null) {
            head.next = new Node(x);
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
                //size++;
                return true;
            }
            tempNode = tempNode.getNext();
        }
        return false;
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
                //size--;
                return true;
            }
            tempNode = tempNode.getNext();
        }
        return false;
    }

    @Override
    public boolean contains(Comparable x) {
        boolean isInSet = false;
        while(head.next != null && !isInSet) {
            if(head.elt.equals(x)) {
                isInSet = true;
            }
        }
        return isInSet;
    }

    public class Node {
        /** The contents of the node is public */
        public Comparable elt;

        protected Node next;

        Node(Comparable elt) {
            this.elt = elt;
            next = null;
        }

        public Node getNext() {
            return next;
        }
    }
}
