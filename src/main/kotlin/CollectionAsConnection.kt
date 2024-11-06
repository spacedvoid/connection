package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.Collectable
import io.github.spacedvoid.connection.characteristic.CollectableImpl
import io.github.spacedvoid.connection.characteristic.Listed
import io.github.spacedvoid.connection.characteristic.ListedImpl
import io.github.spacedvoid.connection.characteristic.Mappable
import io.github.spacedvoid.connection.characteristic.MappableImpl
import io.github.spacedvoid.connection.characteristic.Mutable
import io.github.spacedvoid.connection.characteristic.MutableImpl
import io.github.spacedvoid.connection.characteristic.MutableListed
import io.github.spacedvoid.connection.characteristic.MutableListedImpl
import io.github.spacedvoid.connection.characteristic.MutableMappable
import io.github.spacedvoid.connection.characteristic.MutableMappableImpl
import io.github.spacedvoid.connection.characteristic.MutableSequenced
import io.github.spacedvoid.connection.characteristic.MutableSequencedImpl
import io.github.spacedvoid.connection.characteristic.MutableSequencedMappable
import io.github.spacedvoid.connection.characteristic.MutableSequencedMappableImpl
import io.github.spacedvoid.connection.characteristic.Navigable
import io.github.spacedvoid.connection.characteristic.NavigableImpl
import io.github.spacedvoid.connection.characteristic.NavigableMappable
import io.github.spacedvoid.connection.characteristic.NavigableMappableImpl
import io.github.spacedvoid.connection.characteristic.RemoveOnly
import io.github.spacedvoid.connection.characteristic.RemoveOnlyImpl
import io.github.spacedvoid.connection.characteristic.RemoveOnlySequenced
import io.github.spacedvoid.connection.characteristic.RemoveOnlySequencedImpl
import io.github.spacedvoid.connection.characteristic.Sequenced
import io.github.spacedvoid.connection.characteristic.SequencedImpl
import io.github.spacedvoid.connection.characteristic.SequencedMappable
import io.github.spacedvoid.connection.characteristic.SequencedMappableImpl
import io.github.spacedvoid.connection.characteristic.SortedNavigableImpl
import io.github.spacedvoid.connection.characteristic.SortedNavigableMappableImpl
import io.github.spacedvoid.connection.characteristic.Wrapper
import io.github.spacedvoid.connection.characteristic.WrapperImpl
import kotlin.collections.Collection as KotlinCollection
import kotlin.collections.List as KotlinList
import kotlin.collections.Map as KotlinMap
import kotlin.collections.MutableCollection as KotlinMutableCollection
import kotlin.collections.MutableList as KotlinMutableList
import kotlin.collections.MutableMap as KotlinMutableMap
import kotlin.collections.MutableSet as KotlinMutableSet
import kotlin.collections.Set as KotlinSet

// kotlin.collections.Collection -> Collection

fun <T> KotlinCollection<T>.asConnection(): Collection<T> =
	object: Collectable<T> by CollectableImpl(this), Wrapper<KotlinCollection<T>> by WrapperImpl(this), Collection<T> {
		override fun iterator(): Iterator<T> = this@asConnection.iterator()
	}

fun <T> KotlinCollection<T>.asViewConnection(): MutatingCollectionView<T> =
	object: Collectable<T> by CollectableImpl(this), Wrapper<KotlinCollection<T>> by WrapperImpl(this), MutatingCollectionView<T> {}

fun <T> KotlinMutableCollection<T>.asRemoveOnlyConnection(): RemoveOnlyCollection<T> =
	object: RemoveOnly<T> by RemoveOnlyImpl(this), Wrapper<KotlinMutableCollection<T>> by WrapperImpl(this), RemoveOnlyCollection<T> {}

fun <T> KotlinMutableCollection<T>.asConnection(): MutableCollection<T> =
	object: Mutable<T> by MutableImpl(this), Wrapper<KotlinMutableCollection<T>> by WrapperImpl(this), MutableCollection<T> {}

// java.util.SequencedCollection -> SequencedCollection

fun <T> java.util.SequencedCollection<T>.asConnection(): SequencedCollection<T> =
	object: Collectable<T> by CollectableImpl(this), Sequenced<T> by SequencedImpl(this),
			Wrapper<java.util.SequencedCollection<T>> by WrapperImpl(this), SequencedCollection<T> {
		override fun iterator(): Iterator<T> = this@asConnection.iterator()

		override fun reverse(): SequencedCollection<T> = this@asConnection.reversed().asConnection()
	}

fun <T> java.util.SequencedCollection<T>.asViewConnection(): MutatingSequencedCollectionView<T> =
	object: Collectable<T> by CollectableImpl(this), Sequenced<T> by SequencedImpl(this),
			Wrapper<java.util.SequencedCollection<T>> by WrapperImpl(this), MutatingSequencedCollectionView<T> {
		override fun reverse(): MutatingSequencedCollectionView<T> = this@asViewConnection.reversed().asViewConnection()
	}

