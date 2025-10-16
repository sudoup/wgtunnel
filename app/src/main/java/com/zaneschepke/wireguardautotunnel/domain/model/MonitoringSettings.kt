package com.zaneschepke.wireguardautotunnel.domain.model

data class MonitoringSettings(
    val id: Int = 0,
    val isPingEnabled: Boolean = false,
    val isPingMonitoringEnabled: Boolean = true,
    val tunnelPingIntervalSeconds: Int = 30,
    val tunnelPingAttempts: Int = 3,
    val tunnelPingTimeoutSeconds: Int? = null,
    val showDetailedPingStats: Boolean = false,
    val isLocalLogsEnabled: Boolean = false,
)
