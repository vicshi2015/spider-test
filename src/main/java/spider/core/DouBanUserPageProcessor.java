package spider.core;

import org.apache.log4j.Logger;
import spider.mapper.DouBanUserMapper;
import spider.service.DouBanUserService;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Date;

/**
 *
 * @author shiwei
 * @version $$Id: DouBanUserPageProcessor, V 0.1 2017/1/18 11:19 shiwei Exp $$
 * 爬取地址：https://www.douban.com/group/lovesh/members
 * 豆瓣小组用户信息，爬取基本用户信息
 */

public class DouBanUserPageProcessor implements PageProcessor {
    private        Logger logger = Logger.getLogger(DouBanUserPageProcessor.class);

    //抓取网站相关配置，包括：编码，间隔时间，重试次数等
    private        Site   site   = Site.me().setRetryTimes(10).setSleepTime(1000);
    //
    private static int    num    = 0;

    private DouBanUserService douBanUserService;

    @Override public void process(Page page) {

    }

    @Override public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        long startTime, endTime;
        System.out.println("============豆瓣用户爬虫启动=============");
        startTime = new Date().getTime();
    }
}