fun <T> java.util.SequencedCollection<T>.asRemoveOnlyConnection(): RemoveOnlySequencedCollection<T> =
	object: RemoveOnly<T> by RemoveOnlyImpl(this), RemoveOnlySequenced<T> by RemoveOnlySequencedImpl(this),
			Wrapper<java.util.SequencedCollection<T>> by WrapperImpl(this), RemoveOnlySequencedCollection<T> {
		override fun reverse(): RemoveOnlySequencedCollection<T> = this@asRemoveOnlyConnection.reversed().asRemoveOnlyConnection()
	}

fun <T> java.util.SequencedCollection<T>.asMutableConnection(): MutableSequencedCollection<T> =
	object: Mutable<T> by MutableImpl(this), MutableSequenced<T> by MutableSequencedImpl(this),
			Wrapper<java.util.SequencedCollection<T>> by WrapperImpl(this), MutableSequencedCollection<T> {
		override fun reverse(): MutableSequencedCollection<T> = this@asMutableConnection.reversed().asMutableConnection()
	}

// kotlin.collections.List -> SequencedCollection

@Suppress("UNCHECKED_CAST")
fun <T> KotlinList<T>.asSequencedConnection(): SequencedCollection<T> =
	object: Collectable<T> by CollectableImpl(this), Sequenced<T> by SequencedImpl(this as java.util.SequencedCollection<T>),
			Wrapper<KotlinList<T>> by WrapperImpl(this), SequencedCollection<T> {
		override fun iterator(): Iterator<T> = this@asSequencedConnection.iterator()

		override fun reverse(): SequencedCollection<T> = this@asSequencedConnection.asReversed().asSequencedConnection()
	}

@Suppress("UNCHECKED_CAST")
fun <T> KotlinList<T>.asSequencedViewConnection(): MutatingSequencedCollectionView<T> =
	object: Collectable<T> by CollectableImpl(this), Sequenced<T> by SequencedImpl(this as java.util.SequencedCollection<T>),
			Wrapper<KotlinList<T>> by WrapperImpl(this), MutatingSequencedCollectionView<T> {
		override fun reverse(): MutatingSequencedCollectionView<T> = this@asSequencedViewConnection.asReversed().asSequencedViewConnection()
	}

@Suppress("UNCHECKED_CAST")
fun <T> KotlinMutableList<T>.asSequencedRemoveOnlyConnection(): RemoveOnlySequencedCollection<T> =
	object: RemoveOnly<T> by RemoveOnlyImpl(this), RemoveOnlySequenced<T> by RemoveOnlySequencedImpl(this as java.util.SequencedCollection<T>),
			Wrapper<KotlinMutableList<T>> by WrapperImpl(this), RemoveOnlySequencedCollection<T> {
		override fun reverse(): MutableSequencedCollection<T> = asReversed().asConnection()
	}

@Suppress("UNCHECKED_CAST")
fun <T> KotlinMutableList<T>.asSequencedConnection(): MutableSequencedCollection<T> =
	object: Mutable<T> by MutableImpl(this), MutableSequenced<T> by MutableSequencedImpl(this as java.util.SequencedCollection<T>),
			Wrapper<KotlinMutableList<T>> by WrapperImpl(this), MutableSequencedCollection<T> {
		override fun reverse(): MutableSequencedCollection<T> = asReversed().asConnection()
	}

// kotlin.collections.List -> List

@Suppress("UNCHECKED_CAST")
fun <T> KotlinList<T>.asConnection(): List<T> =
	object: Collectable<T> by CollectableImpl(this), Sequenced<T> by SequencedImpl(this as java.util.SequencedCollection<T>),
			Listed<T> by ListedImpl(this), Wrapper<KotlinList<T>> by WrapperImpl(this), List<T> {
		override fun iterator(): ListIterator<T> = this@asConnection.listIterator()

		override fun reverse(): List<T> = this@asConnection.asReversed().asConnection()

		override fun subList(startInclusive: Int, endExclusive: Int): List<T> =
			this@asConnection.subList(startInclusive, endExclusive).asConnection()
	}

@Suppress("UNCHECKED_CAST")
fun <T> KotlinList<T>.asViewConnection(): MutatingListView<T> =
	object: Collectable<T> by CollectableImpl(this), Sequenced<T> by SequencedImpl(this as java.util.SequencedCollection<T>),
			Listed<T> by ListedImpl(this), Wrapper<KotlinList<T>> by WrapperImpl(this), MutatingListView<T> {
		override fun reverse(): MutatingListView<T> = this@asViewConnection.asReversed().asViewConnection()

		override fun subList(startInclusive: Int, endExclusive: Int): MutatingListView<T> =
			this@asViewConnection.subList(startInclusive, endExclusive).asViewConnection()
	}

