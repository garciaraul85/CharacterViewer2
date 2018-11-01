package com.xfinity.injection.component

import com.xfinity.databinding.Main2Activity
import com.xfinity.injection.PerActivity
import com.xfinity.injection.module.ActivityModule

import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(main2Activity: Main2Activity)
}