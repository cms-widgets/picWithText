/**
 * Created by lhx on 2016/8/11.
 */
CMSWidgets.initWidget({
// 编辑器相关
    editor: {
        properties: null,
        saveComponent: function (onSuccess, onFailed) {
            this.properties.title = $.trim($("input[name='title']").val());
            this.properties.content = $.trim($("textarea[name='content']").text());
            this.properties.haveButton = $("input[name='haveButton']").is(":checked");

            if (this.properties.serial == null || this.properties.serial == ''
                || this.properties.content == "" || this.properties.title == "") {
                onFailed("组件数据缺少,未能保存,请完善。");
                return;
            }
            if (this.properties.haveButton) {
                this.properties.linkUrl = $.trim($("input[name='linkUrl']").val());
                if (this.properties.linkUrl == "") {
                    onFailed("组件数据缺少,未能保存,请完善。");
                    return;
                }
            }
        },
        changeHaveButton: function () {
            $("input[name='haveButton']").on("click", function () {
                var linkUrl = $("input[name='linkUrl']");
                var flag = $("input[name='haveButton']").is(":checked");
                if (flag) {
                    linkUrl.removeAttr("disabled");
                } else {
                    linkUrl.attr("disabled", "disabled");
                }
            });
        },
        open: function (globalId) {
            this.changeHaveButton();
        }
    }
});