@Suppress("UNCHECKED_CAST")
fun <T> KotlinMutableList<T>.asConnection(): MutableList<T> =
	object: Mutable<T> by MutableImpl(this), MutableSequenced<T> by MutableSequencedImpl(this as java.util.SequencedCollection<T>),
			MutableListed<T> by MutableListedImpl(this), Wrapper<KotlinMutableList<T>> by WrapperImpl(this), MutableList<T> {
		override fun reverse(): MutableList<T> = this@asConnection.asReversed().asConnection()

		override fun subList(startInclusive: Int, endExclusive: Int): MutableList<T> =
			this@asConnection.subList(startInclusive, endExclusive).asConnection()
	}

// kotlin.collections.Set -> Set

fun <T> KotlinSet<T>.asConnection(): Set<T> =
	object: Collectable<T> by CollectableImpl(this), Wrapper<KotlinSet<T>> by WrapperImpl(this), Set<T> {
		override fun iterator(): Iterator<T> = this@asConnection.iterator()
	}

fun <T> KotlinSet<T>.asViewConnection(): MutatingSetView<T> =
	object: Collectable<T> by CollectableImpl(this), Wrapper<KotlinSet<T>> by WrapperImpl(this), MutatingSetView<T> {}

fun <T> KotlinMutableSet<T>.asRemoveOnlyConnection(): RemoveOnlySet<T> =
	object: RemoveOnly<T> by RemoveOnlyImpl(this), Wrapper<KotlinMutableSet<T>> by WrapperImpl(this), RemoveOnlySet<T> {}

fun <T> KotlinMutableSet<T>.asConnection(): MutableSet<T> =
	object: Mutable<T> by MutableImpl(this), Wrapper<KotlinMutableSet<T>> by WrapperImpl(this), MutableSet<T> {}

// java.util.SequencedSet -> SequencedSet

fun <T> java.util.SequencedSet<T>.asConnection(): SequencedSet<T> =
	object: Collectable<T> by CollectableImpl(this), Sequenced<T> by SequencedImpl(this),
			Wrapper<java.util.SequencedSet<T>> by WrapperImpl(this), SequencedSet<T> {
		override fun iterator(): Iterator<T> = this@asConnection.iterator()

		override fun reverse(): SequencedSet<T> = this@asConnection.reversed().asConnection()
	}

fun <T> java.util.SequencedSet<T>.asViewConnection(): MutatingSequencedSetView<T> =
	object: Collectable<T> by CollectableImpl(this), Sequenced<T> by SequencedImpl(this),
			Wrapper<java.util.SequencedSet<T>> by WrapperImpl(this), MutatingSequencedSetView<T> {
		override fun reverse(): MutatingSequencedSetView<T> = this@asViewConnection.reversed().asViewConnection()
	}

fun <T> java.util.SequencedSet<T>.asRemoveOnlyConnection(): RemoveOnlySequencedSet<T> =
	object: RemoveOnly<T> by RemoveOnlyImpl(this), RemoveOnlySequenced<T> by RemoveOnlySequencedImpl(this),
			Wrapper<java.util.SequencedSet<T>> by WrapperImpl(this), RemoveOnlySequencedSet<T> {
		override fun reverse(): RemoveOnlySequencedSet<T> = this@asRemoveOnlyConnection.reversed().asRemoveOnlyConnection()
	}

fun <T> java.util.SequencedSet<T>.asMutableConnection(): MutableSequencedSet<T> =
	object: Mutable<T> by MutableImpl(this), MutableSequenced<T> by MutableSequencedImpl(this),
			Wrapper<java.util.SequencedSet<T>> by WrapperImpl(this), MutableSequencedSet<T> {
		override fun reverse(): MutableSequencedSet<T> = this@asMutableConnection.reversed().asMutableConnection()
	}

// java.util.SortedSet -> SortedNavigableSet

/*
 * Manual conversion from java.util.SortedSet to java.util.NavigableSet.
 * For all subSet-creation methods, higher() returns `null` if:
 * 1. Set is empty.
 * 2. No entries are higher than the given entry.
 *
 * In both cases, the returned `null` can be safely replaced with the given entry because:
 * 1. -> The subSet will also be empty.
 * 2. -> The entry is higher (or equal) than the highest entry.
 *
 * If the entry was `null`, higher() will throw (and propagate) a NullPointerException if the set does not permit `null` entries.
 * Otherwise, no visual differences are made(`null` replaced by `null`, or non-`null` not being replaced).
 * In other exceptional cases(where the `from` entry is higher than the `to` entry, or the entry is outside the (sub)set's range),
 * the underlying method will propagate the exceptions, mostly being IllegalArgumentException.
 */

