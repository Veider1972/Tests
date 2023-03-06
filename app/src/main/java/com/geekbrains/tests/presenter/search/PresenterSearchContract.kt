package com.geekbrains.tests.presenter.search

import com.geekbrains.tests.presenter.PresenterContract
import com.geekbrains.tests.view.search.MainActivity

internal interface PresenterSearchContract : PresenterContract {
    fun searchGitHub(searchQuery: String)
    fun onAttach(activity: MainActivity)
    fun onDetach()
}
