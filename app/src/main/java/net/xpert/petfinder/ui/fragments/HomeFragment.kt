package net.xpert.petfinder.ui.fragments

import dagger.hilt.android.AndroidEntryPoint
import net.xpert.petfinder.R
import net.xpert.petfinder.android.base.BaseFragment
import net.xpert.petfinder.android.viewModel.CurrentAction
import net.xpert.petfinder.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun onFragmentReady() {
        setToolBarConfigs(
            R.string.pets,
            backgroundColor = R.color.colorPrimary,
            titleColor = R.color.white
        )
    }

    override fun subscribeToObservables() {}

    override fun onRetryCurrentAction(currentAction: CurrentAction?, message: String) {}
}