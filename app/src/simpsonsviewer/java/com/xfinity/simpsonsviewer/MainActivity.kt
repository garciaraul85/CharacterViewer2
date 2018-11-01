package com.xfinity.simpsonsviewer

import android.app.SearchManager.QUERY
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.xfinity.R
import com.xfinity.data.model.response.RelatedTopic
import com.xfinity.features.masterdetail.fragment.ItemDetailFragment
import com.xfinity.features.masterdetail.fragment.ItemDetailFragment.Companion.ICON
import com.xfinity.features.masterdetail.fragment.ItemDetailFragment.Companion.ITEM
import com.xfinity.features.masterdetail.fragment.ItemListFragment

class MainActivity : AppCompatActivity(), ItemListFragment.OnFragmentInteractionListener {
    private var itemListFragment: ItemListFragment? = null
    private var tabletSize: Boolean = false
    private var showIcon: Boolean = false
    private var toolbar: Toolbar? = null
    private var myMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            showIcon = savedInstanceState.getBoolean(SHOWICON, false)
        }

        setContentView(R.layout.activity_main)

        tabletSize = resources.getBoolean(R.bool.isTablet)

        toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)

        if (supportActionBar != null) { // hide
            supportActionBar?.title = getString(R.string.app_name)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setHomeButtonEnabled(false)
        }

        toolbar!!.setNavigationOnClickListener { v ->
            // what do you want here
            if (supportActionBar != null) { // hide
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setHomeButtonEnabled(false)
            }
            onBackPressed()
        }

        if (savedInstanceState != null)
            return
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportActionBar != null) {
            supportActionBar?.title = getString(R.string.app_name)
        }
        if (!tabletSize) {
            myMenu!!.findItem(MENU_ITEM_ITEM1).isVisible = true
        }
    }

    override fun onResume() {
        super.onResume()

        val bundle = Bundle()
        bundle.putString(QUERY, getString(R.string.search_query))

        itemListFragment = ItemListFragment()
        itemListFragment!!.retainInstance = true
        itemListFragment!!.arguments = bundle

        itemListFragment!!.showIcon = showIcon

        val fragmentManager = supportFragmentManager

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down,
                R.anim.slide_up, R.anim.slide_down)

        fragmentTransaction.replace(R.id.framelayout_left, itemListFragment!!)

        if (findViewById<View>(R.id.framelayout_right) != null) {
            val itemDetailFragment = ItemDetailFragment()
            itemDetailFragment.retainInstance = true
            fragmentTransaction.replace(R.id.framelayout_right, itemDetailFragment)
        }

        fragmentTransaction.commit()
    }

    override fun onItemSelected(relatedTopic: RelatedTopic) {
        if (supportActionBar != null) { // show
            if (!tabletSize) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeButtonEnabled(true)
                toolbar!!.title = relatedTopic.text
                myMenu!!.findItem(MENU_ITEM_ITEM1).isVisible = false
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setHomeButtonEnabled(false)
            }
        }

        val fragmentManager = supportFragmentManager

        val fragmentTransaction = fragmentManager.beginTransaction()
        if (!tabletSize) {
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
                    R.anim.pop_enter, R.anim.pop_exit)
        }

        var containerViewId = R.id.framelayout_left

        if (findViewById<View>(R.id.framelayout_right) != null)
            containerViewId = R.id.framelayout_right

        val bundle = Bundle()
        if (relatedTopic.text != null) {
            bundle.putString(ITEM, relatedTopic.text)
        }
        if (relatedTopic.icon != null && relatedTopic.icon!!.url != null) {
            bundle.putString(ICON, relatedTopic.icon!!.url)
        }

        val itemDetailFragment = ItemDetailFragment()
        itemDetailFragment.retainInstance = true
        itemDetailFragment.arguments = bundle
        fragmentTransaction.replace(containerViewId, itemDetailFragment)

        if (findViewById<View>(R.id.framelayout_right) == null)
            fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        myMenu = menu
        if (!tabletSize) {
            itemListFragment!!.setIconVisibility(showIcon)
            if (showIcon) {
                menu.add(Menu.NONE, MENU_ITEM_ITEM1, Menu.NONE, getString(R.string.show_text))
            } else {
                menu.add(Menu.NONE, MENU_ITEM_ITEM1, Menu.NONE, getString(R.string.show_images))
            }
        }
        return true
    }

    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        state.putBoolean(SHOWICON, showIcon)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            MENU_ITEM_ITEM1 -> {
                showIcon = !showIcon
                itemListFragment!!.setIconVisibility(showIcon)

                if (showIcon) {
                    item.title = getString(R.string.show_text)
                } else {
                    item.title = getString(R.string.show_images)
                }
                return true
            }
            else -> return false
        }
    }

    companion object {
        private val MENU_ITEM_ITEM1 = 1
        private val SHOWICON = "showIcon"
    }
}