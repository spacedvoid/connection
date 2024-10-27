package io.github.spacedvoid.connection

fun <T> Collection<T>.toCollection(): Collection<T> = collectionOf(*toGenericArray())

fun <T> Collection<T>.toSequencedCollection(): SequencedCollection<T> = sequencedCollectionOf(*toGenericArray())

fun <T> Collection<T>.toList(): List<T> = listOf(*toGenericArray())

fun <T> Collection<T>.toSet(): Set<T> = setOf(*toGenericArray())

fun <T> Collection<T>.toSequencedSet(): SequencedSet<T> = sequencedSetOf(*toGenericArray())
