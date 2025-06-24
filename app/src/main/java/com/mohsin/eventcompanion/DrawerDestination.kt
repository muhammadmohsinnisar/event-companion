package com.mohsin.eventcompanion

sealed class DrawerDestination(val route: String, val label: String) {
    object EventList : DrawerDestination("event_list", "Events")
    object Scanner : DrawerDestination("scanner", "Scan QR Code")
    object Favorites : DrawerDestination("favorites", "Favorites")
    object About : DrawerDestination("about", "About")

    companion object {
        val all = listOf(EventList, Scanner, Favorites, About)
    }
}
