<template>
    <Select :loading="loading" :value="value" @on-change="$emit('change', $event)" :placeholder="placeholder">
        <Option v-if="value != null && value !== ''" value="">请选择</Option>
        <Option v-for="(item, idx) in dictList" :value="item.dictKey" :key="idx">{{item.dictName}}</Option>
    </Select>
</template>

<script>

    import { mapState, mapActions,mapGetters } from 'vuex'
    import ajax from '@/libs/ajax'
    import util from '@/libs/util'

    export default {
        name: "dict-select",
        data(){
            return{
                loading: false,
                dictList: [],
                options: []
            }
        },
        model: {
            prop: 'value',
            event: 'change'

        },
        props:{
            'groupId': String,
            'placeholder' : String,
            'value': String | Number
        },
        computed:{

        },
        methods:{
            ...mapActions({
                getGroupItems: 'dict/getGroupItems'
            }),
            /*
            filterGroupItems(query){
                this.options = this.dictList.filter(item =>
                    item.dictName.indexOf(query) > -1);
            }
            */
        },
        created:function () {
            this.loading = true;
            this.getGroupItems(this.groupId)
                .then(result =>{
                    this.loading = false;
                    this.dictList = result;
                }).catch(err =>{
                    this.loading = false;
                    this.$Message.error("字典加载错误！");
                });
            this.selected = this.default;
        }
    }
</script>

<style scoped>

</style>