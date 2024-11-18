package io.github.spacedvoid.connection

interface ListView<T>: SequencedCollectionView<T> {
	override fun reverse(): ListView<T>

	fun subList(startInclusive: Int, endExclusive: Int): ListView<T>

	operator fun get(index: Int): T = kotlin[index]

	fun indexOf(element: T): Int = kotlin.indexOf(element)

	fun lastIndexOf(element: T): Int = kotlin.lastIndexOf(element)

	// On the JVM, kotlin.collections.List = java.util.List, which inherits from java.util.SequencedCollection.
	@Suppress("PROPERTY_TYPE_MISMATCH_ON_OVERRIDE")
	override val CollectionView<T>.kotlin: kotlin.collections.List<T>
}

interface List<T>: SequencedCollection<T>, ListView<T> {
	override operator fun iterator(): ListIterator<T> = kotlin.listIterator()

	override fun reverse(): List<T>

	override fun subList(startInclusive: Int, endExclusive: Int): List<T>

	// Force override, iterator() cannot resolve type as kotlin.collections.List<T>
	@Suppress("PROPERTY_TYPE_MISMATCH_ON_OVERRIDE")
	override val CollectionView<T>.kotlin: kotlin.collections.List<T>
}

interface MutableList<T>: MutableSequencedCollection<T>, ListView<T> {
	override fun reverse(): MutableList<T>

	override fun subList(startInclusive: Int, endExclusive: Int): MutableList<T>

	fun add(index: Int, element: T) = kotlin.add(index, element)

	fun addFirst(element: T) = kotlin.addFirst(element)

	fun addLast(element: T) = kotlin.addLast(element)

	operator fun set(index: Int, element: T): T= kotlin.set(index, element)

	fun removeAt(index: Int): T = kotlin.removeAt(index)

	@Suppress("PROPERTY_TYPE_MISMATCH_ON_OVERRIDE")
	override val CollectionView<T>.kotlin: kotlin.collections.MutableList<T>
}
