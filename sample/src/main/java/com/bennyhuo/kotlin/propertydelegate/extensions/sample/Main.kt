package com.bennyhuo.kotlin.propertydelegate.extensions.sample

import com.bennyhuo.kotlin.propertydelegate.extensions.map
import org.slf4j.LoggerFactory
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

val logger = LoggerFactory.getLogger("kotlin-property-delegate-extensions")

class Cached<T>(val delegate: ReadWriteProperty<Any?, T>) : ReadWriteProperty<Any?, T> {
    private var cached: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return cached ?: delegate.getValue(thisRef, property).also { cached = it }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        cached = value
        delegate.setValue(thisRef, property, value)
    }
}

class ReadOnlyCached<T>(val delegate: ReadOnlyProperty<Any?, T>) : ReadOnlyProperty<Any?, T> {
    private var cached: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return cached ?: delegate.getValue(thisRef, property).also { cached = it }
    }
}

class ValueCached<R, T>(val default: T) : ReadWriteProperty<R, T> {
    private var cached: T? = null

    init {
        logger.debug("init")
    }

    override fun getValue(thisRef: R, property: KProperty<*>): T {
        return cached ?: default
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        cached = value
    }
}


var count = 1.0

var d = lazy { 1 }.map {
    (it + count++).also { value -> logger.debug("map -> $it -> $value") }
}.map {
    ("$it####").also { value -> logger.debug("map2 -> $it -> $value") }
}.map {
    it.length
}.let {
    ReadOnlyCached(it)
}
val x by d

val a by object : ReadOnlyProperty<Any?, Double> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): Double {
        return Math.random()
    }
}.let<ReadOnlyProperty<Any?, Double>, ReadOnlyProperty<Any?, Int>> {
    object : ReadOnlyProperty<Any?, Int> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
            return it.getValue(thisRef, property).toInt()
        }
    }
}













fun main() {
    logger.debug("Hello World!")
    logger.debug(x.toString())
    logger.debug(x.toString())
    logger.debug(x.toString())
    logger.debug(x.toString())
    logger.debug(x.toString())
}