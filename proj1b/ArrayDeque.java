package proj1b;
public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int head;
    private int tail;
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        head = 0;
        tail = 0;
    }
    private void resize(int capacity) {
        int idx = 0;
        T[] newItems = (T[]) new Object[capacity];
        for(int i = 0; i < size; i++){
            idx = (head + i) % items.length;
            newItems[i] = items[idx];
        }
        items = newItems;
        head = 0;
        tail = size;
    }
    @Override
    public void addFirst(T item) {
        if(size == items.length) {
            resize(size * 2);
        }
        head = head - 1 < 0 ? items.length - 1 : head - 1;
        items[head] = item;
        size += 1;
    }
    @Override
    public void addLast(T item) {
        if(size == items.length) {
            resize(size * 2);
        }
        items[tail] = item;
        tail = (tail + 1) % items.length;
        size += 1;
    }
    @Override
    public boolean isEmpty() {
        return size == 0 ? true : false;
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public void printDeque() {
        int idx = 0;
        for(int i = 0; i < size; i++) {
            idx = (head + i) % items.length;
            System.out.print(items[idx]);
            System.out.print(' ');
        }
    }
    @Override
    public T removeFirst() {
        if(size == 0) {
            return null;
        }
        T item = items[head];
        items[head] = null;
        head = head + 1 >= items.length ? 0 : head + 1;
        size -= 1;
        if(items.length >= 16 && (double) size / items.length < 0.25){
            resize(items.length / 2);
        }
        return item;
    }
    @Override
    public T removeLast() {
        if(size == 0){
            return null;
        }
        tail = tail - 1 < 0 ? items.length - 1 : tail - 1;
        T item = items[tail];
        items[tail] = null;
        size -= 1;
        if(items.length >= 16 && (double) size / items.length < 0.25){
            resize(items.length / 2);
        }
        return item;
    }
    @Override
    public T get(int index){
        return items[(head + index) % items.length];
    }
}
