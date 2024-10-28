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
import io.github.spacedvoid.connection.characteristic.Sorted
import io.github.spacedvoid.connection.characteristic.SortedImpl
import io.github.spacedvoid.connection.characteristic.SortedMappable
import io.github.spacedvoid.connection.characteristic.SortedMappableImpl
import io.github.spacedvoid.connection.characteristic.Wrapper
import io.github.spacedvoid.connection.characteristic.WrapperImpl
import java.util.TreeSet
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

		override fun slice(startInclusive: Int, endExclusive: Int): List<T> =
			this@asConnection.subList(startInclusive, endExclusive).asConnection()
	}

@Suppress("UNCHECKED_CAST")
fun <T> KotlinList<T>.asViewConnection(): MutatingListView<T> =
	object: Collectable<T> by CollectableImpl(this), Sequenced<T> by SequencedImpl(this as java.util.SequencedCollection<T>),
			Listed<T> by ListedImpl(this), Wrapper<KotlinList<T>> by WrapperImpl(this), MutatingListView<T> {
		override fun reverse(): MutatingListView<T> = this@asViewConnection.asReversed().asViewConnection()

		override fun slice(startInclusive: Int, endExclusive: Int): MutatingListView<T> =
			this@asViewConnection.subList(startInclusive, endExclusive).asViewConnection()
	}

@Suppress("UNCHECKED_CAST")
fun <T> KotlinMutableList<T>.asConnection(): MutableList<T> =
	object: Mutable<T> by MutableImpl(this), MutableSequenced<T> by MutableSequencedImpl(this as java.util.SequencedCollection<T>),
			MutableListed<T> by MutableListedImpl(this), Wrapper<KotlinMutableList<T>> by WrapperImpl(this), MutableList<T> {
		override fun reverse(): MutableList<T> = this@asConnection.asReversed().asConnection()

		override fun slice(startInclusive: Int, endExclusive: Int): MutableList<T> =
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

// java.util.SortedSet -> SortedSet

fun <T> java.util.SortedSet<T>.asConnection(): SortedSet<T> =
	object: Collectable<T> by CollectableImpl(this), Sequenced<T> by SequencedImpl(this),
			Wrapper<java.util.SortedSet<T>> by WrapperImpl(this), Sorted<T> by SortedImpl(this), SortedSet<T> {
		override fun iterator(): Iterator<T> = this@asConnection.iterator()

		override fun reverse(): SequencedSet<T> = this@asConnection.reversed().asConnection()

		override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): SortedSet<T> =
			sub(from, to, fromInclusive, toInclusive).asConnection()

		override fun headSet(before: T, inclusive: Boolean): SortedSet<T> = head(before, inclusive).asConnection()

		override fun tailSet(after: T, inclusive: Boolean): SortedSet<T> = tail(after, inclusive).asConnection()
	}

fun <T> java.util.SortedSet<T>.asViewConnection(): MutatingSortedSetView<T> =
	object: Collectable<T> by CollectableImpl(this), Sequenced<T> by SequencedImpl(this),
			Wrapper<java.util.SortedSet<T>> by WrapperImpl(this), Sorted<T> by SortedImpl(this), MutatingSortedSetView<T> {
		override fun reverse(): MutatingSortedSetView<T> = this@asViewConnection.reversed().asViewConnection()

		override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutatingSortedSetView<T> =
			sub(from, to, fromInclusive, toInclusive).asViewConnection()

		override fun headSet(before: T, inclusive: Boolean): MutatingSortedSetView<T> = head(before, inclusive).asViewConnection()

		override fun tailSet(after: T, inclusive: Boolean): MutatingSortedSetView<T> = tail(after, inclusive).asViewConnection()
	}

