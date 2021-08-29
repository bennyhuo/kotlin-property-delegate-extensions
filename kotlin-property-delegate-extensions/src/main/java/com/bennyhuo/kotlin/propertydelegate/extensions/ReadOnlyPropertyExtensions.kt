package com.bennyhuo.kotlin.propertydelegate.extensions

import kotlin.properties.ReadOnlyProperty

/**
 * Created by benny.
 */
fun <R, From, To> ReadOnlyProperty<R, From>.map(
    block: (From) -> To
) = createMapping(this, block)

fun <T1, T2> Lazy<T1>.map(
    block: (T1) -> T2
) = createMapping(this, block)
