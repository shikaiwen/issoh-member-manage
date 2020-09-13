<template>
  <j-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>

          <a-col :xs="24" :sm="12">
            <a-form-item label="申請者" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['reqUserName', validatorRules.reqUserName]" placeholder="请输入申請者"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="受け取り時間" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择受け取り時間" v-decorator="['receiveTime', validatorRules.receiveTime]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="状態" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['status', validatorRules.status]" :trigger-change="true" dictCode="asset_request_status" placeholder="请选择状態"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="備考" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-textarea v-decorator="['remarks']" rows="4" placeholder="请输入備考"/>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="資産申請明細" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="issohAssetsRequestDetailTable.loading"
            :columns="issohAssetsRequestDetailTable.columns"
            :dataSource="issohAssetsRequestDetailTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
      </a-tabs>

    </a-spin>
  </j-modal>
</template>

<script>

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: 'IssohAssetsRequestModal',
    mixins: [JEditableTableMixin],
    components: {
      JDate,
      JDictSelectTag,
    },
    data() {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        labelCol2: {
          xs: { span: 24 },
          sm: { span: 3 },
        },
        wrapperCol2: {
          xs: { span: 24 },
          sm: { span: 20 },
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
          reqUserName: {
            rules: [
              { required: true, message: '请输入申請者!'},
            ]
          },
          receiveTime: {
            rules: [
              { required: true, message: '请输入受け取り時間!'},
            ]
          },
          status: {
            rules: [
              { required: true, message: '请输入状態!'},
            ]
          },
        },
        refKeys: ['issohAssetsRequestDetail', ],
        tableKeys:['issohAssetsRequestDetail', ],
        activeKey: 'issohAssetsRequestDetail',
        // 資産申請明細
        issohAssetsRequestDetailTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '完成状態',
              key: 'status',
              type: FormTypes.select,
              dictCode:"asset_request_status",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '資産ID',
              key: 'assetsId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '資産名',
              key: 'assetsName',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '備考',
              key: 'remarks',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        url: {
          add: "/assetsrequest/issohAssetsRequest/add",
          edit: "/assetsrequest/issohAssetsRequest/edit",
          issohAssetsRequestDetail: {
            list: '/assetsrequest/issohAssetsRequest/queryIssohAssetsRequestDetailByMainId'
          },
        }
      }
    },
    methods: {
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'reqUserName','receiveTime','status','remarks')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.issohAssetsRequestDetail.list, params, this.issohAssetsRequestDetailTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          issohAssetsRequestDetailList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'reqUserName','receiveTime','status','remarks'))
     },

    }
  }
</script>

<style scoped>
</style>