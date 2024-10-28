package io.github.spacedvoid.connection

interface Set<T>: Collection<T>

interface MutatingSetView<T>: MutatingCollectionView<T>

interface RemoveOnlySet<T>: RemoveOnlyCollection<T>, MutatingSetView<T>

interface MutableSet<T>: MutableCollection<T>, RemoveOnlySet<T>
