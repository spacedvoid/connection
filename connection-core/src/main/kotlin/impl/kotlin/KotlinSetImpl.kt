package io.github.spacedvoid.connection.impl.kotlin

import io.github.spacedvoid.connection.*

open class KotlinSetImpl<T>(connection: SetView<T>): KotlinCollectionImpl<T>(connection), Set<T> {
	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(other !is Set<*>) return false
		@Suppress("UNCHECKED_CAST")
		return this.size == other.size && containsAll(other)
	}

	override fun hashCode(): Int = sumOf { it.hashCode() }
}

open class KotlinMutableSetImpl<T>(connection: RemoveOnlySet<T>): KotlinMutableCollectionImpl<T>(connection), MutableSet<T>