fun <T> java.util.SortedSet<T>.asRemoveOnlyConnection(): RemoveOnlySortedSet<T> =
	object: RemoveOnly<T> by RemoveOnlyImpl(this), RemoveOnlySequenced<T> by RemoveOnlySequencedImpl(this),
			Sorted<T> by SortedImpl(this), Wrapper<java.util.SortedSet<T>> by WrapperImpl(this), RemoveOnlySortedSet<T> {
		override fun reverse(): RemoveOnlySortedSet<T> = this@asRemoveOnlyConnection.reversed().asRemoveOnlyConnection()

		override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlySortedSet<T> =
			sub(from, to, fromInclusive, toInclusive).asRemoveOnlyConnection()

		override fun headSet(before: T, inclusive: Boolean): RemoveOnlySortedSet<T> = head(before, inclusive).asRemoveOnlyConnection()

		override fun tailSet(after: T, inclusive: Boolean): RemoveOnlySortedSet<T> = tail(after, inclusive).asRemoveOnlyConnection()
	}

fun <T> java.util.SortedSet<T>.asMutableConnection(): MutableSortedSet<T> =
	object: Mutable<T> by MutableImpl(this), MutableSequenced<T> by MutableSequencedImpl(this),
			Wrapper<java.util.SortedSet<T>> by WrapperImpl(this), Sorted<T> by SortedImpl(this), MutableSortedSet<T> {
		override fun reverse(): MutableSortedSet<T> = this@asMutableConnection.reversed().asMutableConnection()

		override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedSet<T> =
			sub(from, to, fromInclusive, toInclusive).asMutableConnection()

		override fun headSet(before: T, inclusive: Boolean): MutableSortedSet<T> = head(before, inclusive).asMutableConnection()

		override fun tailSet(after: T, inclusive: Boolean): MutableSortedSet<T> = tail(after, inclusive).asMutableConnection()
	}

// java.util.NavigableSet -> NavigableSet

fun <T> java.util.NavigableSet<T>.asConnection(): NavigableSet<T> =
	object: Collectable<T> by CollectableImpl(this), Sequenced<T> by SequencedImpl(this), Sorted<T> by SortedImpl(this),
			Navigable<T> by NavigableImpl(this), Wrapper<java.util.NavigableSet<T>> by WrapperImpl(this), NavigableSet<T> {
		override fun iterator(): Iterator<T> = this@asConnection.iterator()

		override fun reverse(): NavigableSet<T> = this@asConnection.reversed().asConnection()

		override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSet<T> =
			sub(from, to, fromInclusive, toInclusive).asNavigable().asConnection()

		override fun headSet(before: T, inclusive: Boolean): NavigableSet<T> = head(before, inclusive).asNavigable().asConnection()

		override fun tailSet(after: T, inclusive: Boolean): NavigableSet<T> = tail(after, inclusive).asNavigable().asConnection()
	}

fun <T> java.util.NavigableSet<T>.asViewConnection(): MutatingNavigableSetView<T> =
	object: Collectable<T> by CollectableImpl(this), Sequenced<T> by SequencedImpl(this), Sorted<T> by SortedImpl(this),
			Navigable<T> by NavigableImpl(this), Wrapper<java.util.NavigableSet<T>> by WrapperImpl(this), MutatingNavigableSetView<T> {
		override fun reverse(): MutatingNavigableSetView<T> = this@asViewConnection.reversed().asViewConnection()

		override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutatingNavigableSetView<T> =
			sub(from, to, fromInclusive, toInclusive).asNavigable().asViewConnection()

		override fun headSet(before: T, inclusive: Boolean): MutatingNavigableSetView<T> =
			head(before, inclusive).asNavigable().asViewConnection()

		override fun tailSet(after: T, inclusive: Boolean): MutatingNavigableSetView<T> =
			tail(after, inclusive).asNavigable().asViewConnection()
	}

fun <T> java.util.NavigableSet<T>.asRemoveOnlyConnection(): RemoveOnlyNavigableSet<T> =
	object: Mutable<T> by MutableImpl(this), MutableSequenced<T> by MutableSequencedImpl(this), Sorted<T> by SortedImpl(this),
			Navigable<T> by NavigableImpl(this), Wrapper<java.util.NavigableSet<T>> by WrapperImpl(this), RemoveOnlyNavigableSet<T> {
		override fun reverse(): RemoveOnlyNavigableSet<T> = this@asRemoveOnlyConnection.reversed().asRemoveOnlyConnection()

		override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlyNavigableSet<T> =
			sub(from, to, fromInclusive, toInclusive).asNavigable().asRemoveOnlyConnection()

		override fun headSet(before: T, inclusive: Boolean): RemoveOnlyNavigableSet<T> =
			head(before, inclusive).asNavigable().asRemoveOnlyConnection()

		override fun tailSet(after: T, inclusive: Boolean): RemoveOnlyNavigableSet<T> =
			tail(after, inclusive).asNavigable().asRemoveOnlyConnection()
	}

