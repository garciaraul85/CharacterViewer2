package com.xfinity.features.masterdetail;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v7.widget.SearchView;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.xfinity.data.DataManager;
import com.xfinity.data.model.response.Character;
import com.xfinity.data.model.response.RelatedTopic;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CharacterViewModel extends ViewModel {

    public boolean showIcon;
    private final DataManager dataManager;
    private CompositeDisposable disposables;
    private MutableLiveData responseLiveData = new MutableLiveData<List<RelatedTopic>>();
    private MutableLiveData queryLiveData = new MutableLiveData<CharSequence>();

    public CharacterViewModel(DataManager dataManager) {
        this.disposables = new CompositeDisposable();
        this.dataManager = dataManager;
    }

    public void loadCharacters(String query) {
        disposables.add(
                dataManager.getCharacters(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(Character::getRelatedTopics)
                        .subscribe(responseLiveData::setValue)
        );
    }

    public void searchCharacters(SearchView searchView) {

        RxSearchView.queryTextChanges(searchView)
                .doOnEach(notification -> {
                    CharSequence query = (CharSequence) notification.getValue();
                    queryLiveData.setValue(query);
                })
                .debounce(300, TimeUnit.MILLISECONDS) // to skip intermediate letters
                .retry(3)
                .subscribe();
    }

    public MutableLiveData getResponseLiveData() {
        return responseLiveData;
    }

    public MutableLiveData getQueryLiveData() {
        return queryLiveData;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}