fun <T> java.util.SortedSet<T>.asConnection(): SortedNavigableSet<T> =
	object: Collectable<T> by CollectableImpl(this), Sequenced<T> by SequencedImpl(this),
			Wrapper<java.util.SortedSet<T>> by WrapperImpl(this), Navigable<T> by SortedNavigableImpl(this), SortedNavigableSet<T> {
		override fun iterator(): Iterator<T> = this@asConnection.iterator()

		override fun reverse(): SequencedSet<T> = this@asConnection.reversed().asConnection()

		override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableSet<T> =
			this@asConnection.subSet(higher(from, fromInclusive) ?: from, higher(to, !toInclusive) ?: to).asConnection()

		override fun headSet(before: T, inclusive: Boolean): SortedNavigableSet<T> =
			this@asConnection.headSet(higher(before, !inclusive) ?: before).asConnection()

		override fun tailSet(after: T, inclusive: Boolean): SortedNavigableSet<T> =
			this@asConnection.tailSet(higher(after, inclusive) ?: after).asConnection()
	}

fun <T> java.util.SortedSet<T>.asViewConnection(): MutatingSortedNavigableSetView<T> =
	object: Collectable<T> by CollectableImpl(this), Sequenced<T> by SequencedImpl(this),
			Wrapper<java.util.SortedSet<T>> by WrapperImpl(this), Navigable<T> by SortedNavigableImpl(this), MutatingSortedNavigableSetView<T> {
		override fun reverse(): MutatingSortedNavigableSetView<T> = this@asViewConnection.reversed().asViewConnection()

		override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutatingSortedNavigableSetView<T> =
			this@asViewConnection.subSet(higher(from, fromInclusive) ?: from, higher(to, !toInclusive) ?: to).asViewConnection()

		override fun headSet(before: T, inclusive: Boolean): MutatingSortedNavigableSetView<T> =
			this@asViewConnection.headSet(higher(before, !inclusive) ?: before).asViewConnection()

		override fun tailSet(after: T, inclusive: Boolean): MutatingSortedNavigableSetView<T> =
			this@asViewConnection.tailSet(higher(after, inclusive) ?: after).asViewConnection()
	}

fun <T> java.util.SortedSet<T>.asRemoveOnlyConnection(): RemoveOnlySortedNavigableSet<T> =
	object: RemoveOnly<T> by RemoveOnlyImpl(this), RemoveOnlySequenced<T> by RemoveOnlySequencedImpl(this),
			Navigable<T> by SortedNavigableImpl(this), Wrapper<java.util.SortedSet<T>> by WrapperImpl(this), RemoveOnlySortedNavigableSet<T> {
		override fun reverse(): RemoveOnlySortedNavigableSet<T> = this@asRemoveOnlyConnection.reversed().asRemoveOnlyConnection()

		override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlySortedNavigableSet<T> =
			this@asRemoveOnlyConnection.subSet(higher(from, fromInclusive) ?: from, higher(to, !toInclusive) ?: to).asRemoveOnlyConnection()

		override fun headSet(before: T, inclusive: Boolean): RemoveOnlySortedNavigableSet<T> =
			this@asRemoveOnlyConnection.headSet(higher(before, !inclusive) ?: before).asRemoveOnlyConnection()

		override fun tailSet(after: T, inclusive: Boolean): RemoveOnlySortedNavigableSet<T> =
			this@asRemoveOnlyConnection.tailSet(higher(after, inclusive) ?: after).asRemoveOnlyConnection()
	}

fun <T> java.util.SortedSet<T>.asMutableConnection(): MutableSortedNavigableSet<T> =
	object: Mutable<T> by MutableImpl(this), MutableSequenced<T> by MutableSequencedImpl(this),
			Wrapper<java.util.SortedSet<T>> by WrapperImpl(this), Navigable<T> by SortedNavigableImpl(this), MutableSortedNavigableSet<T> {
		override fun reverse(): MutableSortedNavigableSet<T> = this@asMutableConnection.reversed().asMutableConnection()

		override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedNavigableSet<T> =
			this@asMutableConnection.subSet(higher(from, fromInclusive) ?: from, higher(to, !toInclusive) ?: to).asMutableConnection()

		override fun headSet(before: T, inclusive: Boolean): MutableSortedNavigableSet<T> =
			this@asMutableConnection.headSet(higher(before, !inclusive) ?: before).asMutableConnection()

		override fun tailSet(after: T, inclusive: Boolean): MutableSortedNavigableSet<T> =
			this@asMutableConnection.tailSet(higher(after, inclusive) ?: after).asMutableConnection()
	}

// java.util.NavigableSet -> NavigableSet

fun <T> java.util.NavigableSet<T>.asConnection(): NavigableSet<T> =
	object: Collectable<T> by CollectableImpl(this), Sequenced<T> by SequencedImpl(this), Navigable<T> by NavigableImpl(this),
			Wrapper<java.util.NavigableSet<T>> by WrapperImpl(this), NavigableSet<T> {
		override fun iterator(): Iterator<T> = this@asConnection.iterator()

		override fun reverse(): NavigableSet<T> = this@asConnection.reversed().asConnection()

		override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSet<T> =
			this@asConnection.subSet(from, fromInclusive, to, toInclusive).asConnection()

		override fun headSet(before: T, inclusive: Boolean): NavigableSet<T> =
			this@asConnection.headSet(before, inclusive).asConnection()

		override fun tailSet(after: T, inclusive: Boolean): NavigableSet<T> =
			this@asConnection.tailSet(after, inclusive).asConnection()
	}

