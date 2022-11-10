import { createApp } from 'vue'
import App from './App.vue'
import './index.css'
// 引入 router
import router from './router/index'
// 引入 store
import store from './store/index'
// 引入element-plus
import ElementPlus from 'element-plus'
import '../node_modules/element-plus/theme-chalk/index.css'
createApp(App).use(router).use(store).use(ElementPlus).mount('#app')



const __sfc__ = {}
import { createElementVNode as _createElementVNode, resolveComponent as _resolveComponent, createVNode as _createVNode, Fragment as _Fragment, openBlock as _openBlock, createElementBlock as _createElementBlock, pushScopeId as _pushScopeId, popScopeId as _popScopeId } from "vue"

const _withScopeId = n => (_pushScopeId("data-v-472cff63"),n=n(),_popScopeId(),n)
const _hoisted_1 = { class: "example-pagination-block" }
const _hoisted_2 = /*#__PURE__*/ _withScopeId(() => /*#__PURE__*/_createElementVNode("div", { class: "example-demonstration" }, "When you have few pages", -1 /* HOISTED */))
const _hoisted_3 = { class: "example-pagination-block" }
const _hoisted_4 = /*#__PURE__*/ _withScopeId(() => /*#__PURE__*/_createElementVNode("div", { class: "example-demonstration" }, "When you have more than 7 pages", -1 /* HOISTED */))
function render(_ctx, _cache) {
    const _component_el_pagination = _resolveComponent("el-pagination")

    return (_openBlock(), _createElementBlock(_Fragment, null, [
        _createElementVNode("div", _hoisted_1, [
            _hoisted_2,
            _createVNode(_component_el_pagination, {
                layout: "prev, pager, next",
                total: 50
            })
        ]),
        _createElementVNode("div", _hoisted_3, [
            _hoisted_4,
            _createVNode(_component_el_pagination, {
                layout: "prev, pager, next",
                total: 1000
            })
        ])
    ], 64 /* STABLE_FRAGMENT */))
}
__sfc__.render = render
__sfc__.__scopeId = "data-v-472cff63"
__sfc__.__file = "App.vue"
export default __sfc__