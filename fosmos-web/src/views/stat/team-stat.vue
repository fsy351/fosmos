<style>
    @import '../../styles/common.less';
</style>

<template>
    <div>
        <Row :gutter="10">
            <Card>
                <p slot="title">
                    <Icon type="stats-bars"></Icon>
                    年度团队数量
                </p>
                <div ref="year_chart" style="height:300px;"></div>
            </Card>
            <Card>
                <p slot="title">
                    <Icon type="stats-bars"></Icon>
                    技术领域团队分布
                </p>
                <div ref="tech_field_chart" style="height:300px;"></div>
            </Card>
        </Row>
    </div>

</template>

<script>

    import ajax from '@/libs/ajax';

    export default {
        name: "team-stat",
        data(){
            return{
                teamCntByTechField: [],
                teamCntByYear: [],
            };
        },

        methods: {
            getTeamCntByCtraYear() {

                let self = this;
                ajax({
                    method: 'post',
                    url: '/api/team-stat/getTeamCntByCtraYear',
                    //data:
                }).then(result => {
                    self.teamCntByYear = result;
                    self.initYearChart();
                }).catch(err => {
                    self.$Message.error("数据读取错误！")
                })
            },
            initYearChart() {
                let self = this;
                let chart = self.$echarts.init(self.$refs.year_chart);

                let dataKey = [];
                let dataVal = [];

                self.teamCntByYear.forEach((obj, index) => {
                    dataKey.push(obj.key);
                    dataVal.push(obj.count);
                });

                let option = {
                    xAxis: {
                        type: 'category',
                        data: dataKey
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: dataVal,
                        type: 'bar'
                    }]
                };
                chart.setOption(option);
            },

            getTeamCntByCtraTechField(){
                let self = this;
                ajax({
                    method: 'post',
                    url: '/api/team-stat/getTeamCntByCtraTechField',
                    //data:
                }).then(result =>{
                    self.teamCntByTechField = result;
                    self.initTechFieldChart();
                }).catch( err=>{
                    self.$Message.error("数据读取错误！")
                })
            },
            initTechFieldChart() {
                let self = this;
                let chart = self.$echarts.init(self.$refs.tech_field_chart);

                let dataKey = [];
                let dataVal = [];

                self.teamCntByTechField.forEach( (obj, index) => {
                    dataKey.push(obj.key);
                    dataVal.push(obj.count);
                });

                let option = {
                    xAxis: {
                        type: 'category',
                        data: dataKey
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: dataVal,
                        type: 'bar'
                    }]
                };
                chart.setOption(option);
            },
        },
        created: function () {
            this.getTeamCntByCtraTechField();
            this.getTeamCntByCtraYear();
        }
    }
</script>


