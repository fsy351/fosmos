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
                        <Icon type="android-list"></Icon> 人员能力列表
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
                        <router-link :to="{ path:'/doc/data-index', params: { type: 'prsnAbility'}}">
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
                        <Input v-model="filter.params.prsn_name" placeholder="人员姓名" style="width: 200px" />
                        <DatePicker :value="filter.params.eval_year" type="year" @on-change="filter.params.eval_year = $event"
                                    format="yyyy" placeholder="年份" style="width: 200px" />
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
            <PrsnAbilityEdit ref="editModel"></PrsnAbilityEdit>
        </Modal>
        <Modal v-model="createModalShow" title="编辑" @on-ok="handleSubmit('createModal')" @on-cancel="handleCanncel('createModal')" >
            <PrsnAbilityEdit ref="createModal"></PrsnAbilityEdit>
        </Modal>
    </div>
</template>

<script>
    import { mapState, mapActions } from 'vuex'
    import ajax from '@/libs/ajax'
    import dictKey from '@/libs/dictKey'
    import PrsnAbilityEdit from './prsn-ability-edit.vue'

    export default {
        name: 'prsn-ability-index',
        components: {
            'PrsnAbilityEdit': PrsnAbilityEdit
        },
        data () {
            return {
                dictKey: dictKey,
                deleteModalShow: false,
                editModalShow: false,
                createModalShow: false,
                selected: [],
                cloumns: [
                    { type: 'selection', width: 55, align: 'center', fixed: 'left' },
                    { key:"prsnNo",title:"编号", sortable: true, width: 150},
                    { key:"prsnName",title:"姓名", sortable: true, width: 150},
                    { key:"evalYear",title:"年份", sortable: true, width: 150},
                    { key:"score",title:"评分", sortable: true, width: 120},
                ]
            };
        },
        computed:{
            ...mapState({
                filter: state => state.prsnAbility.filter,
                datas: state => state.prsnAbility.datas,
                total: state => state.prsnAbility.total,
                pages: state => state.prsnAbility.pages,
                formData: state => state.prsnAbility.formData
            })
        },
        methods: {
            ...mapActions({
                searchData: 'prsnAbility/searchData',
                changeFormData: 'prsnAbility/changeFormData'
            }),
            getEvalYearFilter(ctx){
                console.log(ctx);
            },
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
                        url: '/api/prsn-ability/edit',
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
                    url: '/api/prsn-ability/delete',
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
