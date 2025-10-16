package com.zaneschepke.wireguardautotunnel.core.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.VpnService
import android.os.IBinder
import com.zaneschepke.wireguardautotunnel.core.service.autotunnel.AutoTunnelService
import com.zaneschepke.wireguardautotunnel.data.model.AppMode
import com.zaneschepke.wireguardautotunnel.di.ApplicationScope
import com.zaneschepke.wireguardautotunnel.di.IoDispatcher
import com.zaneschepke.wireguardautotunnel.domain.repository.AutoTunnelSettingsRepository
import com.zaneschepke.wireguardautotunnel.util.extensions.requestAutoTunnelTileServiceUpdate
import com.zaneschepke.wireguardautotunnel.util.extensions.requestTunnelTileServiceStateUpdate
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import timber.log.Timber

class ServiceManager
@Inject
constructor(
    private val context: Context,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    @ApplicationScope applicationScope: CoroutineScope,
    private val mainDispatcher: CoroutineDispatcher,
    private val autoTunnelSettingsRepository: AutoTunnelSettingsRepository,
) {

    private val autoTunnelMutex = Mutex()
    private val tunnelMutex = Mutex()

    private val _tunnelService = MutableStateFlow<TunnelService?>(null)
    private val _autoTunnelService = MutableStateFlow<AutoTunnelService?>(null)
    val autoTunnelService = _autoTunnelService.asStateFlow()
    val tunnelService = _tunnelService.asStateFlow()

    init {
        applicationScope.launch(ioDispatcher) {
            _autoTunnelService
                .onEach { _ -> withContext(mainDispatcher) { updateAutoTunnelTile() } }
                .launchIn(this)
        }
        applicationScope.launch(ioDispatcher) {
            combine(
                    autoTunnelSettingsRepository.flow
                        .map { it.isAutoTunnelEnabled }
                        .distinctUntilChanged(),
                    _autoTunnelService,
                ) { enabled, service ->
                    enabled to (service != null)
                }
                .collect { (enabled, isRunning) ->
                    when {
                        enabled && !isRunning -> {
                            autoTunnelMutex.withLock { startServiceInternal() }
                        }
                        !enabled && isRunning -> {
                            autoTunnelMutex.withLock { stopServiceInternal() }
                        }
                    }
                }
        }
    }

    private val tunnelServiceConnection =
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                val binder = service as? LocalBinder
                _tunnelService.update { binder?.service }
                val serviceClass =
                    when {
                        name.className.contains("VpnForegroundService") -> "VpnForegroundService"
                        name.className.contains("TunnelForegroundService") ->
                            "TunnelForegroundService"
                        else -> "Unknown"
                    }
                Timber.d("$serviceClass connected")
            }

            override fun onServiceDisconnected(name: ComponentName) {
                _tunnelService.update { null }
                val serviceClass =
                    when {
                        name.className.contains("VpnForegroundService") -> "VpnForegroundService"
                        name.className.contains("TunnelForegroundService") ->
                            "TunnelForegroundService"
                        else -> "Unknown"
                    }
                Timber.d("$serviceClass disconnected")
            }
        }

    private val autoTunnelServiceConnection =
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                val binder = service as? AutoTunnelService.LocalBinder
                _autoTunnelService.update { binder?.service }
                Timber.d("AutoTunnelService connected")
            }

            override fun onServiceDisconnected(name: ComponentName) {
                _autoTunnelService.update { null }
                Timber.d("AutoTunnelService disconnected")
            }
        }

    fun hasVpnPermission(): Boolean {
        return VpnService.prepare(context) == null
    }

    private fun startServiceInternal() {
        val intent = Intent(context, AutoTunnelService::class.java)
        context.startForegroundService(intent)
        context.bindService(intent, autoTunnelServiceConnection, Context.BIND_AUTO_CREATE)
    }

    suspend fun startAutoTunnelService() = autoTunnelMutex.withLock { startServiceInternal() }

    private fun stopServiceInternal() {
        _autoTunnelService.value?.stop()
        try {
            context.unbindService(autoTunnelServiceConnection)
        } catch (e: Exception) {
            Timber.e(e, "Failed to unbind AutoTunnelService")
        }
        _autoTunnelService.update { null }
    }

    suspend fun startTunnelService(appMode: AppMode) =
        tunnelMutex.withLock {
            if (_tunnelService.value != null) return@withLock
            val serviceClass =
                when (appMode) {
                    AppMode.VPN,
                    AppMode.LOCK_DOWN -> VpnForegroundService::class.java
                    AppMode.KERNEL,
                    AppMode.PROXY -> TunnelForegroundService::class.java
                }
            val intent = Intent(context, serviceClass)
            context.startForegroundService(intent)
            context.bindService(intent, tunnelServiceConnection, Context.BIND_AUTO_CREATE)
        }

    suspend fun stopTunnelService() =
        tunnelMutex.withLock {
            _tunnelService.value?.let { service ->
                service.stop()
                try {
                    context.unbindService(tunnelServiceConnection)
                } catch (e: Exception) {
                    Timber.e(e, "Failed to stop Tunnel Service")
                }
            }
        }

    fun updateAutoTunnelTile() {
        context.requestAutoTunnelTileServiceUpdate()
    }

    fun updateTunnelTile() {
        context.requestTunnelTileServiceStateUpdate()
    }

    fun handleTunnelServiceDestroy() {
        _tunnelService.update { null }
    }

    fun handleAutoTunnelServiceDestroy() {
        _autoTunnelService.update { null }
    }
}
