package io.github.spacedvoid.connection

import java.util.stream.Stream

/**
 * Returns a new sequential stream for this collection, using [CollectionView.spliterator].
 */
@StreamSupport
fun <T> CollectionView<T>.stream(): Stream<T> = java.util.stream.StreamSupport.stream(spliterator(), false)
