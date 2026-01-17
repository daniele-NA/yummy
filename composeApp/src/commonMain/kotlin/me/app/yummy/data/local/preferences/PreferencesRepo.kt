package me.app.yummy.data.local.preferences

import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.jvm.JvmStatic

// == HANDLE APP PREFERENCES == //
class PreferencesRepo(val settings: Settings) {

    companion object {
        @JvmStatic
        internal val FIRST_BOOT_KEY = "first_boot_key"
    }

    private val scope = CoroutineScope(Dispatchers.IO)

    suspend inline fun <reified T : Any> getPrefValue(key: String): T? =
        withContext(Dispatchers.IO) {
            return@withContext when (T::class) {
                Int::class -> settings.getIntOrNull(key)
                Long::class -> settings.getLongOrNull(key)
                Float::class -> settings.getFloatOrNull(key)
                Double::class -> settings.getDoubleOrNull(key)
                Boolean::class -> settings.getBooleanOrNull(key)
                String::class -> settings.getStringOrNull(key)
                else -> throw IllegalArgumentException("Unsupported type")
            } as T?
        }

    suspend inline fun <reified T : Any> putPrefValue(key: String, value: T) =
        withContext(Dispatchers.IO) {
            when (value) {
                is Int -> settings.putInt(key, value)
                is Long -> settings.putLong(key, value)
                is Float -> settings.putFloat(key, value)
                is Double -> settings.putDouble(key, value)
                is Boolean -> settings.putBoolean(key, value)
                is String -> settings.putString(key, value)
                else -> throw IllegalArgumentException("Unsupported type")
            }
        }

    // INITIALIZE settings preferences if needed //
    init {
        scope.launch {
            if (settings.getBooleanOrNull(FIRST_BOOT_KEY) == null) settings.putBoolean(
                FIRST_BOOT_KEY,
                true
            )
        }
    }


}