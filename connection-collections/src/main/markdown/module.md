# Module connection-collections

Contains the collection types for Connection.

# Package io.github.spacedvoid.connection

Main package for Connection.

## Collection types

Most collection interfaces work similar from the Java Collections Framework:

| Java collection                 | Kotlin collection               | Corresponding Connection type                    |
|---------------------------------|---------------------------------|--------------------------------------------------|
| [java.util.Collection]          | [kotlin.collections.Collection] | [Collection][CollectionView]                     |
| [java.util.SequencedCollection] | -                               | [SequencedCollection][SequencedCollectionView]   |
| [java.util.List]                | [kotlin.collections.List]       | [List][ListView]                                 |
| [java.util.Set]                 | [kotlin.collections.Set]        | [Set][SetView]                                   |
| [java.util.SequencedSet]        | -                               | [SequencedSet][SequencedSetView]                 |
| [java.util.NavigableSet]        | -                               | [NavigableSet][NavigableSetView]                 |
| [java.util.Map]                 | [kotlin.collections.Map]        | [Map][MapView]                                   |
| [java.util.SequencedMap]        | -                               | [SequencedMap][SequencedMapView]                 |
| [java.util.NavigableMap]        | -                               | [NavigableMap][NavigableMapView]                 |
| Stacks                          | [kotlin.collections.ArrayDeque] | [Stack]                                          |
| [java.util.Queue]               | [kotlin.collections.ArrayDeque] | [Queue]<sup><a id="1" href="#f1">[[1]]</a></sup> |
| [java.util.Deque]               | [kotlin.collections.ArrayDeque] | [Deque]                                          |

<a id="f1" href="#1">[[1]]</a>: Not exact. [java.util.Queue] does not need to be LIFO, but here [Queue] is required to be such.

Below are unsupported types because of various reasons, such as replaceable by another type:

| Java collection                      | Obtainable by(example)                     | Obtainable by in Connection(example)                                             |
|--------------------------------------|--------------------------------------------|----------------------------------------------------------------------------------|
| Type-safe collections                | [java.util.Collections.checkedList]        | `Collections.checkedList(ArrayList(), String::class.java).asMutableConnection()` |
| [java.util.EnumSet]                  | [java.util.EnumSet.of]                     | `enumSetOf()`<sup><a id="2" href="#f2">[[2]]</a></sup>                           |
| [java.util.EnumMap]                  | [java.util.EnumMap]                        | `enumMapOf()`<sup><a id="3" href="#f3">[[3]]</a></sup>                           |

<a id="f2" href="#2">[[2]]</a>: See <a href="../../connection-api/io.github.spacedvoid.connection/enum-set-of.html">enumSetOf</a>
and <a href="../../connection-api/io.github.spacedvoid.connection/mutable-enum-set-of.html">mutableEnumSetOf</a>.

<a id="f3" href="#3">[[3]]</a>: See <a href="../../connection-api/io.github.spacedvoid.connection/enum-map-of.html">enumMapOf</a>
and <a href="../../connection-api/io.github.spacedvoid.connection/mutable-enum-map-of.html">mutableEnumMapOf</a>.

## Collection kinds

Collections in this package are separated into 4 kinds, based on their properties:

| Collection kind | Mutability   | Delegation of another collection? | Superkind   |
|-----------------|--------------|-----------------------------------|-------------|
| View            | Unknown      | Unknown                           | -           |
| Immutable       | No           | No                                | View        |
| Remove-only     | Yes(limited) | Unknown(probably yes)             | View        |
| Mutable         | Yes          | Unknown(probably no)              | Remove-only |

Remove-only collections support only element removal operations between mutation operations, such as [RemoveOnlyCollection.remove].
They are usually obtained by map entry collections, such as [MutableMap.keys].

This list of kinds will be enough for all sorts of collections from throwing [UnsupportedOperationException][kotlin.UnsupportedOperationException].
Therefore, there are *no optional operations* in Connection.
However, not all collection types support all kinds; for example, there are no remove-only lists or maps.

