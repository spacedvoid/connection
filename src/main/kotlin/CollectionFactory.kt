package io.github.spacedvoid.connection

fun <T> collectionOf(vararg elements: T): Collection<T> = (arrayListOf(*elements) as kotlin.collections.Collection<T>).asConnection()

fun <T> mutableCollectionOf(vararg elements: T): MutableCollection<T> = (arrayListOf(*elements) as kotlin.collections.MutableCollection<T>).asConnection()

fun <T> sequencedCollectionOf(vararg elements: T): SequencedCollection<T> = (arrayListOf(*elements) as kotlin.collections.List<T>).asSequencedConnection()

fun <T> mutableSequencedCollectionOf(vararg elements: T): MutableSequencedCollection<T> = (arrayListOf(*elements) as kotlin.collections.MutableList<T>).asSequencedConnection()

fun <T> listOf(vararg elements: T): List<T> = (arrayListOf(*elements) as kotlin.collections.List<T>).asConnection()

fun <T> mutableListOf(vararg elements: T): MutableList<T> = arrayListOf(*elements).asConnection()

fun <T> setOf(vararg elements: T): Set<T> = kotlin.collections.setOf(*elements).asConnection()

fun <T> mutableSetOf(vararg elements: T): MutableSet<T> = kotlin.collections.mutableSetOf(*elements).asConnection()

fun <T> sequencedSetOf(vararg elements: T): SequencedSet<T> = linkedSetOf(*elements).asConnection()

fun <T> mutableSequencedSetOf(vararg elements: T): MutableSequencedSet<T> = linkedSetOf(*elements).asMutableConnection()

fun <K, V> mapOf(vararg entries: Pair<K, V>): Map<K, V> = (hashMapOf(*entries) as kotlin.collections.Map<K, V>).asConnection()

fun <K, V> mutableMapOf(vararg entries: Pair<K, V>): MutableMap<K, V> = hashMapOf(*entries).asConnection()

fun <K, V> sequencedMapOf(vararg entries: Pair<K, V>): SequencedMap<K, V> = linkedMapOf(*entries).asConnection()

fun <K, V> mutableSequencedMapOf(vararg entries: Pair<K, V>): MutableSequencedMap<K, V> = linkedMapOf(*entries).asMutableConnection()
