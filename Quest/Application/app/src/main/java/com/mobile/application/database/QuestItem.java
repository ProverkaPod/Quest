package com.mobile.application.database;

import android.graphics.Bitmap;

/**
 * Класс, для хранения данных по Квест-экскурсиям
 */
public class QuestItem {
    /**
     * Идентифкатор
     */
    public long id;
    /**
     * Название квест-экускурсии
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
     * Ссылка на компонент для работы с БД
     */
    public Database database;
}
