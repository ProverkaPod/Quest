package com.mobile.application.database;

import android.graphics.Bitmap;

import java.util.function.DoubleUnaryOperator;

/**
 * Класс, для хранения данных по заданиям Квест-экскурсий
 */
public class QuestTaskItem {
    /**
     * Идентифкатор
     */
    public long id;
    /**
     * Порядковый номер
     */
    public int number;
    /**
     * Название задания
     */
    public String title;
    /**
     * Описание
     */
    public String description;
    /**
     * Фото
     */
    public Bitmap image = null;
    /**
     * Широта
     */
    public Double gps_lat = 0.0;
    /**
     * Долгота
     */
    public Double gps_long = 0.0;


}