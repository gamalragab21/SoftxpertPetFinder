package net.soft.petFinder.ui.fragments.details

import android.content.Intent
import android.net.Uri
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import net.soft.petFinder.android.base.BaseFragment
import net.soft.petFinder.android.extension.PetFinderColor
import net.soft.petFinder.android.extension.PetFinderDrawable
import net.soft.petFinder.android.extension.PetFinderString
import net.soft.petFinder.android.extension.onBackClicked
import net.soft.petFinder.android.extension.showSnackBar
import net.soft.petFinder.android.viewModel.CurrentAction
import net.soft.petFinder.databinding.FragmentPetDetailsScreenBinding

@AndroidEntryPoint
class PetDetailsFragment : BaseFragment<FragmentPetDetailsScreenBinding>() {
    private val args: PetDetailsFragmentArgs by navArgs()
    private val petItem by lazy { args.animal }
    override fun onFragmentReady() {
        setToolBarConfigs(
            PetFinderString.pet_details,
            backgroundColor = PetFinderColor.colorPrimary,
            titleColor = PetFinderColor.white, showBackIcon = true
        )
        loadPetDetails()
        setActions()
    }

    private fun setActions() {
        binding.websiteBtn.setOnClickListener {
            openWebsite(petItem.url)
        }
        onBackClicked { findNavController().popBackStack() }
    }


    private fun loadPetDetails() = with(binding) {
        Glide.with(requireContext()).load(petItem.photos.medium)
            .placeholder(PetFinderDrawable.ic_place_holder)
            .into(petImg)
        petNameValue.text = petItem.name.ifEmpty { requireContext().getString(PetFinderString.na) }
        petGenderValue.text =
            petItem.gender.ifEmpty { requireContext().getString(PetFinderString.na) }
        petTypeValue.text = petItem.type.ifEmpty { requireContext().getString(PetFinderString.na) }
        petSizeValue.text = petItem.size.ifEmpty { requireContext().getString(PetFinderString.na) }
        petAddressValue.text = petItem.address.ifEmpty { requireContext().getString(PetFinderString.na) }
    }

    private fun openWebsite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
    override fun subscribeToObservables() {}

    override fun onRetryCurrentAction(currentAction: CurrentAction?, message: String) {
        showSnackBar(message) { }
    }
}