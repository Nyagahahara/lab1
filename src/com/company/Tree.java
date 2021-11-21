package com.company;

import java.io.Serializable;
import java.util.Comparator;

public class Tree implements Serializable {
    private Node rootNode;
    private int counter;

    public int getCounter() {
        return counter;
    }
    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Tree() {
        rootNode = null;
    }

    public void insertNode(Object value, Comparator comparator){

        Node newNode = new Node();
        newNode.setData(value);
        newNode.setCount(1);
        newNode.setParent(null);

        if (rootNode == null){
            rootNode = newNode;
        } else {

            Node existing = search(rootNode, value, comparator);
            if (existing != null){
                return;
            }

            Node currentNode = rootNode;
            Node parentNode;
            while (true){
                parentNode = currentNode;
                if (newNode.getData() == currentNode.getData()) {
                    return;
                } else {
                    if (comparator.compare(newNode.getData(), currentNode.getData()) < 0) {
                        counter++;
                        Object cur = currentNode.getData();
                        currentNode.setData(newNode.getData());
                        newNode.setData(cur);
                    }
                    currentNode.setCount(currentNode.getCount() + 1);
                    if (currentNode.getLeft() == null || currentNode.getRight() != null &&
                            currentNode.getLeft().getCount() < currentNode.getRight().getCount()){
                        currentNode = currentNode.getLeft();
                        if (currentNode == null){
                            parentNode.setLeft(newNode);
                            newNode.setParent(parentNode);
                            return;
                        }
                    } else {
                        currentNode = currentNode.getRight();
                        if (currentNode == null){
                            parentNode.setRight(newNode);
                            newNode.setParent(parentNode);
                            return;
                        }
                    }
                }
            }
        }
    }

   private void shift(Node currentNode, Comparator comparator){
       if (currentNode == null){
           return;
       }
       //Node parentNode = searchParent(rootNode, currentNode, comparator);
       Node parentNode = currentNode.getParent();
       while (true) {
           if (currentNode.getLeft() == null && currentNode.getRight() == null) {
               if (parentNode.getRight() == currentNode) {
                   parentNode.setRight(null);
                   return;
               } else {
                   parentNode.setLeft(null);
                   return;
               }
           }

           if (currentNode.getLeft() == null || currentNode.getRight() != null &&
                   comparator.compare(currentNode.getRight().getData(), currentNode.getLeft().getData()) < 0) {
               counter++;
               currentNode.setData(currentNode.getRight().getData());
               parentNode = currentNode;
               currentNode = currentNode.getRight();
           } else {
               counter++;
               currentNode.setData(currentNode.getLeft().getData());
               parentNode = currentNode;
               currentNode = currentNode.getLeft();
           }
       }
   }

    private void scan(Node p, int level){
        if (p == null) {
            return;
        }
        scan(p.getLeft(),level + 1);
        for (int i = 1; i < level; i++) {
            System.out.print("  ");
        }
        System.out.print(p.getData() + " (" + p.getParent().getData() + ")\n");
        scan(p.getRight(),level + 1);
    }

    public Node search(Node p, Object value, Comparator comparator){
        Node res = null;
        if (null != p){
            counter++;
            if (comparator.compare(p.getData(), value) == 0){
                res = p;
            } else if (comparator.compare(p.getData(), value) > 0){
                return null; //если в данной вершине значение больше, чем искомое, то среди его потомков меньшего значения уже не будет
            } else {
                res = search(p.getRight(), value, comparator);
                if (null == res){
                    res = search(p.getLeft(), value, comparator);
                }
            }
        }
        return res;
    }

    public Boolean searchElement(Object value, Comparator comparator){
        if (search(rootNode, value, comparator) != null) {
            return true;
        }
        return false;
    }

    public void removeNode(Object value, Comparator comparator){
        Node p = search(rootNode, value, comparator);
        if (p != null) {
            shift(p, comparator);
        }
    }

    public void printTree(){
        scan(rootNode, 1);
    }

    private void countSize(Node p){
        if (p == null) {
            return;
        }
        countSize(p.getLeft());
        counter++;
        countSize(p.getRight());
    }
    public int size(){
        setCounter(0);
        countSize(rootNode);
        return counter;
    }
}
