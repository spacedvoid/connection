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

## Importing

The main package for the API is `io.github.spacedvoid.connection`.
Although you can import everything one by one, since there are a lot of extension methods here,
just star-importing the package might be preferable.

The IDE might require additional configurations to preserve the star imports.
In IntelliJ IDEA, add the imports above to `Editor - Code Style - Kotlin - Imports - Packages to Use Imports with '*'`.
Make sure to uncheck `With Subpackages`, which contain implementations or unsafe features that you might not want to use.

If the names of the collections are resolved to Kotlin's collections even if the star import was added,
you might need to manually add imports for such types.
This is because [star imports have lower priority than default imports](https://youtrack.jetbrains.com/issue/KT-4374).
See https://youtrack.jetbrains.com/issue/KT-40839 for the report of this behavior.

## Building

Run:

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

Generating documentations of submodules separately is disabled since `gradlew dokkaGenerate` would try to do so,
causing one submodule to overwrite another (sub)module's already generated documentations.

## Upcoming features (or just a todo list)

- `v0.1.0`:
  - [ ] Special collections, such as stack, queue, and deque
  - [x] Completely migrate away from `kotlin.collections`, removing exposed internal API that depends on the Java Collections Framework
  - [x] `equals` and `hashCode` implementations
  - [x] Operator and utility methods
  - [x] `Spliterator` support

- `v0.2.0`:
  - [ ] Allow `null` elements, disambiguating between `null` return values, but document `null`-hostile behaviors

- Not scheduled
  - [ ] Simpler ways to express intervals for `subList` and `subSet`
  - [ ] Indexed collections, which are a type between `SequencedCollection` and `List` that allows to customize the index type (other than `Int`)

## License

The source code is licensed with [MPL-2.0](LICENSE), *compatible* with secondary licenses.
