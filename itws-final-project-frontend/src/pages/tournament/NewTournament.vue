<script lang="ts" setup>
import FormCard from '@/components/FormCard.vue'
import type { TournamentModel } from '@/models/tournament.model'
import { DataService } from '@/services/data.service'
import { showConfirm, showError, validateTournament } from '@/utils'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const tournament = ref<Partial<TournamentModel>>({
    name: '',
    location: '',
    startDate: '',
    endDate: '',
    timeControl: 'CLASSICAL'
})

const crumbs = [
    {
        title: 'Tournaments',
        to: '/tournament'
    },
    {
        title: 'New'
    }
]

function create() {

    const error = validateTournament(tournament.value)

    if (error) {
        showError(error)
        return
    }

    showConfirm('Create new tournament?', () => {
        DataService.createTournament(tournament.value)
            .then(rsp => router.push('/tournament'))
            .catch(console.error)
    })
}
</script>

<template>
    <FormCard title="Create Tournament" :crumbs="crumbs" :on-click="create">

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
</template>