package eg.mahmoudshawky.theAir.ui.details

import android.text.method.LinkMovementMethod
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import eg.mahmoudshawky.theAir.R
import eg.mahmoudshawky.theAir.ui.adapters.SimpleAdapter
import eg.mahmoudshawky.theAir.ui.adapters.SimpleImageAdapter
import eg.mahmoudshawky.theAir.ui.base.BaseFragment
import eg.mahmoudshawky.theAir.utils.FetchType
import eg.mahmoudshawky.theAir.utils.RemoteConfig
import eg.mahmoudshawky.theAir.utils.Status
import kotlinx.android.synthetic.main.fragment_tv_show_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TvShowDetailsFragment : BaseFragment<TvShowDetailsViewModel>(TvShowDetailsViewModel::class) {

    private val safeArgs: TvShowDetailsFragmentArgs by navArgs()

    override val viewModel: TvShowDetailsViewModel by viewModel {
        parametersOf(safeArgs.tvShow)
    }

    private val networkAdapter: SimpleAdapter by lazy {
        SimpleAdapter()
    }

    private val genresAdapter: SimpleAdapter by lazy {
        SimpleAdapter(R.layout.item_simple_text_border)
    }

    private val castsAdapter: SimpleImageAdapter by lazy {
        SimpleImageAdapter()
    }


    override val resId: Int
        get() = R.layout.fragment_tv_show_details

    override fun initViews() {
        initTitle(safeArgs.tvShow.name, true)
        rvNetwork.adapter = networkAdapter
        rvGenre.adapter = genresAdapter
        rvCasts.adapter = castsAdapter
        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (fromUser) viewModel.rateTvShow(rating * 2)
        }

        tvRecommendations.setOnClickListener {
            val action =
                TvShowDetailsFragmentDirections.actionTvShowDetailsFragmentToLatestTvShowsFragment(
                    safeArgs.tvShow.id,
                    FetchType.RECOMMENDED
                )
            findNavController().navigate(action)
        }

        tvSimilar.setOnClickListener {
            val action =
                TvShowDetailsFragmentDirections.actionTvShowDetailsFragmentToLatestTvShowsFragment(
                    safeArgs.tvShow.id,
                    FetchType.SIMILAR
                )
            findNavController().navigate(action)
        }

    }

    override fun initObservers() {
        super.initObservers()

        viewModel.posterUrlLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) return@Observer
            Glide
                .with(this)
                .load(RemoteConfig.getOriginalUrl(it))
                .thumbnail(Glide.with(this).load(RemoteConfig.getThumbnailsUrl(it)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(ivPoster)
        })

        viewModel.titleLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) return@Observer
            tvTitle.text = it
        })

        viewModel.epiNoLiveData.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            tvEpisodeNumber.text = getString(R.string.episode_number, it)
        })

        viewModel.overviewLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) return@Observer
            tvOverview.text = it
        })

        viewModel.homePageLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) return@Observer
            tvHomePage.run {
                text = getString(R.string.home_page_Link, it)
                movementMethod = LinkMovementMethod.getInstance()
            }

        })

        viewModel.ratingLiveData.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            tvVoteAverage.text = it.toString()
        })

        viewModel.genresLiveData.observe(viewLifecycleOwner, Observer {
            genresAdapter.updateList(it ?: emptyList())
        })

        viewModel.networksLiveData.observe(viewLifecycleOwner, Observer {
            networkAdapter.updateList(it ?: emptyList())
        })

        viewModel.castsLiveData.observe(viewLifecycleOwner, Observer {
            castsAdapter.updateList(it ?: emptyList())
        })

        viewModel.rateNetworkState.observe(viewLifecycleOwner, Observer {
            val state = it ?: return@Observer
            when (state.status) {
                Status.SUCCESSNoData -> {
                }
                Status.RUNNING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    hideLoading()
                    showMessage(R.string.rating_success)
                }
                Status.FAILED -> {
                    hideLoading()
                    showMessage(R.string.rating_failed)
                }
            }
        })
    }

    override fun showLoading() {
        pbLoading.visibility = View.VISIBLE

    }

    override fun hideLoading() {
        pbLoading.visibility = View.GONE
    }


}