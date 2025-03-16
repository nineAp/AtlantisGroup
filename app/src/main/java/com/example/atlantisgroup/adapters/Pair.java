package com.example.atlantisgroup.adapters;

/**
 * Класс Pair представляет пару ключ-значение для использования в коллекциях.
 * Обычно используется для хранения характеристик и их значений.
 */
public class Pair {

    private String key;    // Поле для хранения ключа
    private String value;  // Поле для хранения значения

    /**
     * Конструктор класса Pair, инициализирует объект с заданным ключом и значением.
     *
     * @param key   Ключ (название характеристики)
     * @param value Значение (значение характеристики)
     */
    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Метод для получения ключа из пары.
     *
     * @return Ключ (название характеристики)
     */
    public String getKey() {
        return key;
    }

    /**
     * Метод для получения значения из пары.
     *
     * @return Значение (значение характеристики)
     */
    public String getValue() {
        return value;
    }
}
