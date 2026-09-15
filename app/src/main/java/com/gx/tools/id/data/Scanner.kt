package com.gx.tools.id.data

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import java.io.File
import java.util.UUID

class Scanner(private val context: Context) {

    fun scan(): List<AppData> {
        val pm = context.packageManager
        val trackers = findTrackers()
        val gaid = getGAID()
        val apps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        val list = mutableListOf<AppData>()

        for (app in apps) {
            if ((app.flags and ApplicationInfo.FLAG_SYSTEM) != 0) continue

            val name = try { pm.getApplicationLabel(app).toString() } catch (e: Exception) { app.packageName }
            val pkg = app.packageName
            val version = try { pm.getPackageInfo(pkg, 0).versionName ?: "1.0" } catch (e: Exception) { "1.0" }
            val icon = try { pm.getApplicationIcon(app) } catch (e: Exception) { null }

            val tracker = trackers[pkg] ?: "None"
            val color = when (tracker) {
                "AppsFlyer" -> 0xFFFF9800
                "Singular"  -> 0xFF00E5FF
                "Adjust"    -> 0xFF00E676
                "Amazon"    -> 0xFFFFC107
                else        -> 0xFF9E9E9E
            }

            list.add(
                AppData(
                    name = name,
                    packageName = pkg,
                    version = version,
                    tracker = tracker,
                    trackerColor = color,
                    icon = icon,
                    gaid = gaid,
                    afId = generateAfId(),
                    installId = generateUuid(),
                    uuid = generateUuid(),
                    deviceId = generateUuid(),
                    amazonId = generateUuid(),
                    singularId = generateUuid(),
                    adjustId = generateUuid()
                )
            )
        }
        return list.sortedBy { it.tracker }
    }

    private fun findTrackers(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        try {
            val dir = File("/data/data")
            if (!dir.exists()) return map
            dir.listFiles()?.forEach { pkgDir ->
                val prefs = File(pkgDir, "shared_prefs")
                if (!prefs.exists()) return@forEach
                val pkg = pkgDir.name
                when {
                    File(prefs, "appsflyer-data.xml").exists() -> map[pkg] = "AppsFlyer"
                    File(prefs, "pref-singular-id.xml").exists() -> map[pkg] = "Singular"
                    File(prefs, "adjust_preferences.xml").exists() -> map[pkg] = "Adjust"
                    File(prefs, "amazon_ads_prefs.xml").exists() -> map[pkg] = "Amazon"
                }
            }
        } catch (e: Exception) { }
        return map
    }

    private fun getGAID(): String {
        return try {
            val f = File("/data/data/com.google.android.gms/shared_prefs/adid_settings.xml")
            if (!f.exists()) return "Not Found"
            val regex = Regex("[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            regex.find(f.readText())?.value ?: "Not Found"
        } catch (e: Exception) { "Not Found" }
    }

    private fun generateUuid(): String = UUID.randomUUID().toString()

    private fun generateAfId(): String {
        val ts = System.currentTimeMillis()
        val rnd = (1000000000000000000L..9999999999999999999L).random()
        return "$ts-$rnd"
    }
}
