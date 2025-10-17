package com.zaneschepke.wireguardautotunnel.ui.state

import com.zaneschepke.wireguardautotunnel.data.model.AppMode
import com.zaneschepke.wireguardautotunnel.domain.model.MonitoringSettings
import com.zaneschepke.wireguardautotunnel.domain.model.TunnelConfig

data class MonitoringUiState(
    val monitoringSettings: MonitoringSettings = MonitoringSettings(),
    val tunnels: List<TunnelConfig> = emptyList(),
    val appMode: AppMode = AppMode.VPN,
    val isLoading: Boolean = true,
)
