<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="saveResume"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">

    <a-card v-if="resume" :bordered="false">
      <detail-list title="社員情報">
        <detail-list-item term="社員番号">{{memberInfo.code}}</detail-list-item>
        <detail-list-item term="名前">{{memberInfo.realName}}</detail-list-item>
        <detail-list-item term="所属会社">{{memberInfo.companyName}}</detail-list-item>
      </detail-list>

      <div v-if="skillTypeAll">
        <a-divider orientation="left">
          スキル
        </a-divider>
        <a-row  v-for="type in skillTypeAll" :key="type.typeId">
          <a-col :span="2">
            {{type.typeName}}
          </a-col>
          <a-col :span="22">

            <a-card>
              <a-card-grid v-for="(item,index) in type.datas" :key="index" style="width:20%;padding:10px;">
                <a-row>
                  <a-col style="text-align:right;" :span="12">{{item.name}}:</a-col>
                  <a-col :span="12">
                    <a-select v-model="item.level" style="width: 100%;">
                      <a-select-option v-for="option of options"
                      :value="option.value" :key="option.value">{{option.label}}</a-select-option>
                    </a-select>
                  </a-col>
                </a-row>
              </a-card-grid>
            </a-card>
          </a-col>
        </a-row>


        <a-divider orientation="left">案件経歴</a-divider>


        <table>

          <thead>

          </thead>
          <tbody>

            <template v-for="(pro,index) in resume.config.projectHisList" >

            <tr :key="index">
              <td>案件名：</td>
              <td><a-input v-model="pro.projectName" placeholder="案件名：" ></a-input></td>
              <td>開始時間：</td>
              <td>
                <j-date v-model="pro.startTime" placeholder="開始時間：" :dateFormat="'YYYY/MM'" class="" ></j-date>
              </td>
              <td>終了時間：</td>
              <td><j-date v-model="pro.endTime" placeholder="終了時間：" :dateFormat="'YYYY/MM'" class="" ></j-date></td>
              <td>作業場所：</td>
              <td>
                  <a-select v-model="pro.workPlace" style="width:150px;">
                    <a-select-option value="中国">中国</a-select-option>
                    <a-select-option value="日本">日本</a-select-option>
                  </a-select>
              </td>
              <td>作業区分：</td>
              <td>
                  <a-select v-model="pro.roles" mode="tags" style="width:150px;">
                    <a-select-option value="PG">PG</a-select-option>
                    <a-select-option value="SE">SE</a-select-option>
                  </a-select>

              </td>
              <td>
                  <div class="actionHolder">
                      <div class="actionBtn-wrapper">
                        <a-button @click="addProjectInfo(index)">追加</a-button>
                        <a-button @click="deleteProjectInfo(index)">削除</a-button>
                      </div>
                  </div>
              </td>
            </tr>

            <tr :key="'b'+index">
              <td>担当工程：</td>
              <td colspan="8">

              <a-checkbox-group v-model="pro.tasks">
                <a-list>
                    <a-list-item>
                      <a-checkbox v-for="(step,index) in steps" :key="index" :value="step.value">{{step.label}}</a-checkbox>
                    </a-list-item>
                </a-list>
              </a-checkbox-group>
              </td>
            </tr>

            <tr :key="'c'+ index">
              <td>機種OS／ＤＢ：</td>
              <td colspan="4">
                <a-input placeholder="Windows10/MySql" style="width:100%;" ></a-input>
              </td>
              <td>言語／ツール：</td>
              <td colspan="4">
                <a-input placeholder="JAVA/JSP/Junit/javascript/Spring/mybatis" style="width:100%;" ></a-input>
              </td>
            </tr>
            <tr :key="'d' +index">
              <td>業務内容説明：</td>
              <td colspan="9">
                    <a-textarea v-model="pro.projectDesc"
                    placeholder="案件情報、担当部分などの説明..."
                    :auto-size="{ minRows: 5,  }"
                  />
              </td>
            </tr>

            </template>

          </tbody>
        </table>

        <!-- <a-table :columns="projectTbColumns" :data-source="projectTbData" bordered>
        </a-table> -->

      </div>

    </a-card>
    </a-spin>
  </j-modal>
</template>

<style scoped>
.actionHolder{
  display: inline-block;
  position: relative;
}
.actionBtn-wrapper{
  position: relative;
  right: -80px;
}
</style>

