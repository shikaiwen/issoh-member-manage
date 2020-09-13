<template>
  <a-row :gutter="18">
    <a-col :md="8" :sm="24">
      <a-card :bordered="false">
        <div style="background: #fff;padding-left:16px;height: 100%; margin-top: 5px">
          <a-input-search @search="onSearch" style="width:100%;margin-top: 10px" placeholder="请输入部门名称"/>
          <!-- 树-->


            <!--组织机构-->
            <a-tree
              showLine
              :selectedKeys="selectedKeys"
              :checkStrictly="true"
              @select="onSelect"
              :dropdownStyle="{maxHeight:'200px',overflow:'auto'}"
              :treeData="departTree"
              :autoExpandParent="autoExpandParent"
              :expandedKeys="iExpandedKeys"
              @expand="onExpand"
            />

        </div>
      </a-card>
    </a-col>
    <a-col :md="16" :sm="24">
      <a-card :bordered="false">
        <a-tabs defaultActiveKey="2" @change="callback">
          <a-tab-pane tab="用户信息" key="1">
            <Dept-User-Info ref="DeptUserInfo" @clearSelectedDepartKeys="clearSelectedDepartKeys"></Dept-User-Info>
          </a-tab-pane>
          <a-tab-pane tab="社員情報" key="2">
            <Issoh-Company-Member-List :disableMixinCreated="true" ref="IssohCompanyMemberList" @clearSelectedDepartKeys="clearSelectedDepartKeys"></Issoh-Company-Member-List>
          </a-tab-pane>
        </a-tabs>
      </a-card>
    </a-col>
  </a-row>
</template>
<script>
  import DeptBaseInfo from '@/views/system/modules/DeptBaseInfo'
  import DeptUserInfo from '@/views/system/modules/DeptUserInfo'
  import {queryMyDepartTreeList, searchByKeywords} from '@/api/api'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import DeptRoleInfo from '@/views/system/modules/DeptRoleInfo'
  import IssohCompanyMemberList from './IssohCompanyMemberList'

  import { getAction, deleteAction, putAction, postAction, httpAction } from '@/api/manage'

  export default {
    name: 'IssohCompanyTree',
    mixins: [JeecgListMixin],
    components: {
      DeptUserInfo,
      IssohCompanyMemberList
    },
    data() {
      return {
        currentDeptId: '',
        iExpandedKeys: [],
        loading: false,
        autoExpandParent: true,
        currFlowId: '',
        currFlowName: '',
        disable: true,
        treeData: [],
        visible: false,
        departTree: [],
        rightClickSelectedKey: '',
        hiding: true,
        model: {},
        dropTrigger: '',
        depart: {},
        disableSubmit: false,
        checkedKeys: [],
        selectedKeys: [],
        autoIncr: 1,
        currSelected: {},
        form: this.$form.createForm(this),
        labelCol: {
          xs: {span: 24},
          sm: {span: 5}
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 16}
        },
        graphDatasource: {
          nodes: [],
          edges: []
        },
        userIdentity:"",
      }
    },
    methods: {
      callback(key) {
        //console.log(key)
      },
      loadData() {
        this.refresh();
      },
      clearSelectedDepartKeys() {
        this.checkedKeys = [];
        this.selectedKeys = [];
        this.currentDeptId = '';
        this.$refs.DeptUserInfo.currentDeptId='';
        this.$refs.DeptRoleInfo.currentDeptId='';
      },
      loadTree() {
        var that = this
        that.treeData = []
        that.departTree = []

        var url = "/company/issohCompany/getTree";
        getAction(url).then((res) => {
          if (res.success && res.result ) {
            for (let i = 0; i < res.result.length; i++) {
              let temp = res.result[i]
              that.treeData.push(temp)
              that.departTree.push(temp)
              that.setThisExpandedKeys(temp)
              // console.log(temp.id)
            }
            this.loading = false
          }
          that.userIdentity = res.message
        })
      },
      setThisExpandedKeys(node) {
        //只展开一级目录
        if (node.children && node.children.length > 0) {
          this.iExpandedKeys.push(node.key)
          //下方代码放开注释则默认展开所有节点
          /**
          for (let a = 0; a < node.children.length; a++) {
            this.setThisExpandedKeys(node.children[a])
          }
          */
        }
      },
      refresh() {
        this.loading = true
        this.loadTree()
      },

      onExpand(expandedKeys) {
        // console.log('onExpand', expandedKeys)
        // if not set autoExpandParent to false, if children expanded, parent can not collapse.
        // or, you can remove all expanded children keys.
        this.iExpandedKeys = expandedKeys
        this.autoExpandParent = false
      },

      onSearch(value) {
        let that = this
        if (value) {
          searchByKeywords({keyWord: value,myDeptSearch:'1'}).then((res) => {
            if (res.success) {
              that.departTree = []
              for (let i = 0; i < res.result.length; i++) {
                let temp = res.result[i]
                that.departTree.push(temp)
              }
            } else {
              that.$message.warning(res.message)
            }
          })
        } else {
          that.loadTree()
        }

      },
      onCheck(checkedKeys, e) {
        let record = e.node.dataRef;
        // console.log('onCheck', checkedKeys, e);
        this.checkedKeys = [];
        // if (e.checked === true) {
        this.currentDeptId = record.id;
        this.checkedKeys.push(record.id);

        this.$refs.DeptBaseInfo.open(record);
        this.$refs.DeptUserInfo.open(record);
        this.$refs.DeptRoleInfo.open(record);
        // }
        // else {
        //   this.checkedKeys = [];
        //   this.$refs.DeptBaseInfo.clearForm();
        //   this.$refs.DeptUserInfo.clearList();
        // }

        this.hiding = false;
        // this.checkedKeys = checkedKeys.checked
      },
      onSelect(selectedKeys, e) {
        if (this.selectedKeys[0] !== selectedKeys[0]) {
          this.selectedKeys = [selectedKeys[0]];
        }
        let record = e.node.dataRef;
        this.$refs.IssohCompanyMemberList.open(record)
        // this.checkedKeys.push(record.id);
        // this.$refs.DeptBaseInfo.open(record);
        // this.$refs.DeptUserInfo.onClearSelected();
        // this.$refs.DeptUserInfo.open(record);
        // this.$refs.DeptRoleInfo.onClearSelected();
        // this.$refs.DeptRoleInfo.open(record);
      },
    },
    created() {
      this.currFlowId = this.$route.params.id
      this.currFlowName = this.$route.params.name
      // this.loadTree()
    },
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>