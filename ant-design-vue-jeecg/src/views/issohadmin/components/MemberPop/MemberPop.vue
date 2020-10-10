<template>
  <div class="components-input-demo-presuffix">
    <!---->
    <a-input @click="openModal" placeholder="クリックで選びます" v-model="showValues" readOnly :disabled="disabled">
      <a-icon slot="prefix" type="cluster" title="プロジェクトセレクター"/>
      <a-icon v-if="memberIds" slot="suffix" type="close-circle" @click="handleEmpty" title="清空"/>
    </a-input>

    <!-- <project-pop-modal
      ref="innerModal"
      :modal-width="modalWidth"
      :multi="multi"
      :rootOpened="rootOpened"
      :depart-id="departIds"
      @ok="handleOK"
      @initComp="initComp"/> -->

<a-modal
    ref="innerModal"
    title="連絡担当者"
    :width="modalWidth"
    :visible="visible"
    @ok="handleSubmit"
    @cancel="handleCancel"
    cancelText="关闭">

    <issoh-member-list ref="memberList">

    </issoh-member-list>

</a-modal>


  </div>
</template>

<script>
  import IssohMemberList from '@/views/issohadmin/member/IssohMemberList'

  export default {
    name: 'MemberPop',
    components:{
      IssohMemberList
    },
    props:{
      modalWidth:{
        type:Number,
        default:1200,
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
    data(){
      return {
        visible:false,
        confirmLoading:false,
        showValues:'',
        memberIds:'',
        memberNames:''
      }
    },
    mounted(){
      // this.memberIds = this.value;
      // this.showValues = this.$attrs.initVal;
    },
    watch:{
     
      value(val){
        // if (this.customReturnField === 'id') {
        //   this.departIds = val
        // }
      }
    },
    computed:{
      showTxt(){
        return this.$attrs.initVal;
      },
    },
    methods:{
      initComp(departNames){
        this.departNames = departNames
      },
      openModal(){
        // this.$refs.innerModal.show()
        this.visible = true;
      },
      handleCancel(){
        this.visible = false;
      },
      handleSubmit(){

      var rows = this.$refs.memberList.selectionRows;
        this.handleOK(rows,)
        // selectionRows
      },
      handleOK(rows, idstr) {

        let value = ''
        if (!rows && rows.length <= 0) {
          this.showValues = ''
          this.memberIds = ''
        } else {
        
          var str = rows.map(row => row['realName']).join("',")
          this.showValues = str;
          this.memberIds = rows.map(row => row['id']).join(',')

          this.visible = false;
          value = this.departIds;
        }
        this.$emit("change", this.memberIds)
      },

      handleEmpty(){
        this.handleOK('')

      }
    },
    model: {
      prop: 'value',
      event: 'change'
    }
  }
</script>

<style scoped>
  .components-input-demo-presuffix .anticon-close-circle {
    cursor: pointer;
    color: #ccc;
    transition: color 0.3s;
    font-size: 12px;
  }
  .components-input-demo-presuffix .anticon-close-circle:hover {
    color: #f5222d;
  }
  .components-input-demo-presuffix .anticon-close-circle:active {
    color: #666;
  }
</style>