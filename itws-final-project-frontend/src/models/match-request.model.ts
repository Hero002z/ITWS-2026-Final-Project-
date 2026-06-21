export interface MatchRequestModel {
    tournament: {
        id: number
    }

    whitePlayer: {
        id: number
    }

    blackPlayer: {
        id: number
    }

    roundNumber: number
    result: string
    playedAt: string
}