package co.folto.gitfinder.ui.repodetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.folto.gitfinder.R
import co.folto.gitfinder.data.model.Repo
import co.folto.gitfinder.util.showSnack
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * Created by Daniel on 6/5/2017 for GitFInder project.
 */
class DetailFragment: Fragment(), DetailContract.View {

    lateinit private var presenter: DetailContract.Presenter

    companion object {
        private val DETAIL_REPO_ID = "DETAIL_REPO_ID"
        fun newInstance(repoId: String): DetailFragment {
            val arguments = Bundle()
            arguments.putString(DETAIL_REPO_ID, repoId)
            val detailFragment = DetailFragment()
            detailFragment.arguments = arguments
            return detailFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_detail, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe();
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun attachPresenter(presenter: DetailContract.Presenter) {
        this.presenter = presenter
    }

    override fun setLoading(active: Boolean) {
        if(active)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }

    override fun showRepo(repo: Repo) {
        activity.title = repo.fullName
        textFullname.text = repo.fullName
    }

    override fun showError(message: String) {
        view?.showSnack(message)
        viewError.visibility = View.VISIBLE
        textError.text = getString(R.string.detail_error_message)
    }
}