function ajaxCall(url,data,method,successCallBack,errorCallback){
    $.ajax({
        url : url,
        data:data,
        method : method,
        success : function(response){
            if (response.status === 200){
                successCallBack(response);
            }else {
                errorCallback(response);
            }
        },
        error : function(response){
            console.log("Error",response);
            webix.message("خطا در عملیات. لطفا صفحه را بازیابی و مجددا تلاش کنید.");
        }
    });
}


function formatDate(date) {
    
    var day = date.getDate();
    var monthIndex = date.getMonth();
    var year = date.getFullYear();

    return day + '-' + (monthIndex +1)  + '-' + year;
}


var n = new Date().toLocaleTimeString().slice(0,8);
var n1 = new Date().toTimeString().slice(0,8);


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
            {view: "label", template: "<span class='time'>" + n + "</span>"},
            {view: "label", template: "<span class='time'>" +  n1 + "</span>"},
            {
                autoheight: true, type: "wide", cols: [right_menu, main_content]
            },
            {
                view: "toolbar",
                height: 30,
                elements: [
                    {view: "label", template: "<span id='footer_label' class='footer_title'> تعداد کاربران آنلاین : " + onlineUsersCount + "</span>"}, {}
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
            {id: 1, name: "تغییر رمز عبور", src:"#"/*src: "/user/change-password"*/},
            {id: 2, name: "خروج از سیستم", src: "/logout"}
        ]
    }
});

webix.ready(function () {

    webix.ui(ui);
});