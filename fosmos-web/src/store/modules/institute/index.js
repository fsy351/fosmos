
import ajax from '@/libs/ajax'

const types = {
    SET_FILTER : 'setFilter',
    SET_DATAS : 'setDatas',
    SET_FORMDATA: 'setFormData'
};

const state = {
    filter: {
        cur: 1,
        size: 10,
        order: null,
        asc: false,
        params: {
            inst_no: null,
            inst_name: null,
            inst_addr_prov: null,
        }
    },
    total: 0,
    pages: 0,
    datas: [],
    formData:{
        id: null,
        instNo: null,
        instName : null,
        instAddrProv: null,
        instAddrDetail :null
    }
};

const getters = {

};

const actions = {
    searchData({commit,state}){
        ajax({
            method: 'post',
            url: '/api/institute',
            data: JSON.stringify(state.filter)
        }).then((result) =>{
            commit(types.SET_FILTER, result);
            commit(types.SET_DATAS, result);
        });
    },

    changeFormData({commit},data) {
        commit(types.SET_FORMDATA, data);
    }
};

const mutations = {

    [types.SET_FILTER](state, page){
        state.filter.cur = page.current;
        state.pages = page.pages;
        state.total = page.total;
    },

    [types.SET_DATAS](state,page){
        state.datas = page.records;
    },


    [types.SET_FORMDATA](state,data){
        state.formData = data;
    }

};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}
