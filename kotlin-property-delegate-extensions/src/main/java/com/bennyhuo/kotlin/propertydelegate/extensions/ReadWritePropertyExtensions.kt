package com.bennyhuo.kotlin.propertydelegate.extensions

import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by benny.
 */
fun <R, T1, T2> ReadWriteProperty<R, T1>.map(
    getter: (T1) -> T2,
    setter: (T2) -> T1
) = createMapping(this, getter, setter)