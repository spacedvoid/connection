@file:Suppress("DEPRECATION_ERROR")

package io.github.spacedvoid.connection

@Deprecated("This type is hidden for public use.", ReplaceWith("Set", "io.github.spacedvoid.connection.Set"), DeprecationLevel.HIDDEN)
interface SetBase<T>: CollectionBase<T>

interface Set<T>: Collection<T>, SetBase<T>

interface MutatingSetView<T>: MutatingCollectionView<T>, SetBase<T>

interface RemoveOnlySet<T>: RemoveOnlyCollection<T>, MutatingSetView<T>

interface MutableSet<T>: MutableCollection<T>, RemoveOnlySet<T>
