package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.Wrapper

fun <T> MutatingCollectionView<T>.snapshot(): Collection<T> = collectionOf(*toGenericArray())

fun <T> MutatingSequencedCollectionView<T>.snapshot(): SequencedCollection<T> = sequencedCollectionOf(*toGenericArray())

fun <T> MutatingListView<T>.snapshot(): List<T> = listOf(*toGenericArray())

fun <T> MutatingSetView<T>.snapshot(): Set<T> = setOf(*toGenericArray())

fun <T> MutatingSequencedSetView<T>.snapshot(): SequencedSet<T> = sequencedSetOf(*toGenericArray())

@JvmName("snapshotComparable")
fun <T: Comparable<T>> MutatingSortedNavigableSetView<T>.snapshot(): SortedNavigableSet<T> = navigableSetOf(*toGenericArray())

fun <T> MutatingSortedNavigableSetView<T>.snapshot(): SortedNavigableSet<T> = navigableSetOf(this.comparator!!, *toGenericArray())

@JvmName("snapshotComparable")
fun <T: Comparable<T>> MutatingNavigableSetView<T>.snapshot(): NavigableSet<T> = navigableSetOf(*toGenericArray())

fun <T> MutatingNavigableSetView<T>.snapshot(): NavigableSet<T> = navigableSetOf(this.comparator!!, *toGenericArray())

fun <K, V> MutatingMapView<K, V>.snapshot(): Map<K, V> = mapOf(*toGenericArray())

fun <K, V> MutatingSequencedMapView<K, V>.snapshot(): SequencedMap<K, V> = sequencedMapOf(*toGenericArray())

@JvmName("snapshotComparable")
fun <K: Comparable<K>, V> MutatingSortedNavigableMapView<K, V>.snapshot(): SortedNavigableMap<K, V> = navigableMapOf(*toGenericArray())

fun <K, V> MutatingSortedNavigableMapView<K, V>.snapshot(): SortedNavigableMap<K, V> = navigableMapOf(this.comparator!!, *toGenericArray())

@JvmName("snapshotComparable")
fun <K: Comparable<K>, V> MutatingNavigableMapView<K, V>.snapshot(): NavigableMap<K, V> = navigableMapOf(*toGenericArray())

fun <K, V> MutatingNavigableMapView<K, V>.snapshot(): NavigableMap<K, V> = navigableMapOf(this.comparator!!, *toGenericArray())

@Suppress("UNCHECKED_CAST")
internal fun <T> MutatingCollectionView<T>.toGenericArray(): Array<T> {
	val iterator = (this as Wrapper<kotlin.collections.Collection<T>>).origin.iterator()
	return this.size().let { size ->
		Array<Any?>(size) {
			try {
				if(size != size()) throw concurrentModification()
				return@Array iterator.next()
			}
			catch(e: NoSuchElementException) {
				throw concurrentModification(e)
			}
		} as Array<T>
	}
}

@Suppress("UNCHECKED_CAST")
internal fun <K, V> MutatingMapView<K, V>.toGenericArray(): Array<Pair<K, V>> {
	val iterator = (this.entries as Wrapper<kotlin.collections.Collection<kotlin.collections.Map.Entry<K, V>>>).origin.iterator()
	return this.size().let { size ->
		Array(size) {
			try {
				if(size != size()) throw concurrentModification()
				return@Array iterator.next().let { it.key to it.value }
			}
			catch(e: NoSuchElementException) {
				throw concurrentModification(e)
			}
		}
	}
}

private fun concurrentModification(cause: Throwable? = null): ConcurrentModificationException =
	ConcurrentModificationException("Failed to snapshot mutating collection", cause)
