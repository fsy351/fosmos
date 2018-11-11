
import ajax from '@/libs/ajax'

const types = {
    SET_DICT: 'setDict',
    SET_DICT_ITEM : 'setDictItem',
    SET_SELECTED_GROUP: 'setSelected',
    SET_SELECTED_ITEM: 'setSelectedItem',
    SET_FORMDATA: 'setFormData',
    SET_SELECTED_GROUP_ITEMS: 'setSelectedGroupItems'
};

const state = {
    dict: [],
    formData: {
        // dictName: "",
        // dictKey: "",
        // parentId: "",
    },
    selectedGroup: null,
    selectedItem: null,
    selectedGroupItems: []
};

const getters = {

    getSelectedItems(state){
        let selected = state.selectedGroup;
        return  selected != null ? selected.items: [];
    }

};

const actions = {
    loadDict({commit}){
        ajax({
            method: 'post',
            url: '/api/dict'
        }).then((result) =>{
            commit(types.SET_DICT, result);
            commit(types.SET_SELECTED_GROUP);
        });
    },
    selectGroup({commit},data){
        commit(types.SET_SELECTED_GROUP,data);
    },
    selectItem({commit},data){
        commit(types.SET_SELECTED_ITEM,data);
    },
    deleteDict({commit},data){
        return ajax({
            method: 'post',
            url: '/api/dict/delete',
            data: data.id
        }).then( result => {
            if( data.isGroup === 1 ) {
                commit(types.SET_SELECTED_GROUP,null);
            }else{
                commit(types.SET_SELECTED_ITEM,null);
            }
            return result;
        });
    },
    saveDict({commit,state}){
        return ajax({
            method: 'post',
            url: '/api/dict/save',
            data: JSON.stringify(state.formData)
        }).then(result =>{
            commit(types.SET_FORMDATA,{});
            return result;
        });
    },
    getGroupItems({commit},groupId) {
        return ajax({
            method: 'post',
            url: '/api/dict/getItemsByPkey',
            data: groupId,
        }).then(result => {
            return result;
        });
    }
};

const mutations = {

    [types.SET_DICT](state, dict){
        state.dict = dict;
    },

    [types.SET_SELECTED_GROUP](state, selected){
        state.selectedGroup = selected;
    },

    [types.SET_SELECTED_ITEM](state, selected){
        state.selectedItem = selected;
    },

    [types.SET_FORMDATA](state, formData){
        state.formData = formData;
    },

    [types.SET_FORMDATA](state, formData){
        state.formData = formData;
    },

    [types.SET_SELECTED_GROUP_ITEMS](state){
        let selected = state.selectedGroup;
        return  selected != null ? selected.items: [];
    }
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}
