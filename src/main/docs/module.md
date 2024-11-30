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

Some exceptions include decorator types, such as [java.util.ConcurrentMap]:

| Java collection                      | Obtainable by(example)                     | Obtainable by in Connection(example)                                      |
|--------------------------------------|--------------------------------------------|---------------------------------------------------------------------------|
| Stacks*                              | [java.util.Collections.asLifoQueue]        | -                                                                         |
| [java.util.Queue]*                   | [java.util.LinkedList]                     | -                                                                         |
| [java.util.Deque]*                   | [java.util.ArrayDeque]                     | -                                                                         |
| [java.util.concurrent.ConcurrentMap] | [java.util.concurrent.ConcurrentHashMap]   | `ConcurrentHashMap().asMutableConnection()`                               |
| [java.util.concurrent.BlockingQueue] | [java.util.concurrent.ArrayBlockingQueue]  | -                                                                         |
| [java.util.concurrent.BlockingDeque] | [java.util.concurrent.LinkedBlockingDeque] | -                                                                         |
| Type-safe collections*               | [java.util.Collections.checkedList]        | `Collections.checkedList(ArrayList(), String::class.java).asConnection()` |

(*: Planned for support.)

SortedNavigable-collections are not different with their corresponding Navigable-collections.
However, since Sorted-collections do not directly support operations from Navigable-collections,
default implementations use a workaround which might cause additional blocking and concurrency issues.
If you can select the collection type, **always** use Navigable-collections.

## Collection kinds

Collections in this package are separated into 4 kinds, based on their properties:

| Collection type        | Mutability   | Delegation of another collection? | Superkind              |
|------------------------|--------------|-----------------------------------|------------------------|
| View collection        | Unknown      | Unknown                           | None                   |
| Immutable collection   | No           | No                                | View collection        |
| Remove-only collection | Yes(limited) | Unknown(probably yes)             | View collection        |
| Mutable collection     | Yes          | Unknown(probably no)              | Remove-only collection |

Remove-only collections, by their name, support only element removal operations, such as [RemoveOnlyCollection.remove].
They are normally obtained by map entry collections, such as [MutableMap.keys].
Also, these collections and mutable collections might be a delegation of another collection,
such as collections obtained by [SequencedCollection.reverse].

This list of kinds will be enough for all sorts of collections from throwing [UnsupportedOperationException].
Therefore, there are no *optional operations* in Connection.
However, not all collection types support all kinds.
For example, there are no remove-only lists or maps.

Immutable collections do not have any prefixes or postfixes in their names
(for example, [Collection] is the immutable version of [java.util.Collection]).

Because of the properties above, the inheritance tree isolates immutable collections from mutable(remove-only) collections;
immutable collections cannot be assigned to a mutable collection kind, and vice versa.

## From Kotlin, to Kotlin (or: Adapters)

There are two ways to convert between a Kotlin(Java) collection and a Connection: delegation and copy.
Delegation simply delegates all operations to the underlying collection, 
while copying prevents operations from affecting the original collection.

<table>
<tr>
<th>Adapter mode</th>
<th>From → To</th>
<th>Extension method</th>
</tr>
<tr>
<td rowspan="2">Delegation</td>
<td>Kotlin → Connection</td>
<td>

[asConnection]

</td>
</tr>
<tr>
<td>Connection → Kotlin</td>
<td>

[asKotlin]

</td>
</tr>
<tr>
<td rowspan="2">Copy</td>
<td>Kotlin → Connection</td>
<td>

[toConnection]

</td>
</tr>
<tr>
<td>Connection → Kotlin</td>
<td>

[toKotlin]

</td>
</tr>
</table>

Additionally, [List] can be converted to [java.util.SequencedCollection] by [asSequencedKotlin] or [toSequencedKotlin],
since [kotlin.collections.List] does not inherit from [java.util.SequencedCollection].

### Adapters are user-dependent

Do note that adapter methods cannot verify whether the originating collection is really a collection of the kind.
For example, in the following snippet:

```kotlin
val kotlinCollection = kotlin.collections.HashMap<String, Any>().keys
// working with `kotlinCollection`...
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

Because the created [ArrayList] will never be directly used afterward, one can safely apply [asRemoveOnlyConnection] to the collection.

### The unsafe way

[Collection.kotlin] is a direct representation of the collection as Kotlin.
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

To convert between collection types or kinds, one can use the corresponding extension methods.

### Kind conversions

The following extensions convert collections between kinds, maintaining the same type:

<table>
<tr>
<th>Conversion mode</th>
<th>From → To</th>
<th>Extension method</th>
</tr>
<tr>
<td rowspan="2">Delegation</td>
<td>View → View</td>
<td>

[asView]

</td>
</tr>
<tr>
<td>View → Remove-only</td>
<td>

[asRemoveOnly]

</td>
</tr>
<tr>
<td rowspan="2">Copy</td>
<td>View → Immutable</td>
<td>

[snapshot]

</td>
</tr>
<tr>
<td>Immutable → Mutable</td>
<td>

[toMutable]

</td>
</tr>
</table>

Note that although simple assignments to superkinds are available, these conversions exist to prevent downcasting.
For example:

```kotlin
val collection: MutableList<String> = ArrayList<String>().asConnection()
val assignment: ListView<String> = collection
val typeSafe: ListView<String> = collection.asView()
(assignment as MutableList<*>).clear() // No exception here, but we don't want this to happen.
(typeSafe as MutableList<*>).clear() // kotlin.TypeCastException: class ListViewImpl cannot be cast to class MutableList
```

### Type conversions

The following extensions convert collections between types, maintaining the same kind:

<table>
    <tr>
        <th>Conversion mode</th>
        <th>From → To</th>
        <th>Extension method</th>
        <th>Defined in</th>
    </tr>
    <tr>
        <td rowspan="2">Copy</td>
        <td>Immutable → Mutable</td>
        <td><code>toMutable&lt;CollectionType&gt;()</code></td>
        <td rowspan="2"><code>Conversion.kt</code></td>
    </tr>
    <tr>
        <td>Immutable → Immutable</td>
        <td><code>to&lt;CollectionType&gt;()</code></td>
    </tr>
</table>

# Package io.github.spacedvoid.connection.impl

Contains basic implementations for each collection types in [io.github.spacedvoid.connection].
Although the collection interfaces already have default implementations by default methods,
implementors may freely use the implementations in here (by inheritance or delegation) as a shortcut.

# Package io.github.spacedvoid.connection.utils

Contains some utilities for the API.
