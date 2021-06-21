package com.mobile.application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Вспомогательный класс, для работы с базой данных, реализующий запросы приложения к базе данных.
 * Данный класс является надстройкой над "DatabaseHelper".
 */
public class Database {

    /**
     * Объект "databaseHelper"
     */
    private DatabaseHelper databaseHelper;
    /**
     * База данных
     */
    private SQLiteDatabase database;

    /**
     * Конструктор класса
     *
     * @param context Контекст
     */
    public Database(Context context) {
        databaseHelper = new DatabaseHelper(context);
        try {
            databaseHelper.updateDataBase();
            database = databaseHelper.getWritableDatabase();
        } catch (Exception exception) {
            throw new Error(exception.getMessage());
        }
    }

    /**
     * Метод реализующий проверку данных пользователя (реализующий авторизацию
     * @param password Пароль
     * @return Флаг успешной проверки данных
     */
    public long Login(String login, String password) {
        long UserID = 0;
        Cursor q = GetQuery("select * from user where login = \"" + login + "\"");
        try {
            if (q.getCount() > 0) {
                q.moveToFirst();
                String psw = q.getString(q.getColumnIndexOrThrow("password"));
                if (psw.equals(password)) {
                    UserID = q.getLong(q.getColumnIndexOrThrow("id"));
                }
            }
        } finally {
            q.close();
        }
        return UserID;
    }

