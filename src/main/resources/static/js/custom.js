function ajaxCall(url, data, method, successCallBack, errorCallback) {
    $.ajax({
        url: url,
        data: data,
        method: method,
        success: function (response) {
            if (response.status === 200) {
                successCallBack(response.data);
            } else {
                errorCallback(response);
            }
        },
        error: function (response) {
            console.log("Error", response);
            webix.message("خطا در عملیات. لطفا صفحه را بازیابی و مجددا تلاش کنید.");
        }
    });
}

function passwordHasAcceptableComplexity(password) {

    var hasUpperCase = /[A-Z]/.test(password);
    var hasLowerCase = /[a-z]/.test(password);
    var hasNumbers = /\d/.test(password);
    var hasNonalphas = /\W/.test(password);

    if (sessionPasswordComplexity === 1) {
        return hasNumbers > 0;
    } else if (sessionPasswordComplexity === 2) {
        return hasUpperCase + hasLowerCase > 1;
    } else if (sessionPasswordComplexity === 3) {
        return hasUpperCase + hasLowerCase + hasNumbers > 2;
    } else if (sessionPasswordComplexity === 4) {
        return hasUpperCase + hasLowerCase + hasNumbers + hasNonalphas > 3;
    } else {
        return true;
    }
}
function isGreaterThanOrEquals(val1, val2) {
    return val1 >= val2;
}

var n = moment().utcOffset('+0330').format('HH:mm:ss');
var n1 = moment().utcOffset(0).format('HH:mm:ss');

var cols;
if (isLoginPage) {
    cols = [main_content];
} else {
    cols = [right_menu, main_content];

}

var body = [];
body.push({
    view: "toolbar",
    height: 40,
    elements: [
        {view: "label", template: "<span class='main_title'>سامانه اطلاعات پرواز</span>"}, {},
        {view: "icon", width: 40, icon: "cog", popup: "config"}

    ]
});
if(!isLoginPage){
    body.push({
        css: "time-and-marquee-section",
        rows: [
            {
                view: "label",
                template: "<marquee class='marquee-text' behavior='scroll' direction='right'>" + bannerText + "</marquee>"
            }
        ]
    })
}
body.push({
    autoheight: true, type: "wide", cols: cols
});
body.push({
    view: "toolbar",
    height: 60,
    elements: [
        {
            rows: [
                {
                    view: "label",
                    template: "<span id='footer_label' class='footer_title'> تعداد کاربران آنلاین : " + onlineUsersCount + "</span>"
                },
                {
                    cols: [
                        {width: 50},
                        {
                            view: "label",
                            template: "<div style='text-align: center'><span id='footer_label' class='footer_title'> </span></div>"
                        },
                        {width: 50}
                    ]
                }

            ]
        }

    ]
});

var ui = {
    view: "scrollview",
    body: {
        type: "space",
        rows: body
    }
};


webix.ui({
    view: "popup", id: "config",
    head: false, width: 150,
    body: {
        view: "list", scroll: false,
        yCount: 2, borderless: true,
        template: "<a class='config-item' href='#src#'>#name#</a>",
        data: [
            {id: 1, name: "تغییر رمز عبور", src: "#"/*src: "/user/change-password"*/},
            {id: 2, name: "خروج از سیستم", src: "/logout"}
        ]
    }
});

function updateClock() {
    $(".time-tehran").html(moment().utcOffset('+0330').format('HH:mm:ss'));
    $(".time-gmt").html(moment().utcOffset(0).format('HH:mm:ss'));
}

webix.ready(function () {

    webix.ui(ui);

    setInterval(function () {
        updateClock();
    }, 1000);
});