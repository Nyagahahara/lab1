package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainFrame extends JFrame {
    //последовательность случайных операций над деревом
    private JButton button1;
    //выгрузка в файл
    private JButton button2;
    //загрузить в файл
    private JButton button3;
    //очистка текстовой области
    private JButton button4;
    private JTextArea textArea1;
    private JPanel rootPanel;

    private UserTypeBuilder builder;
    private Tree tree;

    private void refreshTreeView(){
        System.out.println("refreshTreeView");
        textArea1.selectAll();
        textArea1.replaceSelection("");
        tree.printTree();
    }

    public MainFrame(UserTypeBuilder builder) {

        this.builder = builder;
        tree = new Tree();

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String type_name = builder.typeName();
                    String pathname = "";
                    if (type_name == "String"){
                        pathname = "C:\\Users\\panda\\Documents\\file_string.txt";
                    } else if (type_name == "Integer"){
                        pathname = "C:\\Users\\panda\\Documents\\file_integer.txt";
                    }
                    File file = new File(pathname);
                    FileReader fr = new FileReader(file);
                    BufferedReader reader = new BufferedReader(fr);
                    String line = reader.readLine();
                    while (line != null) {
                        tree.insertNode(builder.parseValue(line), builder.getTypeComparator());
                        line = reader.readLine();
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                refreshTreeView();
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                File serFile = new File("C:\\Users\\panda\\Documents\\file.ser");
                ObjectOutputStream oos = null;
                oos = new ObjectOutputStream(new FileOutputStream(serFile));
                oos.writeObject(tree);
                oos.flush();
                oos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                File serFile = new File("C:\\Users\\panda\\Documents\\file.ser");
                ObjectInputStream ois = null;
                ois = new ObjectInputStream(new FileInputStream(serFile));
                tree = (Tree) ois.readObject();
                ois.close();
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                refreshTreeView();
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.selectAll();
                textArea1.replaceSelection("");
            }
        });

        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea1));
        System.setOut(printStream);
        System.setErr(printStream);

        this.setContentPane(rootPanel);
        this.setMinimumSize(new Dimension(700,550));
        this.setSize(new Dimension(700, 550));
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    private class CustomOutputStream extends OutputStream {
        private JTextArea textArea;

        public CustomOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) throws IOException {
            textArea.append(String.valueOf((char)b));
            textArea.setCaretPosition(textArea.getDocument().getLength());
            textArea.update(textArea.getGraphics());
        }
    }

}
