<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->

   <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="社員名前">
              <a-input placeholder="社員名前" v-model="queryParam.realName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="所属会社">
              <a-input placeholder="所属会社" v-model="queryParam.companyName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="10" :lg="11" :md="12" :sm="24">
            <a-form-item label="生年月日">
              <j-date placeholder="生年月日開始" class="query-group-cust" v-model="queryParam.birthday_begin"></j-date>
              <span class="query-group-split-cust"></span>
              <j-date placeholder="生年月日終了" class="query-group-cust" v-model="queryParam.birthday_end"></j-date>
            </a-form-item>
          </a-col>
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
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button @click="showMemberMap" type="primary" icon="plus">社員マップ</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('社員')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-button type="primary" icon="edit" @click="editResume">履歴書編集</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('社員')">履歴書アウトプット</a-button>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

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

    <issohMember-modal :width="1300" ref="modalForm" @ok="modalFormOk"></issohMember-modal>
    <issohMember-map ref="modalMap"></issohMember-map>

    <issoh-resume-modal ref="resumeModal"></issoh-resume-modal>
    
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import IssohMemberModal from './modules/IssohMemberModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import JDate from '@/components/jeecg/JDate.vue'
  import IssohMemberMap from './modules/IssohMemberMap'

  import IssohResumeModal from '@/views/issohadmin/resume/modules/IssohResumeModal'

  export default {
    name: "IssohMemberList",
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      IssohMemberModal,
      JDate,
      IssohMemberMap,
      IssohResumeModal,
    },
    data () {
      return {
        description: '社員管理页面',
        // 表头
        columns: [
          // {
          //   title: '#',
          //   dataIndex: '',
          //   key:'rowIndex',
          //   width:60,
          //   align:"center",
          //   customRender:function (t,r,index) {
          //     return parseInt(index)+1;
          //   }
          // },
          {
            title:'社員番号',
            align:"center",
            dataIndex: 'code'
          },
          {
            title:'姓名',
            align:"center",
            dataIndex: 'realName'
          },
          {
            title:'所属会社',
            align:"center",
            dataIndex: 'companyName'
          },

          {
            title:'写真',
            align:"center",
            dataIndex: 'image',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'性別',
            align:"center",
            dataIndex: 'sex_dictText'
          },
          {
            title:'生年月日',
            align:"center",
            dataIndex: 'birthday',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'住所',
            align:"center",
            dataIndex: 'address',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },

          {
            title:'在留カード',
            align:"center",
            dataIndex: 'zaiCardImg'
          },
          {
            title:'在留カード画像',
            align:"center",
            dataIndex: 'zaiCardNo',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'パスポート',
            align:"center",
            dataIndex: 'passportImg'
          },
          {
            title:'パスポート画像',
            align:"center",
            dataIndex: 'passportNo',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'入社時間',
            align:"center",
            dataIndex: 'enterTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'備考',
            align:"center",
            dataIndex: 'remarks'
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
          list: "/member/issohMember/list",
          delete: "/member/issohMember/delete",
          deleteBatch: "/member/issohMember/deleteBatch",
          exportXlsUrl: "/member/issohMember/exportXls",
          importExcelUrl: "member/issohMember/importExcel",
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
      editResume(){
          if(this.selectionRows.length <1)return;
          var record = this.selectionRows[0]
          this.$refs.resumeModal.memberPageEdit(record)
      },
      showMemberMap(){
        this.$refs.modalMap.add()
      },
      initDictConfig(){
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>