package com.show.livebus.util

import android.annotation.SuppressLint
import android.app.Application
import java.lang.reflect.InvocationTargetException


/**
 *  com.show.livebus.util
 *  2020/11/21
 *  17:09
 *  ShowMeThe
 */
object Apps {


    private var application: Application? = null


    fun getApplication():Application{
        if(application == null){
            application = getApplicationByReflect()
        }
        return application!!
    }


    private fun getApplicationByReflect(): Application? {
        try {
            val activityThread = Class.forName("android.app.ActivityThread")
            val thread = activityThread.getMethod("currentActivityThread").invoke(null)
            val app = activityThread.getMethod("getApplication").invoke(thread)
                ?: throw NullPointerException("u should init first")
            return app as Application
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        throw NullPointerException("init first")
    }

}