fun <T> java.util.NavigableSet<T>.asMutableConnection(): MutableNavigableSet<T> =
	object: Mutable<T> by MutableImpl(this), MutableSequenced<T> by MutableSequencedImpl(this), Sorted<T> by SortedImpl(this),
			Navigable<T> by NavigableImpl(this), Wrapper<java.util.NavigableSet<T>> by WrapperImpl(this), MutableNavigableSet<T> {
		override fun reverse(): MutableNavigableSet<T> = this@asMutableConnection.reversed().asMutableConnection()

		override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableSet<T> =
			sub(from, to, fromInclusive, toInclusive).asNavigable().asMutableConnection()

		override fun headSet(before: T, inclusive: Boolean): MutableNavigableSet<T> =
			head(before, inclusive).asNavigable().asMutableConnection()

		override fun tailSet(after: T, inclusive: Boolean): MutableNavigableSet<T> =
			tail(after, inclusive).asNavigable().asMutableConnection()
	}

//<editor-fold defaultState="collapsed" desc="// SortedSet as NavigableSet behavior implementation">
private fun <T> java.util.SortedSet<T>.higherEntry(than: T, inclusive: Boolean): T? =
	if(this is java.util.NavigableSet<T>) if(inclusive) ceiling(than) else higher(than)
	else if(inclusive) tailSet(than).run { if(isNotEmpty()) first() else null }
	else reversed().headSet(than).run { if(isNotEmpty()) last() else null }

private fun <T> java.util.SortedSet<T>.lowerEntry(than: T, inclusive: Boolean): T? =
	if(this is java.util.NavigableSet<T>) if(inclusive) floor(than) else lower(than)
	else if(inclusive) reversed().tailSet(than).run { if(isNotEmpty()) first() else null }
	else headSet(than).run { if(isNotEmpty()) last() else null }

private fun <T> java.util.SortedSet<T>.sub(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): java.util.SortedSet<T> =
	if(this is java.util.NavigableSet<T>) subSet(from, fromInclusive, to, toInclusive)
	else subSet(higherEntry(from, fromInclusive) ?: from, lowerEntry(to, toInclusive) ?: to)

private fun <T> java.util.SortedSet<T>.head(before: T, inclusive: Boolean): java.util.SortedSet<T> =
	headSet(lowerEntry(before, inclusive) ?: before)

private fun <T> java.util.SortedSet<T>.tail(after: T, inclusive: Boolean): java.util.SortedSet<T> =
	tailSet(higherEntry(after, inclusive) ?: after)

private fun <T> java.util.SortedSet<T>.asNavigable(): java.util.NavigableSet<T> =
	object: java.util.SortedSet<T> by this, java.util.NavigableSet<T> {
		override fun lower(e: T): T? = lowerEntry(e, inclusive = false)

		override fun floor(e: T): T? = lowerEntry(e, inclusive = true)

		override fun higher(e: T): T? = higherEntry(e, inclusive = false)

		override fun ceiling(e: T): T? = higherEntry(e, inclusive = true)

		override fun pollFirst(): T? = if(isNotEmpty()) removeFirst() else null

		override fun pollLast(): T? = if(isNotEmpty()) removeLast() else null

		override fun subSet(fromElement: T, fromInclusive: Boolean, toElement: T, toInclusive: Boolean): java.util.NavigableSet<T> =
			sub(fromElement, toElement, fromInclusive, toInclusive).asNavigable()

		override fun headSet(toElement: T, inclusive: Boolean): java.util.NavigableSet<T> = head(toElement, inclusive).asNavigable()

		override fun tailSet(fromElement: T, inclusive: Boolean): java.util.NavigableSet<T> = tail(fromElement, inclusive).asNavigable()

		override fun descendingSet(): java.util.NavigableSet<T> = reversed()

		override fun descendingIterator(): MutableIterator<T> = reversed().iterator()
	}
