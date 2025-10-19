package com.zaneschepke.wireguardautotunnel.ui.screens.tunnels.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zaneschepke.wireguardautotunnel.R
import com.zaneschepke.wireguardautotunnel.domain.state.TunnelState
import com.zaneschepke.wireguardautotunnel.ui.LocalNavController
import com.zaneschepke.wireguardautotunnel.ui.common.button.SurfaceRow
import com.zaneschepke.wireguardautotunnel.ui.common.button.SwitchWithDivider
import com.zaneschepke.wireguardautotunnel.ui.navigation.Route
import com.zaneschepke.wireguardautotunnel.ui.state.SharedAppUiState
import com.zaneschepke.wireguardautotunnel.util.extensions.asColor
import com.zaneschepke.wireguardautotunnel.util.extensions.openWebUrl
import com.zaneschepke.wireguardautotunnel.viewmodel.SharedAppViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TunnelList(
    sharedState: SharedAppUiState,
    modifier: Modifier = Modifier,
    viewModel: SharedAppViewModel,
) {
    val navController = LocalNavController.current
    val context = LocalContext.current

    val lazyListState = rememberLazyListState()

    LazyColumn(
        horizontalAlignment = Alignment.Start,
        modifier =
            modifier
                .pointerInput(Unit) {
                    detectTapGestures {
                        if (sharedState.tunnels.isEmpty()) return@detectTapGestures
                        viewModel.clearSelectedTunnels()
                    }
                }
                .overscroll(rememberOverscrollEffect()),
        state = lazyListState,
        userScrollEnabled = true,
        reverseLayout = false,
        flingBehavior = ScrollableDefaults.flingBehavior(),
    ) {
        if (sharedState.tunnels.isEmpty()) {
            item { GettingStartedLabel(onClick = { context.openWebUrl(it) }) }
        }
        items(sharedState.tunnels, key = { it.id }) { tunnel ->
            val tunnelState =
                remember(sharedState.activeTunnels) {
                    sharedState.activeTunnels[tunnel.id] ?: TunnelState()
                }
            val selected =
                remember(sharedState.selectedTunnels) {
                    sharedState.selectedTunnels.any { it.id == tunnel.id }
                }
            var leadingIconColor by
                remember(
                    tunnelState.status,
                    tunnelState.logHealthState,
                    tunnelState.pingStates,
                    tunnelState.statistics,
                ) {
                    mutableStateOf(tunnelState.health().asColor())
                }

            SurfaceRow(
                leading = {
                    Icon(
                        Icons.Rounded.Circle,
                        contentDescription = stringResource(R.string.tunnel_monitoring),
                        tint = leadingIconColor,
                        modifier = Modifier.size(14.dp),
                    )
                },
                title = tunnel.name,
                onClick = {
                    if (sharedState.selectedTunnels.isNotEmpty()) {
                        viewModel.toggleSelectedTunnel(tunnel.id)
                    } else {
                        navController.push(Route.TunnelOptions(tunnel.id))
                        viewModel.clearSelectedTunnels()
                    }
                },
                selected = selected,
                expandedContent =
                    if (!tunnelState.status.isDown()) {
                        {
                            TunnelStatisticsRow(
                                tunnel,
                                tunnelState,
                                sharedState.isPingEnabled,
                                sharedState.showPingStats,
                            )
                        }
                    } else null,
                onLongClick = { viewModel.toggleSelectedTunnel(tunnel.id) },
                trailing = { modifier ->
                    SwitchWithDivider(
                        checked = tunnelState.status.isUpOrStarting(),
                        onClick = { checked ->
                            if (checked) viewModel.startTunnel(tunnel)
                            else viewModel.stopTunnel(tunnel)
                        },
                        modifier = modifier,
                    )
                },
            )
        }
    }
}
