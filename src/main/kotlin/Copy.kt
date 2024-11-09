package io.github.spacedvoid.connection

fun <T> CollectionView<T>.copy(): MutableCollection<T> = mutableCollectionOf(*toGenericArray())

fun <T> SequencedCollectionView<T>.copy(): MutableSequencedCollection<T> = mutableSequencedCollectionOf(*toGenericArray())

fun <T> ListView<T>.copy(): MutableList<T> = mutableListOf(*toGenericArray())

fun <T> SetView<T>.copy(): MutableSet<T> = mutableSetOf(*toGenericArray())

fun <T> SequencedSetView<T>.copy(): MutableSequencedSet<T> = mutableSequencedSetOf(*toGenericArray())

@JvmName("copyComparable")
fun <T: Comparable<T>> SortedNavigableSetView<T>.copy(): MutableSortedNavigableSet<T> = mutableNavigableSetOf(*toGenericArray())

fun <T> SortedNavigableSetView<T>.copy(): MutableSortedNavigableSet<T> = mutableNavigableSetOf(this.comparator!!, *toGenericArray())

@JvmName("copyComparable")
fun <T: Comparable<T>> NavigableSetView<T>.copy(): MutableNavigableSet<T> = mutableNavigableSetOf(*toGenericArray())

fun <T> NavigableSetView<T>.copy(): MutableNavigableSet<T> = mutableNavigableSetOf(this.comparator!!, *toGenericArray())

fun <K, V> MapView<K, V>.copy(): MutableMap<K, V> = mutableMapOf(*toGenericArray())

fun <K, V> SequencedMapView<K, V>.copy(): MutableSequencedMap<K, V> = mutableSequencedMapOf(*toGenericArray())

@JvmName("copyComparable")
fun <K: Comparable<K>, V> SortedNavigableMapView<K, V>.copy(): MutableSortedNavigableMap<K, V> = mutableNavigableMapOf(*toGenericArray())

fun <K, V> SortedNavigableMapView<K, V>.copy(): MutableSortedNavigableMap<K, V> = mutableNavigableMapOf(this.comparator!!, *toGenericArray())

@JvmName("copyComparable")
fun <K: Comparable<K>, V> NavigableMapView<K, V>.copy(): MutableNavigableMap<K, V> = mutableNavigableMapOf(*toGenericArray())

fun <K, V> NavigableMapView<K, V>.copy(): MutableNavigableMap<K, V> = mutableNavigableMapOf(this.comparator!!, *toGenericArray())
