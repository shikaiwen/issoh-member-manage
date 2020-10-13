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
      @keyup.enter="handleChange"
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
  name: "KInputProjectSearch",
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
    getAction(`/projects/issohProjects/search?${str}`,{}).then((res)=>{
        if(res.success){
        // that.$emit('ok');
            const data = [];
            res.result.forEach(elt => {
                var row = {
                    key:elt.id,
                    text:`${elt.name}`
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


  }

  timeout = setTimeout(fake, 300);
}
</script>

<style>
</style>