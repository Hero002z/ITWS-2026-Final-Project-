import type { PlayerModel } from './player.model'
import type { TournamentModel } from './tournament.model'

export interface MatchModel {
    id: number
    tournament: TournamentModel
    whitePlayer: PlayerModel
    blackPlayer: PlayerModel
    roundNumber: number
    result: string
    playedAt: string
    createdAt: string
    updatedAt: null | string
}