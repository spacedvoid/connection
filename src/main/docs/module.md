# Module connection

# Package io.github.spacedvoid.connection

Main package for the Connection.
Contains the base interfaces of collections and operations.

## Collection types

Most collection interfaces from the Java Collections Framework are supported:

| Java collection                 | Kotlin collection               | Corresponding Connection type |
|---------------------------------|---------------------------------|-------------------------------|
| [java.util.Collection]          | [kotlin.collections.Collection] | [Collection]                  |
| [java.util.SequencedCollection] | -                               | [SequencedCollection]         |
| [java.util.List]                | [kotlin.collections.List]       | [List]                        |
| [java.util.Set]                 | [kotlin.collections.Set]        | [Set]                         |
| [java.util.SequencedSet]        | -                               | [SequencedSet]                |
| [java.util.SortedSet]           | -                               | [SortedNavigableSet]          |
| [java.util.NavigableSet]        | -                               | [NavigableSet]                |
| [java.util.Map]                 | [kotlin.collections.Map]        | [Map]                         |
| [java.util.SequencedMap]        | -                               | [SequencedMap]                |
| [java.util.SortedMap]           | -                               | [SortedNavigableMap]          |
| [java.util.NavigableMap]        | -                               | [NavigableMap]                |

Some exceptions include decorator types, such as [java.util.concurrent.ConcurrentMap]:

| Java collection                      | Obtainable by(example)                     | Obtainable by in Connection(example)                                      |
|--------------------------------------|--------------------------------------------|---------------------------------------------------------------------------|
| Stacks*                              | [java.util.Collections.asLifoQueue]        | -                                                                         |
| [java.util.Queue]*                   | [java.util.LinkedList]                     | -                                                                         |
| [java.util.Deque]*                   | [java.util.ArrayDeque]                     | -                                                                         |
| [java.util.concurrent.ConcurrentMap] | [java.util.concurrent.ConcurrentHashMap]   | `ConcurrentHashMap().asMutableConnection()`                               |
| [java.util.concurrent.BlockingQueue] | [java.util.concurrent.ArrayBlockingQueue]  | -                                                                         |
| [java.util.concurrent.BlockingDeque] | [java.util.concurrent.LinkedBlockingDeque] | -                                                                         |
| Type-safe collections                | [java.util.Collections.checkedList]        | `Collections.checkedList(ArrayList(), String::class.java).asConnection()` |
| [java.util.EnumSet]*                 | [java.util.EnumSet.of]                     | -                                                                         |
| [java.util.EnumMap]*                 | [java.util.EnumMap]                        | -                                                                         |

(*: Planned for support.)

SortedNavigable-collections are not different with their corresponding Navigable-collections.
However, since Sorted-collections do not directly support operations from Navigable-collections,
default implementations use a workaround which might cause additional blocking and concurrency issues.
If you can select the collection type, **always** use Navigable-collections.

## Collection kinds

Collections in this package are separated into 4 kinds, based on their properties:

| Collection type        | Mutability   | Delegation of another collection? | Superkind              |
|------------------------|--------------|-----------------------------------|------------------------|
| View collection        | Unknown      | Unknown                           | -                      |
| Immutable collection   | No           | No                                | View collection        |
| Remove-only collection | Yes(limited) | Unknown(probably yes)             | View collection        |
| Mutable collection     | Yes          | Unknown(probably no)              | Remove-only collection |

Remove-only collections, by their name, support only element removal operations, such as [RemoveOnlyCollection.remove].
They are normally obtained by map entry collections, such as [MutableMap.keys].

This list of kinds will be enough for all sorts of collections from throwing [UnsupportedOperationException].
Therefore, there are no *optional operations* in Connection.
However, not all collection types support all kinds.
For example, there are no remove-only lists or maps.

Because of the properties above, the inheritance tree isolates immutable collections from mutable(remove-only) collections;
immutable collections cannot be assigned to a mutable collection kind, and vice versa.

