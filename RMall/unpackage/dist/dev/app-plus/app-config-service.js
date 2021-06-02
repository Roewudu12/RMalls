var isReady = false;
var onReadyCallbacks = [];
var isServiceReady = false;
var onServiceReadyCallbacks = [];
var __uniConfig = {
    "pages": ["pages/index/index", "pages/confirm_order/confirm_order", "pages/shop_details/shop_details", "pages/login/login", "pages/mine/mine", "pages/shopping_cart/shopping_cart", "pages/information/information", "pages/sort/sort", "pages/shop_details/comments/comments", "pages/shop_details/comments/reply", "pages/shop_details/shopping_cart/shopping_cart", "pages/choose_adress/choose_adress", "pages/choose_adress/add_adress/add_adress", "pages/order/order"],
    "window": {
        "navigationStyle": "custom",
        "navigationBarTextStyle": "black",
        "navigationBarTitleText": "uni-app",
        "navigationBarBackgroundColor": "#F8F8F8",
        "backgroundColor": "#F8F8F8",
        "background": "#efeff4"
    },
    "tabBar": {
        "color": "#7A7E83",
        "selectedColor": "#d4237a",
        "list": [{
            "pagePath": "pages/index/index",
            "iconPath": "static/tarbar_image/tarbar_index.png",
            "selectedIconPath": "static/tarbar_image/tarbar_index_selected.png",
            "text": "首页"
        }, {
            "pagePath": "pages/sort/sort",
            "iconPath": "static/tarbar_image/tarbar_sort.png",
            "selectedIconPath": "static/tarbar_image/tarbar_sort_selected.png",
            "text": "分类"
        }, {
            "pagePath": "pages/information/information",
            "iconPath": "static/tarbar_image/tarbar_information.png",
            "selectedIconPath": "static/tarbar_image/tarbar_information_selected.png",
            "text": "消息"
        }, {
            "pagePath": "pages/shopping_cart/shopping_cart",
            "iconPath": "static/tarbar_image/tarbar_shopping_cart.png",
            "selectedIconPath": "static/tarbar_image/tarbar_shopping_cart_selected.png",
            "text": "购物车"
        }, {
            "pagePath": "pages/mine/mine",
            "iconPath": "static/tarbar_image/tarbar_mine.png",
            "selectedIconPath": "static/tarbar_image/tarbar_mine_selected.png",
            "text": "我的"
        }]
    },
    "nvueCompiler": "uni-app",
    "nvueStyleCompiler": "weex",
    "renderer": "auto",
    "splashscreen": {"alwaysShowBeforeRender": true, "autoclose": false},
    "appname": "RMall",
    "compilerVersion": "3.1.2",
    "entryPagePath": "pages/index/index",
    "networkTimeout": {"request": 60000, "connectSocket": 60000, "uploadFile": 60000, "downloadFile": 60000}
};
var __uniRoutes = [{
    "path": "/pages/index/index",
    "meta": {"isQuit": true, "isTabBar": true},
    "window": {"navigationBarTitleText": ""}
}, {
    "path": "/pages/confirm_order/confirm_order",
    "meta": {},
    "window": {"navigationBarTitleText": "", "enablePullDownRefresh": false}
}, {
    "path": "/pages/shop_details/shop_details",
    "meta": {},
    "window": {"navigationBarTitleText": "", "enablePullDownRefresh": false}
}, {
    "path": "/pages/login/login",
    "meta": {},
    "window": {"navigationBarTitleText": "", "enablePullDownRefresh": false}
}, {
    "path": "/pages/mine/mine",
    "meta": {"isQuit": true, "isTabBar": true},
    "window": {"navigationBarTitleText": "", "enablePullDownRefresh": false}
}, {
    "path": "/pages/shopping_cart/shopping_cart",
    "meta": {"isQuit": true, "isTabBar": true},
    "window": {"navigationBarTitleText": "", "enablePullDownRefresh": false}
}, {
    "path": "/pages/information/information",
    "meta": {"isQuit": true, "isTabBar": true},
    "window": {"navigationBarTitleText": "", "enablePullDownRefresh": false}
}, {
    "path": "/pages/sort/sort",
    "meta": {"isQuit": true, "isTabBar": true},
    "window": {"navigationBarTitleText": "", "enablePullDownRefresh": false}
}, {
    "path": "/pages/shop_details/comments/comments",
    "meta": {},
    "window": {"enablePullDownRefresh": false}
}, {
    "path": "/pages/shop_details/comments/reply",
    "meta": {},
    "window": {"enablePullDownRefresh": false}
}, {
    "path": "/pages/shop_details/shopping_cart/shopping_cart",
    "meta": {},
    "window": {"navigationBarTitleText": "", "enablePullDownRefresh": false}
}, {
    "path": "/pages/choose_adress/choose_adress",
    "meta": {},
    "window": {"navigationBarTitleText": "", "enablePullDownRefresh": false}
}, {
    "path": "/pages/choose_adress/add_adress/add_adress",
    "meta": {},
    "window": {"navigationBarTitleText": "", "enablePullDownRefresh": false}
}, {
    "path": "/pages/order/order",
    "meta": {},
    "window": {"navigationBarTitleText": "", "enablePullDownRefresh": false}
}];
__uniConfig.onReady = function (callback) {
    if (__uniConfig.ready) {
        callback()
    } else {
        onReadyCallbacks.push(callback)
    }
};
Object.defineProperty(__uniConfig, "ready", {
    get: function () {
        return isReady
    }, set: function (val) {
        isReady = val;
        if (!isReady) {
            return
        }
        const callbacks = onReadyCallbacks.slice(0);
        onReadyCallbacks.length = 0;
        callbacks.forEach(function (callback) {
            callback()
        })
    }
});
__uniConfig.onServiceReady = function (callback) {
    if (__uniConfig.serviceReady) {
        callback()
    } else {
        onServiceReadyCallbacks.push(callback)
    }
};
Object.defineProperty(__uniConfig, "serviceReady", {
    get: function () {
        return isServiceReady
    }, set: function (val) {
        isServiceReady = val;
        if (!isServiceReady) {
            return
        }
        const callbacks = onServiceReadyCallbacks.slice(0);
        onServiceReadyCallbacks.length = 0;
        callbacks.forEach(function (callback) {
            callback()
        })
    }
});
service.register("uni-app-config", {
    create(a, b, c) {
        if (!__uniConfig.viewport) {
            var d = b.weex.config.env.scale, e = b.weex.config.env.deviceWidth, f = Math.ceil(e / d);
            Object.assign(__uniConfig, {viewport: f, defaultFontSize: Math.round(f / 20)})
        }
        return {
            instance: {
                __uniConfig: __uniConfig,
                __uniRoutes: __uniRoutes,
                global: void 0,
                window: void 0,
                document: void 0,
                frames: void 0,
                self: void 0,
                location: void 0,
                navigator: void 0,
                localStorage: void 0,
                history: void 0,
                Caches: void 0,
                screen: void 0,
                alert: void 0,
                confirm: void 0,
                prompt: void 0,
                fetch: void 0,
                XMLHttpRequest: void 0,
                WebSocket: void 0,
                webkit: void 0,
                print: void 0
            }
        }
    }
});
