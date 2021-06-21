package com.mobile.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.mobile.application.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

/**
 * Класс главной активити приложения
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Конфигуратор меню
     */
    private AppBarConfiguration mAppBarConfiguration;
    /**
     * Привязка к View
     */
    private ActivityMainBinding binding;
    /**
     * Пункт меню "Настройки"
     */
    private MenuItem menuItemSettings;
    /**
     * Пункт меню "Вход"
     */
    private MenuItem menuItemLogin;
    /**
     * Пункт меню "Выход"
     */
    private MenuItem menuItemLogout;
    /**
     * Выдвижное меню
     */
    private DrawerLayout drawer;

    /**
     * Метод, вызываемый при инициализации активити
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
            }
        });
        this.drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // ****************************************************************************************
        // Настройка нещего расчудесного меню
        // нажатие на пункт меню логин
        menuItemLogin = navigationView.getMenu().findItem(R.id.nav_login);
        menuItemLogin.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                DrawerClose();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, LoginActivity.RESULT_LOGIN);
                return false;
            }
        });
        // нажатие на пункт меню "выход"
        menuItemLogout = navigationView.getMenu().findItem(R.id.nav_logout);
        menuItemLogout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                DrawerClose();
                Snackbar.make(MainActivity.this.findViewById(R.id.drawer_layout), R.string.logout_confirmation, Snackbar.LENGTH_LONG)
                        .setAction(R.string.yes, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Global.userID = 0;
                                EnableUserControls(false);
                            }
                        }).show();
                return false;
            }
        });
        // ****************************************************************************************
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_quest, R.id.nav_home,  R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_login)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    /**
     * Метод активации\деактивации элементов интерфецса
     * @param Enable
     */
    private void EnableUserControls (boolean Enable) {
        menuItemLogin.setVisible(!Enable);
        menuItemLogout.setVisible(Enable);
        if (menuItemSettings != null)
            menuItemSettings.setEnabled(Enable);
    }
    /**
     * Метод закрывает компонент "Выдвижной ящик" (Drawer)
     */
    private void DrawerClose() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
    /**
     * Обработчик события "Нажатие кнопки 'назад' смартфона"
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    /**
     * Метод создания меню настроек
     *
     * @param menu Меню
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        menuItemSettings = menu.findItem(R.id.action_settings);
        menuItemSettings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getBaseContext(), UserProfileActivity.class);
                startActivity(intent);
                return false;
            }
        });
        menuItemSettings.setEnabled(false);
        return true;
    }

    /**
     * Конфигурирование меню
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    /**
     *  Метод обработки результатов выполнения вызываемых Активити
     * @param requestCode Код активити
     * @param resultCode Результирующий код
     * @param data Данные
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginActivity.RESULT_LOGIN) {
            if (resultCode == RESULT_OK && Global.getLogged()) {
                EnableUserControls(true);
            } else {

            }
        }
    }
    /**
     * Метод вызывается, когда активити стартует
     */
    @Override
    protected void onStart() {
        super.onStart();
        EnableUserControls(Global.getLogged());
    }



}