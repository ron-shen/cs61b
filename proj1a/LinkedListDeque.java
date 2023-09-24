
class Node<T>{
    public T val;
    public Node<T> next;
    public Node<T> previous;
    public Node(T val, Node<T> next, Node<T> previous) {
        this.val = val;
        this.next = next;
        this.previous = previous;
    }
}

//implement doubly linked list using first and last pointer only.
//no Sentinel node
class LinkedList<T>{
    private Node first;
    private Node last;
    public LinkedList(){
        this.first = null;
        this.last = null;
    }

    public void addFirst(T val){
        Node newNode = new Node(val, null, null);
        //it needs to create an edge case when Linked List is empty
        if(first == null && last == null){
            first = newNode;
            last = newNode;
            return;
        }
        newNode.next = first;
        first.previous = newNode;
        first = newNode;
    }

    public void addLast(T val){
        Node newNode = new Node(val, null, null);
        //it needs to create an edge case when Linked List is empty
        if(first == null && last == null){
            first = newNode;
            last = newNode;
            return;
        }
        newNode.previous = last;
        last.next = newNode;
        last = newNode;
    }

    public void removeFirst(){
        //many edge cases
        if(first == null && last == null){
            return;
        }
        //one node only
        if(first == last){
            first = null;
            last = null;
        }
        //more than one node
        else{
            first = first.next;
            first.previous = null;
        }
    }

    public void removeLast(){
        //many edge cases
        if(first == null && last == null){
            return;
        }
        //one node only
        if(first == last){
            first = null;
            last = null;
        }
        else{
            last = last.previous;
            last.next = null;
        }

    }

    public Node getFirst(){
        return first;
    }

    public Node getLast(){
        return last;
    }
}

public class LinkedListDeque<T> {
    private Node<T> header;
    private Node<T> trailer;
    private int size;
    public LinkedListDeque(){
        this.header = new Node<T>(null, null, null);
        this.trailer = new Node<T>(null, null, header);
        header.next = this.trailer;
        this.size = 0;
    }
    public void addFirst(T item){
        Node newNode = new Node<T>(item, null, null);
        newNode.previous = header;
        newNode.next = header.next;
        header.next.previous = newNode;
        header.next = newNode;
        size += 1;
    }
    public void addLast(T item){
        Node<T> newNode = new Node<T>(item, null, null);
        newNode.next = trailer;
        newNode.previous = trailer.previous;
        trailer.previous.next = newNode;
        trailer.previous = newNode;
        size += 1;
    }
    public boolean isEmpty(){
        return size == 0 ? true : false;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        Node<T> temp = header.next;
        while(temp != trailer){
            System.out.print(temp.val);
            System.out.print(' ');
            temp = temp.next;
        }
    }
    public T removeFirst(){
        if(size == 0){
            return null;
        }
        Node<T> temp = header.next;
        header.next.next.previous = header;
        header.next = header.next.next;
        temp.previous = null;
        temp.next = null;
        size -= 1;
        return temp.val;
    }
    public T removeLast(){
        if(size == 0){
            return null;
        }
        Node<T> temp = trailer.previous;
        trailer.previous.previous.next = trailer;
        trailer.previous = trailer.previous.previous;
        temp.next = null;
        temp.previous = null;
        size -= 1;
        return temp.val;
    }
    public T get(int index){
        if(size == 0 || index >= size || index < 0){
            return null;
        }
        int count = 0;
        Node<T> temp = header.next;
        while(count != index){
            count++;
            temp = temp.next;
        }
        return temp.val;
    }

    private T getRecursiveHelper(Node<T> temp, int index){
        if(index == 0){
            return temp.val;
        }
        return getRecursiveHelper(temp.next, index - 1);
    }
    public T getRecursive(int index){
        if(size == 0 || index >= size || index < 0){
            return null;
        }
        return getRecursiveHelper(header.next, index);
    }
}

