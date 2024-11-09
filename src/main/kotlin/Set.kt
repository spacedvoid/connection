package io.github.spacedvoid.connection

interface SetView<T>: CollectionView<T>

interface Set<T>: Collection<T>, SetView<T>

interface RemoveOnlySet<T>: RemoveOnlyCollection<T>, SetView<T>

interface MutableSet<T>: MutableCollection<T>, RemoveOnlySet<T>
