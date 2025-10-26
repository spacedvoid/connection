# Module connection-api

Contains operations for Connection.

# Package io.github.spacedvoid.connection

Main package for Connection.

## Adapter methods

Adapter methods are extensions that wrap collections between Kotlin and Connection.

Adapters that wrap Kotlin collections to Connection are:

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

Operations on the resulting collection will be delegated the original collection.

<details>
<summary>Additional adapters</summary>

| From → To                                    | Extension method    |
|----------------------------------------------|---------------------|
| [ListView] → [java.util.SequencedCollection] | [asSequencedKotlin] |

</details>

### Adapters are just adapters

Do note that adapter methods cannot verify whether the originating collection is really a collection of the type or kind.
For example:

```kotlin
val isThisFullyMutable = hashMapOf<String, Any>().keys.asMutableConnection()
```

Because keysets typically disallow the usage of [MutableCollection.add],
they will throw [UnsupportedOperationException][kotlin.UnsupportedOperationException],
which shouldn't be happening according to the specification of Connection(no optional operations).

***Always*** convert the collection to the corresponding connection kind
unless you are highly certain at how the collection will be handled:

```kotlin
val connection = arrayListOf("a", "b", "c").asConnection()
```

Because the created [ArrayList][kotlin.collections.ArrayList] will never be directly used afterward,
one can safely apply [asConnection] to the collection.

## Conversions

The following extensions wrap collections between kinds, maintaining the same type:

| From → To          | Extension method |
|--------------------|------------------|
| View → View        | [asView]         |
| View → Remove-only | [asRemoveOnly]   |

Operations on the resulting collection will be delegated to the original collection.

Note that although simple assignments to superkinds are available, these conversions exist to prevent downcasting.
For example:

```kotlin
val collection: MutableList<String> = ArrayList<String>().asMutableConnection()
val assignment: ListView<String> = collection
val typeSafe: ListView<String> = collection.asView()
(assignment as MutableList<*>).clear() // No exception here, but we don't want this to be allowed.
(typeSafe as MutableList<*>).clear() // kotlin.TypeCastException: class ListView cannot be cast to class MutableList
```

Also, [snapshot] copies the elements to an immutable collection, maintaining the type.

## Operator methods

Most extension methods from `kotlin.collections` or `kotlin.sequences` that involve collection types are also here,
such as [map], [sort], and [asList].

Unless noted otherwise, operations that accept lambdas will
- exit immediately when the lambda throws an exception, and relay it to the caller
- invoke the lambda in the same thread that the operation was invoked; and
- assume no other thread calls the lambda.

## Element ordering

By default, if an operation works on a source of elements(such as [Iterable][kotlin.Iterable] or `vararg`)
that has a defined iteration order, the elements will be processed by that order.
Also, if an operation collects the elements from a source to another collection,
the resulting collection will maintain the encounter order of the elements,
regardless of whether the elements were filtered out, mapped to another element, or been unpacked from a collection.
