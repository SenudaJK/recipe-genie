package com.example.getting_started;

import android.annotation.SuppressLint;
import android.os.Bundle;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

// IM/2021/009 - Y.A.D.S.C.Basnayake
public class SavedRecipe extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private List<Recipe> recipeList;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.saved_recipe_activity);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);

        //tabLayout.setupWithViewPager(viewPager2);

        // ensuring that the tab titles and the corresponding fragments are aligning when the user navigates between tabs
        
        VPAdapter vpAdapter = new VPAdapter(this);
        //VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new fragment_SavedRecipes(),"Saved");
        vpAdapter.addFragment(new fragment_MyRecipes(),"My Recipes");
        viewPager2.setAdapter(vpAdapter);

        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Saved");
                            break;
                        case 1:
                            tab.setText("My Recipes");
                            break;
                    }
                }
        ).attach();

    }
}