## From Kotlin, to Kotlin (or: Adapters)

Adapters are extension methods that convert between Kotlin collections and Connections:

| From → To           | Extension method |
|---------------------|------------------|
| Kotlin → Connection | [asConnection]   |
| Connection → Kotlin | [asKotlin]       |

These adapters are delegations, where operations on the resulting collection modifies the original collection.

Additionally, [List] can be converted to [java.util.SequencedCollection] by [asSequencedKotlin],
since [kotlin.collections.List] does not inherit from [java.util.SequencedCollection].

### Adapters are user-dependent

Do note that adapter methods cannot verify whether the originating collection is really a collection of the type or kind.
For example, in the following snippet:

```kotlin
val kotlinCollection = hashMapOf<String, Any>().keys
val right = kotlinCollection.asRemoveOnlyConnection()
// val wrong = kotlinCollection.asConnection() 
```

When using the element addition operators with `wrong`, [UnsupportedOperationException] might be thrown,
which shouldn't be happening according to the specification of Connection(no optional operations).
If you are not highly certain of how the collection will be handled,
***always*** convert the collection to the corresponding connection kind, or just use [asViewConnection].

Exceptions are similar to the following:

```kotlin
val connection = arrayListOf("a", "b", "c").asRemoveOnlyConnection()
```

Because the created [ArrayList][kotlin.collections.ArrayList] will never be directly used afterward, one can safely apply [asRemoveOnlyConnection] to the collection.

### The unsafe way

[CollectionView.kotlin] is a direct representation of the collection as a Kotlin collection.
However, use of this property is considered **unsafe**.
The result is **not** statically bound;
even if a collection is represented as [Collection], the returned collection by this property might be a [List].

While the defining or overriding class can use this by simply writing `this.kotlin`,
to use this from outside such class, one may *opt-in* as the following:

```kotlin
with(collection) {
    println(this.kotlin)
}
```

For more information, refer to the documentation.

## Conversions

The following extensions convert collections between kinds, maintaining the same type:

| From → To                            | Extension method |
|--------------------------------------|------------------|
| View (or its subkinds) → View        | [asView]         |
| View (or its subkinds) → Remove-only | [asRemoveOnly]   |

These conversions are delegations, where operations on the resulting collection delegates to the original collection.

Note that although simple assignments to superkinds are available, these conversions exist to prevent downcasting.
For example:

```kotlin
val collection: MutableList<String> = ArrayList<String>().asConnection()
val assignment: ListView<String> = collection
val typeSafe: ListView<String> = collection.asView()
(assignment as MutableList<*>).clear() // No exception here, but we don't want this to happen.
(typeSafe as MutableList<*>).clear() // kotlin.TypeCastException: class ListView cannot be cast to class MutableList
```

The following extensions change both the type and kind:

| From → To                          | Extension method            |
|------------------------------------|-----------------------------|
| View (or its subkinds) → Mutable   | `toMutable<CollectionType>` |
| View (or its subkinds) → Immutable | `to<CollectionType>`        |

These conversions are copies, where operations on the resulting collection does not affect the original collection.

## Specifications and thread safety

As mentioned in the previous(Adapters are user-dependent) section, most of the specifications work well only if the adapter methods are used properly.
Other specifications, such as thread safety, are defined based on the originating Kotlin collection.
For example, thread safety is guaranteed when using `CopyOnWriteArrayList().asConnection()`, but not with `ArrayList().asConnection()`.

A simple way to determine such properties is to know that Connection is a mere wrapper for Kotlin collections;
if the property comes from the Kotlin collection, it is also guaranteed in Connection as well, unless the specification from Connection says about it.

# Package io.github.spacedvoid.connection.impl

Contains basic implementations for each collection types in [io.github.spacedvoid.connection].
Although the collection interfaces already have default implementations by default methods,
implementors may freely use the implementations in here (by inheritance or delegation) as a shortcut.

# Package io.github.spacedvoid.connection.utils

Contains some utilities for the API.