//</editor-fold>

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
		override val keys: SequencedSet<out K> = this@asConnection.sequencedKeySet().asConnection()

		override val values: SequencedCollection<out V> = this@asConnection.sequencedValues().asConnection()

		override val entries: SequencedSet<out KotlinMap.Entry<K, V>> = this@asConnection.sequencedEntrySet().asConnection()
	}

fun <K, V> java.util.SequencedMap<K, V>.asViewConnection(): MutatingSequencedMapView<K, V> =
	object: Mappable<K, V> by MappableImpl(this), SequencedMappable<K, V> by SequencedMappableImpl(this),
			Wrapper<java.util.SequencedMap<K, V>> by WrapperImpl(this), MutatingSequencedMapView<K, V> {
		override val keys: MutatingSequencedSetView<out K> = this@asViewConnection.sequencedKeySet().asViewConnection()

		override val values: MutatingSequencedCollectionView<out V> = this@asViewConnection.sequencedValues().asViewConnection()

		override val entries: MutatingSequencedSetView<out KotlinMap.Entry<K, V>> = this@asViewConnection.sequencedEntrySet().asViewConnection()
	}

fun <K, V> java.util.SequencedMap<K, V>.asMutableConnection(): MutableSequencedMap<K, V> =
	object: MutableMappable<K, V> by MutableMappableImpl(this), MutableSequencedMappable<K, V> by MutableSequencedMappableImpl(this),
			Wrapper<java.util.SequencedMap<K, V>> by WrapperImpl(this), MutableSequencedMap<K, V> {
		override val keys: RemoveOnlySequencedSet<K> = this@asMutableConnection.sequencedKeySet().asMutableConnection()

		override val values: RemoveOnlySequencedCollection<V> = this@asMutableConnection.sequencedValues().asMutableConnection()

		override val entries: RemoveOnlySequencedSet<KotlinMutableMap.MutableEntry<K, V>> =
			this@asMutableConnection.sequencedEntrySet().asMutableConnection()
	}

// java.util.SortedMap -> SortedMap

fun <K, V> java.util.SortedMap<K, V>.asConnection(): SortedMap<K, V> =
	object: Mappable<K, V> by MappableImpl(this), SequencedMappable<K, V> by SequencedMappableImpl(this),
			SortedMappable<K, V> by SortedMappableImpl(this), Wrapper<java.util.SortedMap<K, V>> by WrapperImpl(this), SortedMap<K, V> {
		override fun reversed(): SortedMap<K, V> = this@asConnection.reversed().asConnection()

		override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): SortedMap<K, V> =
			sub(from, to, fromInclusive, toInclusive).asConnection()

		override fun headMap(before: K, inclusive: Boolean): SortedMap<K, V> = head(before, inclusive).asConnection()

		override fun tailMap(after: K, inclusive: Boolean): SortedMap<K, V> = tail(after, inclusive).asConnection()

		override val keys: SortedSet<K> =
			(TreeSet(this@asConnection.comparator()).also { it.addAll(this@asConnection.keys) } as java.util.SortedSet<K>).asConnection()

		override val values: SequencedCollection<V> = this@asConnection.sequencedValues().asConnection()

		override val entries: SortedSet<out KotlinMap.Entry<K, V>> =
			(TreeSet(Comparator.comparing(KotlinMap.Entry<K, V>::key, this@asConnection.comparator())) as java.util.SortedSet<out KotlinMap.Entry<K, V>>)
				.asConnection()
	}

