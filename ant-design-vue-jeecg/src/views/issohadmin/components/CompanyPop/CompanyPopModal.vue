<template>

<a-modal
    title="会社一覧"
    :width="modalWidth"
    :visible="visible"
    @ok="handleSubmit"
    @cancel="handleCancel"
    cancelText="关闭">
 

  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="会社名">
              <a-input placeholder="请输入会社名" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="担当者">
              <a-input placeholder="请输入担当者" v-model="queryParam.charger"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="携帯番号">
                <a-input placeholder="请输入携帯番号" v-model="queryParam.mobile"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="電話番号">
                <a-input placeholder="请输入電話番号" v-model="queryParam.tel"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="担当者メール">
                <a-input placeholder="请输入担当者メール" v-model="queryParam.email"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <!-- <issohCompany-modal ref="modalForm" @ok="modalFormOk"></issohCompany-modal> -->
  </a-card>

   </a-modal>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
//   import IssohCompanyModal from './modules/IssohCompanyModal'

  export default {
    name: "IssohCompanyList",
    mixins:[JeecgListMixin, mixinDevice],
    components: {
    //   IssohCompanyModal
    },
    // props:['modalWidth','multi','rootOpened','departId'],
    props:{
     modalWidth:{
        type:Number,
        default:1000,
        required:false
      },
      multi:{
        type:Boolean,
        default:false,
        required:false
      },
      rootOpened:{
        type:Boolean,
        default:true,
        required:false
      },
      value:{
        type:String,
        required:false
      },
      disabled:{
        type: Boolean,
        required: false,
        default: false
      },
    // 自定义返回字段，默认返回 id
      customReturnField: {
        type: String,
        default: 'id'
      }
    },
    mounted(){
      this.departIds = this.value;
      this.departNames = this.$attrs.initVal;
    },
    data () {
      return {

        visible:false,
        description: '会社一覧管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'会社名',
            align:"center",
            dataIndex: 'name'
          },
          {
            title:'担当者',
            align:"center",
            dataIndex: 'charger'
          },
          {
            title:'携帯番号',
            align:"center",
            dataIndex: 'mobile'
          },
          {
            title:'電話番号',
            align:"center",
            dataIndex: 'tel'
          },
          {
            title:'担当者メール',
            align:"center",
            dataIndex: 'email'
          },
          {
            title:'名刺',
            align:"center",
            dataIndex: 'businessCardList',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            // fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/company/issohCompany/list",
          delete: "/company/issohCompany/delete",
          deleteBatch: "/company/issohCompany/deleteBatch",
          exportXlsUrl: "/company/issohCompany/exportXls",
          importExcelUrl: "company/issohCompany/importExcel",
        },
        dictOptions:{},
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },

      handleCancel(){
        this.visible=false
      },
      handleSubmit(){
          
          this.$emit("ok",this.selectionRows)
          this.visible=false
      },
      show(){
        this.visible=true
        // this.checkedRows=[]
        // this.checkedKeys=[]
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>