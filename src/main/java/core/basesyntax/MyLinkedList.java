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
            tail.next = newNode;
            newNode.prev = tail;
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
            newNode.prev = null;
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            Node currentNode = getCurrentNode(index);

            currentNode.prev.next = newNode;
            newNode.prev = currentNode.prev;
            currentNode.prev = newNode;
            newNode.next = currentNode;
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

        return getCurrentNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        validateIndexForAccess(index);

        Node currNode = getCurrentNode(index);
        T removedValue = currNode.value;
        currNode.value = value;

        return removedValue;
    }

    @Override
    public T remove(int index) {
        validateIndexForAccess(index);

        Node currNode = getCurrentNode(index);
        T removedValue = currNode.value;
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
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;

            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
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
            tail = node.prev;
            tail.next = null;
            return;
        }

        if (node == head) {
            head = node.next;
            head.prev = null;
            return;
        }

        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private Node getNodeByValue(T value) {
        if (size == 0) {
            return null;
        }

        Node currNode = head;

        for (int i = 0; i < size; i++) {
            T currVal = currNode.value;

            if (value == null ? currVal == null : value.equals(currVal)) {
                return currNode;
            }

            currNode = currNode.next;
        }

        return currNode;
    }

    private class Node {
        private Node next;
        private Node prev;
        private T value;

        public Node(T value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }
}
