package com.project.daksatest.extension

import java.lang.UnsupportedOperationException


fun <T : Enum<*>> Array<T>.fromDescription(description: String) : T  {
    for (obj in this) {
        if (obj is IEnum) {
            val s = (obj as IEnum).toDescription()

            if (s.equals(description, ignoreCase = true)) {
                return obj
            }
        } else {
            throw UnsupportedOperationException("${obj::class.simpleName} does not implement ${IEnum::class.simpleName}")
        }
    }

    throw IllegalArgumentException("No constant with description $description")
}
