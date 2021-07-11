package com.dys.seckill;

import java.util.Iterator;
import java.util.LinkedList;

public class MyHashMap {
    private class Node{
        private int key;
        private int value;
        public Node(int key, int value){
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    private static int capcity = 769;
    private LinkedList[] data;

    /** Initialize your data structure here. */
    public MyHashMap() {
        data = new LinkedList[capcity];
        for(int i = 0; i < capcity; i++){
            data[i] = new LinkedList<Node>();
        }
    }

    /** value will always be non-negative. */
    public void put(int key, int value) {
        int hash = hash(key);
        LinkedList<Node> list = data[hash];
        for (Node node : list) {
            if (node.getKey() == key){
                node.setValue(value);
                return;
            }
        }
        list.addLast(new Node(key, value));
    }

    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int key) {
        int hash = hash(key);
        LinkedList<Node> list = data[hash];
        for (Node node : list) {
            if (node.getKey() == key){
                return node.getValue();
            }
        }
        return -1;
    }

    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {
        int hash = hash(key);
        Iterator iterator = data[hash].iterator();
        while (iterator.hasNext()){
            Node node = (Node) iterator.next();
            if (node.getKey() == key){
                iterator.remove();
                return;
            }
        }
    }

    public int hash(int key){
        return key % capcity;
    }
}
