# Module connection-collections

The base API for Connection.

This module was split from `connection-api` to prevent circular dependencies:
`connection-api` already depends on `connection-gen`, which depends on this module.

# Package io.github.spacedvoid.connection

Main package for the Connection.
Contains the base interfaces of collections.

## Collection types

Most collection interfaces work similar from the Java Collections Framework:

| Java collection                 | Kotlin collection               | Corresponding Connection type                  |
|---------------------------------|---------------------------------|------------------------------------------------|
| [java.util.Collection]          | [kotlin.collections.Collection] | [Collection][CollectionView]                   |
| [java.util.SequencedCollection] | -                               | [SequencedCollection][SequencedCollectionView] |
| [java.util.List]                | [kotlin.collections.List]       | [List][ListView]                               |
| [java.util.Set]                 | [kotlin.collections.Set]        | [Set][SetView]                                 |
| [java.util.SequencedSet]        | -                               | [SequencedSet][SequencedSetView]               |
| [java.util.NavigableSet]        | -                               | [NavigableSet][NavigableSetView]               |
| [java.util.Map]                 | [kotlin.collections.Map]        | [Map][MapView]                                 |
| [java.util.SequencedMap]        | -                               | [SequencedMap][SequencedMapView]               |
| [java.util.NavigableMap]        | -                               | [NavigableMap][NavigableMapView]               |
| Stacks                          | [kotlin.collections.ArrayDeque] | [Stack]                                        |
| [java.util.Queue]               | [kotlin.collections.ArrayDeque] | [Queue]                                        |
| [java.util.Deque]               | [kotlin.collections.ArrayDeque] | [Deque]                                        |

Below are unsupported types because of various reasons, such as replaceable by another type:

| Java collection                      | Obtainable by(example)                     | Obtainable by in Connection(example)                                             |
|--------------------------------------|--------------------------------------------|----------------------------------------------------------------------------------|
| [java.util.SortedSet]                | [java.util.TreeSet]                        | `TreeSet().asMutableConnection()`                                                |
| [java.util.SortedMap]                | [java.util.TreeMap]                        | `TreeMap().asMutableConnection()`                                                |
| [java.util.concurrent.ConcurrentMap] | [java.util.concurrent.ConcurrentHashMap]   | `ConcurrentHashMap().asMutableConnection()`                                      |
| [java.util.concurrent.BlockingQueue] | [java.util.concurrent.ArrayBlockingQueue]  | -                                                                                |
| [java.util.concurrent.BlockingDeque] | [java.util.concurrent.LinkedBlockingDeque] | -                                                                                |
| Type-safe collections                | [java.util.Collections.checkedList]        | `Collections.checkedList(ArrayList(), String::class.java).asMutableConnection()` |
| [java.util.EnumSet]*                 | [java.util.EnumSet.of]                     | `EnumSet.noneOf(EnumClass::class).asMutableConnection()`                         |
| [java.util.EnumMap]*                 | [java.util.EnumMap]                        | `EnumMap(EnumClass::class).asMutableConnection()`                                |

(*: Planned for support.)

## Collection kinds

Collections in this package are separated into 4 kinds, based on their properties:

| Collection kind | Mutability   | Delegation of another collection? | Superkind   |
|-----------------|--------------|-----------------------------------|-------------|
| View            | Unknown      | Unknown                           | -           |
| Immutable       | No           | No                                | View        |
| Remove-only     | Yes(limited) | Unknown(probably yes)             | View        |
| Mutable         | Yes          | Unknown(probably no)              | Remove-only |

All kinds of the same type are called a *type family*.
For example, [ListView], [List], and [MutableList] is a family of [List].

(For sake of readability, when representing type families or their representative names, 
the name of immutable collections such as [List] and [Set] is used,
but do note that the lowest common supertype of all kinds in a family is a view collection, such as [ListView] and [SetView].
This can be seen at the topmost table, where links to what seems to be immutable collections actually lead to view collections.)

Remove-only collections support only element removal operations between mutation operations, such as [RemoveOnlyCollection.remove].
They are usually obtained by map entry collections, such as [MutableMap.keys].

This list of kinds will be enough for all sorts of collections from throwing [UnsupportedOperationException].
Therefore, there are no *optional operations* in Connection.
However, not all collection types support all kinds.
For example, there are no remove-only lists or maps.

Because of the properties above, the inheritance tree isolates immutable collections from mutable (and remove-only) collections;
immutable collections cannot be assigned to a mutable collection kind, and vice versa.

### Introduction to sequenced collections

Java 21 added sequenced collections, which are intermediate collection types in the Java Collections Framework.
However, their documentation is not pretty helpful:
most of the methods derived from their supertypes do not have overrides, not documenting interactions with sequenced collections.
Therefore, we try to redefine the properties of sequenced collections, while maintaining interoperability with Java.

First, sequenced collections have a defined *iteration order*, which are also *consistent*.
*Consistent* indicates that two iterations, with no modifications in between, return the elements in the same order.
So, the defined iteration order will be the *encounter order* for any operations that iterate through a sequenced collection.
And that is also the last and only property of a sequenced collection.

Therefore, it is simple to know why [MutableSequencedCollection] does not provide an `addFirst` method:
the iteration order is defined only for the outgoing elements from the collection,
so incoming elements are irrelevant of the iteration order, making them unavailable for positional additions.
To be easy, one can understand that element additions (mostly) do not define the iteration order.

However, because these positional addition methods do have real-life usages,
we are currently designing a proper intermediate collection type that allows such positional additions.
(Currently, only positional deletions, such as [MutableSequencedCollection.removeFirst], are supported.)

### Null-hostility

Some implementations might reject `null` elements, either by (typically) throwing an exception or returning a predefined value.
These collections are called *`null`-hostile*, and any attempts to add/modify/remove/query `null` elements will result in (typically) a [NullPointerException].
This includes [add][MutableCollection.add]`(null)`, [remove][RemoveOnlyCollection.remove]`(null)`, and [contains][CollectionView.contains]`(null)`.
However, because the generic system would prevent usages of `null` in such cases(for example, an `Int?` to a `MutableList<Int>`),
users wouldn't need to care whether the collection accepts `null` or not, and thus not documented at the API.