fun <T> java.util.NavigableSet<T>.asViewConnection(): MutatingNavigableSetView<T> =
	object: Collectable<T> by CollectableImpl(this), Sequenced<T> by SequencedImpl(this),
			Navigable<T> by NavigableImpl(this), Wrapper<java.util.NavigableSet<T>> by WrapperImpl(this), MutatingNavigableSetView<T> {
		override fun reverse(): MutatingNavigableSetView<T> = this@asViewConnection.reversed().asViewConnection()

		override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutatingNavigableSetView<T> =
			this@asViewConnection.subSet(from, fromInclusive, to, toInclusive).asViewConnection()

		override fun headSet(before: T, inclusive: Boolean): MutatingNavigableSetView<T> =
			this@asViewConnection.headSet(before, inclusive).asViewConnection()

		override fun tailSet(after: T, inclusive: Boolean): MutatingNavigableSetView<T> =
			this@asViewConnection.tailSet(after, inclusive).asViewConnection()
	}

fun <T> java.util.NavigableSet<T>.asRemoveOnlyConnection(): RemoveOnlyNavigableSet<T> =
	object: Mutable<T> by MutableImpl(this), MutableSequenced<T> by MutableSequencedImpl(this),
			Navigable<T> by NavigableImpl(this), Wrapper<java.util.NavigableSet<T>> by WrapperImpl(this), RemoveOnlyNavigableSet<T> {
		override fun reverse(): RemoveOnlyNavigableSet<T> = this@asRemoveOnlyConnection.reversed().asRemoveOnlyConnection()

		override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlyNavigableSet<T> =
			this@asRemoveOnlyConnection.subSet(from, fromInclusive, to, toInclusive).asRemoveOnlyConnection()

		override fun headSet(before: T, inclusive: Boolean): RemoveOnlyNavigableSet<T> =
			this@asRemoveOnlyConnection.headSet(before, inclusive).asRemoveOnlyConnection()

		override fun tailSet(after: T, inclusive: Boolean): RemoveOnlyNavigableSet<T> =
			this@asRemoveOnlyConnection.tailSet(after, inclusive).asRemoveOnlyConnection()
	}

fun <T> java.util.NavigableSet<T>.asMutableConnection(): MutableNavigableSet<T> =
	object: Mutable<T> by MutableImpl(this), MutableSequenced<T> by MutableSequencedImpl(this),
			Navigable<T> by NavigableImpl(this), Wrapper<java.util.NavigableSet<T>> by WrapperImpl(this), MutableNavigableSet<T> {
		override fun reverse(): MutableNavigableSet<T> = this@asMutableConnection.reversed().asMutableConnection()

		override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableSet<T> =
			this@asMutableConnection.subSet(from, fromInclusive, to, toInclusive).asMutableConnection()

		override fun headSet(before: T, inclusive: Boolean): MutableNavigableSet<T> =
			this@asMutableConnection.headSet(before, inclusive).asMutableConnection()

		override fun tailSet(after: T, inclusive: Boolean): MutableNavigableSet<T> =
			this@asMutableConnection.tailSet(after, inclusive).asMutableConnection()
	}

// kotlin.collections.Map -> Map

fun <K, V> KotlinMap<K, V>.asConnection(): Map<K, V> =
	object: Mappable<K, V> by MappableImpl(this), Wrapper<KotlinMap<K, V>> by WrapperImpl(this), Map<K, V> {
		override val keys: Set<out K> = this@asConnection.keys.asConnection()

		override val values: Collection<out V> = this@asConnection.values.asConnection()

		override val entries: Set<KotlinMap.Entry<K, V>> = this@asConnection.entries.asConnection()
	}

fun <K, V> KotlinMap<K, V>.asViewConnection(): MutatingMapView<K, V> =
	object: Mappable<K, V> by MappableImpl(this), Wrapper<KotlinMap<K, V>> by WrapperImpl(this), MutatingMapView<K, V> {
		override val keys: MutatingSetView<out K> = this@asViewConnection.keys.asViewConnection()

		override val values: MutatingCollectionView<out V> = this@asViewConnection.values.asViewConnection()

		override val entries: MutatingSetView<KotlinMap.Entry<K, V>> = this@asViewConnection.entries.asViewConnection()
	}

