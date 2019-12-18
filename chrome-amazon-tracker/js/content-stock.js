﻿//执行一次
//window.onload = initStock;
setTimeout(analyzeStockDetails,2000)
//初始化
function initStock() {
    let flag = getSwitchConfig('stock_tracker_start');
    console.log('开关状态>>>' + flag);
    if (flag) {
        console.log('初始化库存监控程序....');
        trackerStockInterval = setInterval(runLoopStock, 10 * 1000);
    }
}

//库存循环监控
function runLoopStock() {
    //随机加载
    let randomNumber = random(0, 150);
    if (randomNumber > 76) {
        console.log('重新加载页面');
        window.location.reload();//重新加载页面
        return;
    }
    console.log('循环执行监控库存>>>');
    window.location.href;
}

//解析库存
function analyzeStockDetails() {
    //点击下拉
    $('#a-autoid-0-announce').click();
    console.log('延迟等待元素出现')
    delay(500);
    console.log('等待完成')
    //点击 10+
    document.getElementById('dropdown1_10').click();
    //获取警告

    //获取库存
    $('.a-input-text').value
    //有库存的
    $(".sc-product-scarcity").css("background-color", "blue");
    //无库存的
    $(".a-alert-content").css("background-color", "green");
    //let content = $("#a-size-small a-color-price sc-product-scarcity");
}

//延时函数
function delay(timeout) {
    let flag = true;
    while (flag) {
        setTimeout(function () {
            flag = false;
        }, timeout);
    }
}