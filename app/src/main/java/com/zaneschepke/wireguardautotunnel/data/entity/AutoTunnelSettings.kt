package com.zaneschepke.wireguardautotunnel.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zaneschepke.wireguardautotunnel.data.model.WifiDetectionMethod

@Entity(tableName = "auto_tunnel_settings")
data class AutoTunnelSettings(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "is_tunnel_enabled", defaultValue = "0")
    val isAutoTunnelEnabled: Boolean = false,
    @ColumnInfo(name = "is_tunnel_on_mobile_data_enabled", defaultValue = "0")
    val isTunnelOnMobileDataEnabled: Boolean = false,
    @ColumnInfo(name = "trusted_network_ssids", defaultValue = "")
    val trustedNetworkSSIDs: Set<String> = emptySet(),
    @ColumnInfo(name = "is_tunnel_on_ethernet_enabled", defaultValue = "0")
    val isTunnelOnEthernetEnabled: Boolean = false,
    @ColumnInfo(name = "is_tunnel_on_wifi_enabled", defaultValue = "0")
    val isTunnelOnWifiEnabled: Boolean = false,
    @ColumnInfo(name = "is_wildcards_enabled", defaultValue = "0")
    val isWildcardsEnabled: Boolean = false,
    @ColumnInfo(name = "is_stop_on_no_internet_enabled", defaultValue = "0")
    val isStopOnNoInternetEnabled: Boolean = false,
    @ColumnInfo(name = "debounce_delay_seconds", defaultValue = "3")
    val debounceDelaySeconds: Int = 3,
    @ColumnInfo(name = "is_tunnel_on_unsecure_enabled", defaultValue = "0")
    val isTunnelOnUnsecureEnabled: Boolean = false,
    @ColumnInfo(name = "wifi_detection_method", defaultValue = "0")
    val wifiDetectionMethod: WifiDetectionMethod = WifiDetectionMethod.fromValue(0),
    @ColumnInfo(name = "start_on_boot", defaultValue = "0") val startOnBoot: Boolean = false,
)
