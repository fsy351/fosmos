<style>
    @import '../../styles/common.less';
</style>

<template>
    <div>
        <Row :gutter="10">
            <Card>
                <p slot="title">
                    <Icon type="stats-bars"></Icon>
                    单位地区分布
                </p>
                <Row>
                    <Col span="14">
                        <div ref="area_chart" style="height:350px;"></div>
                    </Col>
                    <Col span="10">
                        <Table :height="350" :columns="columns" :data="cityData"></Table>
                    </Col>
                </Row>
            </Card>
            <Card>
                <p slot="title">
                    <Icon type="stats-bars"></Icon>
                    单位类型分布
                </p>
                <div ref="inst_type_chart" style="height:300px;"></div>
            </Card>
        </Row>
    </div>
</template>

<script>

    import geoData from '@/views/home/map-data/get-geography-value';
    import ajax from '@/libs/ajax';
    import chinaJson from '@/views/home/map-data/china.json';

    export default {
        name: "inst-stat",
        components:{
        },
        data(){
            return{
                instCntByArea: [],
                instCntByType: [],
                cityData:[],
                columns: [
                    {
                        title: '城市',
                        key: 'name',
                        sortable: true
                    },
                    {
                        title: '单位数',
                        key: 'value',
                        sortable: true
                    }
                ]
            }
        },
        methods:{
            getInstCntByArea(){
                let self = this;
                ajax({
                    method: 'post',
                    url: '/api/inst-stat/getInstCntByArea',
                    //data:
                }).then(result =>{
                    self.instCntByArea = result;
                    self.initAreaChart();
                }).catch( err=>{
                    self.$Message.error("数据读取错误！")
                })
            },
            initAreaChart() {

                let self = this;

                self.instCntByArea.forEach((obj,index) => {
                    //dataKey.push(obj.key);
                    self.cityData.push({name:obj.key, value:obj.count});
                });

                var convertData = function (data) {
                    let res = [];
                    let len = data.length;
                    for (var i = 0; i < len; i++) {
                        var geoCoord = geoData[data[i].name];
                        if (geoCoord) {
                            res.push({
                                name: data[i].name,
                                value: geoCoord.concat(data[i].value)
                            });
                        }
                    }
                    return res;
                };

                let map = self.$echarts.init(self.$refs.area_chart);
                //var map = echarts.init(document.getElementById('home_page_map'));
                self.$echarts.registerMap('china', chinaJson);
                map.setOption({
                    backgroundColor: '#FFF',
                    geo: {
                        map: 'china',
                        label: {
                            emphasis: {
                                show: false
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: '#91999c',
                                borderColor: '#afafaf'
                            },
                            emphasis: {
                                areaColor: '#E5E5E5'
                            }
                        }
                    },
                    grid: {
                        top: 0,
                        left: '2%',
                        right: '2%',
                        bottom: '0',
                        containLabel: true
                    },
                    series: [{
                        type: 'scatter',
                        coordinateSystem: 'geo',
                        data: convertData(self.cityData),
                        symbolSize: function (val) {
                            return val[2];
                        },
                        label: {
                            normal: {
                                formatter: '{b}',
                                position: 'right',
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        itemStyle: {
                            normal: {
                                color: '#0099CC'
                            }
                        }
                    }]
                });
                window.addEventListener('resize', function () {
                    map.resize();
                });
                //chart.setOption(option);
            },

            getInstCntByType(){
                let self = this;
                ajax({
                    method: 'post',
                    url: '/api/inst-stat/getInstCntByType',
                    //data:
                }).then(result =>{
                    self.instCntByType = result;
                    self.initTypeChart();
                }).catch( err=>{
                    self.$Message.error("数据读取错误！")
                })
            },
            initTypeChart(){

                let self = this;
                let chart = self.$echarts.init(self.$refs.inst_type_chart);

                let dataKey = [];
                let dataVal = [];

                self.instCntByType.forEach( (obj, index) => {
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
            }
        },
        mounted:function(){
            //this.$nextTick(() => {});
        },
        created: function () {
            this.getInstCntByArea();
            this.getInstCntByType();
        }
    }
</script>


