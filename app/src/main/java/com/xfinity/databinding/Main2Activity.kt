package com.xfinity.databinding

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.util.LongSparseArray
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.xfinity.MVVMApplication
import com.xfinity.R
import com.xfinity.data.model.response.RelatedTopic
import com.xfinity.features.masterdetail.CharacterViewModel
import com.xfinity.injection.component.ConfigPersistentComponent
import com.xfinity.injection.component.DaggerConfigPersistentComponent
import com.xfinity.injection.module.ActivityModule
import com.xfinity.util.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject

class Main2Activity : AppCompatActivity(), UserAdapter.OnItemClicked {

    val KEY_FRAGMENT_ID = "KEY_FRAGMENT_ID"
    val NEXT_ID = AtomicLong(0)
    val componentsArray = LongSparseArray<ConfigPersistentComponent>()

    var activityId: Long = 0

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var characterViewModel: CharacterViewModel

    val viewModel = UserViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inject(savedInstanceState)

        val binding: ActivityMain2Binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)

        val query = "simpsons characters"
        addRecyclerView(query, binding)
    }

    private fun addRecyclerView(query: String, binding: ActivityMain2Binding) {

        val adapter = UserAdapter(this.applicationContext, this)
        recyclerView.adapter = adapter

        viewModel.initList()
        binding.viewModel = viewModel

        characterViewModel.responseLiveData.observe(this, Observer<List<RelatedTopic>> {
            if (it != null) {
                viewModel.startUpdates(it)
                //adapter.setData(it)
            }
        })

        characterViewModel.loadCharacters(query)
    }


    fun inject(savedInstanceState: Bundle?) {
        activityId = savedInstanceState?.getLong(KEY_FRAGMENT_ID) ?: NEXT_ID.getAndIncrement()
        val configPersistentComponent: ConfigPersistentComponent
        if (componentsArray.get(activityId) == null) {
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .appComponent(MVVMApplication[getApplicationContext()].component)
                    .build()
            componentsArray.put(activityId, configPersistentComponent)
        } else {
            configPersistentComponent = componentsArray.get(activityId)
        }

        val activityComponent = configPersistentComponent.activityComponent(ActivityModule(this))

        activityComponent.inject(this)

        characterViewModel = ViewModelProviders.of(this, viewModelFactory).get(CharacterViewModel::class.java)
    }

    override fun onItemClick(relatedTopic: RelatedTopic) {
        Log.d("onItemClick", relatedTopic.text)
    }

}