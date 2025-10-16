package com.zaneschepke.wireguardautotunnel.data.mapper

import com.zaneschepke.wireguardautotunnel.data.entity.AutoTunnelSettings as Entity
import com.zaneschepke.wireguardautotunnel.domain.model.AutoTunnelSettings as Domain

fun Entity.toDomain(): Domain =
    Domain(
        id = id,
        isAutoTunnelEnabled = isAutoTunnelEnabled,
        isTunnelOnMobileDataEnabled = isTunnelOnMobileDataEnabled,
        trustedNetworkSSIDs = trustedNetworkSSIDs,
        isTunnelOnEthernetEnabled = isTunnelOnEthernetEnabled,
        isTunnelOnWifiEnabled = isTunnelOnWifiEnabled,
        isWildcardsEnabled = isWildcardsEnabled,
        isStopOnNoInternetEnabled = isStopOnNoInternetEnabled,
        debounceDelaySeconds = debounceDelaySeconds,
        isTunnelOnUnsecureEnabled = isTunnelOnUnsecureEnabled,
        wifiDetectionMethod = wifiDetectionMethod,
    )

fun Domain.toEntity(): Entity =
    Entity(
        id = id,
        isAutoTunnelEnabled = isAutoTunnelEnabled,
        isTunnelOnMobileDataEnabled = isTunnelOnMobileDataEnabled,
        trustedNetworkSSIDs = trustedNetworkSSIDs,
        isTunnelOnEthernetEnabled = isTunnelOnEthernetEnabled,
        isTunnelOnWifiEnabled = isTunnelOnWifiEnabled,
        isWildcardsEnabled = isWildcardsEnabled,
        isStopOnNoInternetEnabled = isStopOnNoInternetEnabled,
        debounceDelaySeconds = debounceDelaySeconds,
        isTunnelOnUnsecureEnabled = isTunnelOnUnsecureEnabled,
        wifiDetectionMethod = wifiDetectionMethod,
    )
