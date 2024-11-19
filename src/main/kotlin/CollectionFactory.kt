package io.github.spacedvoid.connection

import java.util.TreeMap
import java.util.TreeSet
import kotlin.collections.sortedSetOf as kotlinSortedSetOf

fun <T> collectionOf(vararg elements: T): Collection<T> =
	(arrayListOf(*elements) as kotlin.collections.Collection<T>).asConnection()

fun <T> mutableCollectionOf(vararg elements: T): MutableCollection<T> =
	(arrayListOf(*elements) as kotlin.collections.MutableCollection<T>).asConnection()

fun <T> sequencedCollectionOf(vararg elements: T): SequencedCollection<T> =
	(arrayListOf(*elements) as kotlin.collections.List<T>).asConnection()

fun <T> mutableSequencedCollectionOf(vararg elements: T): MutableSequencedCollection<T> =
	(arrayListOf(*elements) as kotlin.collections.MutableList<T>).asConnection()

fun <T> listOf(vararg elements: T): List<T> = (arrayListOf(*elements) as kotlin.collections.List<T>).asConnection()

fun <T> mutableListOf(vararg elements: T): MutableList<T> = arrayListOf(*elements).asConnection()

fun <T> setOf(vararg elements: T): Set<T> = kotlin.collections.setOf(*elements).asConnection()

fun <T> mutableSetOf(vararg elements: T): MutableSet<T> = kotlin.collections.mutableSetOf(*elements).asConnection()

fun <T> sequencedSetOf(vararg elements: T): SequencedSet<T> = linkedSetOf(*elements).asConnection()

fun <T> mutableSequencedSetOf(vararg elements: T): MutableSequencedSet<T> = linkedSetOf(*elements).asMutableConnection()

fun <T: Comparable<T>> navigableSetOf(vararg elements: T): NavigableSet<T> =
	kotlinSortedSetOf(*elements).asConnection()

fun <T> navigableSetOf(comparator: Comparator<in T>, vararg elements: T): NavigableSet<T> =
	kotlinSortedSetOf(comparator, *elements).asConnection()

fun <T: Comparable<T>> mutableNavigableSetOf(vararg elements: T): MutableNavigableSet<T> =
	kotlinSortedSetOf(*elements).asMutableConnection()

fun <T> mutableNavigableSetOf(comparator: Comparator<in T>, vararg elements: T): MutableNavigableSet<T> =
	kotlinSortedSetOf(comparator, *elements).asMutableConnection()

fun <K, V> mapOf(vararg entries: Pair<K, V>): Map<K, V> = (hashMapOf(*entries) as kotlin.collections.Map<K, V>).asConnection()

fun <K, V> mutableMapOf(vararg entries: Pair<K, V>): MutableMap<K, V> = hashMapOf(*entries).asConnection()

fun <K, V> sequencedMapOf(vararg entries: Pair<K, V>): SequencedMap<K, V> = linkedMapOf(*entries).asConnection()

fun <K, V> mutableSequencedMapOf(vararg entries: Pair<K, V>): MutableSequencedMap<K, V> = linkedMapOf(*entries).asMutableConnection()

fun <K: Comparable<K>, V> navigableMapOf(vararg entries: Pair<K, V>): NavigableMap<K, V> =
	TreeMap<K, V>().apply { entries.forEach { put(it.first, it.second) } }.asConnection()

fun <K, V> navigableMapOf(comparator: Comparator<in K>, vararg entries: Pair<K, V>): NavigableMap<K, V> =
	TreeMap<K, V>(comparator).apply { entries.forEach { put(it.first, it.second) } }.asConnection()

fun <K: Comparable<K>, V> mutableNavigableMapOf(vararg entries: Pair<K, V>): MutableNavigableMap<K, V> =
	TreeMap<K, V>().apply { entries.forEach { put(it.first, it.second) } }.asMutableConnection()

fun <K, V> mutableNavigableMapOf(comparator: Comparator<in K>, vararg entries: Pair<K, V>): MutableNavigableMap<K, V> =
	TreeMap<K, V>(comparator).apply { entries.forEach { put(it.first, it.second) } }.asMutableConnection()

// In case the 'comparability' is not known

@JvmName("unsafeNavigableSetOf")
internal fun <T> navigableSetOf(comparator: Comparator<in T>?, vararg elements: T): NavigableSet<T> =
	TreeSet(comparator).apply { addAll(elements) }.asConnection()

@JvmName("unsafeMutableNavigableSetOf")
internal fun <T> mutableNavigableSetOf(comparator: Comparator<in T>?, vararg elements: T): MutableNavigableSet<T> =
	TreeSet(comparator).apply { addAll(elements) }.asMutableConnection()

@JvmName("unsafeNavigableMapOf")
internal fun <K, V> navigableMapOf(comparator: Comparator<in K>?, vararg entries: Pair<K, V>): NavigableMap<K, V> =
	TreeMap<K, V>(comparator).apply { putAll(entries) }.asConnection()

@JvmName("unsafeMutableNavigableMapOf")
internal fun <K, V> mutableNavigableMapOf(comparator: Comparator<in K>?, vararg entries: Pair<K, V>): MutableNavigableMap<K, V> =
	TreeMap<K, V>(comparator).apply { putAll(entries) }.asMutableConnection()
