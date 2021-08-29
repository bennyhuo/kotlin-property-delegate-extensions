@file:Suppress("UNCHECKED_CAST")

package com.bennyhuo.kotlin.propertydelegate.extensions

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by benny.
 */
internal typealias AnyTransform = (Any?) -> Any?

internal class MappingReadOnlyProperty<From, To>(private val delegate: ReadOnlyProperty<Any?, From>) :
    ReadOnlyProperty<Any?, To> {

    val transforms = ArrayList<AnyTransform>()

    override fun getValue(thisRef: Any?, property: KProperty<*>): To {
        return transforms.fold<AnyTransform, Any?>(
            delegate.getValue(thisRef, property)
        ) { acc, function -> function.invoke(acc) } as To
    }
}

internal fun <From, To> createMapping(
    delegate: Any,
    block: (From) -> To
): ReadOnlyProperty<Any?, To> {
    val readOnlyProperty = when (delegate) {
        is MappingReadOnlyProperty<*, *> -> delegate
        is Lazy<*> -> ReadOnlyProperty<Any?, From> { thisRef, property ->
            delegate.getValue(thisRef, property) as From
        }
        is ReadOnlyProperty<*, *> -> delegate
        else -> {
            throw IllegalArgumentException("Type of ${delegate::class.qualifiedName}  is not supported.")
        }
    } as ReadOnlyProperty<Any?, From>

    val mappingReadOnlyProperty = if (readOnlyProperty is MappingReadOnlyProperty<*, *>) {
        readOnlyProperty as MappingReadOnlyProperty<From, To>
    } else {
        MappingReadOnlyProperty(readOnlyProperty)
    }

    mappingReadOnlyProperty.transforms += block as AnyTransform
    return mappingReadOnlyProperty
}

internal class MappingReadWriteProperty<From, To>(private val delegate: ReadWriteProperty<Any?, From>) :
    ReadWriteProperty<Any?, To> {

    val getterTransforms = ArrayList<AnyTransform>()
    val setterTransforms = ArrayList<AnyTransform>()

    override fun getValue(thisRef: Any?, property: KProperty<*>): To {
        return getterTransforms.fold<AnyTransform, Any?>(
            delegate.getValue(thisRef, property)
        ) { acc, function -> function.invoke(acc) } as To
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: To) {
        delegate.setValue(thisRef, property,
            setterTransforms.fold<AnyTransform, Any?>(value) { acc, function ->
                function.invoke(acc)
            } as From
        )
    }
}

internal fun <From, To> createMapping(
    delegate: Any,
    getter: (From) -> To,
    setter: (To) -> From
): ReadWriteProperty<Any?, To> {
    val readWriteProperty = when (delegate) {
        is ReadWriteProperty<*, *> -> delegate
        else -> {
            throw IllegalArgumentException("Type of ${delegate::class.qualifiedName}  is not supported.")
        }
    } as ReadWriteProperty<Any?, From>

    val mappingReadOnlyProperty = if (delegate is MappingReadWriteProperty<*, *>) {
        readWriteProperty as MappingReadWriteProperty<From, To>
    } else {
        MappingReadWriteProperty(readWriteProperty)
    }

    mappingReadOnlyProperty.getterTransforms += getter as AnyTransform
    mappingReadOnlyProperty.setterTransforms += setter as AnyTransform

    return mappingReadOnlyProperty
}