fun <K, V> java.util.SortedMap<K, V>.asViewConnection(): MutatingSortedMapView<K, V> =
	object: Mappable<K, V> by MappableImpl(this), SequencedMappable<K, V> by SequencedMappableImpl(this),
			SortedMappable<K, V> by SortedMappableImpl(this), Wrapper<java.util.SortedMap<K, V>> by WrapperImpl(this), MutatingSortedMapView<K, V> {
		override fun reversed(): MutatingSortedMapView<K, V> = this@asViewConnection.reversed().asViewConnection()

		override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutatingSortedMapView<K, V> =
			sub(from, to, fromInclusive, toInclusive).asViewConnection()

		override fun headMap(before: K, inclusive: Boolean): MutatingSortedMapView<K, V> = head(before, inclusive).asViewConnection()

		override fun tailMap(after: K, inclusive: Boolean): MutatingSortedMapView<K, V> = tail(after, inclusive).asViewConnection()

		override val keys: MutatingSortedSetView<K> =
			(TreeSet(this@asViewConnection.comparator())
				.also { it.addAll(this@asViewConnection.keys) } as java.util.SortedSet<K>).asViewConnection()

		override val values: MutatingSequencedCollectionView<V> = this@asViewConnection.sequencedValues().asViewConnection()

		override val entries: MutatingSortedSetView<out KotlinMap.Entry<K, V>> =
			(TreeSet(
				Comparator.comparing(
					KotlinMap.Entry<K, V>::key,
					this@asViewConnection.comparator()
				)
			) as java.util.SortedSet<out KotlinMap.Entry<K, V>>).asViewConnection()
	}

fun <K, V> java.util.SortedMap<K, V>.asMutableConnection(): MutableSortedMap<K, V> =
	object: MutableMappable<K, V> by MutableMappableImpl(this), MutableSequencedMappable<K, V> by MutableSequencedMappableImpl(this),
			SortedMappable<K, V> by SortedMappableImpl(this), Wrapper<java.util.SortedMap<K, V>> by WrapperImpl(this), MutableSortedMap<K, V> {
		override fun reversed(): MutableSortedMap<K, V> = this@asMutableConnection.reversed().asMutableConnection()

		override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedMap<K, V> =
			sub(from, to, fromInclusive, toInclusive).asMutableConnection()

		override fun headMap(before: K, inclusive: Boolean): MutableSortedMap<K, V> = head(before, inclusive).asMutableConnection()

		override fun tailMap(after: K, inclusive: Boolean): MutableSortedMap<K, V> = head(after, inclusive).asMutableConnection()

		override val keys: RemoveOnlySortedSet<K> =
			(TreeSet(this@asMutableConnection.comparator())
				.also { it.addAll(this@asMutableConnection.keys) } as java.util.SortedSet<K>).asMutableConnection()

		override val values: RemoveOnlySequencedCollection<V> = this@asMutableConnection.sequencedValues().asRemoveOnlyConnection()

		override val entries: RemoveOnlySortedSet<KotlinMutableMap.MutableEntry<K, V>> =
			(TreeSet(
				Comparator.comparing(
					KotlinMutableMap.MutableEntry<K, V>::key,
					this@asMutableConnection.comparator()
				)
			) as java.util.SortedSet<KotlinMutableMap.MutableEntry<K, V>>).asMutableConnection()
	}

// java.util.NavigableMap -> NavigableMap

fun <K, V> java.util.NavigableMap<K, V>.asConnection(): NavigableMap<K, V> =
	object: Mappable<K, V> by MappableImpl(this), SequencedMappable<K, V> by SequencedMappableImpl(this),
			SortedMappable<K, V> by SortedMappableImpl(this), NavigableMappable<K, V> by NavigableMappableImpl(this),
			Wrapper<java.util.NavigableMap<K, V>> by WrapperImpl(this), NavigableMap<K, V> {
		override fun reversed(): NavigableMap<K, V> = this@asConnection.reversed().asConnection()

		override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): NavigableMap<K, V> =
			sub(from, to, fromInclusive, toInclusive).asNavigable().asConnection()

		override fun headMap(before: K, inclusive: Boolean): NavigableMap<K, V> = head(before, inclusive).asNavigable().asConnection()

		override fun tailMap(after: K, inclusive: Boolean): NavigableMap<K, V> = tail(after, inclusive).asNavigable().asConnection()

		override val keys: NavigableSet<K> =
			TreeSet(this@asConnection.comparator()).also { it.addAll(this@asConnection.keys) }.asConnection()

		override val values: SequencedCollection<V> = this@asConnection.sequencedValues().asConnection()

		override val entries: NavigableSet<KotlinMutableMap.MutableEntry<K, V>> =
			TreeSet(
				Comparator.comparing(
					KotlinMutableMap.MutableEntry<K, V>::key,
					this@asConnection.comparator()
				)
			).asConnection()
	}

