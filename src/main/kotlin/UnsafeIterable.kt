package io.github.spacedvoid.connection

@PublishedApi
internal interface UnsafeIterable<T> {
	fun iterator(): Iterator<T>
}

internal interface UnsafeMutableIterable<T>: UnsafeIterable<T> {
	override fun iterator(): MutableIterator<T>
}

internal interface UnsafeListIterable<T>: UnsafeIterable<T> {
	override fun iterator(): ListIterator<T>
}

internal interface UnsafeMutableListIterable<T>: UnsafeIterable<T>, UnsafeListIterable<T> {
	override fun iterator(): MutableListIterator<T>
}
