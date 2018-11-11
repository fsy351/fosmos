import Vue from 'vue';
import Vuex from 'vuex';

import app from './modules/app';
import user from './modules/user';
import contract from './modules/contract'
import person from './modules/person'
import prsnAbility from './modules/prsn-ability'
import institute  from './modules/institute'
import doc from './modules/doc'
import team from './modules/team'
import dict from './modules/dict'
import achiv from './modules/achiv'

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        //
    },
    mutations: {
        //
    },
    actions: {

    },
    modules: {
        app,
        dict,
        user,
        contract,
        institute,
        person,
        prsnAbility,
        doc,
        achiv,
        team
    }
});

export default store;
