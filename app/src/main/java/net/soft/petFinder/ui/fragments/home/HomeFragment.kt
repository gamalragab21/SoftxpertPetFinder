package net.soft.petFinder.ui.fragments.home

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import net.soft.features.getPetTypes.domain.enums.PetType
import net.soft.petFinder.android.base.BaseFragment
import net.soft.petFinder.android.extension.PetFinderColor
import net.soft.petFinder.android.extension.PetFinderString
import net.soft.petFinder.android.extension.init
import net.soft.petFinder.android.extension.navigateSafe
import net.soft.petFinder.android.extension.observe
import net.soft.petFinder.android.extension.show
import net.soft.petFinder.android.extension.showSnackBar
import net.soft.petFinder.android.helpers.components.OnEndless
import net.soft.petFinder.android.viewModel.CurrentAction
import net.soft.petFinder.databinding.FragmentHomeBinding
import net.soft.petFinder.ui.fragments.home.adapters.PetItemAdapter
import net.soft.petFinder.ui.fragments.home.adapters.PetTypeSelectionAdapter
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var petTypeSelectionAdapter: PetTypeSelectionAdapter

    @Inject
    lateinit var petItemAdapter: PetItemAdapter
    private val homeVM: HomeVM by viewModels()
    private var scrollListener: OnEndless? = null

    override fun onFragmentReady() {
        setToolBarConfigs(
            PetFinderString.pets,
            backgroundColor = PetFinderColor.colorPrimary,
            titleColor = PetFinderColor.white
        )
        onSwipeRefreshStatus(true)

        initSelectionPetTypesRecyclerView()
        initPetTypeItemsRecyclerView()

        setActions()
    }

    private fun setActions() {

        petTypeSelectionAdapter.setOnItemClickListener { petType ->
            petItemAdapter.clearData()
            scrollListener?.rest()
            homeVM.getPetByCategoryType(petType)
        }

        petItemAdapter.setOnItemClickListener {
            navigateSafe(HomeFragmentDirections.actionHomeFragmentToPetDetailsFragment(it))
        }
    }

    override fun subscribeToObservables() {
        homeVM.apply {
            observe(viewState) {
                handleUiState(it)
            }
        }
    }

    override fun onRefreshView() {
        homeVM.getPetByCategoryType(petTypeSelectionAdapter.getLasItemClicked())
    }

    private fun handleUiState(state: HomeState) {
        when (state) {
            is HomeState.Failure -> handleHttpsStatusCode(state.exception)

            is HomeState.Loading -> {
                showProgress(state.loading)
                if (!state.loading) stopSwipeRefreshLoading()
            }

            is HomeState.ShowPetData -> petItemAdapter.appendList(state.animals)

            is HomeState.UpdateNoDataView -> updateNoDataView(state.show, state.errorMessage)
        }
    }

    private fun updateNoDataView(show: Boolean = false, error: String? = null) =
        with(binding.emptyViewLayout) {
            emptyView.show(show)
            textEmptyErr.text = error
        }

    private fun initSelectionPetTypesRecyclerView() = binding.petTypeSelectionRecyclerView.apply {
        init(lm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false))
        adapter = petTypeSelectionAdapter.apply {
            petTypeItems = PetType.entries.toList()
        }
    }

    private fun initPetTypeItemsRecyclerView() = binding.petsFinderRecyclerView.apply {
        init(
            lm = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false),
            nestedScroll = true
        )
        adapter = petItemAdapter
        setupPaginationListener()
    }

    private fun setupPaginationListener() = with(binding.petsFinderRecyclerView) {
        scrollListener = object :
            OnEndless((layoutManager as LinearLayoutManager), 1) {
            override fun onLoadMore(currentPage: Int) {
                homeVM.loadCurrentPage(currentPage)
            }
        }
        scrollListener?.let {
            addOnScrollListener(it)
        }
    }

    override fun onDestroyView() {
        scrollListener?.let { binding.petsFinderRecyclerView.removeOnScrollListener(it) }
        super.onDestroyView()
    }

    override fun onRetryCurrentAction(currentAction: CurrentAction?, message: String) {
        showSnackBar(message) { onRefreshView() }
    }
}