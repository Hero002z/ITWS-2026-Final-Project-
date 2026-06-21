import Home from '@/pages/Home.vue'
import Players from '@/pages/player/Players.vue'
import NewPlayer from '@/pages/player/NewPlayer.vue'
import EditPlayer from '@/pages/player/EditPlayer.vue'
import Tournaments from '@/pages/tournament/Tournaments.vue'
import NewTournament from '@/pages/tournament/NewTournament.vue'
import EditTournament from '@/pages/tournament/EditTournament.vue'
import NewMatch from '@/pages/match/NewMatch.vue'
import EditMatch from '@/pages/match/EditMatch.vue'
import TournamentDetails from '@/pages/tournament/TournamentDetails.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),

    routes: [
        {
            path: '/',
            component: Home,
            meta: {
                title: 'Home'
            }
        },

        {
            path: '/player',
            component: Players,
            meta: {
                title: 'Players'
            }
        },
        {
            path: '/player/new',
            component: NewPlayer,
            meta: {
                title: 'New Player'
            }
        },
        {
            path: '/player/:id',
            component: EditPlayer,
            meta: {
                title: 'Edit Player'
            }
        },
        {
            path: '/tournament',
            component: Tournaments,
            meta: {
                title: 'Tournaments'
            }
        },
        {
            path: '/tournament/new',
            component: NewTournament,
            meta: {
                title: 'New Tournament'
            }
        },
        {
            path: '/tournament/:id',
            component: EditTournament,
            meta: {
                title: 'Edit Tournament'
            }
        },
        {
            path: '/tournament/:id/details',
            component: TournamentDetails,
            meta: {
                title: 'Tournament Details'
            }
        },
        {
            path: '/match/new/:tournamentId',
            component: NewMatch,
            meta: {
                title: 'New Match'
            }
        },
        {
            path: '/match/:id',
            component: EditMatch,
            meta: {
                title: 'Edit Match'
            }
        }
    ]
})

router.afterEach((to, from, next) => {
    if (to.meta.title) {
        document.title = `${to.meta.title} :: Chess Hub`
    }
})

export default router
