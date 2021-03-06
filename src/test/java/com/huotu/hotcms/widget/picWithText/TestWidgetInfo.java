/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.picWithText;

import com.huotu.hotcms.service.entity.Gallery;
import com.huotu.hotcms.service.repository.GalleryRepository;
import com.huotu.hotcms.widget.CMSContext;
import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import com.huotu.widget.test.Editor;
import com.huotu.widget.test.WidgetTest;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * @author CJ
 */
public class TestWidgetInfo extends WidgetTest {

    @Override
    protected boolean printPageSource() {
        return true;
    }

    @Override
    protected void editorWork(Widget widget, Editor editor, Supplier<Map<String, Object>> currentWidgetProperties) throws IOException {
        WebElement haveButton = editor.getWebElement().findElement(By.name("haveButton"));
        haveButton.click();
        WebElement title = editor.getWebElement().findElement(By.name("title"));
        WebElement content = editor.getWebElement().findElement(By.name("content"));
        WebElement linkUrl = editor.getWebElement().findElement(By.name("linkUrl"));
        title.clear();
        content.clear();
        Actions actions = new Actions(driver);
        actions.sendKeys(title, "abc").build().perform();
        actions.sendKeys(content, "abc").build().perform();
        actions.sendKeys(linkUrl, "abc").build().perform();
        GalleryRepository galleryRepository = CMSContext.RequestContext().getWebApplicationContext()
                .getBean(GalleryRepository.class);
        Gallery gallery = galleryRepository.findByCategory_SiteAndSerial(CMSContext.RequestContext().getSite()
                , (String) widget.defaultProperties(resourceService).get("serial"));
        editor.chooseGallery("serial", gallery);
        Map map = currentWidgetProperties.get();
        assertThat(map.get("title").toString()).isEqualTo("abc");
        assertThat(map.get("content").toString()).isEqualTo("abc");
        assertThat(map.get("haveButton").toString()).isEqualTo("false");
        assertThat(linkUrl.getAttribute("disabled")).isEqualTo("true");
        if (map.get("haveButton").toString().equals("true")){
            assertThat(map.get("linkUrl").toString()).isEqualTo("abc");
        }

        haveButton.click();
        assertThat(linkUrl.getAttribute("disabled")).isEqualTo(null);
    }

    @Override
    protected void browseWork(Widget widget, WidgetStyle style, Function<ComponentProperties, WebElement> uiChanger) throws IOException {
        ComponentProperties properties = widget.defaultProperties(resourceService);
        WebElement webElement = uiChanger.apply(properties);
        List<WebElement> title = webElement.findElements(By.className("title"));
        List<WebElement> content = webElement.findElements(By.className("desc"));
        List<WebElement> url = webElement.findElements(By.className("btn"));
        List<WebElement> haveButton = webElement.findElements(By.className("detail"));
        Assertions.assertThat(title.get(0).getText()).isEqualToIgnoringCase("图文标题");
        Assertions.assertThat(content.get(0).getText()).isEqualToIgnoringCase("图文内容");
        Assertions.assertThat(haveButton.get(0).getAttribute("class")).isEqualToIgnoringCase("detail");
        Assertions.assertThat(url.get(0).getAttribute("href")).isEqualToIgnoringCase("http://www.huobanplus.com");


    }

    @Override
    protected void editorBrowseWork(Widget widget, Function<ComponentProperties, WebElement> uiChanger
            , Supplier<Map<String, Object>> currentWidgetProperties) throws IOException {
        ComponentProperties properties = widget.defaultProperties(resourceService);
        WebElement webElement = uiChanger.apply(properties);

        List<WebElement> title = webElement.findElements(By.name("title"));
        List<WebElement> content = webElement.findElements(By.name("content"));
        List<WebElement> url = webElement.findElements(By.name("linkUrl"));
        List<WebElement> haveButton = webElement.findElements(By.name("haveButton"));

        Assertions.assertThat(title.get(0).getAttribute("value")).isEqualToIgnoringCase("图文标题");
        Assertions.assertThat(content.get(0).getText()).isEqualToIgnoringCase("图文内容");
        Assertions.assertThat(haveButton.get(0).getAttribute("checked")).isEqualToIgnoringCase("true");
        Assertions.assertThat(url.get(0).getAttribute("value")).isEqualToIgnoringCase("http://www.huobanplus.com");

    }
}
