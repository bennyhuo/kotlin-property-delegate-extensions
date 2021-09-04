package com.bennyhuo.kotlin.propertydelegate.extensions

import kotlin.properties.ReadOnlyProperty

/**
 * Created by benny.
 */
fun <From, To> ReadOnlyProperty<Any?, From>.map(
    block: (From) -> To
) = createMapping(this, block)

fun <From, To> Lazy<From>.map(
    block: (From) -> To
) = createMapping(this, block)
