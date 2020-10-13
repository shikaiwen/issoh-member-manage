<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="プロジェクト名">
              <a-input placeholder="请输入プロジェクト名" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>

            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="案件所属会社">
                <a-input placeholder="请输入案件所属会社" v-model="queryParam.companyName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="案件担当者">
                <a-input placeholder="请输入案件担当者" v-model="queryParam.contactorName"></a-input>
              </a-form-item>
            </a-col>

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="現場ロケーション">
              <a-input placeholder="请输入現場ロケーション" v-model="queryParam.workLocation"></a-input>
            </a-form-item>
          </a-col>

          <template v-if="toggleSearchStatus">

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
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('プロジェクト')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
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

    <issohProjects-modal :width="1300" ref="modalForm" @ok="modalFormOk"></issohProjects-modal>
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import IssohProjectsModal from './modules/IssohProjectsModal'

  export default {
    name: "IssohProjectsList",
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      IssohProjectsModal,
    },
    created(){
          console.log("created ....")
          let name = this.$route.query.name;
          console.log(name)
          this.queryParam.name = name;
    },
    watch: {
      $route: {
        immediate: true,
        handler() {
          // console.log('============= online href  $route props ============= ');
          // console.log('$route projectName: ', name);
        }
      }
    },
    data () {
      return {
        description: 'プロジェクト管理页面',
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
            title:'案件コード',
            align:"center",
            dataIndex: 'code',
            width:100,
          },
          {
            title:'案件名',
            align:"center",
            dataIndex: 'name',
            width:200,
          },
          {
            title:'案件情報',
            align:"center",
            dataIndex: 'proDesc',
            width:200,
            ellipsis: true,
          },
          {
            title:'開始時間',
            align:"center",
            dataIndex: 'startTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'終了予定時間',
            align:"center",
            dataIndex: 'endTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'現場ロケーション',
            align:"center",
            dataIndex: 'workLocation'
          },
          {
            title:'現場アドレス',
            align:"center",
            dataIndex: 'workLocationAddr'
          },
          {
            title:'案件所属会社',
            align:"center",
            dataIndex: 'companyName'
            // dataIndex: 'companyId'
          },
          {
            title:'案件営業担当',
            align:"center",
            dataIndex: 'contactorName'
            // dataIndex: 'contactorId'
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
          list: "/projects/issohProjects/list",
          delete: "/projects/issohProjects/delete",
          deleteBatch: "/projects/issohProjects/deleteBatch",
          exportXlsUrl: "/projects/issohProjects/exportXls",
          importExcelUrl: "projects/issohProjects/importExcel",
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

      beforeLoadData(){
        if(this.$route.query.name){
          this.queryParam.name = this.$route.query.name;
        }
      },

      afterGetQueryParams(params){
        if(params.name){
          params.name = `*${params.name}*`;
        }

        if(params.workLocation){
          params.workLocation = `*${params.workLocation}*`;
        }
      },
      initDictConfig(){
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>