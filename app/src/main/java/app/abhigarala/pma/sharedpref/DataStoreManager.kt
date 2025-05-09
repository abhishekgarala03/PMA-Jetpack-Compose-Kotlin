package app.abhigarala.pma.sharedpref

import android.content.Context

class DataStoreManager(val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("pma_prefs", Context.MODE_PRIVATE)

    fun savePair(key: String, pair: Pair<String, String>) {
        sharedPreferences.edit()
            .putString("${key}_public", pair.first)
            .putString("${key}_private", pair.second)
            .apply()

    }

    fun getPair(key: String): Pair<String, String>? {
        val first = sharedPreferences.getString("${key}_public", null)
        val second = sharedPreferences.getString("${key}_private", null)
        return if (first != null && second != null) Pair(first, second) else null
    }
}