package io.github.spacedvoid.connection

fun <T> MutatingCollectionView<T>.copy(): MutableCollection<T> = mutableCollectionOf(*toGenericArray())

fun <T> MutatingSequencedCollectionView<T>.copy(): MutableSequencedCollection<T> = mutableSequencedCollectionOf(*toGenericArray())

fun <T> MutatingListView<T>.copy(): MutableList<T> = mutableListOf(*toGenericArray())

fun <T> MutatingSetView<T>.copy(): MutableSet<T> = mutableSetOf(*toGenericArray())

fun <T> MutatingSequencedSetView<T>.copy(): MutableSequencedSet<T> = mutableSequencedSetOf(*toGenericArray())

@JvmName("copyComparable")
fun <T: Comparable<T>> MutatingSortedSetView<T>.copy(): MutableSortedSet<T> = mutableSortedSetOf(*toGenericArray())

fun <T> MutatingSortedSetView<T>.copy(): MutableSortedSet<T> = mutableSortedSetOf(this.comparator!!, *toGenericArray())

@JvmName("copyComparable")
fun <T: Comparable<T>> MutatingNavigableSetView<T>.copy(): MutableNavigableSet<T> = mutableNavigableSetOf(*toGenericArray())

fun <T> MutatingNavigableSetView<T>.copy(): MutableNavigableSet<T> = mutableNavigableSetOf(this.comparator!!, *toGenericArray())

fun <K, V> MutatingMapView<K, V>.copy(): MutableMap<K, V> = mutableMapOf(*toGenericArray())

fun <K, V> MutatingSequencedMapView<K, V>.copy(): MutableSequencedMap<K, V> = mutableSequencedMapOf(*toGenericArray())

@JvmName("copyComparable")
fun <K: Comparable<K>, V> MutatingSortedMapView<K, V>.copy(): MutableSortedMap<K, V> = mutableSortedMapOf(*toGenericArray())

fun <K, V> MutatingSortedMapView<K, V>.copy(): MutableSortedMap<K, V> = mutableSortedMapOf(this.comparator!!, *toGenericArray())

@JvmName("copyComparable")
fun <K: Comparable<K>, V> MutatingNavigableMapView<K, V>.copy(): MutableNavigableMap<K, V> = mutableNavigableMapOf(*toGenericArray())

fun <K, V> MutatingNavigableMapView<K, V>.copy(): MutableNavigableMap<K, V> = mutableNavigableMapOf(this.comparator!!, *toGenericArray())
