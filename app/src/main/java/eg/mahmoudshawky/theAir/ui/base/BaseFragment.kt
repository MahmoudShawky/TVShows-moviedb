package eg.mahmoudshawky.theAir.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import eg.mahmoudshawky.theAir.R
import eg.mahmoudshawky.theAir.utils.Status
import eg.mahmoudshawky.theAir.utils.extensions.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class BaseFragment<out ViewModelType : BaseViewModel>(clazz: KClass<ViewModelType>) :
    Fragment() {

    @get:LayoutRes
    abstract val resId: Int

    open val viewModel: ViewModelType by viewModel(clazz)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(resId, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initObservers()
    }

    abstract fun initViews()

    open fun initObservers() {
        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            val state = it ?: return@Observer
            when (state.status) {
                Status.RUNNING -> showLoading()
                Status.SUCCESS -> hideLoading()
                Status.SUCCESSNoData -> onNoDataFound()
                Status.FAILED -> {
                    hideLoading()
                    state.msg?.let { it1 -> showError(it1) }
                    showStopperError(state.msg ?: getString(R.string.unknown_error))
                }
            }
        })
    }

    fun initTitle(@StringRes resId: Int, isBackEnabled: Boolean = false) {
        initTitle(getString(resId), isBackEnabled)
    }

    fun initTitle(title: String, isBackEnabled: Boolean = false) {
        (activity as AppCompatActivity).apply {
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(isBackEnabled)
                setTitle(title)
            }
        }
    }

    open fun onNoDataFound() {
        showMessage(getString(R.string.no_data_found))
    }

    abstract fun showLoading()

    abstract fun hideLoading()

    open fun showStopperError(@StringRes errorString: Int) {
        showStopperError(getString(errorString))
    }

    open fun showStopperError(errorString: String) {
        requireContext().toast(errorString)
    }

    fun showError(@StringRes errorString: Int) {
        showError(getString(errorString))
    }

    open fun showError(errorString: String) {
        requireContext().toast(errorString)
    }

    fun showMessage(@StringRes errorString: Int) {
        showError(getString(errorString))
    }

    open fun showMessage(errorString: String) {
        requireContext().toast(errorString)
    }

}