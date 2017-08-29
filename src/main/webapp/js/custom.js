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
                autoheight: true, type: "wide", cols: [right_menu, main_content]
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