# Module connection-core

Contains implementation details for Connection.

# Package io.github.spacedvoid.connection.impl

Contains basic implementations for each collection types in [io.github.spacedvoid.connection].

Implementors may freely use the implementations in here (by inheritance or delegation) as a shortcut.

# Package io.github.spacedvoid.connection.impl.kotlin

Contains basic implementations for converting Connection to Kotlin collection types.

Since Kotlin's collection kinds are narrower than Connection, most methods depend on the input Connection's dynamic kind (via `is`).

**Warning:** Do not assume mutability of iterators from non-Kotlin collections, such as [KotlinSequencedCollectionImpl].
Treat them as if they were [java.util.Iterator]; they are mutable if the source collection is mutable.
Mutable iterators do **not** imply the source collection is mutable.

# Package io.github.spacedvoid.connection.utils

Contains some utilities for the implementations.
