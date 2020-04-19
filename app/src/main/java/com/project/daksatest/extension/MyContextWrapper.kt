package com.project.daksatest.extension

import android.content.ContextWrapper
import android.os.LocaleList
import android.os.Build
import android.content.Context
import java.util.*


class MyContextWrapper(base: Context) : ContextWrapper(base) {
    companion object {

        fun wrap(context: Context, newLocale: Locale): ContextWrapper {
            var context = context
            val res = context.getResources()
            val configuration = res.getConfiguration()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.setLocale(newLocale)

                val localeList = LocaleList(newLocale)
                LocaleList.setDefault(localeList)
                configuration.setLocales(localeList)

                context = context.createConfigurationContext(configuration)

            } else {
                configuration.locale = newLocale
                res.updateConfiguration(configuration, res.getDisplayMetrics())
            }

            return ContextWrapper(context)
        }
    }
}