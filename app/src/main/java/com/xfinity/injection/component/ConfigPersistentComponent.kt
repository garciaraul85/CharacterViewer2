package com.xfinity.injection.component

import com.xfinity.injection.ConfigPersistent
import com.xfinity.injection.module.ActivityModule
import com.xfinity.injection.module.FragmentModule

import dagger.Component

@ConfigPersistent
@Component(dependencies = arrayOf(AppComponent::class))
interface ConfigPersistentComponent {

    fun activityComponent(activityModule: ActivityModule): ActivityComponent

    fun fragmentComponent(fragmentModule: FragmentModule): FragmentComponent

}