Because of the properties above, the inheritance tree isolates immutable collections from mutable (and remove-only) collections;
immutable collections cannot be assigned to a mutable collection kind, and vice versa.

## Encounter and iteration order

The *encounter order* for a collection is defined with the elements and their order that its [iterator][CollectionView.iterator] produces.
But the encounter orders of two iterations, even without modifications, might neither have the same elements nor be *consistent*.
*Consistent* encounter orders return the same elements in the same order for two iterations with no modifications between.
So operations that depend on the encounter order iterate a collection only once,
treating the encounter order as a snapshot list of elements.

Thus, if the encounter order is *preserved* by some operation,
elements that are in the encounter order appears in the result by that order,
regardless of whether some elements from the encounter order were filtered out or
new elements irrelevant of the encounter order were inserted between the original elements.
This is also the case when an operation works on the elements *by their encounter order*, such as from [toTypedArray].
More details about how it is preserved will be described at the API.

Then, sequenced collections have a defined *iteration order*, which is also consistent.
So the defined iteration order will also be the encounter order for sequenced collections.
Other element collections that are not from Connection may also have a defined iteration order,
such as an [Array][kotlin.Array] or [Sequence][kotlin.sequences.Sequence].

But, for example, merely having a [MutableSequencedSet] is not different with having a [MutableSet]
because we do not know *how* the iteration order is defined.
So always document the iteration order when you return a sequenced collection.
Sequenced collections created by Connection will usually have their behavior equivalent with
[LinkedHashSet][kotlin.collections.LinkedHashSet] or an insertion-ordered [LinkedHashMap][kotlin.collections.LinkedHashMap].
Such cases will be documented at the API.

## Equality comparison

Most operations that check whether an element matches another element uses [Any.equals][kotlin.Any.equals].
However, this method might not always be used, such as comparing the [hash code][kotlin.Any.hashCode] before actually using the method.
Implementations are free to use any optimizations as long as the comparison is effectively equal to using [Any.equals][kotlin.Any.equals],
and users must not understand the specification of an operation as the method will always be invoked even if it specifies to use the method.

## (Unchecked) casting

Because Connection heavily depends on Kotlin's type system,
(unchecked) casting between collection types or kinds will likely cause undefined behavior.
These are not limited to casting within the type(such as `List<Int?>` to `List<Int>`),
but also cross-type casting(such as `ListView<Int?>` to `MutableList<Int>`).
Behavior of operations on such casted collections will be undefined.

## Concurrent modifications and thread-safe collections

Most operations rely on iterators,
so the result of the operation and the collection's state after the operation is not defined
when concurrent modifications, including cross-thread, are ongoing.
Also, operations are not atomic, so concurrent modifications might not be visible to the operation, and vice versa.

This includes class member methods and extension methods.

Because the nature of Kotlin encourages the use of extension methods implemented with basic operations provided from the type,
thread-safe collections do not work well with Connection.
Since they usually provide methods to access and/or modify the collection atomically and with more assurance about thread-safety,
just use the type as-is(not using the interface types) and don't use extension methods.

## Hostility

Some implementations from Kotlin might reject `null` elements, usually by throwing an exception.
These collections are usually called *null-hostile*, and any attempts to add/modify/remove/query `null` elements
to these collections will result in a [NullPointerException][kotlin.NullPointerException].
This includes `add(null)`, `remove(null)`, and `contains(null)`.
However, because the generic system would prevent usages of `null` in such cases(for example, an `Int?` to a `MutableList<Int>`),
users wouldn't need to care whether the collection accepts `null` or not, so this behavior is not allowed in Connection.

Also, Java's collections allow methods to throw [ClassCastException][java.lang.ClassCastException]
when an element's type is prohibited for the collection.
But this case is also prevented by the type system, so this behavior is not allowed in Connection.
