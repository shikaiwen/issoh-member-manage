<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="社員名前" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!-- <a-input v-decorator="['memberName', validatorRules.memberName]" placeholder="请输入社員名前"></a-input> -->
          <!-- <member-pop v-decorator="['memberId',validatorRules.memberName]"
          :initVal="model.memberName" 
          
          /> -->
          <K-Input-Search v-decorator="['memberId',{valuePropName:'value'}]" ref="kmember" />
        </a-form-item>
        <a-form-item label="入場時間" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择入場時間" v-decorator="['startTime']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="退場予定日" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择退場予定日" v-decorator="['endTimePlan']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="退場時間" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择退場時間" v-decorator="['endTime']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="顧客評価備考" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['evaluateRemarks']" rows="4" placeholder="请输入顧客評価備考"/>
        </a-form-item>
        <a-form-item label="プロジェクト" :labelCol="labelCol" :wrapperCol="wrapperCol">
<!-- <a-input v-decorator="['projectId']" placeholder="请输入プロジェクトID"></a-input> -->
<!-- <company-pop v-decorator="['departIds']" :initVal="model.companyName" :modal-width="1000" /> -->
          <project-pop v-decorator="['projectId',{valuePropName:'departIds'}]" 
          :initVal="model.projectName" 
          :modal-width="1200"></project-pop>
        </a-form-item>

        <a-form-item label="我が社営業担当" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!-- <a-input v-decorator="['chargerName']" placeholder="営業担当者..."></a-input> -->
          <member-pop 
          v-decorator="['chargerId']" 
          :initVal="model.chargerName" 
          />
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'  
  import ProjectPop from '@/views/issohadmin/components/ProjectPop/ProjectPop'
  import ContactorPop from '@/views/issohadmin/components/ContactorPop/ContactorPop'
  import MemberPop from '../../components/MemberPop/MemberPop'
  import KInputSearch from '../../components/KInputSearch'

  export default {
    name: "IssohMemberArrangeModal",
    components: { 
      JDate,
      ProjectPop,
      ContactorPop,
      MemberPop,
      KInputSearch
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
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
              { required: true, message: '社員名が必須です!'},
            ]
          },
        },
        url: {
          add: "/memberarrange/issohMemberArrange/add",
          edit: "/memberarrange/issohMemberArrange/edit",
        }
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          var pickedVals = pick(this.model,
                'memberId','memberName','startTime',
                'endTimePlan','endTime','evaluateRemarks',
                'projectId','chargerId')
          console.log(pickedVals)
          this.form.setFieldsValue(pickedVals)
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.getFieldsValue()
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
            // formData.projectId = formData.departIds
            console.log("表单提交数据",formData)
            return;
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
        this.form.setFieldsValue(pick(row,'memberName','startTime','endTimePlan','endTime','evaluateRemarks','projectId'))
      },

      
    }
  }
</script>