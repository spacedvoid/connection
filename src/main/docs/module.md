# Module connection

# Package io.github.spacedvoid.connection

Main package for the Connection.
Contains the base interfaces of collections and operations.

## Collection types

Most collection interfaces work similar from the Java Collections Framework:

| Java collection                 | Kotlin collection               | Corresponding Connection type                  |
|---------------------------------|---------------------------------|------------------------------------------------|
| [java.util.Collection]          | [kotlin.collections.Collection] | [Collection]                                   |
| [java.util.SequencedCollection] | -                               | [SequencedCollection]                          |
| [java.util.List]                | [kotlin.collections.List]       | [List]                                         |
| [java.util.Set]                 | [kotlin.collections.Set]        | [Set]                                          |
| [java.util.SequencedSet]        | -                               | [SequencedSet]                                 |
| [java.util.NavigableSet]        | -                               | [NavigableSet]                                 |
| [java.util.Map]                 | [kotlin.collections.Map]        | [Map]                                          |
| [java.util.SequencedMap]        | -                               | [SequencedMap]                                 |
| [java.util.NavigableMap]        | -                               | [NavigableMap]                                 |

Below are unsupported types because of various reasons, such as replaceable by another type.

| Java collection                      | Obtainable by(example)                     | Obtainable by in Connection(example)                                             |
|--------------------------------------|--------------------------------------------|----------------------------------------------------------------------------------|
| [java.util.SortedSet]                | [java.util.TreeSet]                        | `TreeSet().asMutableConnection()`                                                |
| [java.util.SortedMap]                | [java.util.TreeMap]                        | `TreeMap().asMutableConnection()`                                                |
| Stacks*                              | [java.util.Collections.asLifoQueue]        | -                                                                                |
| [java.util.Queue]*                   | [java.util.LinkedList]                     | -                                                                                |
| [java.util.Deque]*                   | [java.util.ArrayDeque]                     | -                                                                                |
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

Remove-only collections, by their name, support only element removal operations, such as [RemoveOnlyCollection.remove].
They are normally obtained by map entry collections, such as [MutableMap.keys].

This list of kinds will be enough for all sorts of collections from throwing [UnsupportedOperationException].
Therefore, there are no *optional operations* in Connection.
However, not all collection types support all kinds.
For example, there are no remove-only lists or maps.

Because of the properties above, the inheritance tree isolates immutable collections from mutable(remove-only) collections;
immutable collections cannot be assigned to a mutable collection kind, and vice versa.

## From Kotlin, to Kotlin (or: Adapters)

Adapters are extension methods that convert between Kotlin collections and Connections.

Adapters that convert Kotlin collections to Connections are:

| Target kind | Extension method         |
|-------------|--------------------------|
| View        | [asViewConnection]       |
| Immutable   | [asConnection]           |
| Remove-only | [asRemoveOnlyConnection] |
| Mutable     | [asMutableConnection]    |

And for Connection to Kotlin:

| Target kind | Extension method |
|-------------|------------------|
| Any         | [asKotlin]       |

These adapters are delegations, where operations on the resulting collection modifies the original collection.

<details>
<summary>Additional adapters</summary>

| From → To                                    | Extension method    |
|----------------------------------------------|---------------------|
| [ListView] → [java.util.SequencedCollection] | [asSequencedKotlin] |

</details>

### Adapters are user-dependent

Do note that adapter methods cannot verify whether the originating collection is really a collection of the type or kind.
For example, in the following snippet:

```kotlin
val kotlinCollection = hashMapOf<String, Any>().keys
val right = kotlinCollection.asRemoveOnlyConnection()
// val wrong = kotlinCollection.asConnection() 
```

When adding elements to `wrong`, [UnsupportedOperationException] might be thrown,
which shouldn't be happening according to the specification of Connection(no optional operations).
If you are not highly certain of how the collection will be handled,
***always*** convert the collection to the corresponding connection kind, or just use [asViewConnection].

Exceptions are similar to the following:

```kotlin
val connection = arrayListOf("a", "b", "c").asConnection()
```

Because the created [ArrayList][kotlin.collections.ArrayList] will never be directly used afterward, one can safely apply [asConnection] to the collection.

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
val collection: MutableList<String> = ArrayList<String>().asMutableConnection()
val assignment: ListView<String> = collection
val typeSafe: ListView<String> = collection.asView()
(assignment as MutableList<*>).clear() // No exception here, but we don't want this to be allowed.
(typeSafe as MutableList<*>).clear() // kotlin.TypeCastException: class ListView cannot be cast to class MutableList
```

The following extensions change both the type and kind:

| From → To                          | Extension method            |
|------------------------------------|-----------------------------|
| View (or its subkinds) → Mutable   | `toMutable<CollectionType>` |
| View (or its subkinds) → Immutable | `to<CollectionType>`        |

<details>
<summary>Full list of type conversions</summary>

| To             | Immutable        | Mutable                 |
|----------------|------------------|-------------------------|
| [List]         | [toList]         | [toMutableList]         |
| [Set]          | [toSet]          | [toMutableSet]          |
| [SequencedSet] | [toSequencedSet] | [toMutableSequencedSet] |
| [NavigableSet] | [toNavigableSet] | [toMutableNavigableSet] |
| [SequencedMap] | [toSequencedMap] | [toMutableSequencedMap] |
| [NavigableMap] | [toNavigableMap] | [toMutableNavigableMap] |

</details>

These conversions are copies, where operations on the resulting collection does not affect the original collection.

# Package io.github.spacedvoid.connection.impl

Contains basic implementations for each collection types in [io.github.spacedvoid.connection].
Although the collection interfaces already have default implementations by default methods,
implementors may freely use the implementations in here (by inheritance or delegation) as a shortcut.

# Package io.github.spacedvoid.connection.utils

Contains some utilities for the API and implementations.
