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


var n = moment().utcOffset('+0330').format('HH:mm:ss');
var n1 = moment().utcOffset(0).format('HH:mm:ss');

var cols;
if (isLoginPage) {
    cols = [main_content];
} else {
    cols = [right_menu, main_content];

}

var ui = {
    view: "scrollview",
    body: {
        type: "space",
        rows: [
            {
                view: "toolbar",
                height: 55,
                elements: [
                    {view: "label", template: "<span class='main_title'>سامانه هواشناسی</span>"}, {},
                    /*{view: "icon", width: 40, icon: "info-circle"},
                     {view: "icon", width: 40, icon: "comments"},*/
                    {view: "icon", width: 40, icon: "cog", popup: "config"}

                ]
            },
            {
                css: "time-and-marquee-section",
                rows: [
                    {
                        cols: [
                            {
                                view: "label",
                                template: "<div class='time-container'><span class='time time-tehran'>" + n + " </span><span> تهران</span></div>"
                            },
                            {
                                view: "label",
                                template: "<div class='time-container time-gmt-container'><span class='time time-gmt'>" + n1 + "</span><span> GMT</span></div>"
                            }
                        ]
                    },
                    {
                        view: "label",
                        template: "<marquee class='marquee-text' behavior='scroll' direction='right'>" + bannerText + "</marquee>"
                    }
                ]
            },
            {
                autoheight: true, type: "wide", cols: cols
            },
            {
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
                                        template: "<div style='text-align: center'><span id='footer_label' class='footer_title'> قرارگاه پدافند هوایی خاتم الانبیا(ص) - معاونت فاوا - مدیریت فناوری اطلاعات</span></div>"
                                    },
                                    {width: 50}
                                ]
                            }

                        ]
                    }

                ]
            }
        ]
    }
};


webix.ui({
    view: "popup", id: "config",
    head: false, width: 150,
    body: {
        view: "list", scroll: false,
        yCount: 2, borderless: true,
        template: "<a href='#src#'>#name#</a>",
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