package app.abhigarala.pma

import android.app.Application
import android.util.Log
import app.abhigarala.pma.cypher.EncryptionUtils
import app.abhigarala.pma.sharedpref.DataStoreManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PMApp : Application() {

    companion object {
        lateinit var dataStoreManager: DataStoreManager
        lateinit var RSA_PUBLIC_KEY: String
        lateinit var RSA_PRIVATE_KEY: String
    }

    override fun onCreate() {
        super.onCreate()

        dataStoreManager = DataStoreManager(this)

        if (dataStoreManager.getPair("RSA") == null) {

            val keypair = EncryptionUtils.generateRSAKeyPair()

            RSA_PUBLIC_KEY = EncryptionUtils.getPublicKey(keypair)
            Log.e("RSA_PUBLIC_KEY", RSA_PUBLIC_KEY)

            RSA_PRIVATE_KEY = EncryptionUtils.getPrivateKey(keypair)
            Log.e("RSA_PRIVATE_KEY", RSA_PRIVATE_KEY)

            if (RSA_PUBLIC_KEY != "null" && RSA_PRIVATE_KEY != "null") {
                dataStoreManager.savePair("RSA", Pair(RSA_PUBLIC_KEY, RSA_PRIVATE_KEY))
            }

        } else {

            RSA_PUBLIC_KEY = dataStoreManager.getPair("RSA")?.first.toString()
            Log.e("RSA_PUBLIC_KEY", RSA_PUBLIC_KEY)

            RSA_PRIVATE_KEY = dataStoreManager.getPair("RSA")?.second.toString()
            Log.e("RSA_PRIVATE_KEY", RSA_PRIVATE_KEY)

        }
    }


}