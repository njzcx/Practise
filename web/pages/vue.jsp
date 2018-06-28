<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vue测试</title>
<script type="text/javascript" src="https://unpkg.com/vue/dist/vue.js"></script>
<style>
h1 {
	font-size: 20px;
}

.redfont {
	color: red;
}
</style>
</head>
<body>
	<div id="app">
		<h1>大括号</h1>
		<p>{{ message }}</p>
		<p>{{ ok ? "YES" : "NO" }}</p>

		<h1>v-if</h1>
		<input type="checkbox" v-model="seen" />
		<p v-if="seen">你看见我了</p>

		<h1>v-html</h1>
		<div v-html="html"></div>

		<h1>v-bind</h1>
		<div v-bind:id="'id-' + i"></div>
		<input type="checkbox" v-model="isred" />
		<div :class="{'redfont' : isred}">是红色字体吗？</div>

		<h1>v-on</h1>
		<input type="button" v-on:click.once="myclick()" value="弹框一次" />
		<button @click="reverseMessage">反转字符串</button>
		<input @keyup.ctrl.up="reverseMessage" value="ctrl+up试一下"></input>

		<h1>v-model</h1>
		<input type="text" v-model="message" /> <input type="text"
			v-model.number="message" />

		<h1>v-for</h1>
		<span v-for="item in items">{{item + ','}}</span> <span
			v-for="i in 10">{{i}}</span>

		<h1>filters</h1>
		<p>{{ message | capitalize | extend}}</p>

		<h1>computed</h1>
		<p>{{showReverseMessage}}</p>

		<h1>watch</h1>
		<input type="text" v-model="watched" />

		<h1>components</h1>
		<mytag></mytag>
		<mytag2 info="这是消息"></mytag2>
		<mytag2 v-bind:info="message"></mytag2>

		<h1>directives</h1>
		<input v-mydirect></input>

	</div>
</body>
<script type="text/javascript">
	var vm = new Vue({
		el : '#app',
		data : {
			message : "zhangchenxue",
			ok : true,
			seen : true,
			isred : true,
			html : "<p style='background-color:red'>这是html输出</p>",
			i : 55,
			items : [ "one", "two", "thrid" ],
			watched : ""

		},
		methods : {
			myclick : function(event) {
				console.log(this.name);
				alert("点击事件");
			},
			reverseMessage : function() {
				this.message = this.message.split('').reverse().join('')
			}
		},
		filters : {
			//首字母大写
			capitalize : function(value) {
				if (!value)
					return '';
				value = value.toString();
				return value.charAt(0).toUpperCase() + value.slice(1);
			},
			//末尾追加下划线
			extend : function(value) {
				if (!value)
					return '';
				value = value.toString() + "_";
				return value;
			}
		},
		computed : {
			showReverseMessage : function() {
				return this.message.split('').reverse().join('');
			}
		},
		watch : {
			watched : function(val) {
				console.log(val);
			}
		//vm.$watch('watched', function (newValue, oldValue){});
		},
		components : {
			mytag : {
				template : "<p>mytag模板</p>"
			},
			mytag2 : {
				template : "<p>mytag模板{{info}}</p>",
				props : [ "info" ]
			}
		//Vue.component(mytag, {template: "<p>mytag模板</p>"});
		},
		directives : {
			mydirect : {
				bind : function(el) {
					console.log("mydirect绑定");
				},
				// 指令的定义
				inserted : function(el, binding, vnode, oldVnode) {
					// 聚焦元素
					el.focus();
				}
			}
		//Vue.directive('mydirect', {inserted: function (el) {el.focus()}})
		}
	});
	//document.write(vm.$el === document.getElementById('app'))
</script>
</html>