fun <K, V> java.util.NavigableMap<K, V>.asViewConnection(): MutatingNavigableMapView<K, V> =
	object: Mappable<K, V> by MappableImpl(this), SequencedMappable<K, V> by SequencedMappableImpl(this),
			SortedMappable<K, V> by SortedMappableImpl(this), NavigableMappable<K, V> by NavigableMappableImpl(this),
			Wrapper<java.util.NavigableMap<K, V>> by WrapperImpl(this), MutatingNavigableMapView<K, V> {
		override fun reversed(): MutatingNavigableMapView<K, V> = this@asViewConnection.reversed().asViewConnection()

		override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutatingNavigableMapView<K, V> =
			sub(from, to, fromInclusive, toInclusive).asNavigable().asViewConnection()

		override fun headMap(before: K, inclusive: Boolean): MutatingNavigableMapView<K, V> = head(before, inclusive).asNavigable().asViewConnection()

		override fun tailMap(after: K, inclusive: Boolean): MutatingNavigableMapView<K, V> = tail(after, inclusive).asNavigable().asViewConnection()

		override val keys: MutatingNavigableSetView<K> =
			TreeSet(this@asViewConnection.comparator()).also { it.addAll(this@asViewConnection.keys) }.asViewConnection()

		override val values: MutatingSequencedCollectionView<V> = this@asViewConnection.sequencedValues().asViewConnection()

		override val entries: MutatingNavigableSetView<KotlinMutableMap.MutableEntry<K, V>> =
			TreeSet(
				Comparator.comparing(
					KotlinMutableMap.MutableEntry<K, V>::key,
					this@asViewConnection.comparator()
				)
			).asViewConnection()
	}

fun <K, V> java.util.NavigableMap<K, V>.asMutableConnection(): MutableNavigableMap<K, V> =
	object: MutableMappable<K, V> by MutableMappableImpl(this), MutableSequencedMappable<K, V> by MutableSequencedMappableImpl(this),
			SortedMappable<K, V> by SortedMappableImpl(this), NavigableMappable<K, V> by NavigableMappableImpl(this),
			Wrapper<java.util.NavigableMap<K, V>> by WrapperImpl(this), MutableNavigableMap<K, V> {
		override fun reversed(): MutableNavigableMap<K, V> = this@asMutableConnection.reversed().asMutableConnection()

		override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableMap<K, V> =
			sub(from, to, fromInclusive, toInclusive).asNavigable().asMutableConnection()

		override fun headMap(before: K, inclusive: Boolean): MutableNavigableMap<K, V> = head(before, inclusive).asNavigable().asMutableConnection()

		override fun tailMap(after: K, inclusive: Boolean): MutableNavigableMap<K, V> = tail(after, inclusive).asNavigable().asMutableConnection()

		override val keys: RemoveOnlyNavigableSet<K> =
			TreeSet(this@asMutableConnection.comparator()).also { it.addAll(this@asMutableConnection.keys) }.asRemoveOnlyConnection()

		override val values: RemoveOnlySequencedCollection<V> = this@asMutableConnection.sequencedValues().asRemoveOnlyConnection()

		override val entries: RemoveOnlyNavigableSet<KotlinMutableMap.MutableEntry<K, V>> =
			TreeSet(
				Comparator.comparing(
					KotlinMutableMap.MutableEntry<K, V>::key,
					this@asMutableConnection.comparator()
				)
			).asRemoveOnlyConnection()
	}

//<editor-fold defaultState="collapsed" desc="// SortedMap to NavigableMap behavior implementation">
private fun <K, V> java.util.SortedMap<K, V>.higherEntry(than: K, inclusive: Boolean): KotlinMap.Entry<K, V>? =
	if(this is java.util.NavigableMap<K, V>) if(inclusive) ceilingEntry(than) else higherEntry(than)
	else if(inclusive) tailMap(than).run { if(isNotEmpty()) firstEntry() else null }
	else reversed().headMap(than).run { if(isNotEmpty()) lastEntry() else null }

