<script lang="ts" setup>
export interface DataCardProps {
    title: string,
    btnTitle?: string,
    btnTo?: string
}

const props = defineProps<DataCardProps>()
</script>

<template>
    <div class="card">
        <div class="card-header">
            <h5>{{ props.title }}</h5>
        </div>
        <div class="card-body overflow-auto">
            <slot></slot>
        </div>
        <div class="card-footer" v-if="btnTitle && btnTo">
            <RouterLink type="button" class="btn btn-primary" :to="btnTo">
                <i class="fa-solid fa-plus"></i> {{ btnTitle }} 
            </RouterLink>
        </div>
    </div>
</template>