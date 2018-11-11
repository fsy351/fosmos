<style lang="less">
    @import '../../styles/common.less';
    //@import './components/table.less';
</style>

<template>
    <div>
        <Row>
            <Form ref="form" :model="formData" :rules="rules" :label-width="120">
                    <FormItem prop="id" style="display: none">
                        <Input v-model="formData.id"/>
                    </FormItem>
                    <FormItem label="人员" prop="prsnId">
                        <SelectComp type="person" v-model="formData.prsnId"/>
                    </FormItem>
                    <FormItem label="年份" prop="evalYear">
                        <DatePicker type="year" :value="formData.evalYear" @on-change="formData.evalYear = $event"/>
                    </FormItem>
                    <FormItem label="评分" prop="score">
                        <Input v-model="formData.score" placeholder=""/>
                    </FormItem>
                </Form>
            </Row>
    </div>
</template>

<script>

import { mapState, mapActions } from 'vuex'
import ajax from '@/libs/ajax'
import SelectComp from "../my-components/select-comp/select-comp";

export default {
    name: 'prsn-ability-edit',
    components: {SelectComp},
    data () {
        return {
            rules: {
                prsnId: [
                    { required: true, message: '不能为空', trigger: 'blur' }
                ],
                evalYear:[
                    { required: true, message: '不能为空', trigger: 'blur' }
                ],
                score: [
                    { required: true, message: '不能为空', trigger: 'blur' }
                ]
            }
        };
    },
    computed:{
        ...mapState({
            formData: state => state.prsnAbility.formData
        })
    },
    methods: {
        ...mapActions({
            searchData: 'prsnAbility/searchData'
        }),
        submit () {
            let self = this;
            self.$refs.form.validate((valid) => {
                if (valid) {
                    ajax({
                        method: 'post',
                        url: '/api/prsn-ability/save',
                        data: JSON.stringify(self.formData)
                    }).then((result) =>{
                        self.$Message.success('保存成功!');
                        self.reset(name);
                        self.searchData();
                    }).catch((err)=>{
                        self.$Message.error(err);
                    });
                }
            });
        },
        reset () {
            this.$refs.form.resetFields();
        }
    }
};
</script>
