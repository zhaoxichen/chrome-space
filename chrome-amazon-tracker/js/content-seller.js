//跟卖循环监控
function runLoopSeller() {
    let randomNumber = random(0, 99);
    if (randomNumber > 80) {
        console.log('重新加载页面');
        window.location.reload();//重新加载页面
        return;
    }
    console.log('循环执行查询跟卖>>>');
    let msg = $("#olp-upd-new");
    if (msg.length > 0) {
        let href_url = window.location.href;
        let numberStr = $('#olp-upd-new > span:nth-child(1) > a').text()
        let number = getParenthesesStr(numberStr)
        if (number) {
            if (1 < parseInt(number)) {
                console.log(number + "人跟卖！")
                sendEMail('3314143291@qq.com', msg.html(), href_url)
                //sendEMail('1022369911@qq.com', msg.html(), href_url)
            } else {
                console.log(number + "人跟卖！")
            }
        }
        console.log(number)
    } else {
        console.log("没人跟卖！")
    }
}