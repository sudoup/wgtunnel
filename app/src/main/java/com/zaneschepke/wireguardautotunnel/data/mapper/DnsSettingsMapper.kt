package com.zaneschepke.wireguardautotunnel.data.mapper

import com.zaneschepke.wireguardautotunnel.data.entity.DnsSettings as Entity
import com.zaneschepke.wireguardautotunnel.domain.model.DnsSettings as Domain

fun Entity.toDomain(): Domain =
    Domain(id = id, dnsProtocol = dnsProtocol, dnsEndpoint = dnsEndpoint)

fun Domain.toEntity(): Entity =
    Entity(id = id, dnsProtocol = dnsProtocol, dnsEndpoint = dnsEndpoint)
