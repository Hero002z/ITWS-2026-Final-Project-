<script lang="ts" setup>
import FormCard from '@/components/FormCard.vue'
import Loading from '@/components/Loading.vue'
import type { MatchRequestModel } from '@/models/match-request.model'
import type { MatchModel } from '@/models/match.model'
import type { PlayerModel } from '@/models/player.model'
import type { TournamentModel } from '@/models/tournament.model'
import { DataService } from '@/services/data.service'
import { showConfirm, showError, validateMatch } from '@/utils'
import { ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const id = Number(route.params.id)
const match = ref<MatchModel>()
const players = ref<PlayerModel[]>([])
const tournaments = ref<TournamentModel[]>([])

DataService.getMatchById(id)
    .then(rsp => match.value = rsp.data)
    .catch(console.error)

DataService.getPlayers()
    .then(rsp => players.value = rsp.data)
    .catch(console.error)

DataService.getTournaments()
    .then(rsp => tournaments.value = rsp.data)
    .catch(console.error)

const crumbs = [
    {
        title: 'Tournaments',
        to: '/tournament'
    },
    {
        title: 'Details',
        to: `/tournament/${match.value?.tournament.id}/details`
    },
    {
        title: 'Edit Match'
    }
]

watch(() => match.value?.result, (result) => {
    if (result === 'NOT_PLAYED' && match.value) {
        match.value.playedAt = ''
    }
})

function update() {
    const error = validateMatch(match.value!)

    if (error) {
        showError(error)
        return
    }

    const request: MatchRequestModel = {
        tournament: {
            id: match.value!.tournament.id
        },
        whitePlayer: {
            id: match.value!.whitePlayer.id
        },
        blackPlayer: {
            id: match.value!.blackPlayer.id
        },
        roundNumber: match.value!.roundNumber,
        result: match.value!.result,
        playedAt: match.value!.playedAt
    }

    showConfirm('Save changes?', () => {
        DataService.updateMatch(id, request)
            .then(rsp => router.push(`/tournament/${match.value!.tournament.id}/details`))
            .catch(console.error)
    })
}
</script>

<template>
    <FormCard v-if="match" title="Edit Match" :crumbs="crumbs" :on-click="update">
        <div class="mb-3">
            <label for="id" class="form-label">ID:</label>
            <input id="id" class="form-control" type="number" :value="match.id" disabled>
        </div>

        <div class="mb-3">
            <label for="tournament" class="form-label">Tournament:</label>
            <select id="tournament" class="form-select" v-model="match.tournament.id">
                <option v-for="t in tournaments" :key="t.id" :value="t.id">
                    {{ t.name }}
                </option>
            </select>
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
            <input id="playerAt" class="form-control" type="datetime-local" v-model="match.playedAt" :disabled="match.result === 'NOT_PLAYED'">
        </div>
    </FormCard>
    <Loading v-else />
</template>