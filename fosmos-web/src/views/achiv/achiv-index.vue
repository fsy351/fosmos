<style lang="less">
    @import '../../styles/common.less';
    //@import './components/table.less';
</style>

<template>
    <div>
        <Row :gutter="10">
            <Col>
                <Tabs type="card" :value="currentTab" @on-click="onTabChange">
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
                            <Icon type="ios-trash"></Icon>
                            删除
                        </Button>
                        <router-link :to="{ path:'/doc/data-index', params: { type: 'achiv'}}">
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
                    <TabPane label="知识产权" name="ip">
                        <Row>
                            <Input v-model="ip.filter.params.achiv_name" placeholder="成果名" style="width: 200px" />
                            <span @click="searchData" style="margin: 0 10px;"><Button type="primary" icon="search">搜索</Button></span>
                            <p slot="title">
                                <Icon type="android-list"></Icon> 成果列表
                            </p>
                        </Row>
                        <Row class="margin-top-8 searchable-table-con1">
                            <Table :data="ip.datas" @on-selection-change="changeSelection" :columns="cloumns.ip" @on-sort-change="changeSort"  height="400" ></Table>
                        </Row>
                        <Row class="margin-top-8">
                            <Page :total="ip.total" :page-size="ip.filter.size" style="float: right"
                                  @on-change="changePage" @on-page-size-change="changePageSize"
                                  show-sizer show-elevator show-total/>
                        </Row>
                    </TabPane>
                    <TabPane label="标准" name="std">
                        <Row>
                            <Input v-model="std.filter.params.achiv_name" placeholder="成果名" style="width: 200px" />
                            <span @click="searchData" style="margin: 0 10px;"><Button type="primary" icon="search">搜索</Button></span>
                        </Row>
                        <Row class="margin-top-8 searchable-table-con1">
                            <Table :data="std.datas" @on-selection-change="changeSelection" :columns="cloumns.std" @on-sort-change="changeSort"  height="400"></Table>
                        </Row>
                        <Row class="margin-top-8">
                            <Page :total="std.total" :page-size="std.filter.size" style="float: right"
                                  @on-change="changePage" @on-page-size-change="changePageSize"
                                  show-sizer show-elevator show-total/>
                        </Row>
                    </TabPane>
                    <TabPane label="论文著作" name="paper" >
                        <Row>
                            <Input v-model="paper.filter.params.achiv_name" placeholder="成果名" style="width: 200px" />
                            <span @click="searchData" style="margin: 0 10px;"><Button type="primary" icon="search">搜索</Button></span>
                        </Row>
                        <Row class="margin-top-8 searchable-table-con1">
                            <Table :data="paper.datas" @on-selection-change="changeSelection" :columns="cloumns.paper" @on-sort-change="changeSort"  height="400"></Table>
                        </Row>
                        <Row class="margin-top-8">
                            <Page :total="paper.total" :page-size="paper.filter.size" style="float: right"
                                  @on-change="changePage" @on-page-size-change="changePageSize"
                                  show-sizer show-elevator show-total></Page>
                        </Row>
                    </TabPane>
                    <TabPane label="奖项" name="award">
                        <Row>
                            <Input v-model="award.filter.params.achiv_name" placeholder="成果名" style="width: 200px" />
                            <span @click="searchData" style="margin: 0 10px;"><Button type="primary" icon="search">搜索</Button></span>
                        </Row>
                        <Row class="margin-top-8 searchable-table-con1">
                            <Table :data="award.datas" @on-selection-change="changeSelection" :columns="cloumns.award" @on-sort-change="changeSort"  height="400"></Table>
                        </Row>
                        <Row class="margin-top-8">
                            <Page :total="award.total" :page-size="award.filter.size" style="float: right"
                                  @on-change="changePage" @on-page-size-change="changePageSize"
                                  show-sizer show-elevator show-total></Page>
                        </Row>
                    </TabPane>
                </Tabs>
            </Col>
        </Row>
        <Modal v-model="deleteModalShow" title="请确认操作" @on-ok="deleteItems">
                <p>确定删除选择的数据吗？</p>
        </Modal>
        <Modal v-model="editModalShow" title="编辑" @on-ok="handleSubmit('editModel')" @on-cancel="handleCanncel('editModel')" >
            <AchivEdit ref="editModel" :is-edit="true"></AchivEdit>
        </Modal>
        <Modal v-model="createModalShow" title="编辑" @on-ok="handleSubmit('createModal')" @on-cancel="handleCanncel('createModal')" >
            <AchivEdit ref="createModal" :is-edit="false"></AchivEdit>
        </Modal>
    </div>
