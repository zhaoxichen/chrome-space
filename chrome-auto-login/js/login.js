/**
 * 输入用户名,密码
 */
function inputFill() {
    console.log("填写信息")
    const inputElementArr = document.getElementsByTagName('input');
    if (inputElementArr.length < 3) {
        return;
    }
    const tenantCode = inputElementArr[1];
    tenantCode.value = "njhy";
    const username = inputElementArr[1];
    if (username == null) {
        return;
    }
    username.value = "012640";
    const password = inputElementArr[2];
    if (password == null) {
        return;
    }
    password.value = "cz@123456";
}

/**
 * 执行登陆
 */
function executeLogin() {
    inputFill();
    const login = document.getElementsByTagName('button')[0];
    console.log("提交")
    if (login == null) {
        return false;
    }
    login.click();
}