package com.mobile.application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mobile.application.database.Database;
import com.mobile.application.database.QuestItem;
import com.mobile.application.database.QuestTaskItem;
import com.mobile.application.ui.quest.MyQuestRecyclerViewAdapter;
import com.mobile.application.ui.quest.QuestFragment;

import java.util.zip.Inflater;

public class QuestTasksActivity extends AppCompatActivity {
    private Database database;
    private QuestItem questItem;

    private OnListFragmentInteractionListener mListener = new OnListFragmentInteractionListener() {
        @Override
        public void onListFragmentInteraction(QuestTaskItem item) {
            Toast.makeText(QuestTasksActivity.this.getBaseContext(), item.title,Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getContext(), QuestActivity.class);
//            intent.putExtra("questId", item.id);
//            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_tasks);
        database = new Database(this.getBaseContext());

        Long questId = getIntent().getLongExtra("questId", 0);
        questItem = database.QuestGet(questId);

        View view = findViewById(R.id.list);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyQuestTaskRecyclerViewAdapter(database.QuestTaskListGet(questItem.id), mListener));
        }
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(QuestTaskItem item);
    }
}