</template>

<script>

import { mapState, mapActions } from 'vuex'
import ajax from '@/libs/ajax'
import util from '@/libs/util'
import vue from 'vue'
import AchivEdit from './achiv-edit'

export default {
    name: 'achiv-index',
    components: {
        'AchivEdit':AchivEdit
    },
    data () {
        return {
            deleteModalShow: false,
            editModalShow: false,
            createModalShow: false,
            selected: {
                ip:[], std:[], paper:[], award:[]
            },
            cloumns:{
                ip:[
                    { type:'selection', width: 55, align: 'center', fixed: 'left' },
                    { key:"achivName",title:"成果名", sortable: true, width: 150},
                    { key:"techField",title:"技术领域", sortable: true, width: 120 },
                    { key:"belongInstName", title:"所属单位", sortable: true, width: 200},
                    { key:"getOrPubDate",title:"发布（获取）时间", sortable: true, width: 150},
                ],
                std:[
                    { type:'selection', width: 55, align: 'center', fixed: 'left' },
                    { key:"achivName",title:"成果名", sortable: true, width: 150},
                    { key:"techField",title:"技术领域", sortable: true, width: 120 },
                    { key:"belongInstName", title:"所属单位", sortable: true, width: 200},
                    { key:"getOrPubDate",title:"发布（获取）时间", sortable: true, width: 150},
                ],
                paper:[
                    { type:'selection', width: 55, align: 'center', fixed: 'left' },
                    { key:"achivName",title:"成果名", sortable: true, width: 150},
                    { key:"techField",title:"技术领域", sortable: true, width: 120 },
                    { key:"belongInstName", title:"所属单位", sortable: false, width: 200},
                    { key:"getOrPubDate",title:"发布（获取）时间", sortable: true, width: 150},
                ],
                award:[
                    { type:'selection', width: 55, align: 'center', fixed: 'left' },
                    { key:"achivName",title:"成果名", sortable: true, width: 150},
                    { key:"techField",title:"技术领域", sortable: true, width: 120 },
                    { key:"belongInstName", title:"所属单位", sortable: false, width: 200},
                    { key:"getOrPubDate",title:"发布（获取）时间", sortable: true, width: 150},
                ]
            }
        };
    },
    computed:{
        ...mapState({
            currentTab: state => state.achiv.currentTab,
            ip: state => state.achiv.ip,
            std: state => state.achiv.std,
            paper: state => state.achiv.paper,
            award: state => state.achiv.award,
        })
    },
    methods: {
        ...mapActions({
            searchData: 'achiv/searchData',
            changeFormData: 'achiv/changeFormData',
            changeCurTab: 'achiv/changeCurTab'
        }),
        onTabChange(value){
            this.changeCurTab(value);
            this.searchData();
        },
        showCreateModal(){
            this.createModalShow = true;
        },
        showEditModal(){
            let self = this;
            if( self.selected[self.currentTab].length !== 1 ) {
                self.$Message.warning("请选择一条记录");
            }else{
                ajax({
                    method: 'post',
                    url: '/api/achiv/edit',
                    data: self.selected[self.currentTab][0]
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
                url: '/api/achiv/delete',
                data: JSON.stringify(this.selected[this.currentTab])
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
            this.selected[this.currentTab] = arr;
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
