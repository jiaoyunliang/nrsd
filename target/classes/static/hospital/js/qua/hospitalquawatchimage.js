Vue.component("watch-hospital-qua-image-component", {
  template: "#watch-hospital-qua-image-template",
  props: {
    dialogTitle: String,
    showDialog: Boolean,
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
      this.$emit("update:showDialog", false);
    },
    tallDataListChanged: function() {
      this.dialogClosed();
    }
  }
});