fun <K, V> KotlinMutableMap<K, V>.asConnection(): MutableMap<K, V> =
	object: MutableMappable<K, V> by MutableMappableImpl(this), Wrapper<KotlinMutableMap<K, V>> by WrapperImpl(this), MutableMap<K, V> {
		override val keys: RemoveOnlySet<K> = this@asConnection.keys.asConnection()

		override val values: RemoveOnlyCollection<V> = this@asConnection.values.asConnection()

		override val entries: RemoveOnlySet<KotlinMutableMap.MutableEntry<K, V>> = this@asConnection.entries.asConnection()
	}

// java.util.SequencedMap -> SequencedMap

fun <K, V> java.util.SequencedMap<K, V>.asConnection(): SequencedMap<K, V> =
	object: Mappable<K, V> by MappableImpl(this), SequencedMappable<K, V> by SequencedMappableImpl(this),
			Wrapper<java.util.SequencedMap<K, V>> by WrapperImpl(this), SequencedMap<K, V> {
		override fun reversed(): SequencedMap<K, V> = this@asConnection.reversed().asConnection()

		override val keys: SequencedSet<out K> = this@asConnection.sequencedKeySet().asConnection()

		override val values: SequencedCollection<out V> = this@asConnection.sequencedValues().asConnection()

		override val entries: SequencedSet<out KotlinMap.Entry<K, V>> = this@asConnection.sequencedEntrySet().asConnection()
	}

fun <K, V> java.util.SequencedMap<K, V>.asViewConnection(): MutatingSequencedMapView<K, V> =
	object: Mappable<K, V> by MappableImpl(this), SequencedMappable<K, V> by SequencedMappableImpl(this),
			Wrapper<java.util.SequencedMap<K, V>> by WrapperImpl(this), MutatingSequencedMapView<K, V> {
		override fun reversed(): MutatingSequencedMapView<K, V> = this@asViewConnection.reversed().asViewConnection()

		override val keys: MutatingSequencedSetView<out K> = this@asViewConnection.sequencedKeySet().asViewConnection()

		override val values: MutatingSequencedCollectionView<out V> = this@asViewConnection.sequencedValues().asViewConnection()

		override val entries: MutatingSequencedSetView<out KotlinMap.Entry<K, V>> = this@asViewConnection.sequencedEntrySet().asViewConnection()
	}

fun <K, V> java.util.SequencedMap<K, V>.asMutableConnection(): MutableSequencedMap<K, V> =
	object: MutableMappable<K, V> by MutableMappableImpl(this), MutableSequencedMappable<K, V> by MutableSequencedMappableImpl(this),
			Wrapper<java.util.SequencedMap<K, V>> by WrapperImpl(this), MutableSequencedMap<K, V> {
		override fun reversed(): MutableSequencedMap<K, V> = this@asMutableConnection.reversed().asMutableConnection()

		override val keys: RemoveOnlySequencedSet<K> = this@asMutableConnection.sequencedKeySet().asMutableConnection()

		override val values: RemoveOnlySequencedCollection<V> = this@asMutableConnection.sequencedValues().asMutableConnection()

		override val entries: RemoveOnlySequencedSet<KotlinMutableMap.MutableEntry<K, V>> = this@asMutableConnection.sequencedEntrySet().asMutableConnection()
	}

// java.util.SortedMap -> SortedNavigableMap

// See the comment above [java.util.SortedSet.asConnection] for subMap-creation methods.

fun <K, V> java.util.SortedMap<K, V>.asConnection(): SortedNavigableMap<K, V> =
	object: Mappable<K, V> by MappableImpl(this), SequencedMappable<K, V> by SequencedMappableImpl(this),
			NavigableMappable<K, V> by SortedNavigableMappableImpl(this), Wrapper<java.util.SortedMap<K, V>> by WrapperImpl(this), SortedNavigableMap<K, V> {
		override fun reversed(): SortedNavigableMap<K, V> = this@asConnection.reversed().asConnection()

		override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableMap<K, V> =
			this@asConnection.subMap(higherKey(from, fromInclusive) ?: from, higherKey(to, !toInclusive) ?: to).asConnection()

		override fun headMap(before: K, inclusive: Boolean): SortedNavigableMap<K, V> =
			this@asConnection.headMap(higherKey(before, !inclusive) ?: before).asConnection()

		override fun tailMap(after: K, inclusive: Boolean): SortedNavigableMap<K, V> =
			this@asConnection.tailMap(higherKey(after, inclusive) ?: after).asConnection()

		override val keys: SortedNavigableSet<K> = this@asConnection.keySet().asConnection()

		override val values: SequencedCollection<V> = this@asConnection.sequencedValues().asConnection()

		override val entries: SequencedSet<out KotlinMap.Entry<K, V>> = this@asConnection.sequencedEntrySet().asConnection()
	}

