const contextPath = '/naviblog';
const apiPrefix = 'http://localhost:9000' + contextPath;


function logout() {
    axios.get(apiPrefix + '/admin/logout')
        .then(resp => {
            if (resp.data.code === 200) {
                location.href = apiPrefix + '/admin/login.html'
            }
        })
}

function getRequestParam(urlStr) {
    if (typeof urlStr == "undefined") {
        var url = decodeURI(location.search); //获取url中"?"符后的字符串
    } else {
        var url = "?" + urlStr.split("?")[1];
    }
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}
