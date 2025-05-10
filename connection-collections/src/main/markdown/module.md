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
| [java.util.Collection]          | [kotlin.collections.Collection] | [Collection]                                   |
| [java.util.SequencedCollection] | -                               | [SequencedCollection]                          |
| [java.util.List]                | [kotlin.collections.List]       | [List]                                         |
| [java.util.Set]                 | [kotlin.collections.Set]        | [Set]                                          |
| [java.util.SequencedSet]        | -                               | [SequencedSet]                                 |
| [java.util.NavigableSet]        | -                               | [NavigableSet]                                 |
| [java.util.Map]                 | [kotlin.collections.Map]        | [Map]                                          |
| [java.util.SequencedMap]        | -                               | [SequencedMap]                                 |
| [java.util.NavigableMap]        | -                               | [NavigableMap]                                 |

Below are unsupported types because of various reasons, such as replaceable by another type:

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

All kinds of the same type are called a *type family*.
For example, [ListView], [List], and [MutableList] is a family of [List].

Remove-only collections support only element removal operations between mutation operations, such as [RemoveOnlyCollection.remove].
They are usually obtained by map entry collections, such as [MutableMap.keys].

This list of kinds will be enough for all sorts of collections from throwing [UnsupportedOperationException].
Therefore, there are no *optional operations* in Connection.
However, not all collection types support all kinds.
For example, there are no remove-only lists or maps.

Because of the properties above, the inheritance tree isolates immutable collections from mutable (and remove-only) collections;
immutable collections cannot be assigned to a mutable collection kind, and vice versa.
