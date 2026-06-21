<script lang="ts" setup>
import FormCard from '@/components/FormCard.vue'
import Loading from '@/components/Loading.vue'
import type { MatchRequestModel } from '@/models/match-request.model'
import type { PlayerModel } from '@/models/player.model'
import { DataService } from '@/services/data.service'
import { showConfirm, showError, validateMatch } from '@/utils'
import { ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()
const tournamentId = Number(route.params.tournamentId)
const match = ref<MatchRequestModel>({
    tournament: {
        id: tournamentId
    },
    whitePlayer: {
        id: 0
    },
    blackPlayer: {
        id: 0
    },
    roundNumber: 1,
    result: 'NOT_PLAYED',
    playedAt: ''
})

const players = ref<PlayerModel[]>()
DataService.getPlayers()
    .then(rsp => {
        players.value = rsp.data
        if (rsp.data.length >= 2) {
            match.value.whitePlayer.id = rsp.data[0]!.id
            match.value.blackPlayer.id = rsp.data[1]!.id
        }
    })
    .catch(console.error)

const crumbs = [
    {
        title: 'Tournaments',
        to: '/tournament'
    },
    {
        title: 'Details',
        to: `/tournament/${tournamentId}/details`
    },
    {
        title: 'New Match'
    }
]

watch(() => match.value.result, (result) => {
    if (result === 'NOT_PLAYED') {
        match.value.playedAt = ''
    }
})

function create() {
    const error = validateMatch(match.value)
    if (error) {
        showError(error)
        return
    }
    showConfirm('Create new Match?', () => {
        DataService.createMatch(match.value)
            .then(rsp => router.push(`/tournament/${tournamentId}/details`))
            .catch(console.error)
    })
}
</script>

<template>
    <FormCard title="Create Match" :crumbs="crumbs" :on-click="create" v-if="players">
        <div v-if="players.length < 2" class="alert alert-warning">
            At least two players are required to create a match.
        </div>
        <div class="mb-3">
            <label for="white" class="form-label">White Player:</label>
            <select id="white" class="form-select" v-model="match.whitePlayer.id">
                <option v-for="p in players" :key="p.id" :value="p.id">
                    {{ p.firstName }} {{ p.lastName }}
                </option>
            </select>
        </div>

        <div class="mb-3">
            <label for="black" class="form-label">Black Player:</label>
            <select id="black" class="form-select" v-model="match.blackPlayer.id">
                <option v-for="p in players" :key="p.id" :value="p.id">
                    {{ p.firstName }} {{ p.lastName }}
                </option>
            </select>
        </div>

        <div class="mb-3">
            <label for="round" class="form-label">Round Number:</label>
            <input id="round" class="form-control" type="number" v-model="match.roundNumber">
        </div>

        <div class="mb-3">
            <label for="result" class="form-label">Result:</label>
            <select id="result" class="form-select" v-model="match.result">
                <option value="NOT_PLAYED">NOT PLAYED</option>
                <option value="WHITE_WIN">WHITE WIN</option>
                <option value="BLACK_WIN">BLACK WIN</option>
                <option value="DRAW">DRAW</option>
            </select>
        </div>

        <div class="mb-3">
            <label for="playedAt" class="form-label">Played At:</label>
            <input id="playedAt" class="form-control" type="datetime-local" v-model="match.playedAt" :disabled="match.result === 'NOT_PLAYED'">
        </div>
    </FormCard>
    <Loading v-else />
</template>