<style lang="less">
    @import '../../styles/common.less';
</style>

<template>
    <div>
        <Row>
            <Form ref="form" :model="formData" :rules="rules" :label-width="120">
                <FormItem prop="id" style="display: none">
                    <Input v-model="formData.id"/>
                </FormItem>
                <FormItem label="成果名" prop="achivName">
                    <Input v-model="formData.achivName"/>
                </FormItem>
                <FormItem label="成果类型" prop="achivType">
                    <Select v-if="isEdit == true" v-model="formData.achivType" disabled>
                        <Option value="ip">知识产权</Option>
                        <Option value="std">标准</Option>
                        <Option value="paper">论文著作</Option>
                        <Option value="award">奖项</Option>
                    </Select>
                    <Select v-else-if="isEdit == false" v-model="formData.achivType">
                        <Option value="ip">知识产权</Option>
                        <Option value="std">标准</Option>
                        <Option value="paper">论文著作</Option>
                        <Option value="award">奖项</Option>
                    </Select>
                </FormItem>
                <FormItem label="技术领域" prop="techField">
                    <DictSelect :group-id="dictKey.TECH_FIELD" v-model="formData.techField"></DictSelect>
                </FormItem>
                <FormItem label="发布（获得）日期" prop="getOrPubDate">
                    <DatePicker type="date" :value="formData.getOrPubDate" @on-change="formData.getOrPubDate = $event"/>
                </FormItem>
                <template v-if="formData.achivType === 'ip'">
                    <FormItem label="专利类型" prop="ipType">
                        <Input v-model="formData.extendProp.ipType"/>
                    </FormItem>
                    <FormItem label="所有人" prop="ipOwner">
                        <Input v-model="formData.extendProp.ipOwner"/>
                    </FormItem>
                </template>
                <template v-else-if="formData.achivType === 'std'">
                    <FormItem label="拥有者" prop="intvObj">
                        <Input v-model="formData.extendProp.stdOwner"/>
                    </FormItem>
                    <FormItem label="标准等级" prop="intvObj">
                        <Input v-model="formData.extendProp.stdLevel"/>
                    </FormItem>
                    <!--
                    <FormItem label="访谈对象" prop="intvObj">
                        <Input v-model="formData.extendProp.stdDraftInst"/>
                    </FormItem>
                    -->
                </template>
                <template v-else-if="formData.achivType === 'paper'">
                    <FormItem label="作者" prop="authors">
                        <Input v-model="formData.extendProp.authors"/>
                    </FormItem>
                    <FormItem label="出版" prop="publisher">
                        <Input v-model="formData.extendProp.publisher"/>
                    </FormItem>
                </template>
                <template v-else-if="formData.achivType === 'award'">
                    <FormItem label="奖励类别" prop="awardType">
                        <Input v-model="formData.extendProp.awardType"/>
                    </FormItem>
                    <FormItem label="涉及人员" prop="members">
                        <Input v-model="formData.extendProp.members"/>
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
    name: 'achiv-edit',
    components: {DictSelect},
    props:{
        'isEdit': Boolean
    },
    data () {
        return {
            dictKey: dictKey,
            rules: {
                achivName: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                    { type: 'string', max: 100, message: '不能超过100字符', trigger: 'change' }
                ],
                achivType: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                ],
                techField: [
                    { required: true, message: '不能为空', trigger: 'blur' },
                ]
            }
        };
    },
    computed:{
        ...mapState({
            currentTab: state => state.achiv.currentTab,
            formData: state => state.achiv[state.achiv.currentTab].formData
        })
    },
    methods: {
        ...mapActions({
            searchData: 'achiv/searchData'
        }),
        submit () {
            let self = this;
            self.$refs.form.validate((valid) => {
                if (valid) {
                    ajax({
                        method: 'post',
                        url: '/api/achiv/save',
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
