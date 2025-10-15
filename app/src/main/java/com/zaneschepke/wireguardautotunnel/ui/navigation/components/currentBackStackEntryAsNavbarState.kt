package com.zaneschepke.wireguardautotunnel.ui.navigation.components

import android.os.Build
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import com.zaneschepke.wireguardautotunnel.R
import com.zaneschepke.wireguardautotunnel.ui.navigation.NavController
import com.zaneschepke.wireguardautotunnel.ui.navigation.Route
import com.zaneschepke.wireguardautotunnel.ui.navigation.Route.*
import com.zaneschepke.wireguardautotunnel.ui.navigation.TunnelNetwork
import com.zaneschepke.wireguardautotunnel.ui.sideeffect.LocalSideEffect
import com.zaneschepke.wireguardautotunnel.ui.state.NavbarState
import com.zaneschepke.wireguardautotunnel.ui.state.SharedAppUiState
import com.zaneschepke.wireguardautotunnel.viewmodel.SharedAppViewModel

@Composable
fun currentRouteAsNavbarState(
    sharedState: SharedAppUiState,
    sharedViewModel: SharedAppViewModel,
    route: Route?,
    navController: NavController,
): State<NavbarState> {
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    return remember(route, sharedState) {
        derivedStateOf {
            when (route) {
                AdvancedAutoTunnel ->
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        showBottomItems = true,
                        topTitle = context.getString(R.string.advanced_settings),
                    )
                Appearance ->
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        showBottomItems = true,
                        topTitle = context.getString(R.string.appearance),
                    )
                AutoTunnel ->
                    NavbarState(
                        showBottomItems = true,
                        topTitle =
                            if (!sharedState.isLocationDisclosureShown) null
                            else {
                                context.getString(R.string.auto_tunnel)
                            },
                    )
                Display ->
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        showBottomItems = true,
                        topTitle = context.getString(R.string.display_theme),
                    )
                Dns ->
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        showBottomItems = true,
                        topTitle = context.getString(R.string.dns_settings),
                    )
                Language ->
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        showBottomItems = true,
                        topTitle = context.getString(R.string.language),
                    )
                License ->
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        showBottomItems = true,
                        topTitle = context.getString(R.string.licenses),
                    )
                LocationDisclosure -> NavbarState(showBottomItems = true)
                Lock -> NavbarState(showBottomItems = false)
                Logs ->
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        showBottomItems = true,
                        topTitle = context.getString(R.string.logs),
                        topTrailing = {
                            IconButton(
                                onClick = {
                                    sharedViewModel.postSideEffect(
                                        LocalSideEffect.Sheet.LoggerActions
                                    )
                                }
                            ) {
                                Icon(Icons.Rounded.Menu, stringResource(R.string.quick_actions))
                            }
                        },
                    )
                ProxySettings ->
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        showBottomItems = true,
                        topTitle = context.getString(R.string.proxy_settings),
                        topTrailing = {
                            IconButton(
                                onClick = {
                                    keyboardController?.hide()
                                    sharedViewModel.postSideEffect(LocalSideEffect.SaveChanges)
                                }
                            ) {
                                Icon(Icons.Rounded.Save, stringResource(R.string.save))
                            }
                        },
                    )
                Settings ->
                    NavbarState(
                        showBottomItems = true,
                        topTitle = context.getString(R.string.settings),
                    )
                Sort ->
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        showBottomItems = true,
                        topTitle = context.getString(R.string.sort),
                        topTrailing = {
                            Row {
                                IconButton(
                                    onClick = {
                                        sharedViewModel.postSideEffect(LocalSideEffect.Sort)
                                    }
                                ) {
                                    Icon(Icons.Rounded.SortByAlpha, stringResource(R.string.sort))
                                }
                                IconButton(
                                    onClick = {
                                        sharedViewModel.postSideEffect(LocalSideEffect.SaveChanges)
                                    }
                                ) {
                                    Icon(Icons.Rounded.Save, stringResource(R.string.save))
                                }
                            }
                        },
                    )
                is Config -> {
                    val tunnelName = sharedState.tunnelNames[route.id]
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        showBottomItems = true,
                        topTitle = tunnelName ?: context.getString(R.string.new_tunnel),
                        topTrailing = {
                            IconButton(
                                onClick = {
                                    keyboardController?.hide()
                                    sharedViewModel.postSideEffect(LocalSideEffect.SaveChanges)
                                }
                            ) {
                                Icon(Icons.Rounded.Save, stringResource(R.string.save))
                            }
                        },
                    )
                }
                is SplitTunnel -> {
                    val tunnelName = sharedState.tunnelNames[route.id]
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        topTitle = tunnelName ?: "",
                        topTrailing = {
                            IconButton(
                                onClick = {
                                    sharedViewModel.postSideEffect(LocalSideEffect.SaveChanges)
                                }
                            ) {
                                Icon(Icons.Rounded.Save, stringResource(R.string.save))
                            }
                        },
                        showBottomItems = true,
                    )
                }
                is SplitTunnelGlobal -> {
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        topTitle = context.getString(R.string.splt_tunneling),
                        topTrailing = {
                            IconButton(
                                onClick = {
                                    sharedViewModel.postSideEffect(LocalSideEffect.SaveChanges)
                                }
                            ) {
                                Icon(Icons.Rounded.Save, stringResource(R.string.save))
                            }
                        },
                        showBottomItems = true,
                    )
                }
                is ConfigGlobal -> {
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        showBottomItems = true,
                        topTitle = context.getString(R.string.configuration),
                        topTrailing = {
                            IconButton(
                                onClick = {
                                    sharedViewModel.postSideEffect(LocalSideEffect.SaveChanges)
                                }
                            ) {
                                Icon(Icons.Rounded.Save, stringResource(R.string.save))
                            }
                        },
                    )
                }
                Support ->
                    NavbarState(
                        topTitle = context.getString(R.string.support),
                        showBottomItems = true,
                    )
                AndroidIntegrations ->
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        topTitle = context.getString(R.string.android_integrations),
                        showBottomItems = true,
                    )
                TunnelMonitoring ->
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        topTitle = context.getString(R.string.tunnel_monitoring),
                        showBottomItems = true,
                    )
                is TunnelOptions -> {
                    val tunnelName = sharedState.tunnelNames[route.id]
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        showBottomItems = true,
                        topTitle = tunnelName ?: "",
                        topTrailing = {
                            Row {
                                IconButton(
                                    onClick = {
                                        sharedViewModel.postSideEffect(LocalSideEffect.Modal.QR)
                                    }
                                ) {
                                    Icon(Icons.Rounded.QrCode2, stringResource(R.string.show_qr))
                                }
                                IconButton(onClick = { navController.push(Config(route.id)) }) {
                                    Icon(Icons.Rounded.Edit, stringResource(R.string.edit_tunnel))
                                }
                            }
                        },
                    )
                }
                Tunnels -> {
                    NavbarState(
                        topTitle = context.getString(R.string.tunnels),
                        topTrailing = {
                            when (sharedState.selectedTunnelCount) {
                                0 ->
                                    Row {
                                        IconButton(onClick = { navController.push(Sort) }) {
                                            Icon(
                                                Icons.AutoMirrored.Rounded.Sort,
                                                stringResource(R.string.sort),
                                            )
                                        }
                                        IconButton(
                                            onClick = {
                                                sharedViewModel.postSideEffect(
                                                    LocalSideEffect.Sheet.ImportTunnels
                                                )
                                            }
                                        ) {
                                            Icon(
                                                Icons.Rounded.Add,
                                                stringResource(R.string.add_tunnel),
                                            )
                                        }
                                    }
                                else ->
                                    Row {
                                        IconButton(
                                            onClick = {
                                                sharedViewModel.postSideEffect(
                                                    LocalSideEffect.SelectedTunnels.SelectAll
                                                )
                                            }
                                        ) {
                                            Icon(
                                                Icons.Rounded.SelectAll,
                                                stringResource(R.string.select_all),
                                            )
                                        }
                                        // due to permissions, and SAF issues on TV, not support
                                        // less than Android
                                        // 10
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                            IconButton(
                                                onClick = {
                                                    sharedViewModel.postSideEffect(
                                                        LocalSideEffect.Sheet.ExportTunnels
                                                    )
                                                }
                                            ) {
                                                Icon(
                                                    Icons.Rounded.Download,
                                                    stringResource(R.string.download),
                                                )
                                            }
                                        }

                                        if (sharedState.selectedTunnelCount == 1) {
                                            IconButton(
                                                onClick = {
                                                    sharedViewModel.postSideEffect(
                                                        LocalSideEffect.SelectedTunnels.Copy
                                                    )
                                                }
                                            ) {
                                                Icon(
                                                    Icons.Rounded.CopyAll,
                                                    stringResource(R.string.copy),
                                                )
                                            }
                                        }
                                        IconButton(
                                            onClick = {
                                                sharedViewModel.postSideEffect(
                                                    LocalSideEffect.Modal.DeleteTunnels
                                                )
                                            }
                                        ) {
                                            Icon(
                                                Icons.Rounded.Delete,
                                                stringResource(R.string.delete_tunnel),
                                            )
                                        }
                                    }
                            }
                        },
                        showBottomItems = true,
                    )
                }
                WifiDetectionMethod ->
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        topTitle = context.getString(R.string.wifi_detection_method),
                        showBottomItems = true,
                    )
                Donate -> {
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        topTitle = context.getString(R.string.donate_title),
                        showBottomItems = true,
                    )
                }
                Addresses -> {
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        topTitle = context.getString(R.string.addresses),
                        showBottomItems = true,
                    )
                }
                is TunnelGlobals -> {
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        topTitle = context.getString(R.string.global_overrides),
                        showBottomItems = true,
                    )
                }
                is WifiPreferences -> {
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        topTitle = context.getString(R.string.wifi_settings),
                        showBottomItems = true,
                    )
                }
                is PreferredTunnel -> {
                    val title =
                        when (route.tunnelNetwork) {
                            TunnelNetwork.MOBILE_DATA,
                            TunnelNetwork.ETHERNET -> context.getString(R.string.preferred_tunnel)
                            TunnelNetwork.WIFI -> context.getString(R.string.tunnel_mapping)
                        }
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        topTitle = title,
                        showBottomItems = true,
                    )
                }
                PingTarget ->
                    NavbarState(
                        topLeading = {
                            IconButton(onClick = { navController.pop() }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    stringResource(R.string.back),
                                )
                            }
                        },
                        topTitle = context.getString(R.string.ping_target),
                        showBottomItems = true,
                    )
                null -> NavbarState()
            }
        }
    }
}
