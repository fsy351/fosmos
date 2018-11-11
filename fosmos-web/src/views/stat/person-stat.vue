<style>
    @import '../../styles/common.less';
</style>

<template>
    <div>
        <Row :gutter="10">
            <Card>
                <p slot="title">
                    <Icon type="stats-bars"></Icon>
                    技术领域人员分布
                </p>
                <div ref="tech_filed_chart" style="height:300px;"></div>
            </Card>
            <Card>
                <p slot="title">
                    <Icon type="stats-bars"></Icon>
                    职称人员分布
                </p>
                <div ref="title_chart" style="height:300px;"></div>
            </Card>
            <Card>
                <p slot="title">
                    <Icon type="stats-bars"></Icon>
                    技术领域人员等级分布
                </p>
                <div ref="rank_chart" style="height:300px;"></div>
            </Card>
        </Row>
    </div>

</template>

<script>

    import ajax from '@/libs/ajax';

    export default {
        name: "person-stat",
        data(){
            return{
                prsnCntByTechField: [],
                prsnCntByTitle: [],
                prsnCntByRank: []
            };
        },
        methods:{
            getPrsnCntByTechField(){
                let self = this;
                ajax({
                    method: 'post',
                    url: '/api/person-stat/getPrsnCntByTechField',
                    //data:
                }).then(result =>{
                    self.prsnCntByTechField = result;
                    self.initTechFieldChart();
                }).catch( err=>{
                    self.$Message.error("数据读取错误！")
                })
            },
            initTechFieldChart() {
                let self = this;
                let chart = self.$echarts.init(self.$refs.tech_filed_chart);

                let dataKey = [];
                let dataVal = [];

                self.prsnCntByTechField.forEach( (obj, index) => {
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

            getPrsnCntByTitle(){
                let self = this;
                ajax({
                    method: 'post',
                    url: '/api/person-stat/getPrsnCntByTitle',
                    //data:
                }).then(result =>{
                    self.prsnCntByTitle = result;
                    self.initTitleChart();
                }).catch( err=>{
                    self.$Message.error("数据读取错误！")
                })
            },
            initTitleChart(){

                let self = this;
                let chart = self.$echarts.init(self.$refs.title_chart);

                let dataKey = [];
                let dataVal = [];

                self.prsnCntByTitle.forEach( (obj, index) => {
                    dataKey.push(obj.key);
                    dataVal.push({name:obj.key, value:obj.count});
                });

                let option = {
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b}: {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        x: 'right',
                        data: dataKey
                    },
                    series: [
                        {
                            type:'pie',
                            radius: ['50%', '70%'],
                            avoidLabelOverlap: false,
                            label: {
                                normal: {
                                    show: false,
                                    position: 'center'
                                },
                                emphasis: {
                                    show: true,
                                    textStyle: {
                                        fontSize: '30',
                                        fontWeight: 'bold'
                                    }
                                }
                            },
                            labelLine: {
                                normal: {
                                    show: false
                                }
                            },
                            data: dataVal
                        }
                    ]
                };

                chart.setOption(option);
            },
            getPrsnCntByRank(){
                let self = this;
                ajax({
                    method: 'post',
                    url: '/api/person-stat/getPrsnCntByTechFieldAdnTitle',
                    //data:
                }).then(result =>{
                    self.prsnCntByRank = result;
                    self.initRankChart();
                }).catch( err=>{
                    self.$Message.error("数据读取错误！")
                })
            },
            initRankChart(){

                let self = this;
                let chart = self.$echarts.init(self.$refs.rank_chart);
                let techFieldKey = [], titleKey = [];

                self.prsnCntByRank = self.prsnCntByRank.sort((a,b) =>{
                    if( a.dim1 > b.dim1 ) return 1;
                    else if( a.dim1 < b.dim1 ) return -1;
                    else if(a.dim2 > b.dim2) return 1;
                    else if(a.dim2 < b.dim2) return -1;
                    else return 0;
                });

                 self.prsnCntByRank.forEach( (obj, index) => {
                    if( !techFieldKey.includes(obj.dim2))
                        techFieldKey.push(obj.dim2);
                    if( !titleKey.includes(obj.dim1))
                        titleKey.push(obj.dim1);
                });

                let seriesArr = new Array(0);
                let i = 0;
                titleKey.forEach( v => {
                    let data = [];

                    for( var j=i*techFieldKey.length; j < (i+1)*techFieldKey.length ;j++)
                        data.push(self.prsnCntByRank[j].count);

                    seriesArr.push({
                        name: v,
                        type: 'bar',
                        stack: '总量',
                        label: {
                            normal: {
                                show: true,
                                position: 'insideRight'
                            }
                        },
                        data: data
                    });
                    i++;
                });

                let option = {
                    tooltip : {
                        trigger: 'axis',
                        axisPointer : {
                            type : 'shadow'
                        }
                    },
                    legend: {
                        data: titleKey
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },

                    xAxis:  {
                        type: 'value'
                    },
                    yAxis: {
                        type: 'category',
                        data: techFieldKey
                    },
                    series: seriesArr
                };

                chart.setOption(option);

            }

        },
        mounted:function(){
            //this.$nextTick(() => {});
        },
        created: function () {
            this.getPrsnCntByTechField();
            this.getPrsnCntByTitle();
            this.getPrsnCntByRank();
        }
    }
</script>

