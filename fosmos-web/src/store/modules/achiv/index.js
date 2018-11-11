
import ajax from '@/libs/ajax'

const types = {
    SET_FILTER : 'setFilter',
    SET_DATAS : 'setDatas',
    SET_FORMDATA: 'setFormData',
    SET_CURRENT_TAB: 'setCurrentTab'
};

const state = {
    currentTab:'ip',
    ip: {
        filter: {
            cur: 1,
            size: 10,
            order: null,
            asc: false,
            params: {}
        },
        total: 0,
        pages: 0,
        datas: [],
        formData:{
            extendProp:{}
        },
    },

    std: {
        filter: {
            cur: 1,
            size: 10,
            order: null,
            asc: false,
            params: {}
        },
        total: 0,
        pages: 0,
        datas: [],
        formData:{
            extendProp:{}
        },
    },

    paper: {
        filter: {
            cur: 1,
            size: 10,
            order: null,
            asc: false,
            params: {}
        },
        total: 0,
        pages: 0,
        datas: [],
        formData:{
            extendProp:{}
        },
    },

    award: {
        filter: {
            cur: 1,
            size: 10,
            order: null,
            asc: false,
            params: {}
        },
        total: 0,
        pages: 0,
        datas: [],
        formData:{
            extendProp:{}
        },
    },
};

const getters = {

};

const actions = {
    searchData({commit,state}){
        ajax({
            method: 'post',
            url: '/api/achiv/' + state.currentTab,
            data: JSON.stringify(state[state.currentTab].filter)
        }).then((result) =>{
            commit(types.SET_FILTER, result);
            commit(types.SET_DATAS, result);
        });
    },

    changeFormData({commit},data) {
        commit(types.SET_FORMDATA, data);
    },

    changeCurTab({commit}, data) {
        commit(types.SET_CURRENT_TAB, data);
    }
};

const mutations = {

    [types.SET_FILTER](state, page){
        state[state.currentTab].filter.cur = page.current;
        state[state.currentTab].pages = page.pages;
        state[state.currentTab].total = page.total;
    },

    [types.SET_DATAS](state,page){
        state[state.currentTab].datas = page.records;
    },

    [types.SET_FORMDATA](state,data){
        state[state.currentTab].formData = data;
        if( !state[state.currentTab].formData.extendProp ) {
            state[state.currentTab].formData.extendProp = {};
        }
    },

    [types.SET_CURRENT_TAB](state,data){
        state.currentTab = data;
    }
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}
