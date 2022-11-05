package com.othadd.hngtask1thadd

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.datastore.core.DataStore
import androidx.fragment.app.activityViewModels
import com.othadd.hngtask1thadd.databinding.FragmentHomeBinding
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
const val DARK_THEME = "dark theme"
const val LIGHT_THEME = "light theme"
const val SYSTEM_THEME = "system theme"

class HomeFragment : Fragment() {

    private val sharedViewModel: MyViewModel by activityViewModels {
        MyViewModelFactory(requireActivity().application)
    }

    private lateinit var binding: FragmentHomeBinding
    private lateinit var dialog: ConstraintLayout
    private lateinit var dialogOverlay: View
    private lateinit var backPressedCallback: OnBackPressedCallback
    private lateinit var settingsMenu: LinearLayout
    private lateinit var themeSettingsMenu: LinearLayout
    private lateinit var languageSettingsMenu: LinearLayout
    private lateinit var lightMenuItem: TextView
    private lateinit var darkMenuItem: TextView
    private lateinit var systemThemeMenuItem: TextView
    private lateinit var englishMenuItem: TextView
    private lateinit var frenchMenuItem: TextView
    private lateinit var germanMenuItem: TextView
    private lateinit var menuOverlay: View

    val darkTheme = DARK_THEME
    val lightTheme = LIGHT_THEME
    val systemTheme = SYSTEM_THEME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        backPressedCallback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            sharedViewModel.hideDialog()
        }
        backPressedCallback.isEnabled = false

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.dialogConstraintLayout?.let { dialog = it }
        binding.dialogOverLay?.let { dialogOverlay = it }
        binding.apply {
            settingsMenu = settingsLinearLayout
            themeSettingsMenu= themeSettingsLinearLayout
            languageSettingsMenu = languageSettingsLinearLayout
            lightMenuItem = lightMenuItemTextView
            darkMenuItem = darkMenuItemTextView
            systemThemeMenuItem = themeSystemMenuItemTextView
            englishMenuItem = englishMenuItemTextView
            frenchMenuItem = frenchMenuItemTextView
            germanMenuItem = germanMenuItemTextView
            menuOverlay = menuOverLay
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            homeFragment = this@HomeFragment
            lightTheme = LIGHT_THEME
            darkTheme = DARK_THEME
            systemTheme = SYSTEM_THEME
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.dialog.observe(viewLifecycleOwner){
            updateDialogState(it)
        }

        sharedViewModel.themeMode.observe(viewLifecycleOwner){
            updateMenuTheme(it)
        }
    }

    fun changeTheme(update: String){
        hideAllMenus()
        menuOverlay.visibility = View.GONE
        sharedViewModel.updateThemeMode(update)
    }

    fun showMenu(){
        hideAllMenus()
        settingsMenu.visibility = View.VISIBLE
        menuOverlay.visibility = View.VISIBLE
    }

    fun showThemeMenu(){
        hideAllMenus()
        themeSettingsMenu.visibility = View.VISIBLE
    }

    fun showLanguageMenu(){
        hideAllMenus()
        languageSettingsMenu.visibility = View.VISIBLE
    }

    private fun hideAllMenus(){
        settingsMenu.visibility = View.GONE
        themeSettingsMenu.visibility = View.GONE
        languageSettingsMenu.visibility = View.GONE
    }

    fun hideMenusAndOverlay(){
        hideAllMenus()
        menuOverlay.visibility = View.GONE
    }

    private fun updateMenuTheme(themeUpdate: String) {
        when(themeUpdate){
            DARK_THEME -> {
                makeAllThemeMenuItemVisible()
                darkMenuItem.visibility = View.GONE
            }

            LIGHT_THEME -> {
                makeAllThemeMenuItemVisible()
                lightMenuItem.visibility = View.GONE
            }

            SYSTEM_THEME -> {
                makeAllThemeMenuItemVisible()
                systemThemeMenuItem.visibility = View.GONE
            }
        }
    }

    private fun makeAllThemeMenuItemVisible() {
        lightMenuItem.visibility = View.VISIBLE
        darkMenuItem.visibility = View.VISIBLE
        systemThemeMenuItem.visibility = View.VISIBLE
    }

    private fun updateDialogState(dialog: MyViewModel.Dialog) {

        if (appIsInDoubleSegmentMode()){
            return
        }

        if (dialog is MyViewModel.NoDialog){
            hideDialog()
            return
        }

        showDialog(dialog)
    }

    private fun showDialog(dialog: MyViewModel.Dialog) {
        dialog as MyViewModel.RealDialog
        binding.apply {
            dialogHeadImageImageView?.setImageResource(dialog.headerImageRsrcId)
            dialogHeadTextTextView?.text = getString(dialog.headerTextRsrcId)
            dialogBodyTextView?.text = getString(dialog.bodyRscrcId)
        }

        animateShowDialog()
    }

    private fun animateShowDialog() {
        val parent = dialog.parent as ViewGroup

        dialogOverlay.visibility = View.VISIBLE
        backPressedCallback.isEnabled = true

        val dialogHeight = binding.dialogConstraintLayout?.height ?: dialog.height

        val movePropertyValueHolder = PropertyValuesHolder.ofFloat(
            View.TRANSLATION_Y,
            0f,
            -(parent.height / 2 + dialogHeight / 2).toFloat()
        )
        val transparencyValueHolder = PropertyValuesHolder.ofFloat(View.ALPHA, 1.0f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(
            dialog,
            movePropertyValueHolder,
            transparencyValueHolder
        )
        animator.duration = 500
        animator.interpolator = OvershootInterpolator()
        animator.start()
    }

    private fun hideDialog() {
        dialogOverlay.visibility = View.GONE
        backPressedCallback.isEnabled = false

        val movePropertyValueHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f)
        val transparencyValueHolder = PropertyValuesHolder.ofFloat(View.ALPHA, 0.0f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(
            dialog,
            movePropertyValueHolder,
            transparencyValueHolder
        )
        animator.start()
    }

    private fun appIsInDoubleSegmentMode(): Boolean{
        return binding.detailScrollView != null
    }
}