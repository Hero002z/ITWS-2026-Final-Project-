<script setup lang="ts">
import DataCard from '@/components/DataCard.vue'
import Loading from '@/components/Loading.vue'
import type { MatchModel } from '@/models/match.model'
import type { PlayerModel } from '@/models/player.model'
import type { TournamentModel } from '@/models/tournament.model'
import { DataService } from '@/services/data.service'
import { ref } from 'vue'

const players = ref<PlayerModel[]>()
const tournaments = ref<TournamentModel[]>()
const matches = ref<MatchModel[]>()

DataService.getPlayers()
    .then(rsp => players.value = rsp.data)
    .catch(console.error)

DataService.getTournaments()
    .then(rsp => tournaments.value = rsp.data)
    .catch(console.error)

DataService.getMatches()
    .then(rsp => matches.value = rsp.data)
    .catch(console.error)

function getHighestRatedPlayer() {
    if (!players.value?.length) {
        return null
    }

    return [...players.value]
        .sort((a, b) => b.rating - a.rating)[0]
}

function getRecentTournaments() {
    if (!tournaments.value?.length) {
        return []
    }

    return [...tournaments.value]
        .sort(
            (a, b) =>
                new Date(b.createdAt).getTime() -
                new Date(a.createdAt).getTime()
        )
        .slice(0, 2)
}
</script>

<template>
    <div v-if="players && tournaments && matches" class="row g-3">
        <div class="col-12 col-lg-4">
            <DataCard title="Players">
                <div class="text-center">
                    <h2>{{ players.length }}</h2>
                </div>
            </DataCard>
        </div>
        <div class="col-12 col-lg-4">
            <DataCard title="Tournaments">
                <div class="text-center">
                    <h2>{{ tournaments.length }}</h2>
                </div>
            </DataCard>
        </div>
        <div class="col-12 col-lg-4">
            <DataCard title="Matches">
                <div class="text-center">
                    <h2>{{ matches.length }}</h2>
                </div>
            </DataCard>
        </div>

        <div class="col-12 col-lg-6">
            <DataCard title="Highest Rated Player">
                <div v-if="getHighestRatedPlayer()">
                    <h5>
                        {{ getHighestRatedPlayer()?.firstName }}
                        {{ getHighestRatedPlayer()?.lastName }}
                    </h5>
                    <p class="mb-2">{{ getHighestRatedPlayer()?.country }}</p>
                    <span class="badge text-bg-primary me-2">{{ getHighestRatedPlayer()?.title }}</span>
                    <span class="badge text-bg-secondary">{{ getHighestRatedPlayer()?.rating }}</span>
                </div>
                <div v-else class="text-secondary">
                    No players available.
                </div>
            </DataCard>
        </div>

        <div class="col-12 col-lg-6">
            <DataCard title="Recent Tournaments">
                <ul v-if="getRecentTournaments().length > 0" class="list-group list-group-flush">
                    <li v-for="t in getRecentTournaments()" :key="t.id" class="list-group-item d-flex justify-content-between align-items-center">
                        <RouterLink type="button" class="btn btn-sm btn-primary" :to="`/tournament/${t.id}/details`">
                            {{ t.name }}
                        </RouterLink>
                        <span class="badge text-bg-secondary">{{ t.timeControl }}</span>
                    </li>
                </ul>
                <div v-else class="text-secondary">
                    No tournaments available.
                </div>
            </DataCard>
        </div>
    </div>
    <Loading v-else />
</template>
