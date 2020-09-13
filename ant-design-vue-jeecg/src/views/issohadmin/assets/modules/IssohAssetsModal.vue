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
        <!-- <j-form-container :disabled="false"> -->
              <!-- 表单内容省略..... -->
          <a-form-item label="コード" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['code']" placeholder="请输入コード"></a-input>
          </a-form-item>
          <a-form-item label="備品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['name']" placeholder="请输入備品名称"></a-input>
          </a-form-item>
          <a-form-item label="タイプ" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-category-select v-decorator="['type']" pcode="B03" placeholder="请选择タイプ" />
          </a-form-item>
          <a-form-item label="写真" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-image-upload isMultiple v-decorator="['img']"></j-image-upload>
          </a-form-item>
          <a-form-item label="備考" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-textarea v-decorator="['remarks']" rows="4" placeholder="请输入備考"/>
          </a-form-item>
          <a-form-item label="创建人" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['createBy']" placeholder="请输入创建人"></a-input>
          </a-form-item>
        <!-- </j-form-container> -->

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import JCategorySelect from '@/components/jeecg/JCategorySelect'
  import { mapState,mapGetters}from "vuex"

  export default {
    name: "IssohAssetsModal",
    components: { 
      JImageUpload,
      JCategorySelect,
    },
    data () {
      return {
        editable:true,
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
        },
        url: {
          add: "/assets/issohAssets/add",
          edit: "/assets/issohAssets/edit",
        }
      }
    },

    computed:{
      ...mapState({
        userInfo:state=> state.user.info
      })
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
          this.form.setFieldsValue(pick(this.model,'code','name','type','img','remarks','createBy','createTime'))
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
        this.form.setFieldsValue(pick(row,'code','name','type','img','remarks','createBy','createTime'))
      },
      handleCategoryChange(value,backObj){
        this.form.setFieldsValue(backObj)
      }

      
    }
  }
</script>