package com.zaneschepke.wireguardautotunnel.core.notification

import com.zaneschepke.wireguardautotunnel.R
import com.zaneschepke.wireguardautotunnel.WireGuardAutoTunnel
import com.zaneschepke.wireguardautotunnel.core.tunnel.TunnelManager
import com.zaneschepke.wireguardautotunnel.domain.events.BackendCoreException
import com.zaneschepke.wireguardautotunnel.util.StringValue
import jakarta.inject.Inject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotificationMonitor
@Inject
constructor(
    private val tunnelManager: TunnelManager,
    private val notificationManager: NotificationManager,
) {
    suspend fun handleApplicationNotifications() = coroutineScope {
        launch { handleTunnelErrors() }
        launch { handleTunnelMessages() }
    }

    private suspend fun handleTunnelErrors() =
        tunnelManager.errorEvents.collectLatest { (tunName, error) ->
            if (!WireGuardAutoTunnel.uiActive.value) {
                val notification =
                    notificationManager.createNotification(
                        WireGuardNotification.NotificationChannels.VPN,
                        title = StringValue.DynamicString(tunName),
                        description =
                            when (error) {
                                is BackendCoreException.BounceFailed -> error.toStringValue()
                                else ->
                                    StringValue.StringResource(
                                        R.string.tunnel_error_template,
                                        error.toStringRes(),
                                    )
                            },
                        groupKey = NotificationManager.VPN_GROUP_KEY,
                    )
                notificationManager.show(
                    NotificationManager.TUNNEL_ERROR_NOTIFICATION_ID,
                    notification,
                )
            }
        }

    private suspend fun handleTunnelMessages() =
        tunnelManager.messageEvents.collectLatest { (tunName, message) ->
            if (!WireGuardAutoTunnel.uiActive.value) {
                val notification =
                    notificationManager.createNotification(
                        WireGuardNotification.NotificationChannels.VPN,
                        title = StringValue.DynamicString(tunName),
                        description = message.toStringValue(),
                        groupKey = NotificationManager.VPN_GROUP_KEY,
                    )
                notificationManager.show(
                    NotificationManager.TUNNEL_MESSAGES_NOTIFICATION_ID,
                    notification,
                )
            }
        }
}
