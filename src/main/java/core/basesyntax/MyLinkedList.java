package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }

        size++;
    }

    @Override
    public void add(T value, int index) {
        validateIndexForInsert(index);

        if (index == size) {
            add(value);
            return;
        }

        Node newNode = new Node(value);

        if (index == 0) {
            newNode.setPrev(null);
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        } else {
            Node currentNode = getCurrentNode(index);

            currentNode.getPrev().setNext(newNode);
            newNode.setPrev(currentNode.getPrev());
            currentNode.setPrev(newNode);
            newNode.setNext(currentNode);
        }

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        validateIndexForAccess(index);

        return getCurrentNode(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        validateIndexForAccess(index);

        Node currNode = getCurrentNode(index);
        T removedValue = currNode.getValue();
        currNode.setValue(value);

        return removedValue;
    }

    @Override
    public T remove(int index) {
        validateIndexForAccess(index);

        Node currNode = getCurrentNode(index);
        T removedValue = currNode.getValue();
        unlinkNode(currNode);
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node currNode = getNodeByValue(object);

        if (currNode == null) {
            return false;
        }

        unlinkNode(currNode);
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private class Node {
        private Node next;
        private Node prev;
        private T value;

        public Node(T value) {
            this.value = value;
        }

        public Node getNext() {
            return this.next;
        }

        public Node getPrev() {
            return this.prev;
        }

        public void setNext(Node node) {
            this.next = node;
        }

        public void setPrev(Node node) {
            this.prev = node;
        }

        public T getValue() {
            return this.value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

    private void validateIndexForInsert(int ind) {
        if (ind < 0 || ind > size) {
            throw new IndexOutOfBoundsException("Invalid index for insert: " + ind);
        }
    }

    private void validateIndexForAccess(int ind) {
        if (ind < 0 || ind >= size) {
            throw new IndexOutOfBoundsException("Invalid index for acess: " + ind);
        }
    }

    private Node getCurrentNode(int index) {
        Node currentNode = head;

        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.getNext();
            }
        } else {
            currentNode = tail;

            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.getPrev();
            }
        }

        return currentNode;
    }

    private void unlinkNode(Node node) {
        if (node == head && head == tail) {
            head = null;
            tail = null;
            return;
        }

        if (node == tail) {
            tail = node.getPrev();
            tail.setNext(null);
            return;
        }

        if (node == head) {
            head = node.getNext();
            head.setPrev(null);
            return;
        }

        node.getPrev().setNext(node.getNext());
        node.getNext().setPrev(node.getPrev());
    }

    private Node getNodeByValue(T value) {
        if (size == 0) {
            return null;
        }

        Node currNode = head;

        for (int i = 0; i < size; i++) {
            T currVal = currNode.getValue();
            boolean currCondition = value == null ? currVal == null : value.equals(currVal);

            if (currCondition) {
                return currNode;
            }

            currNode = currNode.getNext();
        }

        return currNode;
    }
}
