package io.github.spacedvoid.connection

import java.util.TreeMap
import kotlin.collections.sortedMapOf as kotlinSortedMapOf
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

fun <T: Comparable<T>> sortedSetOf(vararg elements: T): SortedSet<T> =
	(kotlinSortedSetOf(*elements) as java.util.SortedSet<T>).asConnection()

fun <T> sortedSetOf(comparator: Comparator<in T>, vararg elements: T): SortedSet<T> =
	(kotlinSortedSetOf(comparator, *elements) as java.util.SortedSet<T>).asConnection()

fun <T: Comparable<T>> mutableSortedSetOf(vararg elements: T): MutableSortedSet<T> =
	(kotlinSortedSetOf(*elements) as java.util.SortedSet<T>).asMutableConnection()

fun <T> mutableSortedSetOf(comparator: Comparator<in T>, vararg elements: T): MutableSortedSet<T> =
	(kotlinSortedSetOf(comparator, *elements) as java.util.SortedSet<T>).asMutableConnection()

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

fun <K: Comparable<K>, V> sortedMapOf(vararg entries: Pair<K, V>): SortedMap<K, V> = kotlinSortedMapOf(*entries).asConnection()

fun <K, V> sortedMapOf(comparator: Comparator<in K>, vararg entries: Pair<K, V>): SortedMap<K, V> = kotlinSortedMapOf(comparator, *entries).asConnection()

fun <K: Comparable<K>, V> mutableSortedMapOf(vararg entries: Pair<K, V>): MutableSortedMap<K, V> = kotlinSortedMapOf(*entries).asMutableConnection()

fun <K, V> mutableSortedMapOf(comparator: Comparator<in K>, vararg entries: Pair<K, V>): MutableSortedMap<K, V> = kotlinSortedMapOf(comparator, *entries).asMutableConnection()

fun <K: Comparable<K>, V> navigableMapOf(vararg entries: Pair<K, V>): NavigableMap<K, V> =
	TreeMap<K, V>().apply { entries.forEach { put(it.first, it.second) } }.asConnection()

fun <K, V> navigableMapOf(comparator: Comparator<in K>, vararg entries: Pair<K, V>): NavigableMap<K, V> =
	TreeMap<K, V>(comparator).apply { entries.forEach { put(it.first, it.second) } }.asConnection()

fun <K: Comparable<K>, V> mutableNavigableMapOf(vararg entries: Pair<K, V>): MutableNavigableMap<K, V> =
	TreeMap<K, V>().apply { entries.forEach { put(it.first, it.second) } }.asMutableConnection()

fun <K, V> mutableNavigableMapOf(comparator: Comparator<in K>, vararg entries: Pair<K, V>): MutableNavigableMap<K, V> =
	TreeMap<K, V>(comparator).apply { entries.forEach { put(it.first, it.second) } }.asMutableConnection()
