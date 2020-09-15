//页面所有内容加载完成执行
window.onload = function () {
    const currentUrl = window.location.href;
    console.log(currentUrl)
    if (/passport-mg-test.myyscm.com/.test(currentUrl)) {
        executeLogin();
        //跳转到setting
        window.open("https://mgfrontend-test.myyscm.com/settring")
    } else if (/mgfrontend-test.myyscm.com\/settring/.test(currentUrl)) {
        const inputUrl = document.getElementsByTagName('input')[0];
        if (null == inputUrl) {
            return;
        }
        inputUrl.value = "http://localhost:6003";
        document.getElementsByTagName('button')[0].click();
    }
}