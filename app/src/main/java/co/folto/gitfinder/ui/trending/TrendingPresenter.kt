package co.folto.gitfinder.ui.trending

import co.folto.gitfinder.data.RepoRepository
import co.folto.gitfinder.data.model.Repo
import co.folto.gitfinder.util.start
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

/**
 * Created by Daniel on 5/23/2017 for GitFInder project.
 */
class TrendingPresenter(
        val repoRepository: RepoRepository,
        val view: TrendingContract.View
): TrendingContract.Presenter {

    init {
        view.attachPresenter(this)
    }

    private var composite = CompositeDisposable()

    override fun subscribe() = loadRepos()

    override fun unsubscribe() = composite.dispose()

    override fun loadRepos() {
        view.setLoading(true)
        composite.clear()
        val request = getRepo(1)
            .subscribeBy (
                onNext = {
                    if(it.isEmpty())
                        view.showNoRepo(false)
                    else
                        view.showRepos(it)
                    view.setLoading(false)
                },
                onError = {
                    Timber.e(it)
                    view.showError("Unable to fetch data from github")
                    view.showNoRepo(true)
                    view.setLoading(false)
                }
            )
        composite.add(request)
        //composite.add(repoRepository.clearFavorite().start().subscribe())
    }

    override fun loadMoreRepos(page: Int) {
        val request = getRepo(page)
            .subscribeBy (
                onNext = {
                    view.showMoreRepo(it)
                },
                onError = {
                    view.showError("Unable to fetch more data from github")
                },
                onComplete = {  }
            )
        composite.add(request)
    }

    fun getRepo(page: Int): Flowable<List<Repo>>
            = repoRepository.getTrending(page)
                .start()

}