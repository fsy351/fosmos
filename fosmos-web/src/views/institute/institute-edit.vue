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
                <FormItem label="编号" prop="instNo">
                    <Input v-model="formData.instNo" placeholder=""/>
                </FormItem>
                <FormItem label="名称" prop="instName">
                    <Input v-model="formData.instName" placeholder=""/>
                </FormItem>
                <FormItem label="类型" prop="instType">
                    <DictSelect :group-id="dictKey.INST_TYPE" v-model="formData.instType"/>
                </FormItem>
                <FormItem label="类型" prop="instType">
                    <DictSelect :group-id="dictKey.INST_TYPE2" v-model="formData.instType2"/>
                </FormItem>
                <FormItem label="所在地" prop="instAddrProv">
                    <DictSelect :group-id="dictKey.PROVINCE" v-model="formData.instAddrProv"></DictSelect>
                </FormItem>
                <FormItem label="详细地址" prop="instAddrDetail">
                    <Input v-model="formData.instAddrDetail" placeholder=""/>
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

export default {
    name: 'contract-edit',
    components: {DictSelect},
    data () {
        return {
            dictKey: dictKey,
            rules: {
                instNo: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                    { type: 'string', max: 50, message: '不能超过50字符', trigger: 'blur' }
                ],
                instName: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                    { type: 'string', max: 100, message: '不能超过100字符', trigger: 'blur' }
                ],
                instType:[
                    { required: true, message: '不能为空', trigger: 'blur' },
                ],
                instAddrProv: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                ],
                instAddrDetail: [
                    { required: false },
                    { type: 'string', max: 200, message: '不能超过200字符', trigger: 'blur' }
                ],
            }
        };
    },
    computed:{
        ...mapState({
            formData: state => state.institute.formData
        })
    },
    methods: {
        ...mapActions({
            searchData: 'institute/searchData'
        }),
        submit () {
            let self = this;
            self.$refs.form.validate((valid) => {
                if (valid) {
                    ajax({
                        method: 'post',
                        url: '/api/institute/save',
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
