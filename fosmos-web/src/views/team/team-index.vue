<style lang="less">
    @import '../../styles/common.less';
    //@import './components/table.less';
</style>

<template>
    <div>
        <Row :gutter="10">
            <Col>
                <Card>
                    <p slot="title">
                        <Icon type="android-list"></Icon> 团队列表
                    </p>
                    <ButtonGroup slot="extra">
                        <Button @click="showCreateModal">
                            <Icon type="plus"></Icon>
                            新建
                        </Button>
                        <Button @click="showEditModal">
                            <Icon type="edit"></Icon>
                            编辑
                        </Button>
                        <Button @click="showDeleteModal">
                            <Icon type="ios-trash" ></Icon>
                            删除
                        </Button>
                        <router-link :to="{ path:'/doc/data-index', params: { type: 'team'}}">
                            <Button>
                                <Icon type="ios-upload"></Icon>
                                导入
                            </Button>
                        </router-link>
                        <!--
                        <Button>
                            <Icon type="ios-download"></Icon>
                            导出
                        </Button>
                        -->
                    </ButtonGroup>
                    <Row>
                        <Input v-model="filter.params.team_no" placeholder="团队编号" style="width: 200px" />
                        <Input v-model="filter.params.team_name" placeholder="团队名称" style="width: 200px" />
                        <span @click="searchData" style="margin: 0 10px;"><Button type="primary" icon="search">搜索</Button></span>
                    </Row>
                    <Row class="margin-top-8 searchable-table-con1">
                        <Table @on-selection-change="changeSelection" :columns="cloumns" @on-sort-change="changeSort"  height="400" :data="datas"></Table>
                    </Row>
                    <Row class="margin-top-8">
                        <Page :total="total" :page-size="filter.size" style="float: right"
                              @on-change="changePage" @on-page-size-change="changePageSize"
                              show-sizer show-elevator show-total></Page>
                    </Row>
                </Card>
            </Col>
        </Row>
        <Modal v-model="deleteModalShow" title="请确认操作" @on-ok="deleteItems">
                <p>确定删除选择的 {{selected.length}} 条数据吗？</p>
        </Modal>
        <Modal v-model="editModalShow" title="编辑" @on-ok="handleSubmit('editModel')" @on-cancel="handleCanncel('editModel')" >
            <TeamEdit ref="editModel"></TeamEdit>
        </Modal>
        <Modal v-model="createModalShow" title="编辑" @on-ok="handleSubmit('createModal')" @on-cancel="handleCanncel('createModal')" >
            <TeamEdit ref="createModal"></TeamEdit>
        </Modal>
    </div>
</template>

<script>

import { mapState, mapActions } from 'vuex'
import ajax from '@/libs/ajax'
import vue from 'vue'
import teamEdit from './team-edit.vue'

export default {
    name: 'team-index',
    components: {
        'TeamEdit':teamEdit
    },
    data () {
        return {
            deleteModalShow: false,
            editModalShow: false,
            createModalShow: false,
            selected: [],
            cloumns: [
                { type: 'selection', width: 55, align: 'center', fixed: 'left' },
                { key:"teamNo",title:"编号", sortable: true, width: 150},
                { key:"teamName",title:"名称", sortable: true, width: 200},
                { key:"teamLeaderName",title:"负责人", sortable: true, width: 100 },
                { key:"instName",title:"所属单位", sortable: true, width: 150}
            ]
        };
    },
    computed:{
        ...mapState({
            filter: state => state.team.filter,
            datas: state => state.team.datas,
            total: state => state.team.total,
            pages: state => state.team.pages,
            formData: state => state.team.formData
        })
    },
    methods: {
        ...mapActions({
            searchData: 'team/searchData',
            changeFormData: 'team/changeFormData'
        }),
        showCreateModal(){
            this.createModalShow = true;
        },
        showEditModal(){
            let self = this;
            if( self.selected.length !== 1 ) {
                self.$Message.warning("请选择一条记录");
            }else{
                ajax({
                    method: 'post',
                    url: '/api/team/edit',
                    data: self.selected[0]
                }).then((result) =>{
                    self.changeFormData(result);
                });
                self.editModalShow = true
            }
        },
        showDeleteModal(){
            this.deleteModalShow = true;
        },
        handleSubmit (name) {
            this.$refs[name].submit();
        },
        handleCanncel(name) {
            for(let p in this.formData){
                this.formData[p] = null;
            }
        },
        deleteItems(){
            ajax({
                method: 'post',
                url: '/api/team/delete',
                data: JSON.stringify(this.selected)
            }).then((result) =>{
                this.searchData();
                this.$Message.info("删除成功！");
            });
        },
        changeSelection(selected){
            let arr = [];
            selected.forEach((v,k) => {
                arr.push(v.id);
            });
            this.selected = arr;
        },
        changeSort(cloumn) {
            this.filter.order = cloumn.key;
            this.filter.asc = (cloumn.order === 'asc');
            this.searchData();
        },
        changePage(value){
            this.filter.cur = value;
            this.searchData();
        },
        changePageSize(value){
            this.filter.size = value;
            this.searchData();
        }
    },
    created:function() {
        this.searchData();
    }
};
</script>