<script>

  import { httpAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import _ from "lodash"
  import { validateDuplicateValue } from '@/utils/util'
  import PageLayout from '@/components/page/PageLayout'
  import STable from '@/components/table/'
  import DetailList from '@/components/tools/DetailList'
  import ABadge from "ant-design-vue/es/badge/Badge"
  const DetailListItem = DetailList.Item
  import JDate from '@/components/jeecg/JDate.vue'


var projectHisList = [
          {projectName:"",startTime:"",endTime:"",workPlace:"",roles:"",
            tasks:[],hardwareInfo:"",softwareInfo:"",
            projectDesc:""
          }
        ];
var skills = [
          {cid:"A01",name:"DOS",level:"",skilltype:"1"},
          {cid:"A02",name:"Windows",level:"",skilltype:"1"},
          {cid:"A03",name:"Windows Server",level:"",skilltype:"1"},
          {cid:"A04",name:"Mac OS",level:"",skilltype:"1"},
          {cid:"A05",name:"Linux",level:"",skilltype:"1"},
          {cid:"A06",name:"Unix",level:"",skilltype:"1"},
          {cid:"B01",name:"C",level:"",skilltype:"2"},
          {cid:"B02",name:"C++",level:"",skilltype:"2"},
          {cid:"B03",name:"C#",level:"",skilltype:"2"},
          {cid:"B04",name:"VBA",level:"",skilltype:"2"},
          {cid:"B05",name:"VB",level:"",skilltype:"2"},
          {cid:"B06",name:"COBOL",level:"",skilltype:"2"},
          {cid:"B07",name:"JAVA",level:"",skilltype:"2"},
          {cid:"B08",name:"JAVA",level:"",skilltype:"2"},
          {cid:"B09",name:"STRUTS",level:"",skilltype:"2"},
          {cid:"B10",name:"Hibernate",level:"",skilltype:"2"},
          {cid:"B11",name:"Spring",level:"",skilltype:"2"},
          {cid:"B12",name:"Javascript",level:"",skilltype:"2"},
          {cid:"B13",name:"Jsp",level:"",skilltype:"2"},
          {cid:"B14",name:"Jquery",level:"",skilltype:"2"},
          {cid:"B15",name:"Php",level:"",skilltype:"2"},
          {cid:"B16",name:"Eclipse",level:"",skilltype:"2"},
          {cid:"B17",name:"Shell",level:"",skilltype:"2"},

      // DB
          {cid:"C01",name:"Oracle",level:"",skilltype:"3"},
          {cid:"C02",name:"PostgreSQL",level:"",skilltype:"3"},
          {cid:"C03",name:"Mysql",level:"",skilltype:"3"},
          {cid:"C04",name:"Access",level:"",skilltype:"3"},
          {cid:"C05",name:"DB2",level:"",skilltype:"3"},
          {cid:"C06",name:"HiRDB",level:"",skilltype:"3"},
          
      // サーバー
          {cid:"D01",name:"Tomcat",level:"",skilltype:"4"},
          {cid:"D02",name:"IIS",level:"",skilltype:"4"},
          {cid:"D03",name:"WebSphere",level:"",skilltype:"4"},
          {cid:"D04",name:"Weblogic",level:"",skilltype:"4"},
          {cid:"D05",name:"Jboss",level:"",skilltype:"4"},
        ];
// cid client side 指定
  export default {
    name: "IssohResumeModal",
    computed:{
      options(){
        return [
          {label:'無し',value:""},
          {label:'実務1年以上',value:"1"},
          {label:'知識あり',value:"2"},
        ]
      },

      steps(){
        return [
          {label:"要件定義",value:"1"},
          {label:"調査分析",value:"2"},
          {label:"基本設計",value:"3"},
          {label:"詳細設計",value:"4"},
          {label:"単体試験",value:"5"},
          {label:"結合試験",value:"6"},
          {label:"総合試験",value:"7"},
          {label:"保守運用",value:"8"},
          {label:"サポート",value:"9"},
          ]
      },
      skillType (){
        return [
          {cid:"1",name:"OS"},
          {cid:"2",name:"言語・フレームワーク・ツール"},
          {cid:"3",name:"ＤＢ"},
          {cid:"4",name:"サーバ"},
        ]
      },
      osData(){
        var result = this.skills.filter(e=>e.skilltype=="1")
        return {name:'OS',datas:result};
      },

      skillTypeAll(){

        var skillAll = this.resume.config.skills;
        var result = this.skillType.reduce((a,type)=>{
          var typeName = type.name;
          var typeId = type.cid;
          var datas = skillAll.filter((e)=>e.skilltype == typeId)
          a.push({typeId,typeName,datas});
          return a;
        },[]);
        return result;
      }
    },
    components: {
      PageLayout,
      ABadge,
      DetailList,
      DetailListItem,
      STable,
      JDate
    },
    data () {
      return {
        resume:null,
        memberInfo:{},

        form: this.$form.createForm(this),
        title:"操作",
        width:1500,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          memberName: {
            rules: [
              { required: true, message: '请输入社員名前!'},
            ]
          },
          memberCode: {
            rules: [
              { required: true, message: '请输入社員コード!'},
            ]
          },
        },
        url: {
          add: "/resume/issohResume/add",
          edit: "/resume/issohResume/edit",
        }
      }
    },
    created () {
    },
    methods: {
      addProjectInfo(){
        this.projectHisList.push({})
      },
      deleteProjectInfo(index){
        this.projectHisList.splice(index,1)
      },
      add () {
        this.edit({});
      },
      memberPageEdit(memberInfo){
        console.log("resume edit page.");

        this.memberInfo = memberInfo;
        getAction(`/resume/issohResume/find?memberId=${memberInfo.id}`).then(res=>{

            var resume = null;
            if(res.success && res.result){

              resume = res.result;
              // this.skills = resume.config.skills;
              // this.projectHisList = resume.config.projectHisList;
            }else{
              resume = {config:{}}
              resume.memberId = this.memberInfo.id;
              resume.memberName = this.memberInfo.realName;
              resume.memberCode = this.memberInfo.code;

              var copiedSkill = _.cloneDeep(skills);
              var copiedProjectHisList = _.cloneDeep(projectHisList);

              // JSON.parse(JSON.stringify())
              resume.config.skills =  copiedSkill;
              resume.config.projectHisList =  copiedProjectHisList;
              
            }
          this.resume = resume;
          console.log(res)
        })

        this.visible = true;
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'memberId','memberName','memberCode','config'))
        })
      },

      saveResume(){
        const that = this;
        var httpurl = this.resume.id ? this.url.edit : this.url.add;
        var method = this.resume.id ? "put" : "post";

        httpAction(httpurl,this.resume,method).then((res)=>{

            if(res.success){

            }else{
              that.$message.warning(res.message);
            }
          }).finally(() => {
            that.confirmLoading = false;
            that.close();
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
            that.confirmLoading = true;
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
            console.log("表单提交数据",formData)
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
        this.form.setFieldsValue(pick(row,'memberId','memberName','memberCode','config'))
      },

      
    }
  }
</script>