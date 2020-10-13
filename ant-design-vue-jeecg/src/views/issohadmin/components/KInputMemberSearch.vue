 <template>
  <div>
    <a-select
      show-search
      allow-clear
      :value="value"
      :placeholder="placeholder"
      style="width: 100%"
      :default-active-first-option="false"
      :show-arrow="false"
      :filter-option="false"
      :not-found-content="null"
      @search="handleSearch"
      @inputKeydown="inputKeydown"
      @change="handleChange"
    >
      <a-select-option v-for="d in data" :key="d.key">
        {{ d.text }}
      </a-select-option>
    </a-select>
  </div>
</template>

<script>
import jsonp from "fetch-jsonp";
import moment from "moment";
import { version } from "ant-design-vue";
import debounce from "lodash/debounce";
import querystring from "querystring";
import { getAction, deleteAction, putAction, postAction, httpAction } from '@/api/manage'
let timeout;
let currentValue;

export default {
  name: "KInputSearch",
  props: {
      placeholder: {
        type: String,
        default: '検索内容を入力してください'
      },

  },
  data() {
    return {
      data: [],
      value: undefined,
      key: undefined,
    };
  },
  methods: {

    inputKeydown(evt){
      // if(evt.keyCode == 13){
      //   this.handleChange(evt.target.value)
      // }
    },
    // onSelect(value, option){
    //     console.log(value)
    // },
    // 直接プルダウンのその値を設置します
    setValue(row) {
      if(!row.key){
        this.data = []
      }else{
        this.data = [row];
      }
      this.key = row.key;
      this.value = row.key;
    },
    btnClick() {
      alert("ok");
      this.value = "qianfang aa";
    },
    handleSearch(value) {
      fetch(value, (data) => (this.data = data));
    },
    handleChange(value) {
      console.log(value);
      this.value = value;
      this.$emit("change", this.value)
      fetch(value, (data) => (this.data = data));
    },
  },
};

function fetch(value, callback) {
  if (timeout) {
    clearTimeout(timeout);
    timeout = null;
  }
  currentValue = value;

  function fake() {
    const str = querystring.encode({
      code: "utf-8",
      q: value,
    });

    const that = this;
    getAction(`/member/issohMember/searchMember?${str}`,{}).then((res)=>{
        if(res.success){
        // that.$message.success(res.message);
        // that.$emit('ok');
            const data = [];
            res.result.forEach(elt => {
                var row = {
                    key:elt.id,
                    text:`${elt.realName}`
                };
                data.push(row);
            });

            callback(data);
        }else{
        // that.$message.warning(res.message);
        }
    }).finally(() => {
        // that.confirmLoading = false;
        // that.close();
    })




    // jsonp(`https://suggest.taobao.com/sug?${str}`)
    //   .then((response) => response.json())
    //   .then((d) => {
    //     if (currentValue === value) {
    //       const result = d.result;
    //       const data = [];
    //       result.forEach((r) => {
    //         data.push({
    //           key: r[0],
    //           text: r[0],
    //         });
    //       });
    //       callback(data);
    //     }
    //   });






  }

  timeout = setTimeout(fake, 300);
}
</script>

<style>
</style>