package co.folto.gitfinder.ui.repodetail

import co.folto.gitfinder.data.model.Repo
import co.folto.gitfinder.ui.base.BasePresenter
import co.folto.gitfinder.ui.base.BaseView

/**
 * Created by Daniel on 6/5/2017 for GitFInder project.
 */
interface DetailContract {
    interface Presenter: BasePresenter {
        fun loadRepo()
        fun openChromeTab()
        fun toogleFavorite()
    }

    interface View: BaseView<Presenter> {
        fun showRepo(repo: Repo)
        fun showMessage(message: String)
        fun showError(message: String)
        fun setLoading(active: Boolean)
        fun activeFavorite(active: Boolean)
        fun openChromeTab(repo: Repo)
    }
}