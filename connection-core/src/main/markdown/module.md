# Module connection-core

Contains implementation details for Connection.

# Package io.github.spacedvoid.connection.impl

Contains basic implementations for each collection types in [io.github.spacedvoid.connection].

Implementors may freely use the implementations in here (by inheritance or delegation) as a shortcut.

# Package io.github.spacedvoid.connection.impl.kotlin

Contains basic implementations for converting Connection to Kotlin collection types.

Since Kotlin's collection kinds are narrower than Connection, most methods depend on the input Connection's dynamic kind (via `is`).

# Package io.github.spacedvoid.connection.utils

Contains some utilities for the implementations.
