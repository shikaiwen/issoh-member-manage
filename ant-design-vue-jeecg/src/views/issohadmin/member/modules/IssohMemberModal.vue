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

        <a-form-item label="社員番号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['code', validatorRules.code]" placeholder="请输入社員番号"></a-input>
        </a-form-item>
        <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['realName', validatorRules.realName]" placeholder="请输入姓名"></a-input>
        </a-form-item>

        <a-form-item label="所属会社" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <company-pop ref="companyPop" v-decorator="['departIds',{valuePropName:'departIds'}]"  :modal-width="1000" />

        </a-form-item>

        <a-form-item label="写真" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-image-upload isMultiple v-decorator="['image']"></j-image-upload>
        </a-form-item>
        <a-form-item label="性別" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['sex', validatorRules.sex]" :trigger-change="true" dictCode="sex" placeholder="请选择性別"/>
        </a-form-item>
        <a-form-item label="生年月日" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择生年月日" v-decorator="['birthday', validatorRules.birthday]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>

        <a-form-item label="住所" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['address']" placeholder="住所..."></a-input>
        </a-form-item>

        <a-form-item label="在留カード" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['zaiCardImg']" placeholder="请输入在留カード"></a-input>
        </a-form-item>
        <a-form-item label="在留カード番号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-image-upload isMultiple v-decorator="['zaiCardNo']"></j-image-upload>
        </a-form-item>
        <a-form-item label="パスポート" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['passportImg']" placeholder="请输入パスポート"></a-input>
        </a-form-item>
        <a-form-item label="パスポート番号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-image-upload isMultiple v-decorator="['passportNo']"></j-image-upload>
        </a-form-item>
        <a-form-item label="入社時間" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择入社時間" v-decorator="['enterTime', validatorRules.enterTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="備考" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['remarks']" rows="4" placeholder="请输入備考"/>
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
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import KSearchSelectTag from "@/components/myextend/KSearchSelectTag"

  import CompanyPop from "@/views/issohadmin/components/CompanyPop/CompanyPop"

  export default {
    name: "IssohMemberModal",
    components: { 
      JDate,
      JImageUpload,
      JDictSelectTag,
      KSearchSelectTag,
      CompanyPop
    },
    props:{
      width:{
        type:Number,
        default:1000
      }
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
              { validator: (rule, value, callback) => validateDuplicateValue('issoh_member', 'code', value, this.model.id, callback)},
            ]
          },
          realName: {
            rules: [
              { required: true, message: '请输入姓名!'},
            ]
          },
          sex: {
            rules: [
              { required: true, message: '请输入性別!'},
            ]
          },
          birthday: {
            rules: [
              { required: true, message: '请输入生年月日!'},
            ]
          },
          enterTime: {
            rules: [
              { required: true, message: '请输入入社時間!'},
            ]
          },
        },
        url: {
          add: "/member/issohMember/add",
          edit: "/member/issohMember/edit",
        }
      }
    },
    created () {
    },
    methods: {

      getSearchCompanyUrl(){
        var url = "/company/issohCompany/list";
        // this.$refs.CompanySearchTag.queryParam = {name:'隆海一創'}
        return url;
      },
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'code','realName','image','sex','birthday','zaiCardImg','zaiCardNo','passportImg','passportNo','enterTime','remarks'))

          // 所属会社
          this.$refs.companyPop.initVal(this.model.companyId,this.model.companyName);
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