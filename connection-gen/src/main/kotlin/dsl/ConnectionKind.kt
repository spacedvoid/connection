/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.gen.dsl

/**
 * Represents the kind of Connection types.
 */
enum class ConnectionKind {
	VIEW, IMMUTABLE, REMOVE_ONLY, MUTABLE;
}
