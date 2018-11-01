package com.xfinity.injection.component

import com.xfinity.features.masterdetail.fragment.ItemListFragment
import com.xfinity.injection.PerFragment
import com.xfinity.injection.module.FragmentModule

import dagger.Subcomponent

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {
    fun inject(itemListFragment: ItemListFragment)
}