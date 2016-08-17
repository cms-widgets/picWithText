/**
 * Created by lhx on 2016/8/11.
 */
CMSWidgets.initWidget({
// 编辑器相关
    editor: {
        properties: null,
        saveComponent: function (onSuccess, onFailed) {
            this.properties.title=$.trim($("input[name='title']").val());
            this.properties.content= $.trim($("textarea[name='content']").text());
            this.properties.haveButton=$("input[name='haveButton']").is(":checked");

            //console.error(this.properties.title+" "+this.properties.content+"  "+this.properties.haveButton+" "+this.properties.pcImg);
            if(this.properties.pcImg==""||this.properties.content==""||this.properties.title==""){
                onFailed("组件数据缺少,未能保存,请完善。");
            }
            if(this.properties.haveButton){
                this.properties.linkUrl=$.trim($("input[name='linkUrl']").val());
                if(this.properties.linkUrl==""){
                    onFailed("组件数据缺少,未能保存,请完善。");
                }
            }
            onSuccess(this.properties);
            return this.properties;
        },
        changeHaveButton: function () {
            $("input[name='haveButton']").on("click", function () {
                console.error("some one click")
                var linkUrl=$("input[name='linkUrl']");
                var flag=$("input[name='haveButton']").is(":checked");
                console.error(flag);
                if(flag){
                    linkUrl.removeAttr("disabled");
                }else{
                    linkUrl.attr("disabled","disabled");
                }

            });
        },

        uploadImage: function () {
            var me = this;
            uploadForm({
                ui: '#imageTextUpload',
                inputName: 'file',
                maxWidth: 540,
                maxHeight: 540,
                maxFileCount: 1,
                isCongruent: false,
                successCallback: function (files, data, xhr, pd) {
                    me.properties.pcImg = data.fileUri;
                },
                deleteCallback: function (resp, data, jqXHR) {
                    me.properties.pcImg = "";
                }
            });
        },
        open: function (globalId) {
            this.properties = widgetProperties(globalId);
            this.uploadImage();
            this.changeHaveButton();
            this.properties.pcImg=".";
        },
        close: function (globalId) {

        }
    }
});
