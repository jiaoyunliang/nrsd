/**
 * 查询产品资质图片
 **/
Vue.component("watch-product-qua-image-component", {
  template: "#watch-product-qua-image-template",
  props: {
    imageDialogTitle: String,
    imageShowDialog: Boolean,
    imgs: Array
  },
  data() {
    return {
      loading: false
    };
  },
  created: function() {},
  mounted: function() {},
  watch: {},
  methods: {
    dialogClosed: function() {
      this.$parent.imgs = [];
      this.$emit("update:imageShowDialog", false);
    },
    tallDataListChanged: function() {
      this.dialogClosed();
    }
  }
});
