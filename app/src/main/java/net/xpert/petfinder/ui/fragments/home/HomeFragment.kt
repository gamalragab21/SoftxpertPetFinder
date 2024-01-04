package net.xpert.petfinder.ui.fragments.home

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import net.xpert.petfinder.android.base.BaseFragment
import net.xpert.petfinder.android.extension.PetFinderColor
import net.xpert.petfinder.android.extension.PetFinderString
import net.xpert.petfinder.android.extension.init
import net.xpert.petfinder.android.viewModel.CurrentAction
import net.xpert.petfinder.databinding.FragmentHomeBinding
import net.xpert.petfinder.ui.fragments.home.adapters.PetItemAdapter
import net.xpert.petfinder.ui.fragments.home.adapters.PetTypeSelectionAdapter
import net.xpert.petfinder.ui.fragments.home.models.PetType
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    @Inject
    lateinit var petTypeSelectionAdapter: PetTypeSelectionAdapter
    @Inject
    lateinit var petItemAdapter: PetItemAdapter

    override fun onFragmentReady() {
        setToolBarConfigs(
            PetFinderString.pets,
            backgroundColor = PetFinderColor.colorPrimary,
            titleColor = PetFinderColor.white
        )

        initSelectionPetTypesRecyclerView()
        initPetTypeItemsRecyclerView()

        petTypeSelectionAdapter.petTypeItems = PetType.getAllTypes()
        setActions()
    }

    private fun setActions() {
        petTypeSelectionAdapter.setOnItemClickListener {

        }
    }

    override fun subscribeToObservables() {}

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