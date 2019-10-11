/**院内资质主页面
 * @type {boolean}
 */
const maintab = new Vue({
  el: "#app",
  data() {
    return {
      activeName: ""
    };
  },
  created: function() {
    this.activeName = "hospitalQuaList";
  },
  mounted: function() {},
  watch: {},
  methods: {
    handleClick(tab, event) {
      this.activeName = tab.name;
    }
  }
});
