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
                        <Icon type="android-list"></Icon> 文件列表
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
                            <Icon type="ios-trash"></Icon>
                            删除
                        </Button>
                        <router-link :to="{ path:'/doc/data-index', params: { type: 'doc'}}">
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
                        <Input v-model="filter.params.doc_no" placeholder="编号" style="width: 200px" />
                        <Input v-model="filter.params.doc_name" placeholder="文件名称" style="width: 200px" />
                        <Select v-model="filter.params.doc_type" placeholder="文件类型" style="width: 200px" >
                            <Option v-if="filter.params.doc_type && filter.params.doc_type !== ''" value="">全部</Option>
                            <Option value="1">知识产权</Option>
                            <Option value="2">访谈记录</Option>
                            <Option value="3">管理制度</Option>
                            <Option value="4">报告</Option>
                        </Select>
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
            <DocEdit ref="editModel"></DocEdit>
        </Modal>
        <Modal v-model="createModalShow" title="编辑" @on-ok="handleSubmit('createModal')" @on-cancel="handleCanncel('createModal')" >
            <DocEdit ref="createModal"></DocEdit>
        </Modal>
    </div>
</template>

<script>

import { mapState, mapActions } from 'vuex'
import ajax from '@/libs/ajax'
import util from '@/libs/util'
import vue from 'vue'
import DocEdit from './doc-edit.vue'

export default {
    name: 'doc-index',
    components: {
        'DocEdit':DocEdit
    },
    data () {
        return {
            deleteModalShow: false,
            editModalShow: false,
            createModalShow: false,
            selected: [],
            cloumns: [
                { type:'selection', width: 55, align: 'center', fixed: 'left' },
                { key:"docNo",title:"编号", sortable: true, width: 150},
                { key:"docName",title:"文件名", sortable: true, width: 200},
                { key:"docType",title:"文件类型", sortable: true, width: 120,
                    render:(h,c) => {
                        let docTypeName = null;
                        if(c.row.docType == '1') {
                            docTypeName = '知识产权'
                        }else if(c.row.docType == '2') {
                            docTypeName = '访谈记录'
                        }else if(c.row.docType == '3') {
                            docTypeName = '管理制度'
                        }else if(c.row.docType == '4') {
                            docTypeName = '报告'
                        }else{
                            docTypeName = '其他'
                        }
                        return h('p', docTypeName);
                    }
                },
                { key:"docKeywords",title:"关键词", sortable: true, width: 150},
                { key:"docAbstract",title:"摘要", sortable: true, width: 150},
                //{ key:"version",title:"版本", sortable: true, width: 80},
                { key:"Action",title:"操作", width: 150, align:'center',  fixed: 'right',
                    render: (h, c) => {
                        if( c.row.docAttachPath && c.row.docAttachPath !== "") {
                            return h('div',  [
                                h('Upload', {
                                        props: {
                                            action: 'http://localhost:8081/api/doc/upload',
                                            maxSize: 102400,
                                            showUploadList: false,
                                            data: {
                                                fileId: c.row.id
                                            }
                                        },
                                        style: {
                                            marginRight: '5px',
                                            display: 'inline-block'
                                        },
                                        on: {
                                            'success': function(e){
                                                this.$Notice.success({
                                                    title: '上传成功！',
                                                    duration: 0
                                                })
                                            },
                                            'error': function(e){
                                                this.$Notice.error({
                                                    title: '上传失败！',
                                                    duration: 0
                                                })
                                            }
                                        }
                                    }
                                    , [
                                        h('Button',{
                                            props: {
                                                type: 'primary',size: 'small'
                                            }
                                        },'重传')
                                    ]
                                ),
                                h('Button', {
                                    props: { type: 'error',  size: 'small'},
                                    on: {
                                        click: () => {
                                            window.open("http://localhost:8081/api/doc/download?docId="+ c.row.id);
                                        }
                                    }
                                }, '下载')
                            ]);
                        }else{
                            return h('div',  [
                                h('Upload', {
                                        props: {
                                            action: 'http://localhost:8081/api/doc/upload',
                                            maxSize: 102400,
                                            showUploadList: false,
                                            data: {
                                                fileId: c.row.id
                                            }
                                        },
                                        style: {
                                            marginRight: '5px',
                                            display: 'inline-block'
                                        },
                                        on: {
                                            'success': function(e){
                                                this.$Notice.success({
                                                    title: '上传成功！',
                                                    duration: 0
                                                })
                                            },
                                            'error': function(e){
                                                this.$Notice.error({
                                                    title: '上传失败！',
                                                    duration: 0
                                                })
                                            }
                                        }
                                    }
                                    , [
                                        h('Button',{
                                            props: {
                                                type: 'primary',size: 'small'
                                            }
                                        },'上传')
                                    ]
                                )
                            ]);
                        }
                    }
                },
            ]
        };
    },
    computed:{
        ...mapState({
            filter: state => state.doc.filter,
            datas: state => state.doc.datas,
            total: state => state.doc.total,
            pages: state => state.doc.pages,
            formData: state => state.doc.formData
        })
    },
    methods: {
        ...mapActions({
            searchData: 'doc/searchData',
            changeFormData: 'doc/changeFormData'
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
                    url: '/api/doc/edit',
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
                url: '/api/doc/delete',
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
