<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="会社名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['name', validatorRules.name]" placeholder="请输入会社名"></a-input>
        </a-form-item>
        <a-form-item label="担当者" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['charger']" placeholder="请输入担当者"></a-input>
        </a-form-item>
        <a-form-item label="携帯番号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['mobile', validatorRules.mobile]" placeholder="请输入携帯番号"></a-input>
        </a-form-item>
        <a-form-item label="電話番号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['tel', validatorRules.tel]" placeholder="请输入電話番号"></a-input>
        </a-form-item>
        <a-form-item label="担当者メール" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['email']" placeholder="请输入担当者メール"></a-input>
        </a-form-item>
        <a-form-item label="名刺" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-image-upload isMultiple v-decorator="['businessCardList']"></j-image-upload>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JImageUpload from '@/components/jeecg/JImageUpload'
  
  export default {
    name: "IssohCompanyModal",
    components: { 
      JImageUpload,
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
              { required: true, message: '请输入会社名!'},
            ]
          },
          mobile: {
            rules: [
              { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
            ]
          },
          tel: {
            rules: [
              { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
            ]
          },
        },
        url: {
          add: "/company/issohCompany/add",
          edit: "/company/issohCompany/edit",
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
          this.form.setFieldsValue(pick(this.model,'name','charger','mobile','tel','email','businessCardList'))
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
        this.form.setFieldsValue(pick(row,'name','charger','mobile','tel','email','businessCardList'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>