Vue.prototype.$http = axios;
Vue.prototype.$moment = moment;

// 日期格式化
Vue.filter("dateFormat", function(value) {
  var formatString = "YYYY-MM-DD";
  if (moment(value).format(formatString) == "Invalid date") {
    return value;
  } else {
    return value ? moment(value).format(formatString) : null;
  }
});
// 日期格式化 完整时间
Vue.filter("dateFormatFull", function(value) {
  var formatString = "YYYY-MM-DD HH:mm:ss";
  if (moment(value).format(formatString) == "Invalid date") {
    return value;
  } else {
    return value ? moment(value).format(formatString) : null;
  }
});
Vue.prototype.dateFormat = value => {
  var formatString = "YYYY-MM-DD";
  return value ? moment(value).format(formatString) : null;
};
