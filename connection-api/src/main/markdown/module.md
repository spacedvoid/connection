# Module connection-api

The base API for Connection.

# Package io.github.spacedvoid.connection

Main package for the Connection.
Contains operations for collections.

## Adapters

Adapters are extension methods that convert between Kotlin collections and Connection.

Adapters that convert Kotlin collections to Connection are:

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
// val wrong = kotlinCollection.asMutableConnection() 
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

## Conversions

The following extensions convert collections between kinds, maintaining the same type:

| From → To          | Extension method |
|--------------------|------------------|
| View → View        | [asView]         |
| View → Remove-only | [asRemoveOnly]   |

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

[snapshot] is a special case; it copies the elements to an immutable collection, maintaining the type.

The following extensions change both the type and kind:

| From → To        | Extension method            |
|------------------|-----------------------------|
| View → Mutable   | `toMutable<CollectionType>` |
| View → Immutable | `to<CollectionType>`        |

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
