
// popup主动发消息给content-script，启动跟卖监控定时器
$('#seller_tracker_start').click(() => {
    sendMessageToContentScript({cmd: 'tracker_start', description: '启动监控....'}, (response) => {
        if (response) alert('收到来自content-script的回复：' + response);
    });
});

// popup主动发消息给content-script，终止跟卖监控定时器
$('#seller_tracker_stop').click(() => {
    sendMessageToContentScript({cmd: 'tracker_stop', description: '终止监控....'}, (response) => {
        if (response) alert('收到来自content-script的回复：' + response);
    });
});


// 打开后台页
$('#open_background').click(e => {
    window.open(chrome.extension.getURL('background.html'));
});

// 调用后台JS
$('#invoke_background_js').click(e => {
    var bg = chrome.extension.getBackgroundPage();
    bg.testBackground();
});

// 获取后台页标题
$('#get_background_title').click(e => {
    var bg = chrome.extension.getBackgroundPage();
    alert(bg.document.title);
});

// 设置后台页标题
$('#set_background_title').click(e => {
    var title = prompt('请输入background的新标题：', '这是新标题');
    var bg = chrome.extension.getBackgroundPage();
    bg.document.title = title;
    alert('修改成功！');
});

// 自定义窗体大小
$('#custom_window_size').click(() => {
    chrome.windows.getCurrent({}, (currentWindow) => {
        var startLeft = 10;
        chrome.windows.update(currentWindow.id,
            {
                left: startLeft * 10,
                top: 100,
                width: 800,
                height: 600
            });
        var inteval = setInterval(() => {
            if (startLeft >= 40) clearInterval(inteval);
            chrome.windows.update(currentWindow.id, {left: (++startLeft) * 10});
        }, 50);
    });
});


// 新标签打开网页
$('#open_url_new_tab').click(() => {
    chrome.tabs.create({url: 'https://www.amazon.com'});
});

// 当前标签打开网页
$('#open_url_current_tab').click(() => {
    getCurrentTabId(tabId => {
        chrome.tabs.update(tabId, {url: 'https://www.amazon.com'});
    });
});

// popup主动发消息给content-script
$('#send_message_to_content_script').click(() => {
    sendMessageToContentScript('你好，我是popup！', (response) => {
        if (response) alert('收到来自content-script的回复：' + response);
    });
});

// 监听来自content-script的消息
chrome.runtime.onMessage.addListener(function (request, sender, sendResponse) {
    console.log('收到来自content-script的消息：');
    console.log(request, sender, sendResponse);
    sendResponse('我是popup，我已收到你的消息：' + JSON.stringify(request));
});

// popup与content-script建立长连接
$('#connect_to_content_script').click(() => {
    getCurrentTabId((tabId) => {
        var port = chrome.tabs.connect(tabId, {name: 'test-connect'});
        port.postMessage({question: '你是谁啊？'});
        port.onMessage.addListener(function (msg) {
            alert('收到长连接消息：' + msg.answer);
            if (msg.answer && msg.answer.startsWith('我是')) {
                port.postMessage({question: '哦，原来是你啊！'});
            }
        });
    });
});

// 获取当前选项卡ID
function getCurrentTabId(callback) {
    chrome.tabs.query({active: true, currentWindow: true}, function (tabs) {
        if (callback) callback(tabs.length ? tabs[0].id : null);
    });
}

// 向content-script主动发送消息
function sendMessageToContentScript(message, callback) {
    getCurrentTabId((tabId) => {
        chrome.tabs.sendMessage(tabId, message, function (response) {
            if (callback) callback(response);
        });
    });
}

/**********************************************库存监控*******************************************************/
// 当前标签打开购物车页面
$('#stock_url_current_tab').click(() => {
    getCurrentTabId(tabId => {
        chrome.tabs.update(tabId, {url: 'https://www.amazon.com/gp/cart/view.html/ref=lh_cart_vc_btn'});
    });
});

// 启动库存监控定时器
$('#stock_tracker_start').click(() => {
    sendMessageToContentScript({cmd: 'stock_tracker_start', description: '启动监控....'}, (response) => {
        if (response) alert('收到来自content-stock的回复：' + response);
    });
});

// 终止库存监控定时器
$('#stock_tracker_stop').click(() => {
    sendMessageToContentScript({cmd: 'stock_tracker_stop', description: '终止监控....'}, (response) => {
        if (response) alert('收到来自content-stock的回复：' + response);
    });
});