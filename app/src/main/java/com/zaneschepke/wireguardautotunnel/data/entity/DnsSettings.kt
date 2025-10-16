package com.zaneschepke.wireguardautotunnel.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zaneschepke.wireguardautotunnel.data.model.DnsProtocol

@Entity(tableName = "dns_settings")
data class DnsSettings(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "dns_protocol", defaultValue = "0")
    val dnsProtocol: DnsProtocol = DnsProtocol.fromValue(0),
    @ColumnInfo(name = "dns_endpoint") val dnsEndpoint: String? = null,
)
