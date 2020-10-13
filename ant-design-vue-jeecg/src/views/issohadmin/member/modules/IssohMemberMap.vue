<template>
  <j-modal
    :title="title"
    :width="width"
    :height="height"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">

<a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
              <a-col :xl="6" :lg="7" :md="8" :sm="24">
                <a-form-item label="人材検索" style="width:100%;">
                  <a-input placeholder="例:java,c#..." v-model="queryParam.words"></a-input>
              </a-form-item>
              </a-col>

              <a-col :xl="6" :lg="7" :md="8" :sm="24">
                <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              </a-col>
            </a-row>

</a-form>
        <!--The div element for the map -->
         <!-- <div id="map"></div> -->
<!-- :options="{streetViewControl: false}" -->
<GmapMap
      ref="memberMap"
      :center="{lat:35.710063, lng:139.810700}"
      :zoom="11"
      :map-type-id="map.mapTypeId"
      style="width: 100%; height: 600px"
    >
      <GmapMarker
        :key="index"
        v-for="(m, index) in map.markers"

        :position="m.position"
        :clickable="true"
        :draggable="true"
        @click="makerClick(m)"
        
      />
        <!-- @mouseover="makerClick(m)" -->
      <!-- @mouseout="infoWinOpen = false" -->

      <!-- <gmap-info-window :options="infoOptions" :position="infoWindowPos" :opened="infoWinOpen" @closeclick="infoWinOpen=false">
      </gmap-info-window> -->

    <GmapInfoWindow
        ref="infoWindow"
        :options="infoOptions"
        :position="infoWindowPos"
        :opened="infoWinOpen"
        @closeclick="infoWinOpen=false"
      >

<table v-if="infoMember" class="personInfoTb">
  <tr>
    <td rowspan="2">
      
      <img :src="infoMember.image ? `http://127.0.0.1:8080/jeecg-boot/sys/common/static/${infoMember.image}` : 'https://studio.kitamura.jp/service/images/img_id_02.jpg'"
      >
      
    </td>
    <td>名前:{{infoMember.realName}},{{infoMember.code}}</td>
    <td>住所:{{infoMember.address}}</td>
  </tr>
    <tr>
      <td colspan="2">
        スキル：{{infoMember.issohResume? infoMember.issohResume.searchSkills : ""}}
      </td>

  </tr>
</table>

    </GmapInfoWindow>

    </GmapMap>



  </j-modal>
</template>

<style>


  table.personInfoTb{
    border-collapse: collapse;
    color: black;
    font-weight: 300;
  }

    table.personInfoTb td{
    border:1px solid black;
  }

    table.personInfoTb img{
    width:100px;
    height:120px;
  }


</style>

<script>

  import {gmapApi} from 'vue2-google-maps'
  import { httpAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'  
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import KSearchSelectTag from "@/components/myextend/KSearchSelectTag"

  import CompanyPop from "@/views/issohadmin/components/CompanyPop/CompanyPop"
  import { Loader } from '@googlemaps/js-api-loader';
  import _ from "lodash"


/**
 * google map vue example
 * http://xkjyeah.github.io/vue-google-maps/
 *  
 * */ 

  export default {
    name: "IssohMemberMap",
    components: { 
      JDate,
    },
    computed: {
      google: gmapApi
    },
    created(){
      this.searchQuery()
    },
    data () {
      return {
        queryParam:{},
        map:{
          mapTypeId:"terrain",
          markers:[
            
          ]
          
        },
      
      memberList:[],
      infoOptions: {
        pixelOffset: {
          width: 0,
          height: -35
        }
      },
      infoMember:null,
      infoWindowPos: null,
      infoWinOpen: false,

        form: this.$form.createForm(this),
        title:"社員住所マップ",
        width:1200,
        height:800,
        visible: false,
        model: {},
        confirmLoading: false,

      }
    },

    methods: {
      searchQuery(){

          const that = this;
          var param = Object.assign({}, this.queryParam);
          // TODO httpActionがダメだったわけを調べる
          getAction("/member/issohMember/loadMemberForGoogleMap",param).then((res)=>{
            if(res.success){
              // that.$message.success(res.message);
              that.$emit('ok');
              // _.remove(that.map.markers);
              that.map.markers.splice(0,that.map.markers.length)
              res.result.forEach(elt => {
                that.map.markers.push({
                  position:{lat:elt.addressLat, lng: elt.addressLng},
                  member:elt
                  })
              });

              this.memberList = res.result;
            }else{
              that.$message.warning(res.message);
            }
          }).finally(() => {
            // that.confirmLoading = false;
            // that.close();
          })
      },

      makerClick(marker){
      var contentString = 
            ` 
<table class="personInfoTb">
  <tr>
    <td rowspan="2">
      
      <img src="https://studio.kitamura.jp/service/images/img_id_02.jpg" alt="">
      
    </td>
    <td>名前:施凱文</td>
    <td>年齢:30</td>
  </tr>
    <tr>
      <td colspan="2">
        スキル：Java,Javascript,Vue,Spring,C,C++,Python,Php
      </td>

  </tr>
</table>

            `;

        // new this.google.maps.InfoWindow({content: contentString})
        // var infowindow = new this.$refs.memberMap.maps.InfoWindow({
        //   content: contentString
        // });
        // this.infoOptions.content = contentString;
        this.infoWinOpen = true;
        this.infoWindowPos = marker.position;
        this.infoMember = marker.member;

        // this.$refs.infoWindow.options.content = contentString;
        
        // infowindow.open(this.$refs.memberMap, marker);
      },
      getSearchCompanyUrl(){
        var url = "/company/issohCompany/list";
        // this.$refs.CompanySearchTag.queryParam = {name:'隆海一創'}
        return url;
      },
      add () {
        // this.edit({});
        this.visible = true;
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'code','realName','image','sex','birthday','zaiCardImg','zaiCardNo','passportImg','passportNo','enterTime','remarks'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            // that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            formData.companyId = formData.departIds;
            console.log("表单提交数据",formData)
            return
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'code','realName','image','sex','birthday','zaiCardImg','zaiCardNo','passportImg','passportNo','enterTime','remarks'))
      },

      
    }
  }
</script>