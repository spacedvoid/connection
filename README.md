# Connection

Another collections API for more utilities

## What is this?

This is yet another a collection API based on Kotlin.
However, the collections are strictly separated into 4 kinds, based on their mutability:

1. Immutable collections: The collection is neither mutable nor self-mutating.
2. View collections: The collection is not mutable, but is(or might be) self-mutating.
3. Remove-only collections: The collections is both mutable and self-mutating, but only removal operations (such as `remove(element: T)`) are available.
4. Mutable collections: The collection is both mutable and self-mutating.

3 is for entry collections of maps; they typically do not allow addition of new entries, only removals.

There are no *optional operations*; use a mutable collection at your own will.

For a quickstart guide, go to our documentation(which sadly requires a manual build currently).

## Git structure

This library uses the [Tip & Tail Model](https://openjdk.org/jeps/14) experimentally.
As specified in the [description](https://openjdk.org/jeps/14#Backport-as-little-as-possible) of the model, 
the specific rules of the model are modified, listed below.

Releases must have an associated commit tagged with the release version number, containing content equal to the release.
All release versions must be unique; to improve searching specific versions, there must be no duplicate release tags with the same version.

The `main` branch is for new features, bug fixes, and all other changes.
This is the *Tip* branch.

After a major or minor version release, a new branch may be created from the tip branch with the format `v<version>`.
These branches are the *Tail* branches.
A tail branch must start with the commit with the corresponding version tag:
for example, if commit `abc123` is tagged with `v1.2.0`, the corresponding tail branch must be created as `git branch v1.2.0 abc123`.

Changing the contents of a tail branch is restricted.
New features, API/implementation changes, minor bug fixes that change the API/implementation behavior are prohibited.
Only critical bug fixes that impact security or API usage(such as infinite loops or specification violations) are allowed.
Therefore, releases from a tail branch must not increment the major or minor version number.

Releases can come from any branches, but for simplicity, most releases will come from the tip branch.
Tail branches will only be created if necessary.

## Editing and building

Because this project uses a source code generator, it requires an initial compilation to resolve errors before editing:

```
gradlew compileKotlin
```

To build, run:

```
gradlew build
```

This will create multiple `.jar` files.
`connection-api` contains only the API, while `connection-core` will also contain the implementations.
`connection-javadoc` is a Dokka-based documentation.

Below will generate the Dokka documentation under `docs/`:

```
gradlew :dokkaGenerate
```

## Upcoming features (or just a todo list)

- `v0.1.0`:
  - [x] Special collections, such as stack, queue, and deque
  - [x] Completely migrate away from `kotlin.collections`, removing exposed internal API that depends on the Java Collections Framework
  - [x] `equals` and `hashCode` implementations
  - [x] Operator and utility methods
  - [x] `Spliterator` support
  - [x] Make non-modifiable collections be covariant(`out`) by default

- `v0.2.0`:
  - [ ] Linked collections, which are a subtype of `SequencedCollection` that supports `addFirst` and `addLast`
  - [ ] Add range information for navigable collections

- Not scheduled
  - [ ] Simpler ways to express intervals for `subList` and `subSet`
  - [ ] Transform usages of Connection types to Kotlin collection types when compiling, alike mapped Java types in Kotlin
  - [ ] Allow all Connection types to be a receiver for appropriate extension methods in Kotlin, such as `Iterable.map`

## License

The source code is licensed with [MPL-2.0](LICENSE), *compatible* with secondary licenses.
