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
                    <FormItem label="合同编号" prop="ctraNo">
                        <Input v-model="formData.ctraNo" placeholder=""/>
                    </FormItem>
                    <FormItem label="合同名称" prop="ctraName">
                        <Input v-model="formData.ctraName" placeholder=""/>
                    </FormItem>
                    <FormItem label="负责人" prop="projLeader">
                        <SelectComp type="person" v-model="formData.projLeader"></SelectComp>
                    </FormItem>
                    <FormItem label="法人单位" prop="legalEntity">
                        <SelectComp type="inst" v-model="formData.legalEntity"></SelectComp>
                    </FormItem>
                    <FormItem label="签订时间" prop="signDate">
                        <DatePicker type="date" :value="formData.signDate" @on-change="formData.signDate = $event"/>
                    </FormItem>
                    <FormItem label="项目类型" prop="projType">
                        <DictSelect :group-id="dictKey.PROJ_TYPE" v-model="formData.projType"></DictSelect>
                    </FormItem>
                    <FormItem label="技术领域" prop="techField">
                        <DictSelect :group-id="dictKey.TECH_FIELD" v-model="formData.techField"></DictSelect>
                    </FormItem>
                    <FormItem label="起始时间" prop="projStartDate">
                        <DatePicker type="date" :value="formData.projStartDate" @on-change="formData.projStartDate = $event"/>
                    </FormItem>
                    <FormItem label="验收时间" prop="projEvalDate">
                        <DatePicker type="date" :value="formData.projEvalDate" @on-change="formData.projEvalDate = $event"/>
                    </FormItem>
                    <FormItem label="验收分值" prop="evalScore">
                        <InputNumber v-model="formData.evalScore" :min="0"></InputNumber>
                    </FormItem>
                    <FormItem label="经费(元）" prop="funds">
                        <InputNumber v-model="formData.funds" :min="0"></InputNumber>
                    </FormItem>
                    <FormItem label="拨付率" prop="fundsApprRate">
                        <InputNumber v-model="formData.fundsApprRate" :min="0" :max="100"></InputNumber>
                    </FormItem>
                    <FormItem label="成果形式" prop="achivType">
                        <DictSelect :group-id="dictKey.ACHIV_TYPE" v-model="formData.achivType"></DictSelect>
                    </FormItem>
                    <FormItem label="成果描述" prop="achivDetials">
                        <Input v-model="formData.achivDetials" placeholder=""/>
                    </FormItem>
                </Form>
            </Row>
    </div>
</template>

<script>

import { mapState, mapActions } from 'vuex'
import ajax from '@/libs/ajax'
import dictKey from '@/libs/dictKey'
import DictSelect from "../my-components/dict-select/dict-select";
import SelectComp from "../my-components/select-comp/select-comp";

export default {
    name: 'contract-edit',
    components: {SelectComp, DictSelect},
    data () {
        return {
            dictKey: dictKey,
            rules: {
                ctraNo: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                    { type: 'string', max: 50, message: '不能超过50字符', trigger: 'blur' }
                ],
                ctraName: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                    { type: 'string', max: 100, message: '不能超过100字符', trigger: 'blur' }
                ],
                projLeader: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                    { type: 'string', max: 50, message: '不能超过50字符', trigger: 'blur' }
                ],
                legalEntity: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                    { type: 'string', max: 100, message: '不能超过100字符', trigger: 'blur' }
                ],
                signDate: [
                    { required: true, type:'string', message: '不能为空', trigger: 'blur' }
                ],
                projType:[],
                techField:[
                    { required: true, message: '不能为空', trigger: 'blur' }
                ],
                projStartDate:[],
                projEvalDate:[],
                evalScore:[],
                funds:[],
                fundsApprRate:[],
                achivType:[],
                achivDetials:[
                    { type: 'string',max:1000, message:'不超过1000字符', trigger: 'blur' }
                ]
            }
        };
    },
    computed:{
        ...mapState({
            formData: state => state.contract.formData
        })
    },
    methods: {
        ...mapActions({
            searchData: 'contract/searchData'
        }),
        submit () {
            let self = this;
            self.$refs.form.validate((valid) => {
                if (valid) {
                    ajax({
                        method: 'post',
                        url: '/api/contract/save',
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
