package com.company;

import java.io.Serializable;

public class Node implements Serializable {
    private Object data;
    private int count;
    private Node left;
    private Node right;
    private Node parent;

    public Node(){}

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCount(){
        return count;
    }

    public void setCount(int count){
        this.count = count;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setParent(Node parent) { this.parent = parent; }

    public Node getParent() { return parent; }
}
