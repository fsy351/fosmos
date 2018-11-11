<style lang="less">
    @import '../../styles/common.less';
</style>

<template>
    <div>
        <Card>
            <p slot="title">
                <Icon type="android-list"></Icon> 数据导入
            </p>
            <Row style="margin:50px 100px 50px 50px; height: 250px;">
                <Form :model="uploadData" :label-width="125">
                    <FormItem label="导入表格类型" prop="dataTableType">
                        <Select v-model="uploadData.dataType">
                            <Option value="contract">合同表</Option>
                            <Option value="person">人员表</Option>
                            <Option value="prsnAbility">人员能力表</Option>
                            <Option value="inst">单位信息</Option>
                            <Option value="team">团队信息</Option>
                            <Option value="doc">文件信息</Option>
                            <Option value="archiv">成果信息</Option>
                        </Select>
                    </FormItem>
                    <FormItem label="上传文件选择" prop="file">
                        <Upload ref="upload" :data="uploadData"
                                action="//localhost:8081/api/data-import/upload"
                                :before-upload="onBeforeUpload"
                                :on-error="onError"
                                :on-success="onSuccess"  >
                            <Button type="ghost" icon="ios-cloud-upload-outline">选择上传的文件</Button>
                        </Upload>
                        <span v-if="file !== null">已选择: {{ file.name }} </span>
                    </FormItem>
                    <FormItem>
                        <Button type="primary" @click="onUpload" :loading="uploadStatus">
                            <p v-if="uploadStatus">
                                正在导入...
                            </p>
                            <p v-if="!uploadStatus">
                                导入
                            </p>
                        </Button>
                    </FormItem>
                </Form>
            </Row>
        </Card>
    </div>
</template>

<script>

export default {
    name: 'data-index',
    data () {
        return {
            file : null,
            current: 0,
            status: 'process',
            uploadData: {},
            uploadStatus: false
        };
    },
    methods: {
        onBeforeUpload(file){
            this.file = file;
            return false;
        },
        onUpload(){
            this.uploadStatus = true;
            this.$refs.upload.post(this.file);
        },
        onError(){
            this.uploadStatus = false;
            this.$Notice.error({
                title: "数据导入失败！",
                duration: 0
            });
        },
        onSuccess(){
            this.uploadStatus = false;
            this.$Notice.success({
                title: "数据导入成功！",
                duration: 0
            });
        },
    },
    mounted () {
        //this.uploadList = this.$refs.upload.fileList;
    }
};
</script>
