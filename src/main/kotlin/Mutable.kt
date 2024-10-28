package io.github.spacedvoid.connection

fun <T> Collection<T>.toMutable(): MutableCollection<T> = mutableCollectionOf(*toGenericArray())

fun <T> SequencedCollection<T>.toMutable(): MutableSequencedCollection<T> = mutableSequencedCollectionOf(*toGenericArray())

fun <T> List<T>.toMutable(): MutableList<T> = mutableListOf(*toGenericArray())

fun <T> Set<T>.toMutable(): MutableSet<T> = mutableSetOf(*toGenericArray())

fun <T> SequencedSet<T>.toMutable(): MutableSequencedSet<T> = mutableSequencedSetOf(*toGenericArray())

@JvmName("toComparableMutable")
fun <T: Comparable<T>> SortedSet<T>.toMutable(): MutableSortedSet<T> = mutableSortedSetOf(*toGenericArray())

fun <T> SortedSet<T>.toMutable(): MutableSortedSet<T> = mutableSortedSetOf(this.comparator!!, *toGenericArray())

@JvmName("toComparableMutable")
fun <T: Comparable<T>> NavigableSet<T>.toMutable(): MutableNavigableSet<T> = mutableNavigableSetOf(*toGenericArray())

fun <T> NavigableSet<T>.toMutable(): MutableNavigableSet<T> = mutableNavigableSetOf(this.comparator!!, *toGenericArray())

fun <K, V> Map<K, V>.toMutable(): MutableMap<K, V> = mutableMapOf(*toGenericArray())

fun <K, V> SequencedMap<K, V>.toMutable(): MutableSequencedMap<K, V> = mutableSequencedMapOf(*toGenericArray())

@JvmName("toComparableMutable")
fun <K: Comparable<K>, V> SortedMap<K, V>.toMutable(): MutableSortedMap<K, V> = mutableSortedMapOf(*toGenericArray())

fun <K, V> SortedMap<K, V>.toMutable(): MutableSortedMap<K, V> = mutableSortedMapOf(this.comparator!!, *toGenericArray())

@JvmName("toComparableMutable")
fun <K: Comparable<K>, V> NavigableMap<K, V>.toMutable(): MutableNavigableMap<K, V> = mutableNavigableMapOf(*toGenericArray())

fun <K, V> NavigableMap<K, V>.toMutable(): MutableNavigableMap<K, V> = mutableNavigableMapOf(this.comparator!!, *toGenericArray())
