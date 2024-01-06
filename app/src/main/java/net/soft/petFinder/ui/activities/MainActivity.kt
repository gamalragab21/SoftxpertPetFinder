package net.soft.petFinder.ui.activities

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import net.soft.petFinder.R
import net.soft.petFinder.android.base.BaseActivity
import net.soft.petFinder.android.extension.PetFinderId
import net.soft.petFinder.android.extension.hideStatusBars
import net.soft.petFinder.android.extension.onBackClicked
import net.soft.petFinder.android.extension.show
import net.soft.petFinder.android.extension.showStatusBars
import net.soft.petFinder.android.viewModel.CurrentAction
import net.soft.petFinder.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(),
    NavController.OnDestinationChangedListener {

    private lateinit var navController: NavController

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onActivityReady(savedInstanceState: Bundle?) {

        onBackClicked {

        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        val navGraph =
            navHostFragment.navController.navInflater.inflate(R.navigation.main_nav_graph)
        navHostFragment.navController.graph = navGraph
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.fragment_container_view).navigateUp()

    override fun onLoading(loading: Boolean) {
        super.onLoading(loading)
        binding.includeLoading.root.show(loading)
    }

    override fun onDestinationChanged(
        controller: NavController, destination: NavDestination, arguments: Bundle?
    ) {
        when (destination.id) {
            PetFinderId.entryPointFragment -> hideStatusBars()
            else -> showStatusBars()
        }
    }

    override fun onDestroy() {
        navController.removeOnDestinationChangedListener(this)
        val fragmentList: List<Fragment> = supportFragmentManager.fragments
        for (f in fragmentList) {
            navController.popBackStack(f.id, true)
        }
        super.onDestroy()
    }

    override fun viewInit() {}
    override fun onRetryCurrentAction(currentAction: CurrentAction?, message: String) {}
}