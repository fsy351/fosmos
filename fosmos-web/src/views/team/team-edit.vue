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
                    <FormItem label="编号" prop="teamNo">
                        <Input v-model="formData.teamNo" placeholder=""/>
                    </FormItem>
                    <FormItem label="名称" prop="teamName">
                        <Input v-model="formData.teamName" placeholder=""/>
                    </FormItem>
                    <FormItem label="负责人" prop="teamLeaderId">
                        <SelectComp type="person" v-model="formData.teamLeaderId"/>
                    </FormItem>
                    <FormItem label="所属单位" prop="instId">
                        <SelectComp type="inst"  v-model="formData.instId"/>
                    </FormItem>
                    <!--
                    <FormItem label="技术领域" prop="techField">
                        <Select v-model="formData.techField" placeholder="">
                            <Option value="0">New York</Option>
                            <Option value="1">London</Option>
                            <Option value="2">Sydney</Option>
                        </Select>
                    </FormItem>
                    -->
                </Form>
            </Row>
    </div>
</template>

<script>

import { mapState, mapActions } from 'vuex'
import ajax from '@/libs/ajax'
import SelectComp from "../my-components/select-comp/select-comp";

export default {
    name: 'team-edit',
    components: {SelectComp},
    data () {
        return {
            rules: {
                teamNo: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                    { type: 'string', max: 50, message: '不能超过50字符', trigger: 'change' }
                ],
                teamName: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                    { type: 'string', max: 100, message: '不能超过100字符', trigger: 'change' }
                ],
                teamLeaderId: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                    { type: 'string', max: 50, message: '不能超过50字符', trigger: 'change' }
                ],
                techField:[
                    { required: true, message: '不能为空', trigger: 'blur' }
                ],
                instId: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                    { type: 'string', max: 50, message: '不能超过50字符', trigger: 'change' }
                ]
            }
        };
    },
    computed:{
        ...mapState({
            formData: state => state.team.formData
        })
    },
    methods: {
        ...mapActions({
            searchData: 'team/searchData'
        }),
        submit () {
            let self = this;
            self.$refs.form.validate((valid) => {
                if (valid) {
                    ajax({
                        method: 'post',
                        url: '/api/team/save',
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
