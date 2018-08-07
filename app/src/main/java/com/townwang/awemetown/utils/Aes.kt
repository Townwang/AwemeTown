/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.utils

import android.annotation.SuppressLint
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec

/**
 * @author Town
 * @created at 2018/8/2 22:34
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/8/2 22:34
 * @Remarks
 */

object Aes {
    private val SHA1PRNG = "SHA1PRNG"   // SHA1PRNG
    private val AES = "AES"   //AES 加密
    /**
     * 加密
     */
    fun encrypt(key: String, cleartext: String?): String? {
        if (cleartext == null || "" == cleartext) {
            return cleartext
        }
        try {
            val result = encrypt(key, cleartext.toByteArray())
            return parseByte2HexStr(result)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    /**
     * 加密
     */
    @SuppressLint("GetInstance")
    @Throws(Exception::class)
  private  fun encrypt(key: String, clear: ByteArray): ByteArray {
        val raw = getRawKey(key.toByteArray())
        val skeySpec = SecretKeySpec(raw, AES)
        val cipher = Cipher.getInstance("AES")// 创建密码器
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec)// 初始化
        return cipher.doFinal(clear)
    }

    /**
     * 解密
     */
    fun decrypt(key: String, encrypted: String?): String? {
        if (encrypted == null || "" == encrypted) {
            return encrypted
        }
            val enc = parseHexStr2Byte(encrypted)
            val result = decrypt(key, enc)
            return String(result)
    }

    /**
     * 解密
     */
    @SuppressLint("GetInstance")
    @Throws(Exception::class)
  private  fun decrypt(key: String, encrypted: ByteArray?): ByteArray {
        val raw = getRawKey(key.toByteArray())
        val skeySpec = SecretKeySpec(raw, AES)
        val cipher = Cipher.getInstance("AES")// 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, skeySpec)// 初始化
        return cipher.doFinal(encrypted)
    }


    /**
     * 对密钥进行处理
     */
    @Throws(Exception::class)
    fun getRawKey(seed: ByteArray): ByteArray {
        val kgen = KeyGenerator.getInstance(AES)
        //for android
        val sr: SecureRandom = if (android.os.Build.VERSION.SDK_INT >= 17) {
            SecureRandom.getInstance(SHA1PRNG, "Crypto")
        } else {
            SecureRandom.getInstance(SHA1PRNG)
        }
        // 在4.2以上版本中，SecureRandom获取方式发生了改变
        // for Java
        // secureRandom = SecureRandom.getInstance(SHA1PRNG);
        sr.setSeed(seed)
        kgen.init(128, sr) //256 bits or 128 bits,192bits
        //AES中128位密钥版本有10个加密循环，192比特密钥版本有12个加密循环，256比特密钥版本则有14个加密循环。
        val skey = kgen.generateKey()
        return skey.encoded
    }
    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    fun parseByte2HexStr(buf: ByteArray): String {
        val sb = StringBuilder()
        for (i in buf.indices) {
            var hex = Integer.toHexString(buf[i].toInt() and 0xFF)
            if (hex.length == 1) {
                hex = "0$hex"
            }
            sb.append(hex.toUpperCase())
        }
        return sb.toString()
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    private fun parseHexStr2Byte(hexStr: String): ByteArray? {
        if (hexStr.isEmpty())
            return null
        val result = ByteArray(hexStr.length / 2)
        for (i in 0 until hexStr.length / 2) {
            val high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16)
            val low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16)
            result[i] = (high * 16 + low).toByte()
        }
        return result
    }
}
