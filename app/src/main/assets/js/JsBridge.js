  function connectWebViewJavascriptBridge(callback) {
        //document.getElementById("content").innerHTML = ("connectWebViewJavascriptBridge");
        if (window.WebViewJavascriptBridge) {
            callback(WebViewJavascriptBridge);
        } else {
            document.addEventListener(
                'WebViewJavascriptBridgeReady'
                , function() {
                    callback(WebViewJavascriptBridge)
                },
                false
            );
        }
    }

    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
                + " " + date.getHours() + seperator2 + date.getMinutes()
                + seperator2 + date.getSeconds();
        return currentdate;
    }

    function showLog(logData){
    window.WebViewJavascriptBridge.callHandler(
                'showLog'
                , logData
                , function(data) {}
            );
    }


