function connectWebViewJavascriptBridge(callback) {
    if (window.WebViewJavascriptBridge) {
        callback(WebViewJavascriptBridge)
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

function registerBridge(MyInitCallback,registerCallBack){
    connectWebViewJavascriptBridge(function(bridge){
        bridge.init(MyInitCallback);
        registerCallBack(bridge);

    });
}

function toManyHPage(pages,datasss,pageCount,title,tabs){
    var myData = {
            	        "type":"manyH5",
            	        "pages":pages,
            	        "datas":datasss,
            	        "pageCount":pageCount,
            	        "title":title,
            	        "tabs":tabs
            };
            showLog("toManyH5.data:"+myData);
            window.WebViewJavascriptBridge.callHandler('forward', myData, null);
}

function toNativePage(page,data,isReturn,callback){
    var datas = {
        	        "type":"native",
        	        "page":page,
        	        "data":data,
        	        "isReturn":isReturn,
        	        "callback":callback
        };
        window.WebViewJavascriptBridge.callHandler('forward', datas, null);
}

function toH5Page(page,data,isReturn,callback){
    var datas = {
    	        "type":"h5",
    	        "page":page,
    	        "data":data,
    	        "isReturn":isReturn,
    	        "callback":callback
    };
    window.WebViewJavascriptBridge.callHandler('forward', datas, null);
}

function loadComplete(){
    window.WebViewJavascriptBridge.callHandler('loadComplete', "", null);
}

function showLoading(title,content){
    var data =
    {
        "type":"loading",
        "title":title,
        "content":content
    };
    window.WebViewJavascriptBridge.callHandler('dialog', data, null);
}


function showAlert(title,content,okMsg,cancelMsg,okCallback,cancelCallback){
        var data =
        {
	        "type":"alert",
	        "title":title,
	        "content":content,
	        "okMsg":okMsg,
            "cancelMsg":cancelMsg,
            "okCallback":okCallback,
            "cancelCallback":cancelCallback
        };
        window.WebViewJavascriptBridge.callHandler('dialog', data, null);
}

function showSimpleDialog(title,content,okCallback){
        var data =
        {
	        "type":"alert",
	        "title":title,
	        "content":content,
	        "okMsg":"确定",
            "cancelMsg":"取消",
            "okCallback":okCallback,
            "cancelCallback":""
        };
        window.WebViewJavascriptBridge.callHandler('dialog', data, null);
}

function sendGetRequest(data,callback){
    window.WebViewJavascriptBridge.callHandler(
        'sendGet'
        , data
        , function(data) {
            callback(data);
        }
    );
}

function sendPostRequest(data,callback){
    window.WebViewJavascriptBridge.callHandler(
        'sendPost'
        , data
        , function(data) {
            callback(data);
        }
    );
}

function showLog(data){
    window.WebViewJavascriptBridge.callHandler(
        'showLog'
        , data
        , null
    );
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