private fun <K, V> java.util.SortedMap<K, V>.lowerEntry(than: K, inclusive: Boolean): KotlinMap.Entry<K, V>? =
	if(this is java.util.NavigableMap<K, V>) if(inclusive) floorEntry(than) else lowerEntry(than)
	else if(inclusive) reversed().tailMap(than).run { if(isNotEmpty()) firstEntry() else null }
	else headMap(than).run { if(isNotEmpty()) lastEntry() else null }

private fun <K, V> java.util.SortedMap<K, V>.sub(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): java.util.SortedMap<K, V> =
	if(this is java.util.NavigableMap<K, V>) subMap(from, fromInclusive, to, toInclusive)
	else subMap(higherEntry(from, fromInclusive)?.key ?: from, lowerEntry(to, toInclusive)?.key ?: to)

private fun <K, V> java.util.SortedMap<K, V>.head(before: K, inclusive: Boolean): java.util.SortedMap<K, V> =
	headMap(lowerEntry(before, inclusive)?.key ?: before)

private fun <K, V> java.util.SortedMap<K, V>.tail(after: K, inclusive: Boolean): java.util.SortedMap<K, V> =
	tailMap(higherEntry(after, inclusive)?.key ?: after)

private fun <K, V> java.util.SortedMap<K, V>.asNavigable(): java.util.NavigableMap<K, V> =
	object: java.util.SortedMap<K, V> by this, java.util.NavigableMap<K, V> {
		override fun lowerEntry(key: K): KotlinMap.Entry<K, V>? = lowerEntry(key, inclusive = false)

		override fun floorEntry(key: K): KotlinMap.Entry<K, V>? = lowerEntry(key, inclusive = true)

		override fun higherEntry(key: K): KotlinMap.Entry<K, V>? = higherEntry(key, inclusive = false)

		override fun ceilingEntry(key: K): KotlinMap.Entry<K, V>? = higherEntry(key, inclusive = true)

		override fun higherKey(key: K): K? = higherEntry(key, inclusive = false)?.key

		override fun ceilingKey(key: K): K? = higherEntry(key, inclusive = true)?.key

		override fun lowerKey(key: K): K? = lowerEntry(key, inclusive = false)?.key

		override fun floorKey(key: K): K? = lowerEntry(key, inclusive = true)?.key

		override fun firstEntry(): KotlinMap.Entry<K, V>? = sequencedEntrySet().takeIf { isNotEmpty() }?.first.copy()

		override fun lastEntry(): KotlinMap.Entry<K, V>? = sequencedEntrySet().takeIf { isNotEmpty() }?.last.copy()

		override fun pollFirstEntry(): KotlinMap.Entry<K, V>? = sequencedEntrySet().takeIf { isNotEmpty() }?.removeFirst().copy()

		override fun pollLastEntry(): KotlinMap.Entry<K, V>? = sequencedEntrySet().takeIf { isNotEmpty() }?.removeLast().copy()

		override fun subMap(fromKey: K, fromInclusive: Boolean, toKey: K, toInclusive: Boolean): java.util.NavigableMap<K, V> =
			sub(fromKey, toKey, fromInclusive, toInclusive).asNavigable()

		override fun headMap(toKey: K, inclusive: Boolean): java.util.NavigableMap<K, V> = head(toKey, inclusive).asNavigable()

		override fun tailMap(fromKey: K, inclusive: Boolean): java.util.NavigableMap<K, V> = tail(fromKey, inclusive).asNavigable()

		override fun navigableKeySet(): java.util.NavigableSet<K> = TreeSet(comparator()).also { it.addAll(this.keys) }

		override fun descendingMap(): java.util.NavigableMap<K, V> = this.reversed().asNavigable()

		override fun descendingKeySet(): java.util.NavigableSet<K> = reversed().run { TreeSet(comparator()).also { it.addAll(this.keys) } }

		private fun KotlinMap.Entry<K, V>?.copy(): KotlinMap.Entry<K, V>? =
			if(this != null) object: KotlinMap.Entry<K, V> {
				override val key: K = this@copy.key
				override val value: V = this@copy.value
			}
			else null
	}
//</editor-fold>
