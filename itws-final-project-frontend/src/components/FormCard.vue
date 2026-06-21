<script lang="ts" setup>
export interface FormCardProps {
    title: string,
    crumbs: {
        title: string
        to?: string
    }[],
    onClick: Function
    btnText?: string
    btnIcon?: string
}

const props = defineProps<FormCardProps>()
</script>

<template>
    <div class="crud-wrapper">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <RouterLink to="/">Home</RouterLink>
                </li>
                <template v-for="crumb in props.crumbs">
                    <li class="breadcrumb-item" v-if="crumb.to">
                        <RouterLink :to="crumb.to">{{ crumb.title }}</RouterLink>
                    </li>
                    <li class="breadcrumb-item active" aria-current="page" v-else>
                        {{ crumb.title }}
                    </li>
                </template>
            </ol>
        </nav>
        <div class="card">
            <div class="card-header">
                <h5>{{ props.title }}</h5>
            </div>
            <div class="card-body">
                <slot></slot>
            </div>
            <div class="card-footer">
                <button type="button" class="btn btn-success" @click="onClick()">
                    <i :class="btnIcon ?? 'fa-solid fa-floppy-disk'"></i> {{ btnText ?? 'Save changes' }}
                </button>
            </div>
        </div>
    </div>
</template>