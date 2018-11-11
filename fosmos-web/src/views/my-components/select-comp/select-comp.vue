<template>
    <Select :loading="loading" :value="value" @on-change="$emit('change', $event)" :placeholder="placeholder">
        <Option v-if="value != null && value !== ''" value="">请选择</Option>
        <Option v-for="(item, idx) in options" :value="item.value" :key="idx">{{item.label}}</Option>
    </Select>
</template>

<script>

    import ajax from '@/libs/ajax'

    export default {
        name: "select-comp",
        data(){
            return{
                loading: false,
                options: []
            }
        },
        model: {
            prop: 'value',
            event: 'change'
        },
        props:{
            'type': {
                required: true
            },
            'placeholder':String,
            'value': String | Number
        },
        computed:{

        },
        methods:{

        },
        created:function () {
            this.loading = true;
            let url = '/api/select-comp/' + this.type;
            ajax({
                method: 'post',
                url: url
            }).then(result => {
                this.loading = false;
                this.options = result;
            }).catch(err =>{
                this.loading = false;
                this.$Message.error("数据加载错误！");
            });
        }
    }
</script>

<style scoped>

</style>