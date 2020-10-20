package eg.mahmoudshawky.theAir.ui.latestTvShows

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import eg.mahmoudshawky.theAir.R
import eg.mahmoudshawky.theAir.data.models.entity.TvShow
import eg.mahmoudshawky.theAir.ui.adapters.OnTvClickListener
import eg.mahmoudshawky.theAir.ui.adapters.TvShowsAdapter
import eg.mahmoudshawky.theAir.ui.base.BaseFragment
import eg.mahmoudshawky.theAir.utils.FetchType
import kotlinx.android.synthetic.main.base_states_view.*
import kotlinx.android.synthetic.main.fragment_latest_tv_shows.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LatestTvShowsFragment : BaseFragment<LatestTvShowsViewModel>(LatestTvShowsViewModel::class),
    OnTvClickListener {

    private val safeArgs: LatestTvShowsFragmentArgs by navArgs()

    override val viewModel: LatestTvShowsViewModel by viewModel {
        parametersOf(safeArgs.tvShowId, safeArgs.fetchType)
    }

    override val resId: Int
        get() = R.layout.fragment_latest_tv_shows

    private val tvShowsAdapter: TvShowsAdapter by lazy {
        TvShowsAdapter()
    }

    override fun initViews() {
        initTitleBar()
        tvShowsAdapter.onTvClickListener = this
        rvTvList.adapter = tvShowsAdapter
        btnRetry.setOnClickListener {
            viewModel.updateFetchType(safeArgs.fetchType)
        }
    }

    private fun initTitleBar() {
        when (safeArgs.fetchType) {
            FetchType.LATEST -> {
                initTitle(R.string.latest_tv_shows)
            }
            FetchType.RECOMMENDED -> {
                initTitle(R.string.recommended_tv_shows, true)
            }
            FetchType.SIMILAR -> {
                initTitle(R.string.similar_tv_shows, true)
            }
        }
    }

    override fun initObservers() {
        super.initObservers()

        viewModel.tvShowsLiveData.observe(viewLifecycleOwner, {
            if(it.isNullOrEmpty()) onNoDataFound()
            else tvShowsAdapter.updateList(it)
        })

        viewModel.updateFetchType(safeArgs.fetchType)
    }

    override fun showLoading() {
        placeHolderLayout.visibility = View.VISIBLE
        centerLoading.visibility = View.VISIBLE
        rvTvList.visibility = View.GONE
        errorLayout.visibility = View.GONE
    }

    override fun hideLoading() {
        placeHolderLayout.visibility = View.GONE
        rvTvList.visibility = View.VISIBLE
    }

    override fun onNoDataFound() {
        showStopperError(getString(R.string.no_data_found))
    }

    override fun showStopperError(errorString: String) {
        placeHolderLayout.visibility = View.VISIBLE
        centerLoading.visibility = View.GONE
        rvTvList.visibility = View.GONE
        errorLayout.visibility = View.VISIBLE
        tvErrorMsg.text = errorString
    }

    override fun onItemClicked(item: TvShow) {
        val action =
            LatestTvShowsFragmentDirections.actionLatestTvShowsFragmentToTvShowDetailsFragment(item)
        findNavController().navigate(action)
    }

    override fun onFavClicked(item: TvShow) {
        showMessage(item.name)
    }

}