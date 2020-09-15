// 当前标签打开网页
$('#open_url_current_tab').click(() => {
    getCurrentTabId(tabId => {
        chrome.tabs.update(tabId, {url: 'http://www.so.com'});
    });
});