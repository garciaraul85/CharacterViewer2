package com.xfinity.features.masterdetail.fragment;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.xfinity.MVVMApplication;
import com.xfinity.R;
import com.xfinity.data.model.response.RelatedTopic;
import com.xfinity.features.masterdetail.CharacterViewModel;
import com.xfinity.features.masterdetail.data.adapter.ItemListAdapter;
import com.xfinity.injection.component.ConfigPersistentComponent;
import com.xfinity.injection.component.DaggerConfigPersistentComponent;
import com.xfinity.injection.component.FragmentComponent;
import com.xfinity.injection.module.FragmentModule;
import com.xfinity.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

public class ItemListFragment extends Fragment implements ItemListAdapter.OnItemClicked {

    private static final String KEY_FRAGMENT_ID = "KEY_FRAGMENT_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final LongSparseArray<ConfigPersistentComponent> componentsArray =
    new LongSparseArray<>();
    private static final String QUERY = "query";

    private long activityId;
    public boolean showIcon;

    @Inject
    ViewModelFactory viewModelFactory;
    CharacterViewModel characterViewModel;

    private RecyclerView recyclerView;
    private ItemListAdapter itemAdapter;

    OnFragmentInteractionListener listener;

    public ItemListFragment(){
        return;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inject(savedInstanceState);
        String query = "";
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(QUERY)) {
                query = bundle.getString(QUERY);
            }
        }
        addRecyclerView(view, query);
    }

    @Override
    public void onItemClick(RelatedTopic relatedTopic) {
        if (listener != null)
            listener.onItemSelected(relatedTopic);
    }

    private void addRecyclerView(View view, String query) {
        itemAdapter = new ItemListAdapter(getActivity().getApplicationContext(),
        new ArrayList<>(), R.layout.item_detail, characterViewModel.showIcon);
        itemAdapter.setOnClick(this);
        recyclerView = view.findViewById(R.id.list);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(itemAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        if (characterViewModel != null) {
            characterViewModel.getResponseLiveData().observe(this, response -> {
                if (response != null) {
                    itemAdapter.addAll((List<RelatedTopic>) response);
                }
            });

            characterViewModel.loadCharacters(query);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof  OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
        void onItemSelected(RelatedTopic relatedTopic);
    }

    private void inject(Bundle savedInstanceState) {
        activityId =
                savedInstanceState != null
        ? savedInstanceState.getLong(KEY_FRAGMENT_ID)
        : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;
        if (componentsArray.get(activityId) == null) {
            configPersistentComponent =
                    DaggerConfigPersistentComponent.builder()
                            .appComponent(MVVMApplication.Companion.get(getActivity().getApplicationContext()).getComponent())
                            .build();
            componentsArray.put(activityId, configPersistentComponent);
        } else {
            configPersistentComponent = componentsArray.get(activityId);
        }

        FragmentComponent fragmentComponent =
        configPersistentComponent.fragmentComponent(new FragmentModule(this));

        fragmentComponent.inject(this);


        characterViewModel = ViewModelProviders.of(this, viewModelFactory).get(CharacterViewModel.class);
    }

    public void setIconVisibility(boolean iconVisibility) {
        characterViewModel.showIcon = iconVisibility;
        itemAdapter.setIconVisibility(iconVisibility);
    }

    public void setupSearchView(MenuItem searchMenuItem) {
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));

        characterViewModel.getQueryLiveData().observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object query) {
                if (query != null && query.toString().length() >= 4) {
                    itemAdapter.filter((CharSequence) query);
                }
            }
        });

        characterViewModel.searchCharacters(searchView);

        //optional: collapse the searchView on close
        searchView.setOnQueryTextFocusChangeListener((view, queryTextFocused) -> {
            if (!queryTextFocused) {
                searchView.setQuery("", false);
                searchView.clearFocus();
                searchView.setIconified(true);
                searchMenuItem.collapseActionView();
                itemAdapter.closeSearch();
            }
        });
    }

}