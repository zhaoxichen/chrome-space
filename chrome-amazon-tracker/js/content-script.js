//当前运行状态
window.onload = switchState;

//当前工作情况,同一时间只能运行一种模式
function switchState() {
    //库存监控
    let flag = getSwitchConfig('stock_tracker_start');
    console.log('库存监控程序开关状态>>>' + flag);
    if (flag) {
        console.log('运行库存监控程序....');
        trackerStockInterval = setInterval(runLoopStock, 10 * 1000);
        //关闭跟卖监控
        if (trackerInterval) {
            clearInterval(trackerInterval)
            setSwitchConfig('seller_tracker_start', false);
        }
        return;
    }
    //跟卖监控
    flag = getSwitchConfig('seller_tracker_start');
    if (flag) {
        console.log('运行跟卖监控程序....');
        trackerInterval = setInterval(runLoopSeller, 10 * 1000);
        return;
    }
}

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
        trackerInterval = setInterval(runLoopSeller, 10 * 1000);
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

let tipCount = 0;

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