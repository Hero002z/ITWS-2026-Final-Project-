<script lang="ts" setup>
import DataCard from '@/components/DataCard.vue'
import Loading from '@/components/Loading.vue'
import type { TournamentModel } from '@/models/tournament.model'
import { DataService } from '@/services/data.service'
import { formatDate, showConfirm } from '@/utils'
import { onMounted, ref } from 'vue'

const tournaments = ref<TournamentModel[]>()

function deleteData(tournament: TournamentModel) {
    showConfirm(`Are you sure you want to delete ${tournament.name}?`, () => {
            DataService.deleteTournament(tournament.id)
                .then(rsp => loadData())
                .catch(console.error)
    })
}

function loadData() {
    DataService.getTournaments()
        .then(rsp => tournaments.value = rsp.data)
        .catch(console.error)
}

onMounted(() => loadData())
</script>

<template>
    <DataCard title="Tournament List" btn-title="Create Tournament" btn-to="/tournament/new" v-if="tournaments">
        <table class="table">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Location</th>
                    <th>Time Control</th>
                    <th>Created At</th>
                    <th>Updated At</th>
                    <th>Options</th>
                </tr>
            </thead>

            <tbody>
                <tr v-if="tournaments.length === 0">
                    <td colspan="7" class="text-center">
                        No tournaments found.
                    </td>
                </tr>

                <tr v-for="t in tournaments" :key="t.id">
                    <th>{{ t.id }}</th>
                    <td>{{ t.name }}</td>
                    <td>{{ t.location }}</td>
                    <td>{{ t.timeControl }}</td>
                    <td>{{ formatDate(t.createdAt) }}</td>
                    <td>{{ formatDate(t.updatedAt) }}</td>
                    <td>
                        <div class="btn-group">
                            <RouterLink type="button" class="btn btn-sm btn-primary" :to="`/tournament/${t.id}/details`">
                                <i class="fa-solid fa-list"></i>
                            </RouterLink>
                            <RouterLink type="button" class="btn btn-sm btn-success" :to="`/tournament/${t.id}`">
                                <i class="fa-solid fa-pen-to-square"></i>
                            </RouterLink>
                            <button type="button" class="btn btn-sm btn-danger" @click="deleteData(t)">
                                <i class="fa-solid fa-trash-can"></i>
                            </button>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </DataCard>

    <Loading v-else />
</template>