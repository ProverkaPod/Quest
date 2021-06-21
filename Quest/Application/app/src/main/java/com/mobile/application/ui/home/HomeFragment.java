package com.mobile.application.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mobile.application.R;
import com.mobile.application.database.Database;
import com.mobile.application.databinding.FragmentHomeBinding;

/**
 * Класс фрагмента (О проекте)
 */
public class HomeFragment extends Fragment {

    /**
     * Модель
     */
    private HomeViewModel homeViewModel;
    /**
     * Приязка View
     */
    private FragmentHomeBinding binding;

    /**
     * Инициализация
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.homeText;
        Database database = new Database(this.getActivity().getBaseContext());
        textView.setText(database.ParamsGet().about);

        return root;
    }

    /**
     * Деинициализация
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}