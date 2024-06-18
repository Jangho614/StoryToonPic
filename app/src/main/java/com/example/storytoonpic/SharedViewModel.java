package com.example.storytoonpic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<List<ViewAdapter.Item>> itemList = new MutableLiveData<>();

    public void addItem(ViewAdapter.Item item) {
        List<ViewAdapter.Item> currentList = itemList.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(item);
        itemList.setValue(currentList);
    }

    public LiveData<List<ViewAdapter.Item>> getItemList() {
        return itemList;
    }
}

