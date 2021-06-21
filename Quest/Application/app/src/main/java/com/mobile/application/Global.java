package com.mobile.application;
/**
 * Глобальные переменны проекта
 */
public class Global {

    /**
     * Идентификатор авторизованного пользователя
     */
    public static long userID = 0;

    /**
     * Метод возвращает "true", если выполнен вход
     * @return "true", если выполнен вход
     */
    public static Boolean getLogged() {
        return userID != 0;
    }
}
