/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.utils

import android.content.Context
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectOutputStream
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author Town
 * @created at 2018/7/28 12:02
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/28 12:02
 * @Remarks 委托储存
 */
class Preference<T>(val context: Context, val string:String, val default : T) : ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(string, default)
    }

    val prefs by lazy{context.getSharedPreferences("Realnen",Context.MODE_PRIVATE)}

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(string, value)
    }

    private fun<A> putPreference(name:String,value:A)= with(prefs.edit()){
        when(value){//if语句 现在在kotlin中是表达式
            is Long -> putLong(name,value)
            is String -> putString(name,value)
            is Int -> putInt(name,value)
            is Boolean -> putBoolean(name,value)
            is Float -> putFloat(name,value)
            else ->   putString(name,serialize(value))
        }.apply()
    }
    private fun <U> findPreference(name: String, default: U): U = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("This type can not be saved")
        }
        res as U
    }

    /**
     * 序列化对象

     * @param person
     * *
     * @return
     * *
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun<A> serialize(obj: A): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(
                byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()

        return serStr    }

}