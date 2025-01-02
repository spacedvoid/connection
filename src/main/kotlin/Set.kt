package io.github.spacedvoid.connection

/**
 * A [CollectionView] that only stores one instance per element.
 *
 * The behavior of the set when mutable elements are used, when their mutation affects the result of [Any.equals], is undefined.
 *
 * All operations that check whether an element matches an instance is determined via [Any.equals].
 */
interface SetView<T>: CollectionView<T> {
	override val CollectionView<T>.kotlin: kotlin.collections.Set<T>
}

/**
 * An immutable [SetView].
 */
interface Set<T>: Collection<T>, SetView<T>

/**
 * A [SetView] that additionally supports element removal operations.
 */
interface RemoveOnlySet<T>: RemoveOnlyCollection<T>, SetView<T> {
	override val CollectionView<T>.kotlin: kotlin.collections.MutableSet<T>
}

/**
 * A [RemoveOnlySet] that additionally supports element addition operations.
 *
 * When an element which already matches an instance in the set is added,
 * the contained instance is not replaced, and the set remains unchanged.
 */
interface MutableSet<T>: MutableCollection<T>, RemoveOnlySet<T>
