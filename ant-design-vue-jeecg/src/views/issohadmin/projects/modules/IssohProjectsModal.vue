<template>
  <j-modal
    :title="title"
    :width="modalWidth"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="プロジェクトコード" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['code', validatorRules.code]" placeholder="请输入プロジェクトコード"></a-input>
        </a-form-item>

        <a-form-item label="プロジェクト名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['name', validatorRules.name]" placeholder="请输入プロジェクト名"></a-input>
        </a-form-item>


        <a-form-item label="案件情報" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['proDesc']" rows="8" placeholder="请输入案件情報"/>
        </a-form-item>
        <a-form-item label="開始時間" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择開始時間" v-decorator="['startTime']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="終了予定時間" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择終了予定時間" v-decorator="['endTime']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="現場ロケーション" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['workLocation']" rows="4" placeholder="请输入現場ロケーション"/>
        </a-form-item>
        <a-form-item label="現場アドレス" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['workLocationAddr']" rows="4" placeholder="请输入現場アドレス"/>
        </a-form-item>

        <a-form-item label="案件所属会社" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!-- <a-input v-decorator="['companyId']" placeholder="请输入案件所属会社"></a-input> -->
        
          <company-pop ref="companyPop" v-decorator="['companyId',{valuePropName:'departIds'}]" :modal-width="1000" />
        
        </a-form-item>

        <!-- <a-form-item label="案件担当者" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['contactorId']" placeholder="请输入案件担当者"></a-input>
        </a-form-item> -->


        <a-form-item label="案件連絡担当者" :labelCol="labelCol" :wrapperCol="wrapperCol">

          <contactor-pop ref="contactorPop" v-decorator="['contactorId',{valuePropName:'contactorId'}]" 
          :modal-width="1200"/>

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
  import CompanyPop from "@/views/issohadmin/components/CompanyPop/CompanyPop"

  import ContactorPop from '@/views/issohadmin/components/ContactorPop/ContactorPop'
  import MemberSelect from '../../components/MemberSelect/MemberSelect'
  export default {
    name: "IssohProjectsModal",
    components: { 
      JDate,
      CompanyPop,
      ContactorPop,
      MemberSelect
    },
    props:{
      modalWidth:{
        type:Number,
        default:1200,
        required:false
    },
        },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
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
          code: {
            rules: [
              { required: true, message: '请输入プロジェクトコード!'},
            ]
          },
          name: {
            rules: [
              { required: true, message: '请输入プロジェクト名!'},
            ]
          },
        },
        url: {
          add: "/projects/issohProjects/add",
          edit: "/projects/issohProjects/edit",
        }
      }
    },
    created () {
    },
    methods: {
      add () {

        this.edit({});
        this.$nextTick(()=>{
        })

      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'code','name','proDesc','startTime','endTime','workLocation','workLocationAddr','companyId','contactorId'))
        
          this.$refs.companyPop.initVal(this.model.companyId,this.model.companyName);
          this.$refs.contactorPop.initVal(this.model.contactorId,this.model.contactorName)
        });
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
        this.form.setFieldsValue(pick(row,'code','name','proDesc','startTime','endTime','workLocation','workLocationAddr','companyId','contactorId'))
      },

      
    }
  }
</script>