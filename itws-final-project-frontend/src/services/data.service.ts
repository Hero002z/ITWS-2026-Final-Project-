import axios from 'axios'
import type { MatchRequestModel } from '@/models/match-request.model'
import type { PlayerModel } from '@/models/player.model'
import type { TournamentModel } from '@/models/tournament.model'
import type { MatchModel } from '@/models/match.model'

export const API_BASE = 'http://localhost:7000/api'

const client = axios.create({
    baseURL: API_BASE,
    headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
    }
})

export class DataService {

    static async getPlayers() {
        return await client.get<PlayerModel[]>('/player')
    }

    static async getPlayerById(id: number) {
        return await client.get<PlayerModel>(`/player/${id}`)
    }

    static async createPlayer(player: Partial<PlayerModel>) {
        return await client.request({
            url: '/player',
            method: 'POST',
            data: player
        })
    }

    static async updatePlayer(id: number, player: PlayerModel) {
        return await client.request({
            url: `/player/${id}`,
            method: 'PUT',
            data: player
        })
    }

    static async deletePlayer(id: number) {
        return await client.request({
            url: `/player/${id}`,
            method: 'DELETE'
        })
    }

    static async getTournaments() {
        return await client.get<TournamentModel[]>('/tournament')
    }

    static async getTournamentById(id: number) {
        return await client.get<TournamentModel>(`/tournament/${id}`)
    }

    static async createTournament(tournament: Partial<TournamentModel>) {
        return await client.request({
            url: '/tournament',
            method: 'POST',
            data: tournament
        })
    }

    static async updateTournament(id: number, tournament: TournamentModel) {
        return await client.request({
            url: `/tournament/${id}`,
            method: 'PUT',
            data: tournament
        })
    }

    static async deleteTournament(id: number) {
        return await client.request({
            url: `/tournament/${id}`,
            method: 'DELETE'
        })
    }

    static async getMatches() {
        return await client.get<MatchModel[]>('/match')
    }

    static async getMatchById(id: number) {
        return await client.get<MatchModel>(`/match/${id}`)
    }

    static async getMatchesByTournamentId(id: number) {
        return await client.get(`/match/tournament/${id}`)
    }

    static async createMatch(match: Partial<MatchRequestModel>) {
        return await client.request({
            url: '/match',
            method: 'POST',
            data: match
        })
    }

    static async updateMatch(id: number, match: MatchRequestModel) {
        return await client.request({
            url: `/match/${id}`,
            method: 'PUT',
            data: match
        })
    }

    static async deleteMatch(id: number) {
        return await client.request({
            url: `/match/${id}`,
            method: 'DELETE'
        })
    }
}