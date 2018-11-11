<style lang="less">
    @import '../../styles/common.less';
    //@import './components/table.less';
</style>

<template>
    <div>
        <Row :gutter="10">
            <Col>
                <Col span="8">
                    <Card>
                        <p slot="title">
                            <Icon type="android-list"></Icon> 字典组
                        </p>
                        <ButtonGroup slot="extra">
                            <Button @click="onCreateGroupBtnClk" size="small">
                                <Icon type="plus"></Icon>
                                新建
                            </Button>
                            <Button @click="onDeleteGroupBtnClk" size="small">
                                <Icon type="ios-trash"></Icon>
                                删除
                            </Button>
                        </ButtonGroup>
                        <Table :columns="cloumnsGroup" highlightRow height="400" :data="dict" @on-row-click="selectGroup"></Table>
                    </Card>
                </Col>
                <Col span="16">
                    <Card>
                        <p slot="title">
                            <Icon type="android-list"></Icon> 字典项
                        </p>
                        <ButtonGroup slot="extra">
                            <Button @click="onCreateItemBtnClk" size="small">
                                <Icon type="plus"></Icon>
                                新建
                            </Button>
                            <Button @click="onDeleteItemBtnClk" size="small">
                                <Icon type="ios-trash"></Icon>
                                删除
                            </Button>
                        </ButtonGroup>
                        <Table :columns="cloumnsItem" height="400" :data="getSelectedItems" highlightRow @on-row-click="selectItem"/>
                    </Card>
                </Col>
            </Col>
        </Row>

        <Modal v-model="deleteGroupModalShow" title="请确认操作" @on-ok="onDeleteGroupModalOk">
            <p>确定删除字典组?</p>
        </Modal>
        <Modal v-model="deleteItemModalShow" title="请确认操作" @on-ok="onDeleteItemModalOk">
            <p>确定删除字典项？</p>
        </Modal>
        <Modal v-model="createModalShow" title="编辑" @on-ok="onFormSubmit">
            <Form ref="formData" :model="formData" :rules="formDataRules" :label-width="120">
                <FormItem label="字典名" prop="dictName">
                    <Input v-model="formData.dictName" placeholder=""/>
                </FormItem>
                <FormItem label="字典编码" prop="dictKey">
                    <Input v-model="formData.dictKey" placeholder=""/>
                </FormItem>
            </Form>
        </Modal>
    </div>
</template>

<script>

    import { mapState, mapActions,mapGetters } from 'vuex'
    import ajax from '@/libs/ajax'
    import vue from 'vue'
    import util from '@/libs/util'

    export default {
        name: 'dict-index',
        data () {
            return {
                deleteGroupModalShow: false,
                deleteItemModalShow: false,
                createModalShow: false,
                cloumnsGroup: [
                    { key:"dictName",title:"组名", sortable: true, width: 100},
                    { key:"dictKey",title:"编号", sortable: true, width: 200}
                ],
                cloumnsItem: [
                    { key:"dictName",title:"组名", sortable: true, width: 150},
                    { key:"dictKey",title:"编号", sortable: true, width: 250}
                ],
                formDataRules:{

                }
            };
        },
        computed:{
            ...mapState({
                formData: state => state.dict.formData,
                dict: state => state.dict.dict,
                selectedGroup : state => state.dict.selectedGroup,
                selectedItem : state => state.dict.selectedItem,
            }),
            ...mapGetters({
                getSelectedItems: 'dict/getSelectedItems'
            })
        },
        methods: {
            ...mapActions({
                loadDict: 'dict/loadDict',
                selectGroup: 'dict/selectGroup',
                selectItem: 'dict/selectItem',
                deleteDict:'dict/deleteDict',
                saveDict:'dict/saveDict'
            }),
            onCreateGroupBtnClk(){
                this.formData.isGroup = 1;
                this.createModalShow = true;
            },
            onCreateItemBtnClk(){
                if( this.selectedGroup == null || util.isEmptyObject(this.selectedGroup) ) {
                    this.$Message.warning("请选择一行字典组");
                }else{
                    this.formData.isGroup = 0;
                    this.formData.parentId = this.selectedGroup.id;
                    this.createModalShow = true;
                }
            },
            onDeleteGroupBtnClk(){
                if( this.selectedGroup == null || util.isEmptyObject(this.selectedGroup) ) {
                    this.$Message.warning("请选择一行字典组");
                }else{
                    this.deleteGroupModalShow = true;
                }
            },
            onDeleteItemBtnClk(){
                if( this.selectedItem == null || util.isEmptyObject(this.selectedItem) ) {
                    this.$Message.warning("请选择一行字典项");
                }else{
                    this.deleteItemModalShow = true;
                }
            },
            onDeleteGroupModalOk(){
                let self = this;
                self.deleteDict(self.selectedGroup)
                    .then((result) => {
                        self.$Message.success("删除成功！");
                        self.loadDict();
                    }).catch((error) =>{
                        self.$Message.error("删除失败！");
                    });
            },
            onDeleteItemModalOk(){
                let self = this;
                self.deleteDict(self.selectedItem)
                    .then((result) => {
                        self.$Message.success("删除成功！");
                        self.loadDict();
                    }).catch((error) =>{
                    self.$Message.error("删除失败！");
                });
            },
            onFormSubmit(){
                let self = this;
                self.$refs['formData'].validate((valid) => {
                    if (valid) {
                        self.saveDict().then((result) => {
                            self.$Message.success("新建成功");
                            this.loadDict();
                        }).catch(error => {
                            self.$Message.error(error.message);
                        });
                    }
                });
            },
        },
        created:function() {
            this.loadDict();
        }
    };
</script>
