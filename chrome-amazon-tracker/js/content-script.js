let trackerInterval;//跟卖监控定时器
let trackerStockInterval;//库存监控定时器

/**
 * 产生随机整数，包含下限值，但不包括上限值
 * @param {Number} lower 下限
 * @param {Number} upper 上限
 * @return {Number} 返回在下限到上限之间的一个随机整数
 */
function random(lower, upper) {
    return Math.floor(Math.random() * (upper - lower)) + lower;
}

/**
 * 取出小括号内的内容
 * @param text
 * @returns {string}
 */
function getParenthesesStr(text) {
    let result = ''
    if (!text)
        return result
    let regex = /\((.+?)\)/g;
    let options = text.match(regex)
    if (options) {
        let option = options[0]
        if (option) {
            result = option.substring(1, option.length - 1)
        }
    }
    return result
}

/**
 * 邮件发送
 * */
function sendEMail(to, msg, goodsUrl) {
    $.post(domain + "/notify/send/mail", {
            email: to,
            msg: msg,
            goodsUrl: goodsUrl
        },
        function (data, status) {
            console.log("数据: \n" + data + "\n状态: " + status);
        });
}

/**
 * 设置参数
 * */
function setSwitchConfig(key, val) {
    $.post(domain + "/config/set-switch", {
            key: key,
            value: val
        },
        function (data, status) {
            console.log("数据: \n" + data + "\n状态: " + status);
        });
}

/**
 * 获取参数
 * */
function getSwitchConfig(key) {
    let result = false;
    $.ajax({
        dataType: 'json',
        url: domain + '/config/get-switch',
        type: "get",
        data: {key: key},
        async: false,//这里选择同步为false，那么这个程序执行到这里的时候会暂停，等待数据加载完成后才继续执行
        success: function (data) {
            result = data;
        }
    });
    return result;
}

/**
 * 保存库存记录
 * */
function saveStock(cartHtml) {
    $.post(domain + "/stock/save", {
            cartHtmlBody: cartHtml
        },
        function (data, status) {
            console.log("数据: \n" + data + "\n状态: " + status);
        });
}

// 注意，必须设置了run_at=document_start 此段代码才会生效
document.addEventListener('DOMContentLoaded', function () {
    // 注入自定义JS
    injectCustomJs();
    // 给谷歌搜索结果的超链接增加 _target="blank"
    if (location.host == 'www.google.com.tw') {
        var objs = document.querySelectorAll('h3.r a');
        for (var i = 0; i < objs.length; i++) {
            objs[i].setAttribute('_target', 'blank');
        }
        console.log('已处理谷歌超链接！');
    } else if (location.host == 'www.baidu.com') {
        function fuckBaiduAD() {
            if (document.getElementById('my_custom_css')) return;
            var temp = document.createElement('style');
            temp.id = 'my_custom_css';
            (document.head || document.body).appendChild(temp);
            var css = `
			/* 移除百度右侧广告 */
			#content_right{display:none;}
			/* 覆盖整个屏幕的相关推荐 */
			.rrecom-btn-parent{display:none;}'
			/* 难看的按钮 */
			.result-op.xpath-log{display:none !important;}`;
            temp.innerHTML = css;
            console.log('已注入自定义CSS！');
            // 屏蔽百度推广信息
            removeAdByJs();
            // 这种必须用JS移除的广告一般会有延迟，干脆每隔一段时间清楚一次
            interval = setInterval(removeAdByJs, 2000);

            // 重新搜索时页面不会刷新，但是被注入的style会被移除，所以需要重新执行
            temp.addEventListener('DOMNodeRemoved', function (e) {
                console.log('自定义CSS被移除，重新注入！');
                if (interval) clearInterval(interval);
                fuckBaiduAD();
            });
        }

        let interval = 0;

        function removeAdByJs() {
            $('[data-tuiguang]').parents('[data-click]').remove();
        }

        fuckBaiduAD();
        initCustomPanel();
        initCustomEventListen();
    }
});

/**
 * 初始化操作演示区
 */
function initCustomPanel() {
    var panel = document.createElement('div');
    panel.className = 'chrome-plugin-demo-panel';
    panel.innerHTML = `
		<h2>injected-script操作content-script演示区：</h2>
		<div class="btn-area">
			<a href="javascript:sendMessageToContentScriptByPostMessage('你好，我是普通页面！')">通过postMessage发送消息给content-script</a><br>
			<a href="javascript:sendMessageToContentScriptByEvent('你好啊！我是通过DOM事件发送的消息！')">通过DOM事件发送消息给content-script</a><br>
			<a href="javascript:invokeContentScript('sendMessageToBackground()')">发送消息到后台或者popup</a><br>
		</div>
		<div id="my_custom_log">
		</div>
	`;
    document.body.appendChild(panel);
}

