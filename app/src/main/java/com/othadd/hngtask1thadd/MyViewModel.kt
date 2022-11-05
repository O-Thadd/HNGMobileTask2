package com.othadd.hngtask1thadd

import android.app.Application
import androidx.lifecycle.*

const val NO_UPDATE = "no update"

class MyViewModel(application: Application): AndroidViewModel(application) {

    private val settingsStore = SettingsStore(getApplication())

    private var _dialog: MutableLiveData<Dialog> = MutableLiveData()
    val dialog: LiveData<Dialog> get() = _dialog

    val themeMode: LiveData<String> = settingsStore.themeModeFlow().asLiveData()

    private var _language: MutableLiveData<String> = MutableLiveData()
    val language: LiveData<String> get() = _language

    private val aboutMeDialog = RealDialog(R.drawable.aboutme_white, R.string.about_me, R.string.aboutMe_body)
    private val socialsDialog = RealDialog(R.drawable.socials_white, R.string.socials, R.string.socials_body)
    private val myOffersDialog = RealDialog(R.drawable.tools_white, R.string.my_offers, R.string.myOffers_body)
    private val noDialog = NoDialog()

    fun showDialog(type: Int){
        when(type){
            1 -> _dialog.value = aboutMeDialog
            2 -> _dialog.value = socialsDialog
            3 -> _dialog.value = myOffersDialog
        }
    }

    fun hideDialog(){
        _dialog.value = noDialog
    }

    fun updateThemeMode(update: String){
        settingsStore.updateThemeMode(update)
    }

    fun changeLanguage(languageTag: String){
        _language.value = languageTag
    }

    open class Dialog
    class RealDialog(val headerImageRsrcId: Int, val headerTextRsrcId: Int, val bodyRscrcId: Int): Dialog()
    class NoDialog: Dialog()

    init {
        _dialog.value = noDialog
        _language.value = NO_UPDATE
    }
}

class MyViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}