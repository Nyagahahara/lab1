package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static UserTypeBuilder getBuilderByName(String name){
        switch (name){
            case "Integer":
                return new IntegerBuilder();
            case "String":
                return new StringBuilder();
            default:
                break;
        }
        return null;
    }

    private static void test_rand(int n)
    {
        Tree tree = new Tree();
        UserTypeBuilder builder = getBuilderByName("Integer");
        //массив для ключей, которые присутствуют в дереве
        ArrayList<Integer> m = new ArrayList<Integer>();
        //установка первого случайного числа
        Random R = new Random();
        //заполнение дерева и массива элементами со случайными ключами
        for (int i = 0; i < n; i++)
        {
            Integer newInt = R.nextInt();
            m.add(newInt);
            tree.insertNode(newInt, builder.getTypeComparator());
        }
        //вывод размера дерева до теста
        System.out.println("Размер дерева до теста:" + tree.size() + "\n");
        //обнуление счётчиков трудоёмкости вставки, удаления и поиска
        double I = 0;
        double D = 0;
        double S = 0;
        //генерация потока операций, 10% - промахи операций
        for (int i = 0; i < n / 2; i++)
            if (i % 10 == 0)		//10% промахов
            {
                tree.setCounter(0);
                tree.removeNode(R.nextInt(), builder.getTypeComparator());
                D += tree.getCounter();
                tree.setCounter(0);
                tree.insertNode(m.get((int) (Math.random() * n)), builder.getTypeComparator());
                I += tree.getCounter();
                tree.setCounter(0);
                tree.searchElement(R.nextInt(), builder.getTypeComparator());
                S += tree.getCounter();
            }
            else  //90% успешных операций
            {
                int ind = (int) (Math.random() * n);
                tree.setCounter(0);
                tree.removeNode(m.get(ind), builder.getTypeComparator());
                D += tree.getCounter();
                Integer key = R.nextInt();
                tree.setCounter(0);
                tree.insertNode(R.nextInt(), builder.getTypeComparator());
                I += tree.getCounter();
                tree.setCounter(0);
                m.add(ind, key);
                tree.setCounter(0);
                tree.searchElement((int) (Math.random() * n), builder.getTypeComparator());
                S += tree.getCounter();
            }	//конец теста
        //вывод результатов:
        //вывод размера дерева после теста
        System.out.println("items count: " + tree.size() + "\n");
        //теоретической оценки трудоёмкости операций BST
        System.out.println("2*n*log2(n) = " + 2*n*(Math.log((double)n) / Math.log(2.0)) + "\n");
        //экспериментальной оценки трудоёмкости вставки
        System.out.println("Count insert: " + I + " " + I / (n / 2) +"\n");
        //экспериментальной оценки трудоёмкости удаления
        System.out.println("Count delete: " + D + " " + D / (n / 2) +"\n");
        //экспериментальной оценки трудоёмкости поиска
        System.out.println("Count search: " + S + " " + S / (n / 2) + "\n");
    }

    public static void test_ord(int n)
    {
        Tree tree = new Tree();
        UserTypeBuilder builder = getBuilderByName("Integer");
        //массив для ключей, которые присутствуют в дереве
        ArrayList<Integer> m = new ArrayList<Integer>();
        Random R = new Random();

        //заполнение дерева и массива элементами с возрастающими чётными ключами на интервале [0, 10000, 20000, ... ,10000*n]
        for (int i = 0; i < n; i++) {
            m.add(i * 1000);
            tree.insertNode(m.get(i), builder.getTypeComparator());
        }
        //вывод размера дерева до теста
        System.out.println("Размер дерева до теста:" + tree.size() + "\n");
        //обнуление счётчиков трудоёмкости вставки, удаления и поиска
        double I = 0;
        double D = 0;
        double S = 0;

        //генерация потока операций, 10% - промахи операций
        for (int i = 0; i < n / 2; i++) {
            if (i % 10 == 0)        //10% промахов
            {
                Integer k = R.nextInt() % (1000 * n);
                k = k + (k / 2) + 1;    //случайный нечётный ключ
                tree.setCounter(0);
                tree.removeNode(k, builder.getTypeComparator());
                D += tree.getCounter();
                tree.setCounter(0);
                tree.insertNode(m.get((int) (Math.random() * n)), builder.getTypeComparator());
                I += tree.getCounter();
                tree.setCounter(0);
                k = R.nextInt() % (1000 * n);
                k = k + (k /2) + 1;    // случайный нечётный ключ
                tree.searchElement(k, builder.getTypeComparator());
                S += tree.getCounter();
            } else  //90% успешных операций
            {
                int ind = (int) (Math.random() * n);
                tree.setCounter(0);
                tree.removeNode(m.get(ind), builder.getTypeComparator());
                D += tree.getCounter();
                tree.setCounter(0);
                int k = R.nextInt() % (1000 * n);
                k = k + k / 2 + 1;        // случайный чётный ключ
                tree.insertNode(k, builder.getTypeComparator());
                I += tree.getCounter();
                tree.setCounter(0);
                m.add(ind, k);
                tree.searchElement(m.get((int) Math.random() % n), builder.getTypeComparator());
                S += tree.getCounter();
            }
        }
        //вывод результатов:
        //вывод размера дерева после теста
        System.out.println("items count: " + tree.size() + "\n");
        //теоретической оценки трудоёмкости операций BST
        System.out.println("2*n*log2(n) = " + 2*n*(Math.log((double)n) / Math.log(2.0)) + "\n");
        //экспериментальной оценки трудоёмкости вставки
        System.out.println("Count insert: " + I + " " + I / (n / 2) +"\n");
        //экспериментальной оценки трудоёмкости удаления
        System.out.println("Count delete: " + D + " " + D / (n / 2) +"\n");
        //экспериментальной оценки трудоёмкости поиска
        System.out.println("Count search: " + S + " " + S / (n / 2) + "\n");
    }	//конец теста


    public static void main(String[] args) {
	// write your code here
        System.out.println("Для упорядоченных значений:");
        test_ord(10000);
        System.out.println("Для случайных значений:");
        test_rand(10000);
    }
}
