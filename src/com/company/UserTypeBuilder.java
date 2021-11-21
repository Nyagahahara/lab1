package com.company;

import java.io.InputStreamReader;
import java.util.Comparator;

public interface UserTypeBuilder {
    public String typeName(); // Имя типа
    public Object create(); // Создает объект
    public Object readValue(InputStreamReader in); // Создает и читает объект
    public Object parseValue(String ss); // Создает и парсит одно
    public Comparator getTypeComparator(); // Возвращает компаратор для сравнения
}
