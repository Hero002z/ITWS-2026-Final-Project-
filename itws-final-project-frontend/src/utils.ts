import Swal from 'sweetalert2'

export function formatDate(iso: string | null) {
    if (iso == null) return 'N/A'
    return new Date(iso).toLocaleString('sr-RS', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    })
}

export const bootstrapClasses = {
    popup: 'card',
    cancelButton: 'btn btn-danger',
    denyButton: 'btn btn-secondary',
    confirmButton: 'btn btn-primary'
}

export function showConfirm(message: string, callback: Function) {
    Swal.fire({
        title: message,
        showCancelButton: true,
        confirmButtonText: 'Yes, continue',
        cancelButtonText: 'Cancel',
        icon: 'question',
        customClass: bootstrapClasses
    }).then(result => {
        if (result.isConfirmed) {
            callback()
            Swal.fire({
                title: 'Completed',
                confirmButtonText: 'OK',
                icon: 'success',
                customClass: bootstrapClasses
            })
        }
    })
}

export function validatePlayer(player: {
    firstName?: string
    lastName?: string
    country?: string
    rating?: number
}) {
    if (!player.firstName?.trim()) {
        return 'First name is required.'
    }

    if (!player.lastName?.trim()) {
        return 'Last name is required.'
    }

    if (!player.country?.trim()) {
        return 'Country is required.'
    }

    if (!player.rating || player.rating < 100) {
        return 'Rating must be at least 100.'
    }

    return null
}

export function validateTournament(tournament: {
    name?: string
    location?: string
    startDate?: string
    endDate?: string
}) {
    if (!tournament.name?.trim()) {
        return 'Tournament name is required.'
    }

    if (!tournament.location?.trim()) {
        return 'Location is required.'
    }

    if (!tournament.startDate) {
        return 'Start date is required.'
    }

    if (!tournament.endDate) {
        return 'End date is required.'
    }

    if (tournament.endDate < tournament.startDate) {
        return 'End date cannot be before start date.'
    }

    return null
}

export function validateMatch(match: {
    whitePlayer?: { id?: number }
    blackPlayer?: { id?: number }
    roundNumber?: number
    playedAt?: string
    result?: string
}) {
    if (!match.whitePlayer?.id) {
        return 'White player is required.'
    }

    if (!match.blackPlayer?.id) {
        return 'Black player is required.'
    }

    if (match.whitePlayer.id === match.blackPlayer.id) {
        return 'Players must be different.'
    }

    if (!match.roundNumber || match.roundNumber < 1) {
        return 'Round number must be greater than 0.'
    }

    if (match.result !== 'NOT_PLAYED' && !match.playedAt) {
    return 'Played date is required.'
    }

    return null
    
}

export function showError(error: string) {
    Swal.fire({
        title: 'Validation error',
        text: error,
        icon: 'error',
        confirmButtonText: 'OK',
        customClass: bootstrapClasses
    })
}