// 向页面注入JS
function injectCustomJs(jsPath) {
    jsPath = jsPath || 'js/inject.js';
    var temp = document.createElement('script');
    temp.setAttribute('type', 'text/javascript');
    // 获得的地址类似：chrome-extension://ihcokhadfjfchaeagdoclpnjdiokfakg/js/inject.js
    temp.src = chrome.extension.getURL(jsPath);
    temp.onload = function () {
        // 放在页面不好看，执行完后移除掉
        this.parentNode.removeChild(this);
    };
    document.body.appendChild(temp);
}

/**
 * 接收来自后台的消息(popup或者background)
 */
chrome.runtime.onMessage.addListener(function (request, sender, sendResponse) {
    console.log('收到来自 ' + (sender.tab ? "content-script(" + sender.tab.url + ")" : "popup或者background") + ' 的消息：', request);
    if (request.cmd == 'update_font_size') {
        var ele = document.createElement('style');
        ele.innerHTML = `* {font-size: ${request.size}px !important;}`;
        document.head.appendChild(ele);
    } else if (request.cmd == 'tracker_start') {
        setSwitchConfig('seller_tracker_start', 'true');
        tip(JSON.stringify(request.description));
        trackerInterval = setInterval(runLoop, 10 * 1000);
        sendResponse('我收到你的消息了：' + JSON.stringify("已经启动"));
    } else if (request.cmd == 'tracker_stop') {
        setSwitchConfig('seller_tracker_start', 'false');
        tip(JSON.stringify(request.description));
        if (trackerInterval) {
            clearInterval(trackerInterval)
            sendResponse('我收到你的消息了：' + JSON.stringify("已经停止"));
        } else {
            sendResponse('我收到你的消息了：' + JSON.stringify("未启动"));
        }
    } else if (request.cmd == 'stock_tracker_start') {
        setSwitchConfig('stock_tracker_start', 'true');
        tip(JSON.stringify(request.description));
        trackerStockInterval = setInterval(runLoopStock, 10 * 1000);
        sendResponse('我收到你的消息了：' + JSON.stringify("已经启动库存监控"));
    } else if (request.cmd == 'stock_tracker_stop') {
        setSwitchConfig('stock_tracker_start', 'false');
        tip(JSON.stringify(request.description));
        if (trackerStockInterval) {
            clearInterval(trackerStockInterval)
            sendResponse('我收到你的消息了：' + JSON.stringify("已经停止库存监控"));
        } else {
            sendResponse('我收到你的消息了：' + JSON.stringify("未启动库存监控"));
        }
    } else {
        tip(JSON.stringify(request));
        sendResponse('我收到你的消息了：' + JSON.stringify(request));
    }
});

// 主动发送消息给后台
// 要演示此功能，请打开控制台主动执行sendMessageToBackground()
function sendMessageToBackground(message) {
    chrome.runtime.sendMessage({greeting: message || '你好，我是content-script呀，我主动发消息给后台！'}, function (response) {
        tip('收到来自后台的回复：' + response);
    });
}

// 监听长连接
chrome.runtime.onConnect.addListener(function (port) {
    console.log(port);
    if (port.name == 'test-connect') {
        port.onMessage.addListener(function (msg) {
            console.log('收到长连接消息：', msg);
            tip('收到长连接消息：' + JSON.stringify(msg));
            if (msg.question == '你是谁啊？') port.postMessage({answer: '我是你爸！'});
        });
    }
});

window.addEventListener("message", function (e) {
    console.log('收到消息：', e.data);
    if (e.data && e.data.cmd == 'invoke') {
        eval('(' + e.data.code + ')');
    } else if (e.data && e.data.cmd == 'message') {
        tip(e.data.data);
    }
}, false);


function initCustomEventListen() {
    var hiddenDiv = document.getElementById('myCustomEventDiv');
    if (!hiddenDiv) {
        hiddenDiv = document.createElement('div');
        hiddenDiv.style.display = 'none';
        hiddenDiv.id = 'myCustomEventDiv';
        document.body.appendChild(hiddenDiv);
    }
    hiddenDiv.addEventListener('myCustomEvent', function () {
        var eventData = document.getElementById('myCustomEventDiv').innerText;
        tip('收到自定义事件：' + eventData);
    });
}

var tipCount = 0;

// 简单的消息通知
function tip(info) {
    info = info || '';
    var ele = document.createElement('div');
    ele.className = 'chrome-plugin-simple-tip slideInLeft';
    ele.style.top = tipCount * 70 + 20 + 'px';
    ele.innerHTML = `<div>${info}</div>`;
    document.body.appendChild(ele);
    ele.classList.add('animated');
    tipCount++;
    setTimeout(() => {
        ele.style.top = '-100px';
        setTimeout(() => {
            ele.remove();
            tipCount--;
        }, 400);
    }, 3000);
}