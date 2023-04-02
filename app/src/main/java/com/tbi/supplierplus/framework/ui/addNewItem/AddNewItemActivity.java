package com.tbi.supplierplus.framework.ui.addNewItem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tbi.supplierplus.R;
import com.tbi.supplierplus.databinding.ActivityAddNewItemBinding;

public class AddNewItemActivity extends AppCompatActivity {

    ActivityAddNewItemBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
    }
}