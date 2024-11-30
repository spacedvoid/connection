package io.github.spacedvoid.connection

fun <T> Collection<T>.toMutable(): MutableCollection<T> = mutableCollectionOf(*toGenericArray())

fun <T> SequencedCollection<T>.toMutable(): MutableSequencedCollection<T> = mutableSequencedCollectionOf(*toGenericArray())

fun <T> List<T>.toMutable(): MutableList<T> = mutableListOf(*toGenericArray())

fun <T> Set<T>.toMutable(): MutableSet<T> = mutableSetOf(*toGenericArray())

fun <T> SequencedSet<T>.toMutable(): MutableSequencedSet<T> = mutableSequencedSetOf(*toGenericArray())

fun <T> SortedNavigableSet<T>.toMutable(): MutableSortedNavigableSet<T> = mutableNavigableSetOf(this.comparator, *toGenericArray())

fun <T> NavigableSet<T>.toMutable(): MutableNavigableSet<T> = mutableNavigableSetOf(this.comparator, *toGenericArray())

fun <K, V> Map<K, V>.toMutable(): MutableMap<K, V> = mutableMapOf(*toGenericArray())

fun <K, V> SequencedMap<K, V>.toMutable(): MutableSequencedMap<K, V> = mutableSequencedMapOf(*toGenericArray())

fun <K, V> SortedNavigableMap<K, V>.toMutable(): MutableSortedNavigableMap<K, V> = mutableNavigableMapOf(this.comparator, *toGenericArray())

fun <K, V> NavigableMap<K, V>.toMutable(): MutableNavigableMap<K, V> = mutableNavigableMapOf(this.comparator, *toGenericArray())
