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
                    <Input v-model="formData.version"/>
                </FormItem>
                <FormItem label="编号" prop="docNo">
                    <Input v-model="formData.docNo"/>
                </FormItem>
                <FormItem label="文件名" prop="docName">
                    <Input v-model="formData.docName"/>
                </FormItem>
                <FormItem label="关键字" prop="docKeywords">
                    <Input v-model="formData.docKeywords"/>
                </FormItem>
                <FormItem label="文件类型" prop="docType">
                    <Select v-model="formData.docType">
                        <Option value="1">知识产权</Option>
                        <Option value="2">访谈记录</Option>
                        <Option value="3">管理制度</Option>
                        <Option value="4">报告</Option>
                    </Select>
                </FormItem>
                <FormItem label="技术领域" prop="techField">
                    <DictSelect :group-id="dictKey.TECH_FIELD" v-model="formData.techField"></DictSelect>
                </FormItem>
                <FormItem label="摘要" prop="docAbstract">
                    <Input type="textarea" v-model="formData.docAbstract"/>
                </FormItem>
                <template v-if="formData.docType == 1">
                    <FormItem label="专利类型" prop="ipType">
                        <Input v-model="formData.extendProp.ipType"/>
                    </FormItem>
                    <FormItem label="等级" prop="docLevel">
                        <Input v-model="formData.extendProp.docLevel"/>
                    </FormItem>
                    <FormItem label="所有人" prop="ipOwner">
                        <Input v-model="formData.extendProp.ipOwner"/>
                    </FormItem>
                    <FormItem label="发布日期" prop="pubDate">
                        <DatePicker type="date" :value="formData.extendProp.pubDate" @on-change="formData.extendProp.pubDate = $event"/>
                    </FormItem>
                    <FormItem label="生效日期" prop="effctDate">
                        <DatePicker type="date" :value="formData.extendProp.effctDate" @on-change="formData.extendProp.effctDate = $event"/>
                    </FormItem>
                    <FormItem label="是否废止" prop="abolish">
                        <DictSelect :group-id="dictKey.YES_OR_NO" v-model="formData.extendProp.abolish"/>
                    </FormItem>
                </template>
                <template v-else-if="formData.docType == 2">
                    <FormItem label="访谈时间" prop="intvDate">
                        <DatePicker type="date" :value="formData.extendProp.intvDate" @on-change="formData.extendProp.intvDate = $event"/>
                    </FormItem>
                    <FormItem label="访谈对象" prop="intvObj">
                        <Input v-model="formData.extendProp.intvObj"/>
                    </FormItem>
                </template>
                <template v-else-if="formData.docType == 3">
                    <FormItem label="等级" prop="docLevel">
                        <Input v-model="formData.extendProp.docLevel"/>
                    </FormItem>
                    <FormItem label="发布日期" prop="pubDate">
                        <DatePicker type="date" :value="formData.extendProp.pubDate" @on-change="formData.extendProp.pubDate = $event"/>
                    </FormItem>
                    <FormItem label="生效日期" prop="effctDate">
                        <DatePicker type="date" :value="formData.extendProp.effctDate" @on-change="formData.extendProp.effctDate = $event"/>
                    </FormItem>
                    <FormItem label="是否废止" prop="isAbolish">
                        <DictSelect :group-id="dictKey.YES_OR_NO" v-model="formData.extendProp.isAbolish"/>
                    </FormItem>
                </template>
                <template v-else-if="formData.docType == 4">
                    <FormItem label="访谈时间" prop="authors">
                        <Input v-model="formData.extendProp.authors"/>
                    </FormItem>
                    <FormItem label="是否废止" prop="pubDate">
                        <DictSelect v-model="formData.extendProp.pubDate" :group-id="dictKey.YES_OR_NO"></DictSelect>
                    </FormItem>
                </template>
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
    name: 'doc-edit',
    components: {DictSelect},
    data () {
        return {
            dictKey: dictKey,
            rules: {
                docNo: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                    { type: 'string', max: 50, message: '不能超过50字符', trigger: 'change' }
                ],
                docName: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                    { type: 'string', max: 100, message: '不能超过100字符', trigger: 'change' }
                ],
                docKeywords: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                    { type: 'string', max: 50, message: '不能超过50字符', trigger: 'change' }
                ],
                docAbstract: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                    { type: 'string', max: 100, message: '不能超过1000字符', trigger: 'change' }
                ],
                techField: [
                    { required: true, type:'string', message: '不能为空', trigger: 'change' }
                ],
            }
        };
    },
    computed:{
        ...mapState({
            formData: state => state.doc.formData
        })
    },
    methods: {
        ...mapActions({
            searchData: 'doc/searchData'
        }),
        submit () {
            let self = this;
            self.$refs.form.validate((valid) => {
                if (valid) {
                    ajax({
                        method: 'post',
                        url: '/api/doc/save',
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
