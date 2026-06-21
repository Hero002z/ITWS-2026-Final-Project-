<script lang="ts" setup>
import FormCard from '@/components/FormCard.vue'
import Loading from '@/components/Loading.vue'
import type { PlayerModel } from '@/models/player.model'
import { DataService } from '@/services/data.service'
import { showConfirm, showError, validatePlayer } from '@/utils'
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const id = Number(route.params.id)

const player = ref<PlayerModel>()
DataService.getPlayerById(id)
    .then(rsp => player.value = rsp.data)
    .catch(console.error)

const crumbs = [
    {
        title: 'Players',
        to: '/player'
    },
    {
        title: 'Edit'
    }
]

function update() {
    const error = validatePlayer(player.value!)

    if (error) {
        showError(error)
        return
    }

    showConfirm('Save changes?', () => {
        DataService.updatePlayer(id, player.value!)
            .then(() => router.push('/player'))
            .catch(console.error)
    })
}
</script>

<template>
    <FormCard title="Edit Player" :crumbs="crumbs" :on-click="update" v-if="player">
        <div class="mb-3">
            <label for="id" class="form-label">ID:</label>
            <input id="id" class="form-control" type="number" v-model="player.id" disabled>
        </div>

        <div class="mb-3">
            <label for="first-name" class="form-label">First Name:</label>
            <input id="first-name" class="form-control" type="text" v-model="player.firstName">
        </div>

        <div class="mb-3">
            <label for="last-name" class="form-label">Last Name:</label>
            <input id="last-name" class="form-control" type="text" v-model="player.lastName">
        </div>

        <div class="mb-3">
            <label for="country" class="form-label">Country:</label>
            <input id="country" class="form-control" type="text" v-model="player.country">
        </div>

        <div class="mb-3">
            <label for="rating" class="form-label">Rating:</label>
            <input id="rating" class="form-control" type="number" v-model="player.rating">
        </div>

        <div class="mb-3">
            <label for="title" class="form-label">Title:</label>
            <select id="title" class="form-select" v-model="player.title">
                <option value="GM">GM</option>
                <option value="IM">IM</option>
                <option value="FM">FM</option>
                <option value="CM">CM</option>
                <option value="NONE">NONE</option>
            </select>
        </div>
    </FormCard>
    <Loading v-else />
</template>