fun <K, V> java.util.SortedMap<K, V>.asViewConnection(): MutatingSortedNavigableMapView<K, V> =
	object: Mappable<K, V> by MappableImpl(this), SequencedMappable<K, V> by SequencedMappableImpl(this),
			NavigableMappable<K, V> by SortedNavigableMappableImpl(this), Wrapper<java.util.SortedMap<K, V>> by WrapperImpl(this), MutatingSortedNavigableMapView<K, V> {
		override fun reversed(): MutatingSortedNavigableMapView<K, V> = this@asViewConnection.reversed().asViewConnection()

		override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutatingSortedNavigableMapView<K, V> =
			this@asViewConnection.subMap(higherKey(from, fromInclusive) ?: from, higherKey(to, !toInclusive) ?: to).asViewConnection()

		override fun headMap(before: K, inclusive: Boolean): MutatingSortedNavigableMapView<K, V> =
			this@asViewConnection.headMap(higherKey(before, !inclusive) ?: before).asViewConnection()

		override fun tailMap(after: K, inclusive: Boolean): MutatingSortedNavigableMapView<K, V> =
			this@asViewConnection.tailMap(higherKey(after, inclusive) ?: after).asViewConnection()

		override val keys: MutatingSortedNavigableSetView<K> = this@asViewConnection.keySet().asViewConnection()

		override val values: MutatingSequencedCollectionView<V> = this@asViewConnection.sequencedValues().asViewConnection()

		override val entries: MutatingSequencedSetView<out KotlinMap.Entry<K, V>> = this@asViewConnection.sequencedEntrySet().asViewConnection()
	}

fun <K, V> java.util.SortedMap<K, V>.asMutableConnection(): MutableSortedNavigableMap<K, V> =
	object: MutableMappable<K, V> by MutableMappableImpl(this), MutableSequencedMappable<K, V> by MutableSequencedMappableImpl(this),
			NavigableMappable<K, V> by SortedNavigableMappableImpl(this), Wrapper<java.util.SortedMap<K, V>> by WrapperImpl(this), MutableSortedNavigableMap<K, V> {
		override fun reversed(): MutableSortedNavigableMap<K, V> = this@asMutableConnection.reversed().asMutableConnection()

		override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedNavigableMap<K, V> =
			this@asMutableConnection.subMap(higherKey(from, fromInclusive) ?: from, higherKey(to, !toInclusive) ?: to).asMutableConnection()

		override fun headMap(before: K, inclusive: Boolean): MutableSortedNavigableMap<K, V> =
			this@asMutableConnection.headMap(higherKey(before, !inclusive) ?: before).asMutableConnection()

		override fun tailMap(after: K, inclusive: Boolean): MutableSortedNavigableMap<K, V> =
			this@asMutableConnection.tailMap(higherKey(after, inclusive) ?: after).asMutableConnection()

		override val keys: RemoveOnlySortedNavigableSet<K> = this@asMutableConnection.keySet().asRemoveOnlyConnection()

		override val values: RemoveOnlySequencedCollection<V> = this@asMutableConnection.sequencedValues().asRemoveOnlyConnection()

		override val entries: RemoveOnlySequencedSet<KotlinMutableMap.MutableEntry<K, V>> = this@asMutableConnection.sequencedEntrySet().asRemoveOnlyConnection()
	}

//<editor-fold defaultState="collapsed" desc="// java.util.SortedMap keySet implementation">
private fun <K> java.util.SortedMap<K, *>.keySet(): java.util.NavigableSet<K> {
	val keys = sequencedKeySet()
	return object: java.util.SequencedSet<K> by keys, java.util.NavigableSet<K> {
		override fun reversed(): java.util.NavigableSet<K> = super.reversed()

		override fun comparator(): Comparator<in K>? = this@keySet.comparator()

		override fun first(): K = keys.first

		override fun last(): K = keys.last

		override fun pollFirst(): K? = if(isNotEmpty()) keys.removeFirst() else null

		override fun pollLast(): K? = if(isNotEmpty()) keys.removeLast() else null

		override fun descendingSet(): java.util.NavigableSet<K> = this@keySet.reversed().keySet()

		override fun descendingIterator(): MutableIterator<K> = descendingSet().iterator()

		override fun higher(e: K): K? = this@keySet.reversed().headMap(e).lastKey()

		override fun ceiling(e: K): K? = this@keySet.tailMap(e).firstKey()

		override fun floor(e: K): K? = this@keySet.reversed().tailMap(e).firstKey()

		override fun lower(e: K): K? = this@keySet.headMap(e).lastKey()

		override fun tailSet(fromElement: K): java.util.NavigableSet<K> = tailSet(fromElement, true)

		override fun tailSet(fromElement: K, inclusive: Boolean): java.util.NavigableSet<K> =
			this@keySet.tailMap(if(inclusive) fromElement else higher(fromElement)).keySet()

		override fun headSet(toElement: K): java.util.NavigableSet<K> = headSet(toElement, false)

		override fun headSet(toElement: K, inclusive: Boolean): java.util.NavigableSet<K> =
			this@keySet.headMap(if(inclusive) higher(toElement) else toElement).keySet()

		override fun subSet(fromElement: K, toElement: K): java.util.NavigableSet<K> = subSet(fromElement, true, toElement, false)

		override fun subSet(fromElement: K, fromInclusive: Boolean, toElement: K, toInclusive: Boolean): java.util.NavigableSet<K> =
			this@keySet.subMap(if(fromInclusive) fromElement else higher(fromElement), if(toInclusive) higher(toElement) else toElement).keySet()
	}
}
//</editor-fold>

