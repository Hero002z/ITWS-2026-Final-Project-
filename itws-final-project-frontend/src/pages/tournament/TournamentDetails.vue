<script setup lang="ts">
import DataCard from '@/components/DataCard.vue'
import Loading from '@/components/Loading.vue'
import type { MatchModel } from '@/models/match.model'
import type { TournamentModel } from '@/models/tournament.model'
import { DataService } from '@/services/data.service'
import { formatDate, showConfirm } from '@/utils'
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const id = Number(route.params.id)
const tournament = ref<TournamentModel>()
const matches = ref<MatchModel[]>([])

DataService.getTournamentById(id)
    .then(rsp => tournament.value = rsp.data)
    .catch(console.error)

function loadData() {
    DataService.getMatchesByTournamentId(id)
        .then(rsp => matches.value = rsp.data)
        .catch(console.error)
}

function deleteData(match: MatchModel) {
    showConfirm('Delete match?', () => {
        DataService.deleteMatch(match.id)
            .then(rsp => loadData())
            .catch(console.error)
    })
}

onMounted(() => loadData())
</script>

<template>
    <div v-if="tournament">
        <h2>{{ tournament.name }}</h2>
        <p>{{ tournament.location }} · {{ tournament.timeControl }}</p>
        <DataCard title="Matches" btn-title="Create Match" :btn-to="`/match/new/${tournament.id}`">
            <table class="table">
                <thead>
                    <tr>
                        <th>Round</th>
                        <th>White</th>
                        <th>Black</th>
                        <th>Result</th>
                        <th>Played At</th>
                        <th>Options</th>
                    </tr>
                </thead>

                <tbody>
                    <tr v-if="matches.length === 0">
                        <td colspan="6" class="text-center">
                            No matches found.
                        </td>
                    </tr>
                    
                    <tr v-for="m in matches" :key="m.id">
                        <td>{{ m.roundNumber }}</td>
                        <td>{{ m.whitePlayer.lastName }}</td>
                        <td>{{ m.blackPlayer.lastName }}</td>
                        <td>{{ m.result }}</td>
                        <td>{{ formatDate(m.playedAt) }}</td>
                        <td>
                            <div class="btn-group">
                                <RouterLink type="button" class="btn btn-sm btn-success" :to="`/match/${m.id}`">
                                    <i class="fa-solid fa-pen-to-square"></i>
                                </RouterLink>
                                <button type="button" class="btn btn-sm btn-danger" @click="deleteData(m)">
                                    <i class="fa-solid fa-trash-can"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </DataCard>
    </div>
    <Loading v-else />
</template>