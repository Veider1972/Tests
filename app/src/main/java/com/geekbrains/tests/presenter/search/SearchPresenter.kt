package com.geekbrains.tests.presenter.search

import com.geekbrains.tests.model.SearchResponse
import com.geekbrains.tests.presenter.RepositoryContract
import com.geekbrains.tests.repository.RepositoryCallback
import com.geekbrains.tests.view.search.MainActivity
import com.geekbrains.tests.view.search.ViewSearchContract
import retrofit2.Response

/**
 * В архитектуре MVP все запросы на получение данных адресуются в Репозиторий.
 * Запросы могут проходить через Interactor или UseCase, использовать источники
 * данных (DataSource), но суть от этого не меняется.
 * Непосредственно Презентер отвечает за управление потоками запросов и ответов,
 * выступая в роли регулировщика движения на перекрестке.
 */

internal class SearchPresenter internal constructor(
    private val viewContract: ViewSearchContract,
    private val repository: RepositoryContract
) : PresenterSearchContract, RepositoryCallback {

    private var activity: MainActivity? = null

    override fun searchGitHub(searchQuery: String) {
        viewContract.displayLoading(true)
        repository.searchGithub(searchQuery, this)
    }

    override fun onAttach(activity: MainActivity) {
        this.activity = activity
    }

    override fun onDetach() {
        this.activity = null
    }

    fun isLive() = activity != null

    override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
        if (isLive()){
            viewContract.displayLoading(false)
            if (response != null && response.isSuccessful) {
                val searchResponse = response.body()
                val searchResults = searchResponse?.searchResults
                val totalCount = searchResponse?.totalCount
                if (searchResults != null && totalCount != null) {
                    viewContract.displaySearchResults(
                        searchResults,
                        totalCount
                    )
                } else {
                    viewContract.displayError("Search results or total count are null")
                }
            } else {
                viewContract.displayError("Response is null or unsuccessful")
            }
        }
    }

    override fun handleGitHubError() {
        if (isLive()){
            viewContract.displayLoading(false)
            viewContract.displayError()
        }
    }
}
