package com.example.androidconcertapp.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/** The ConcertNavigationRail component.
 *
 * @param goHome The function to navigate to the home screen.
 * @param saveConcertsToApi The function to save the concerts to the Api.
 * @param onLogout The function to logout.
 */
@Composable
fun NavigationDrawerContent(
    goHome: () -> Unit,
    onLogout: (Context) -> Unit,
    saveConcertsToApi: () -> Unit,
    modifier: Modifier
) {
    val context = LocalContext.current

    Column(modifier = modifier) {
        CustomNavDrawerItem(
            name = "Log Out",
            icontype = Icons.Filled.ExitToApp,
            onClick = { onLogout(context) }
        )
        CustomNavDrawerItem(
            name = "Home",
            icontype = Icons.Filled.Home,
            onClick = goHome
        )
        CustomNavDrawerItem(
            name = "Save All",
            icontype = Icons.Filled.Save,
            onClick = saveConcertsToApi
        )
    }
}

/** The CustomNavDrawerItem component used in the NavigationDrawerContent.
 *
 * @param name The name of the item.
 * @param icontype The icon of the item.
 * @param onClick The function to execute when the item is clicked.
 */
@Composable
fun CustomNavDrawerItem(name: String, icontype: ImageVector, onClick: () -> Unit) {
    NavigationDrawerItem(
        modifier = Modifier.padding(vertical = 4.dp),
        selected = false,
        label = { Text(text = name) },
        icon = { Icon(icontype, contentDescription = name) },
        onClick = onClick,
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent
        )
    )
}
