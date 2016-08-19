/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.picWithText;
import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import com.huotu.hotcms.widget.common.ValidHelper;
import me.jiangcai.lib.resource.service.ResourceService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * @author CJ
 */
public class WidgetInfo implements Widget{
    /*
     * 指定风格的模板类型 如：html,text等
     */
    public static final String VALID_STYLE_TEMPLATE = "styleTemplate";

    public static final String VALID_PC_IMG = "pcImg";
    public static final String VALID_URL = "linkUrl";
    public static final String VALID_HAVEBUTTON = "haveButton";
    public static final String VALID_TITLE="title";
    public static final String VALID_CONTENT="content";


    @Override
    public String groupId() {
        return "com.huotu.hotcms.widget.picWithText";
    }

    @Override
    public String widgetId() {
        return "picWithText";
    }

    @Override
    public String name(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
            return "A custom Widget";
        }
        return "picWithText";
    }

    @Override
    public String description(Locale locale) {
        if (locale.equals(Locale.CHINESE)) {
            return "这是一个 A custom Widget，你可以对组件进行自定义修改。";
        }
        return "This is a picWithText,  you can make custom change the component.";
    }

    @Override
    public String dependVersion() {
        return "1.0-SNAPSHOT";
    }

    @Override
    public WidgetStyle[] styles() {
        return new WidgetStyle[]{new DefaultWidgetStyle()};
    }

    @Override
    public Resource widgetDependencyContent(MediaType mediaType){
        if (mediaType.equals(Widget.Javascript))
            return new ClassPathResource("js/widgetInfo.js", getClass().getClassLoader());
        return null;
    }

    @Override
    public Map<String, Resource> publicResources() {
        Map<String, Resource> map = new HashMap<>();
        map.put("thumbnail/defaultStyleThumbnail.png",new ClassPathResource("thumbnail/defaultStyleThumbnail.png",getClass().getClassLoader()));
        map.put("thumbnail.png",new ClassPathResource("thumbnail.png",getClass().getClassLoader()));
        map.put("js/widgetInfo.js",new ClassPathResource("js/widgetInfo.js",getClass().getClassLoader()));
        return map;
    }

    @Override
    public void valid(String styleId, ComponentProperties componentProperties) throws IllegalArgumentException {
        WidgetStyle style = WidgetStyle.styleByID(this,styleId);
        //加入控件独有的属性验证
        String pcImg = (String) componentProperties.get(VALID_PC_IMG);
        String haveButton = componentProperties.get(VALID_HAVEBUTTON).toString();
        String title=(String) componentProperties.get(VALID_TITLE);
        String content=(String) componentProperties.get(VALID_CONTENT);
        if(ValidHelper.isAnyOneEmpty(new Object[]{pcImg,haveButton,title,content})){
            throw new IllegalArgumentException();
        }
        //是否显示按钮
        if("show".equals(haveButton)){
            String url = (String) componentProperties.get(VALID_URL);
            if(StringUtils.isEmpty(url)){
                throw new IllegalArgumentException();
            }
        }
    }

    @Override
    public Class springConfigClass() {
        return null;
    }


    @Override
    public ComponentProperties defaultProperties(ResourceService resourceService) throws IOException {
        ComponentProperties properties = new ComponentProperties();
        properties.put(VALID_PC_IMG,"http://placehold.it/540x540");
        properties.put(VALID_HAVEBUTTON,"true");
        properties.put(VALID_TITLE,"图文标题");
        properties.put(VALID_CONTENT,"图文内容");
        properties.put(VALID_URL,"http://www.huobanplus.com");
        return properties;
    }

}
