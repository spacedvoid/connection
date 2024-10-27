package io.github.spacedvoid.connection

inline fun <reified T> Collection<T>.toTypedArray(): Array<T> {
	val array = arrayOfNulls<T>(size())
	for((index, element) in iterator().withIndex()) array[index] = element
	@Suppress("UNCHECKED_CAST")
	return array as Array<T>
}

fun <T> MutatingCollectionView<T>.snapshot(): Collection<T> = collectionOf(*toGenericArray())

fun <T> MutatingSequencedCollectionView<T>.snapshot(): SequencedCollection<T> = sequencedCollectionOf(*toGenericArray())

fun <T> MutatingListView<T>.snapshot(): List<T> = listOf(*toGenericArray())

fun <T> MutatingSetView<T>.snapshot(): Set<T> = setOf(*toGenericArray())

fun <T> MutatingSequencedSetView<T>.snapshot(): SequencedSet<T> = sequencedSetOf(*toGenericArray())

fun <K, V> MutatingMapView<K, V>.snapshot(): Map<K, V> = mapOf(*toGenericArray())

fun <K, V> MutatingSequencedMapView<K, V>.snapshot(): SequencedMap<K, V> = sequencedMapOf(*toGenericArray())

@Suppress("UNCHECKED_CAST")
internal fun <T> Collection<T>.toGenericArray(): Array<T> {
	val iterator = this.iterator()
	return Array<Any?>(this.size()) { iterator.next() } as Array<T>
}

@Suppress("UNCHECKED_CAST")
@PublishedApi
internal fun <T> MutatingCollectionView<T>.toGenericArray(): Array<T> {
	val iterator = (this as UnsafeIterable<T>).iterator()
	return this.size().let { size ->
		Array<Any?>(size) {
			try {
				if(it != size) throw concurrentModification()
				return@Array iterator.next()
			}
			catch(e: NoSuchElementException) {
				throw concurrentModification(e)
			}
		} as Array<T>
	}
}

@PublishedApi
internal fun <K, V> MutatingMapView<K, V>.toGenericArray(): Array<Pair<K, V>> {
	@Suppress("UNCHECKED_CAST")
	val iterator = (this.entries as UnsafeIterable<kotlin.collections.Map.Entry<K, V>>).iterator()
	return this.size().let { size ->
		Array(size) {
			try {
				if(it != size) throw concurrentModification()
				return@Array with(iterator.next()) { this.key to this.value }
			}
			catch(e: NoSuchElementException) {
				throw concurrentModification(e)
			}
		}
	}
}

private fun concurrentModification(cause: Throwable? = null): ConcurrentModificationException = ConcurrentModificationException("Failed to snapshot mutating collection", cause)
