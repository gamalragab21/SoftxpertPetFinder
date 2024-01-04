package net.xpert.petfinder.ui.fragments.details

import android.content.Intent
import android.net.Uri
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import net.xpert.petfinder.android.base.BaseFragment
import net.xpert.petfinder.android.extension.PetFinderColor
import net.xpert.petfinder.android.extension.PetFinderDrawable
import net.xpert.petfinder.android.extension.PetFinderString
import net.xpert.petfinder.android.extension.onBackClicked
import net.xpert.petfinder.android.extension.showSnackBar
import net.xpert.petfinder.android.viewModel.CurrentAction
import net.xpert.petfinder.databinding.FragmentPetDetailsScreenBinding

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
        Glide.with(requireContext()).load(petItem.getSmallPhotoUrl())
            .placeholder(PetFinderDrawable.ic_place_holder)
            .into(petImg)
        petNameValue.text = petItem.name.ifEmpty { requireContext().getString(PetFinderString.na) }
        petGenderValue.text =
            petItem.gender.ifEmpty { requireContext().getString(PetFinderString.na) }
        petTypeValue.text = petItem.type.ifEmpty { requireContext().getString(PetFinderString.na) }
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