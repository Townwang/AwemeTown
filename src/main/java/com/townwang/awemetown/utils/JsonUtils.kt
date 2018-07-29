package com.townwang.awemetown.utils

import android.os.Environment
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.townwang.logutils.Log
import java.io.*
import com.google.gson.JsonParser
import java.lang.reflect.Type


/**
 * @author Town
 * @created at 2018/7/20 16:26
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/20 16:26
 * @Remarks JSON utils
 */
object JsonUtils{
    private var bw: BufferedWriter? = null
    private var br: BufferedReader? = null
    private var cacheFile: File? = null  //存在本地的文件夹
    private val gson = Gson()
    /**
     * bean to String
     */
    fun <T> beanToString(t:T):String{
        return gson.toJson(t)
    }

    /**
     * string to bean
     */
    fun  <T>stringToBean(s:String, typeOfT:Class<T>):T {
        return   gson.fromJson(s,typeOfT)
    }

    /**
     * create a dir
     */
    fun createMdir(mdir:String){
        //新建一个File，传入文件夹目录
        val file = File(Environment.getExternalStorageDirectory().path+"/"+mdir)
        if (!file.exists()) {
            //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
            file.mkdirs()
        }
    }

    /**
     * save json data
     */
    fun storeJsonData( jsonData: String,mdir: String,fileName:String):String {
        return try {
            cacheFile = File(Environment.getExternalStorageDirectory().path+"/"+mdir, fileName)
            bw = BufferedWriter(FileWriter(cacheFile!!))
            bw!!.newLine()
            bw!!.write(jsonData.toCharArray())
            bw!!.close()
            "初始化数据完成"
        } catch (e: Exception) {
            e.printStackTrace()
            "初始化数据出错，权限缺失，点击执行下一步"
        } finally {
            bw = null
        }
    }

    /**
     * read json data
     */
    fun loadJsonDatafromLocal(mdir: String,fileName:String): String? {
        cacheFile = File(Environment.getExternalStorageDirectory().path+"/"+mdir, fileName)
        val stringBuffer = StringBuffer()
        if (cacheFile!!.exists()) {
            try {
                br = BufferedReader(FileReader(cacheFile!!))
                while (br!!.ready()) {
                    stringBuffer.append(br!!.readLine().trim { it <= ' ' })
                }
                br!!.close()
                return stringBuffer.toString()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("查询JSON出错")
            }

        }
        return null
    }

}