// java.util.NavigableMap -> NavigableMap

fun <K, V> java.util.NavigableMap<K, V>.asConnection(): NavigableMap<K, V> =
	object: Mappable<K, V> by MappableImpl(this), SequencedMappable<K, V> by SequencedMappableImpl(this),
			NavigableMappable<K, V> by NavigableMappableImpl(this), Wrapper<java.util.NavigableMap<K, V>> by WrapperImpl(this), NavigableMap<K, V> {
		override fun reversed(): NavigableMap<K, V> = this@asConnection.reversed().asConnection()

		override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableMap<K, V> =
			this@asConnection.subMap(from, fromInclusive, to, toInclusive).asConnection()

		override fun headMap(before: K, inclusive: Boolean): SortedNavigableMap<K, V> =
			this@asConnection.headMap(before, inclusive).asConnection()

		override fun tailMap(after: K, inclusive: Boolean): SortedNavigableMap<K, V> =
			this@asConnection.tailMap(after, inclusive).asConnection()

		override val keys: NavigableSet<K> = this@asConnection.navigableKeySet().asConnection()

		override val values: SequencedCollection<V> = this@asConnection.sequencedValues().asConnection()

		override val entries: SequencedSet<out KotlinMap.Entry<K, V>> = this@asConnection.sequencedEntrySet().asConnection()
	}

fun <K, V> java.util.NavigableMap<K, V>.asViewConnection(): MutatingNavigableMapView<K, V> =
	object: Mappable<K, V> by MappableImpl(this), SequencedMappable<K, V> by SequencedMappableImpl(this),
			NavigableMappable<K, V> by NavigableMappableImpl(this), Wrapper<java.util.NavigableMap<K, V>> by WrapperImpl(this), MutatingNavigableMapView<K, V> {
		override fun reversed(): MutatingNavigableMapView<K, V> = this@asViewConnection.reversed().asViewConnection()

		override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutatingSortedNavigableMapView<K, V> =
			this@asViewConnection.subMap(from, fromInclusive, to, toInclusive).asViewConnection()

		override fun headMap(before: K, inclusive: Boolean): MutatingSortedNavigableMapView<K, V> =
			this@asViewConnection.headMap(before, inclusive).asViewConnection()

		override fun tailMap(after: K, inclusive: Boolean): MutatingSortedNavigableMapView<K, V> =
			this@asViewConnection.tailMap(after, inclusive).asViewConnection()

		override val keys: MutatingNavigableSetView<K> = this@asViewConnection.navigableKeySet().asViewConnection()

		override val values: MutatingSequencedCollectionView<V> = this@asViewConnection.sequencedValues().asViewConnection()

		override val entries: MutatingSequencedSetView<out KotlinMap.Entry<K, V>> = this@asViewConnection.sequencedEntrySet().asViewConnection()
	}

fun <K, V> java.util.NavigableMap<K, V>.asMutableConnection(): MutableNavigableMap<K, V> =
	object: MutableMappable<K, V> by MutableMappableImpl(this), MutableSequencedMappable<K, V> by MutableSequencedMappableImpl(this),
			NavigableMappable<K, V> by NavigableMappableImpl(this), Wrapper<java.util.NavigableMap<K, V>> by WrapperImpl(this), MutableNavigableMap<K, V> {
		override fun reversed(): MutableNavigableMap<K, V> = this@asMutableConnection.reversed().asMutableConnection()

		override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedNavigableMap<K, V> =
			this@asMutableConnection.subMap(from, fromInclusive, to, toInclusive).asMutableConnection()

		override fun headMap(before: K, inclusive: Boolean): MutableSortedNavigableMap<K, V> =
			this@asMutableConnection.headMap(before, inclusive).asMutableConnection()

		override fun tailMap(after: K, inclusive: Boolean): MutableSortedNavigableMap<K, V> =
			this@asMutableConnection.tailMap(after, inclusive).asMutableConnection()

		override val keys: RemoveOnlyNavigableSet<K> = this@asMutableConnection.navigableKeySet().asRemoveOnlyConnection()

		override val values: RemoveOnlySequencedCollection<V> = this@asMutableConnection.sequencedValues().asRemoveOnlyConnection()

		override val entries: RemoveOnlySequencedSet<KotlinMutableMap.MutableEntry<K, V>> = this@asMutableConnection.sequencedEntrySet().asRemoveOnlyConnection()
	}
