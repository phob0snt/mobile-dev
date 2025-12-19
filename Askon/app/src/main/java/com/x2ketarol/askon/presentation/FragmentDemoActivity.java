package com.x2ketarol.askon.presentation;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.x2ketarol.askon.R;
import com.x2ketarol.askon.presentation.fragments.BlankFragment;
import com.x2ketarol.askon.presentation.fragments.DataInputFragment;
import com.x2ketarol.askon.presentation.fragments.ExpertListFragment;
import com.x2ketarol.askon.presentation.fragments.ProfileFragment;

public class FragmentDemoActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_demo);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(new ExpertListFragment());
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;

            if (item.getItemId() == R.id.nav_experts) {
                fragment = new ExpertListFragment();
            } else if (item.getItemId() == R.id.nav_bundle) {
                fragment = BlankFragment.newInstance(15);
            } else if (item.getItemId() == R.id.nav_result_api) {
                fragment = new DataInputFragment();
            } else if (item.getItemId() == R.id.nav_profile) {
                fragment = new ProfileFragment();
            }

            if (fragment != null) {
                loadFragment(fragment);
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragmentContainer, fragment)
            .commit();
    }
}
