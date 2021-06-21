package com.mobile.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mobile.application.database.Database;
import com.mobile.application.database.QuestItem;
import com.mobile.application.databinding.ActivityQuestBinding;

/**
 * Класс активити "Квест" (сведения квеста)
 */
public class QuestActivity extends AppCompatActivity {
    /**
     * Привязка к View
     */
    private ActivityQuestBinding binding;
    /**
     * Компонент обращения к базе данных
     */
    private Database mDatabase;
    /**
     * Данные квеста
     */
    private QuestItem questItem;

    /**
     * Инициализация активити
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestBinding.inflate (getLayoutInflater());
        setContentView(binding.getRoot());
        mDatabase = new Database(getApplicationContext());
        Long questId = getIntent().getLongExtra("questId", 0);
        questItem =  mDatabase.QuestGet(questId);
        binding.itemDescription.setText(questItem.description);
        binding.itemTitle.setText(questItem.title);
        binding.itemImage.setImageBitmap(questItem.image);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestActivity.this.getBaseContext(), QuestTasksActivity.class);
                intent.putExtra("questId", QuestActivity.this.questItem.id);
                startActivity(intent);
            }
        });
    }
}