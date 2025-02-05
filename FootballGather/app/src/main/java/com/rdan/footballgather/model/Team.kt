package com.rdan.footballgather.model

enum class Team {
    Bench, TeamA, TeamB;

    companion object {
        private val displayNameMap = mapOf(
            "Bench" to Bench,
            "Team A" to TeamA,
            "Team B" to TeamB
        )

        fun getTeamDisplayNames(): Array<String> {
            return displayNameMap.keys.toTypedArray()
        }

        fun fromDisplayName(displayName: String): Team? {
            return displayNameMap[displayName]
        }

        fun toDisplayName(team: Team): String? {
            val map = mapOf(
                Bench to "Bench",
                TeamA to "Team A",
                TeamB to "Team B"
            )
            return map[team]
        }
    }
}