package com.zaneschepke.wireguardautotunnel.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monitoring_settings")
data class MonitoringSettings(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "is_ping_enabled", defaultValue = "0") val isPingEnabled: Boolean = false,
    @ColumnInfo(name = "is_ping_monitoring_enabled", defaultValue = "1")
    val isPingMonitoringEnabled: Boolean = true,
    @ColumnInfo(name = "tunnel_ping_interval_sec", defaultValue = "30")
    val tunnelPingIntervalSeconds: Int = 30,
    @ColumnInfo(name = "tunnel_ping_attempts", defaultValue = "3") val tunnelPingAttempts: Int = 3,
    @ColumnInfo(name = "tunnel_ping_timeout_sec") val tunnelPingTimeoutSeconds: Int? = null,
    @ColumnInfo(name = "show_detailed_ping_stats", defaultValue = "0")
    val showDetailedPingStats: Boolean = false,
    @ColumnInfo(name = "is_local_logs_enabled", defaultValue = "0")
    val isLocalLogsEnabled: Boolean = false,
)
