package net.xpert.petfinder.ui.fragments.home

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import net.xpert.features.getPetTypes.domain.enums.PetType
import net.xpert.petfinder.android.base.BaseFragment
import net.xpert.petfinder.android.extension.PetFinderColor
import net.xpert.petfinder.android.extension.PetFinderString
import net.xpert.petfinder.android.extension.init
import net.xpert.petfinder.android.extension.observe
import net.xpert.petfinder.android.viewModel.CurrentAction
import net.xpert.petfinder.databinding.FragmentHomeBinding
import net.xpert.petfinder.ui.fragments.home.adapters.PetItemAdapter
import net.xpert.petfinder.ui.fragments.home.adapters.PetTypeSelectionAdapter
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var petTypeSelectionAdapter: PetTypeSelectionAdapter

    @Inject
    lateinit var petItemAdapter: PetItemAdapter
    private val homeVM: HomeVM by viewModels()

    override fun onFragmentReady() {
        setToolBarConfigs(
            PetFinderString.pets,
            backgroundColor = PetFinderColor.colorPrimary,
            titleColor = PetFinderColor.white
        )

        initSelectionPetTypesRecyclerView()
        initPetTypeItemsRecyclerView()

        petTypeSelectionAdapter.petTypeItems = PetType.entries.toList()
        setActions()
    }

    private fun setActions() {
        petTypeSelectionAdapter.setOnItemClickListener { petType ->
            homeVM.getPetByCategoryType(petType)
        }
    }

    override fun subscribeToObservables() {
        homeVM.apply {
            observe(viewState) {
                handleUiState(it)
            }
        }
    }

    private fun handleUiState(state: HomeState) {
        when (state) {
            is HomeState.Failure -> handleHttpsStatusCode(state.exception)
            is HomeState.Loading -> showProgress(state.loading)
            is HomeState.ShowPetData -> petItemAdapter.animals = state.animals
        }
    }

    private fun initSelectionPetTypesRecyclerView() = binding.petTypeSelectionRecyclerView.apply {
        init(lm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false))
        adapter = petTypeSelectionAdapter
    }

    private fun initPetTypeItemsRecyclerView() = binding.petsFinderRecyclerView.apply {
        init(lm = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false))
        adapter = petItemAdapter
    }

    override fun onRetryCurrentAction(currentAction: CurrentAction?, message: String) {}
}