    /**
     * Метод выполнения SQL-запроса
     *
     * @param SQL SQL-запрос к базе данных
     * @return Курсор набора данных
     */
    public Cursor GetQuery(String SQL) {
        return database.rawQuery(SQL, null);
    }
    /**
     * Метод добавления пользователя
     * @param item
     * @return
     */
    public long UserAdd(UserItem item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("registration_date", item.registration_date);
        contentValues.put("last_name", item.last_name);
        contentValues.put("first_name", item.first_name);
        contentValues.put("middle_name", item.middle_name);
        contentValues.put("login", item.login);
        contentValues.put("password", item.password);
        contentValues.put("phone", item.phone);
        contentValues.put("id_user_type", item.id_user_type);
        return database.insert("user", null, contentValues);
    }
    /**
     * Метод обновления данных пользователя
     * @param item
     * @return
     */
    public boolean UserUpdate(UserItem item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("registration_date", item.registration_date);
        contentValues.put("last_name", item.last_name);
        contentValues.put("first_name", item.first_name);
        contentValues.put("middle_name", item.middle_name);
        contentValues.put("login", item.login);
        contentValues.put("password", item.password);
        contentValues.put("phone", item.phone);
        contentValues.put("id_user_type", item.id_user_type);
        return database.update("user", contentValues, "id = ?", new String[] {item.id.toString()}) > 0;
    }
    /**
     * Метод извлечения данных пользователя
     * @param id
     * @return
     */
    public UserItem UserGet(Long id) {
        Cursor cursor = database.rawQuery("select * from user where id = ?", new String[] {id.toString()});
        try {
            if (cursor.moveToFirst()) {
                UserItem item = new UserItem();
                item.id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                item.registration_date = cursor.getLong(cursor.getColumnIndexOrThrow("registration_date"));
                item.id_user_type = cursor.getLong(cursor.getColumnIndexOrThrow("id_user_type"));
                item.login = cursor.getString(cursor.getColumnIndexOrThrow("login"));
                item.password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                item.last_name = cursor.getString(cursor.getColumnIndexOrThrow("last_name"));
                item.first_name = cursor.getString(cursor.getColumnIndexOrThrow("first_name"));
                item.middle_name = cursor.getString(cursor.getColumnIndexOrThrow("middle_name"));
                item.phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                return item;
            }
        } finally {
            cursor.close();
        }
        return null;
    }
    /**
     * Проверка, существует ли логин
     * @param login
     * @return
     */
    public boolean UserLoginExists (String login) {
        Cursor cursor = database.rawQuery("select * from user where login = ?", new String[] {login});
        try {
            if (cursor.moveToFirst()) {
                return true;
            }
        } finally {
            cursor.close();
        }
        return false;
    }
    /**
     * Проверка, существует ли логин
     * @param login
     * @param exception
     * @return
     */
    public boolean UserLoginExists (String login, String exception) {
        Cursor cursor = database.rawQuery("select * from user where login = ? and login <> ?", new String[] {login, exception});
        try {
            if (cursor.moveToFirst()) {
                return true;
            }
        } finally {
            cursor.close();
        }
        return false;
    }
    /**
     * Метод возвращает сведения для конкретного идентификатора квест-экускурсии
     *
     * @param id Идентификатор квест-экскурсии
     * @return Данные по квес-экскурсии
     */
    public QuestItem QuestGet(Long id) {
        Cursor cursor = GetQuery("select * from Quest where id = " + id.toString());
        try {
            if (cursor.moveToFirst()) {
                QuestItem item = new QuestItem();
                item.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                item.title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                item.description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                if (cursor.isNull(cursor.getColumnIndexOrThrow("image")) == false) {
                    byte[] data = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
                    item.image = BitmapFactory.decodeByteArray(data, 0, data.length);
                }
                return item;
            }
        } finally {
            cursor.close();
        }
        return null;
    }
    /**
     * Метод возвращает список квест-экскурсий из БД
     *
     * @return Список сведений по квест-экскурсиям
     */
    public List<QuestItem> QuestListGet() {
        List<QuestItem> list = new ArrayList<QuestItem>();
        Cursor cursor = GetQuery("select id from quest");
        try {
            if (cursor.moveToFirst()) {
                do {
                    list.add(QuestGet(cursor.getLong(cursor.getColumnIndexOrThrow("id"))));
                }
                while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    /**
     * Метод возвращает сведения из таблицы параметры
     *
     * @return Параметры приложения
     */
    public ParamsItem ParamsGet() {
        Cursor cursor = GetQuery("select * from Params where id = 1");
        try {
            if (cursor.moveToFirst()) {
                ParamsItem item = new ParamsItem();
                item.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                item.about = cursor.getString(cursor.getColumnIndexOrThrow("about"));
                return item;
            }
        } finally {
            cursor.close();
        }
        return null;
    }
    /**
     * Метод возвращает сведения для конкретного идентификатора задания квест-экускурсии
     *
     * @param id Идентификатор задания квест-экскурсии
     * @return Данные по заданию квес-экскурсии
     */
    public QuestTaskItem QuestTaskGet(Long id) {
        Cursor cursor = GetQuery("select * from quest_task where id = " + id.toString());
        try {
            if (cursor.moveToFirst()) {
                QuestTaskItem item = new QuestTaskItem();
                item.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                item.title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                item.description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                if (cursor.isNull(cursor.getColumnIndexOrThrow("image")) == false) {
                    byte[] data = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
                    item.image = BitmapFactory.decodeByteArray(data, 0, data.length);
                }
                item.gps_lat = cursor.getDouble(cursor.getColumnIndexOrThrow("gps_lat"));
                item.gps_long = cursor.getDouble(cursor.getColumnIndexOrThrow("gps_long"));
                item.number = cursor.getInt(cursor.getColumnIndexOrThrow("number"));

                return item;
            }
        } finally {
            cursor.close();
        }
        return null;
    }

    /**
     * Метод возвращает список заданий для квест-экскурсии из БД
     *
     * @return Список заданий
     */
    public List<QuestTaskItem> QuestTaskListGet(Long idQuest) {
        List<QuestTaskItem> list = new ArrayList<QuestTaskItem>();
        Cursor cursor = GetQuery("select id from quest_task where id_quest = " + idQuest.toString() + " order by number asc");
        try {
            if (cursor.moveToFirst()) {
                do {
                    list.add(QuestTaskGet(cursor.getLong(cursor.getColumnIndexOrThrow("id"))));
                }
                while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return list;
    }
}
