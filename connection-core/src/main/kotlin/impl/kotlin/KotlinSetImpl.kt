package io.github.spacedvoid.connection.impl.kotlin

import io.github.spacedvoid.connection.*

open class KotlinSetImpl<T>(connection: SetView<T>): KotlinCollectionImpl<T>(connection), Set<T>

open class KotlinMutableSetImpl<T>(connection: RemoveOnlySet<T>): KotlinMutableCollectionImpl<T>(connection), MutableSet<T>
