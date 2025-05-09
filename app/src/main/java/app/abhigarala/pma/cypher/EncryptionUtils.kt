package app.abhigarala.pma.cypher

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Enumeration
import javax.crypto.Cipher

object EncryptionUtils {

    fun generateRSAKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048) // Key size
        return keyPairGenerator.generateKeyPair()
    }

    fun getPublicKey(keyPair: KeyPair): String {
        return Base64.encodeToString(keyPair.public.encoded, Base64.DEFAULT)
    }

    fun getPrivateKey(keyPair: KeyPair): String {
        return Base64.encodeToString(keyPair.private.encoded, Base64.DEFAULT)
    }

    private fun decodePublicKey(publicKeyString: String): PublicKey {
        val decodedKey = Base64.decode(publicKeyString, Base64.DEFAULT)
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = X509EncodedKeySpec(decodedKey)
        return keyFactory.generatePublic(keySpec)
    }

    private fun decodePrivateKey(privateKeyString: String): PrivateKey {
        val decodedKey = Base64.decode(privateKeyString, Base64.DEFAULT)
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = PKCS8EncodedKeySpec(decodedKey)
        return keyFactory.generatePrivate(keySpec)
    }

    fun encrypt(data: String, publicKey: String): String {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, decodePublicKey(publicKey))
        val encryptedBytes = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    fun decrypt(encryptedData: String, privateKey: String): String {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.DECRYPT_MODE, decodePrivateKey(privateKey))
        val decodedData = Base64.decode(encryptedData, Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(decodedData)
        return String(decryptedBytes, Charsets.UTF_8)
    }
}