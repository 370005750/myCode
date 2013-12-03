定位页面元素  
- By.className(className))
- By.cssSelector(selector)
- By.id(id)
- By.linkText(linkText)
- By.name(name)
- By.partialLinkText(linkText)
- By.tagName(name)
- By.xpath(xpathExpression)

按id查找
定位一个元素最有效和首选的方法
下面是如何找到一个看起来像这样的元素的示例：
    <div id="coolestWidgetEvah">...</div>  
    WebElement element = driver.findElement(By.id("coolestWidgetEvah"));
