package com.xfinity.wireviewer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.xfinity.R;
import com.xfinity.data.model.response.RelatedTopic;
import com.xfinity.features.masterdetail.fragment.ItemDetailFragment;
import com.xfinity.features.masterdetail.fragment.ItemListFragment;

import static android.app.SearchManager.QUERY;
import static com.xfinity.features.masterdetail.fragment.ItemDetailFragment.ITEM;
import static com.xfinity.features.masterdetail.fragment.ItemDetailFragment.ICON;

public class MainActivity extends AppCompatActivity implements ItemListFragment.OnFragmentInteractionListener {

    private static final int MENU_ITEM_ITEM1 = 1;
    private ItemListFragment itemListFragment;
    private boolean tabletSize;
    private boolean showIcon;
    private Toolbar toolbar;
    private Menu myMenu;

    private MenuItem searchMenuItem;

    private static final String SHOWICON = "showIcon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            showIcon = savedInstanceState.getBoolean(SHOWICON, false);
        }

        setContentView(R.layout.activity_main);

        tabletSize = getResources().getBoolean(R.bool.isTablet);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        if (getSupportActionBar() != null) { // hide
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
        }

        toolbar.setNavigationOnClickListener(v -> {
            // what do you want here
            if (getSupportActionBar() != null) { // hide
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setHomeButtonEnabled(false);
            }
            onBackPressed();
        });

        if (savedInstanceState != null)
            return;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportActionBar().setTitle(getString(R.string.app_name));
        if (!tabletSize) {
            myMenu.findItem(MENU_ITEM_ITEM1).setVisible(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle bundle = new Bundle();
        bundle.putString(QUERY, getString(R.string.search_query));

        itemListFragment = new ItemListFragment();
        itemListFragment.setRetainInstance(true);
        itemListFragment.setArguments(bundle);

        itemListFragment.showIcon = showIcon;

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down,
                R.anim.slide_up, R.anim.slide_down);

        fragmentTransaction.replace(R.id.framelayout_left, itemListFragment);

        if (findViewById(R.id.framelayout_right) != null) {
            ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
            itemDetailFragment.setRetainInstance(true);
            fragmentTransaction.replace(R.id.framelayout_right, itemDetailFragment);
        }

        fragmentTransaction.commit();
    }

    @Override
    public void onItemSelected(RelatedTopic relatedTopic) {
        if (getSupportActionBar() != null) { // show
            if (!tabletSize) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                toolbar.setTitle(relatedTopic.getText());
                myMenu.findItem(MENU_ITEM_ITEM1).setVisible(false);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setHomeButtonEnabled(false);
            }
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!tabletSize) {
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
                    R.anim.pop_enter, R.anim.pop_exit);
        }

        int containerViewId = R.id.framelayout_left;

        if (findViewById(R.id.framelayout_right) != null)
            containerViewId = R.id.framelayout_right;

        Bundle bundle = new Bundle();
        if (relatedTopic.getText() != null) {
            bundle.putString(ITEM, relatedTopic.getText());
        }
        if (relatedTopic.getIcon() != null && relatedTopic.getIcon().getUrl() != null) {
            bundle.putString(ICON, relatedTopic.getIcon().getUrl());
        }

        ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
        itemDetailFragment.setRetainInstance(true);
        itemDetailFragment.setArguments(bundle);
        fragmentTransaction.replace(containerViewId, itemDetailFragment);

        if (findViewById(R.id.framelayout_right) == null)
            fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        myMenu = menu;
        if (!tabletSize) {
            itemListFragment.setIconVisibility(showIcon);
            if (showIcon) {
                menu.add(Menu.NONE, MENU_ITEM_ITEM1, Menu.NONE, getString(R.string.show_text));
            } else {
                menu.add(Menu.NONE, MENU_ITEM_ITEM1, Menu.NONE, getString(R.string.show_images));
            }

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);

            searchMenuItem = menu.findItem(R.id.action_search);
            if (itemListFragment != null) {
                itemListFragment.setupSearchView(searchMenuItem);
            }
        }
        return true;
    }


    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putBoolean(SHOWICON, showIcon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ITEM_ITEM1:
                showIcon = !showIcon;
                itemListFragment.setIconVisibility(showIcon);

                if (showIcon) {
                    item.setTitle(getString(R.string.show_text));
                } else {
                    item.setTitle(getString(R.string.show_images));
                }
                return true;
            default:
                return false;
        }
    }

}