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

        <a-form-item label="名前" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['name', validatorRules.name]" placeholder="请输入名前"></a-input>
        </a-form-item>
        <a-form-item label="職務" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['positionName']" placeholder="请输入職務"></a-input>
        </a-form-item>
        <a-form-item label="携帯番号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['mobile', validatorRules.mobile]" placeholder="请输入携帯番号"></a-input>
        </a-form-item>
        <a-form-item label="電話番号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['tel', validatorRules.tel]" placeholder="请输入電話番号"></a-input>
        </a-form-item>
        <a-form-item label="メール" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['email', validatorRules.email]" placeholder="请输入メール"></a-input>
        </a-form-item>

        <a-form-item label="所属会社" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!-- <a-input v-decorator="['companyId']" placeholder="请输入所属会社"></a-input> -->

          <company-pop v-decorator="['departIds']" :initVal="model.companyName" :modal-width="1000" />

        </a-form-item>

        <a-form-item label="名刺" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-image-upload isMultiple v-decorator="['businessCards']"></j-image-upload>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JImageUpload from '@/components/jeecg/JImageUpload'
import CompanyPop from "@/views/issohadmin/components/CompanyPop/CompanyPop"

  export default {
    name: "IssohContactorModal",
    components: { 
      JImageUpload,
      CompanyPop
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
          name: {
            rules: [
              { required: true, message: '请输入名前!'},
            ]
          },
          mobile: {
            rules: [
              { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
            ]
          },
          tel: {
            rules: [
              { pattern: /\d{6,18}/, message: '请输入6到16位数字!'},
            ]
          },
          email: {
            rules: [
              { pattern: /^([\w]+\.*)([\w]+)@[\w]+\.\w{3}(\.\w{2}|)$/, message: '请输入正确的电子邮件!'},
            ]
          },
        },
        url: {
          add: "/contactor/issohContactor/add",
          edit: "/contactor/issohContactor/edit",
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
          this.form.setFieldsValue(pick(this.model,'name','positionName','mobile','tel','email','companyId','businessCards'))
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
            formData.companyId = formData.departIds;
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
        this.form.setFieldsValue(pick(row,'name','positionName','mobile','tel','email','companyId','businessCards'))
      },

      
    }
  }
</script>