manifest.json的说明：
"content_scripts": [
        {
            "matches": [ "*://github.com/login*" ],
            "js": [ "js/inject.js" ],
            "run_at": "document_end",
            "all_frames": true
        }
    ],
matches字段：表示只对*://github.com/login登录页面生效，只有打开GitHub的登录页面时才会把inject.js注入到页面中。
run_at字段：document_end 页面加载完后注入 | document_start页面加载开始时注入 | document_idle游览器会在start|end之间选择一个不繁忙的时间注入。
all_frames：是否注入所有iframes。（有些document中还包含iframes，为true也会向其注入）