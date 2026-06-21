<script lang="ts" setup>
import FormCard from '@/components/FormCard.vue'
import Loading from '@/components/Loading.vue'
import type { TournamentModel } from '@/models/tournament.model'
import { DataService } from '@/services/data.service'
import { showConfirm, showError, validateTournament } from '@/utils'
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const id = Number(route.params.id)

const tournament = ref<TournamentModel>()
DataService.getTournamentById(id)
    .then(rsp => tournament.value = rsp.data)
    .catch(console.error)

const crumbs = [
    {
        title: 'Tournaments',
        to: '/tournament'
    },
    {
        title: 'Edit'
    }
]

function update() {
    const error = validateTournament(tournament.value!)

    if (error) {
        showError(error)
        return
    }
    
    showConfirm('Save changes?', () => {
        DataService.updateTournament(id, tournament.value!)
            .then(rsp => router.push('/tournament'))
            .catch(console.error)
    })
}
</script>

<template>
    <FormCard title="Edit Tournament" :crumbs="crumbs" :on-click="update" v-if="tournament">
        <div class="mb-3">
            <label for="id" class="form-label">ID:</label>
            <input id="id" class="form-control" type="number" v-model="tournament.id" disabled>
        </div>

        <div class="mb-3">
            <label for="name" class="form-label">Name:</label>
            <input id="name" class="form-control" type="text" v-model="tournament.name">
        </div>

        <div class="mb-3">
            <label for="location" class="form-label">Location:</label>
            <input id="location" class="form-control" type="text" v-model="tournament.location">
        </div>

        <div class="mb-3">
            <label for="start" class="form-label">Start Date:</label>
            <input id="start" class="form-control" type="date" v-model="tournament.startDate">
        </div>

        <div class="mb-3">
            <label for="end" class="form-label">End Date:</label>
            <input id="end" class="form-control" type="date" v-model="tournament.endDate">
        </div>

        <div class="mb-3">
            <label for="time" class="form-label">Time Control:</label>
            <select id="time" class="form-select" v-model="tournament.timeControl">
                <option value="CLASSICAL">Classical</option>
                <option value="RAPID">Rapid</option>
                <option value="BLITZ">Blitz</option>
            </select>
        </div>
    </FormCard>
    <Loading v-else />
</template>