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
                <FormItem label="编号" prop="prsnNo">
                    <Input v-model="formData.prsnNo" placeholder=""/>
                </FormItem>
                <FormItem label="姓名" prop="prsnName">
                    <Input v-model="formData.prsnName" placeholder=""/>
                </FormItem>
                <FormItem label="性别" prop="prsnSex">
                    <DictSelect :group-id="dictKey.SEX_TYPE" v-model="formData.prsnSex"></DictSelect>
                </FormItem>
                <FormItem label="所属单位" prop="instId">
                    <SelectComp type="inst" v-model="formData.instId"/>
                </FormItem>
                <FormItem label="所属团队" prop="belongTeamId">
                    <SelectComp type="team" v-model="formData.belongTeamId"></SelectComp>
                </FormItem>
                <FormItem label="职称" prop="title">
                    <DictSelect v-model="formData.title" :group-id="dictKey.TITLE_RANK"></DictSelect>
                </FormItem>
                <FormItem label="专业领域" prop="title">
                    <DictSelect v-model="formData.title" :group-id="dictKey.TITLE_RANK"></DictSelect>
                </FormItem>
                <FormItem label="技术领域" prop="researchArea">
                    <Input v-model="formData.researchArea" placeholder=""/>
                </FormItem>
            </Form>
        </Row>
    </div>
</template>

<script>

import { mapState, mapActions } from 'vuex'
import ajax from '@/libs/ajax'
import dictKey from "@/libs/dictKey"
import DictSelect from "../my-components/dict-select/dict-select";
import SelectComp from "../my-components/select-comp/select-comp";

export default {
    name: 'person-edit',
    components: {SelectComp, DictSelect},
    data () {
        return {
            dictKey:dictKey,
            rules: {
                prsnNo: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                    { type: 'string', max: 50, message: '不能超过50字符', trigger: 'blur' }
                ],
                prsnName: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                    { type: 'string', max: 100, message: '不能超过100字符', trigger: 'blur' }
                ],
                prsnSex:[
                    { required: true, message: '不能为空', trigger: 'blur' }
                ],
                techField:[
                    { required: true, message: '不能为空', trigger: 'blur' }
                ],
                title:[
                    { required: true, message: '不能为空', trigger: 'blur' }
                ],
                instId: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                ]
            }
        };
    },
    computed:{
        ...mapState({
            formData: state => state.person.formData
        })
    },
    methods: {
        ...mapActions({
            searchData: 'person/searchData'
        }),
        submit () {
            let self = this;
            self.$refs.form.validate((valid) => {
                if (valid) {
                    ajax({
                        method: 'post',
                        url: '/api/person/save',
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
