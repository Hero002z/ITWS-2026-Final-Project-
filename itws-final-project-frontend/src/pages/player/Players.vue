<script lang="ts" setup>
import DataCard from '@/components/DataCard.vue'
import Loading from '@/components/Loading.vue'
import type { PlayerModel } from '@/models/player.model'
import { DataService } from '@/services/data.service'
import { formatDate, showConfirm } from '@/utils'
import { onMounted, ref } from 'vue'

const players = ref<PlayerModel[]>()

function deleteData(player: PlayerModel) {
    showConfirm(`Are you sure you want to delete ${player.firstName} ${player.lastName}?`, () => {
        DataService.deletePlayer(player.id)
            .then(rsp => loadData())
            .catch(console.error)
    })
}

function loadData() {
    DataService.getPlayers()
        .then(rsp => players.value = rsp.data)
        .catch(console.error)
}

onMounted(() => loadData())
</script>

<template>
    <DataCard title="Player List" btn-title="Create Player" btn-to="/player/new" v-if="players">
        <table class="table">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Country</th>
                    <th>Rating</th>
                    <th>Title</th>
                    <th>Created At</th>
                    <th>Updated At</th>
                    <th>Options</th>
                </tr>
            </thead>

            <tbody>
                <tr v-if="players.length === 0">
                    <td colspan="8" class="text-center">
                        No players found.
                    </td>
                </tr>
                
                <tr v-for="p in players" :key="p.id">
                    <th>{{ p.id }}</th>
                    <td>{{ p.firstName }} {{ p.lastName }}</td>
                    <td>{{ p.country }}</td>
                    <td>{{ p.rating }}</td>
                    <td>{{ p.title }}</td>
                    <td>{{ formatDate(p.createdAt) }}</td>
                    <td>{{ formatDate(p.updatedAt) }}</td>
                    <td>
                        <div class="btn-group">
                            <RouterLink type="button" class="btn btn-sm btn-success" :to="`/player/${p.id}`">
                                <i class="fa-solid fa-pen-to-square"></i>
                            </RouterLink>
                            <button type="button" class="btn btn-sm btn-danger" @click="deleteData(p)">
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