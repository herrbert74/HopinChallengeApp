package com.babestudios.hopin.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.babestudios.hopin.R

class HopinNavigation : BaseNavigator(), HopinNavigator {

	override var navController: NavController? = null

	override fun mainToStreamer() {
		navController?.navigateSafe(R.id.action_mainFragment_to_streamerFragment)
	}
}

@Suppress("MaxLineLength")
		/**
		 * https://stackoverflow.com/questions/51060762/java-lang-illegalargumentexception-navigation-destination-xxx-is-unknown-to-thi
		 */
fun NavController.navigateSafe(
		@IdRes resId: Int,
		args: Bundle? = null,
		navOptions: NavOptions? = null,
		navExtras: Navigator.Extras? = null
) {
	val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)
	if (action != null && currentDestination?.id != action.destinationId) {
		navigate(resId, args, navOptions, navExtras)
	}
}
