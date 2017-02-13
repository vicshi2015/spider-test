package spider.core;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import spider.mapper.DouBanUserMapper;
import spider.service.DouBanUserService;
import spider.util.HttpClientUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author shiwei
 * @version $$Id: DouBanUserPageProcessor, V 0.1 2017/1/18 11:19 shiwei Exp $$
 * 爬取地址：https://www.douban.com/group/lovesh/members
 * 豆瓣小组用户信息，爬取基本用户信息
 */

public class DouBanUserPageProcessor implements PageProcessor {
    private Logger logger = Logger.getLogger(DouBanUserPageProcessor.class);

    //抓取网站相关配置，包括：编码，间隔时间，重试次数等
    private        Site site = Site.me().setRetryTimes(10).setSleepTime(1000)
            .addHeader("Accept-Encoding", "/").setUserAgent(
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.59 Safari/537.36");
    //人数
    private static int  num  = 0;

    public static final String index_list = "(.*).douban.com/group/lovesh/members";

    private DouBanUserService douBanUserService;
    //搜索关键词
    private static String keyword = "";

    @Override public void process(Page page) {
        try {
            //列表页
            if (page.getUrl().regex(index_list).match()) {
                List<String> Urllist =new ArrayList<String>();
                String url =page.getUrl().toString();
                String pageUrl = url;
                Urllist = saveDoubanUserDate(pageUrl);
                page.addTargetRequests(Urllist);//添加地址，根据url对该地址处理
            }
            //可增加else if 处理不同URL地址
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> saveDoubanUserDate(String pageUrl)
    {
        List<String> urlList = new ArrayList<String>();

        Document docList = null;
        String newsIdFirst = "";
        String pageListStr = HttpClientUtil.getPage(pageUrl);

        if(StringUtils.isNotEmpty(pageListStr)){
            try {
                docList = Jsoup.parse(pageListStr);
                Elements memberList = docList.getElementsByClass("member-list");

                if(!memberList.isEmpty()){
                    Elements liTag = memberList.get(2).getElementsByTag("li");
                    if(!liTag.isEmpty())
                    {
                        //循环取详细数据
                        for (int i = 0; i < liTag.size(); i++)
                        {

                        }
                    }
                }

                if(!pageUrl.contains("/group/")){
                    //得到分页内容
                    Element pages = docList.getElementsByClass("paginator").first();
                    int num = pages.getElementsByTag("a").size();
                    String pageMaxStr = pages.getElementsByTag("a").get(num-2).text();
                    int pageMax=0;
                    if(StringUtils.isNotEmpty(pageMaxStr)){
                        pageMax= Integer.parseInt(pageMaxStr);
                    }
//                    if(pageMax>historyMaxPage){//控制历史抓取页数
//                        pageMax = historyMaxPage;
//                    }
                    for(int i=1 ;i<pageMax;i++){//翻页请求
                        String link = "";
                        link = pageUrl + "?start=" + (i*35);
                        urlList.add(link);//循环处理url，翻页内容
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return urlList;
    }

    @Override

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        long startTime, endTime;
        System.out.println("============豆瓣用户爬虫启动=============");
        startTime = new Date().getTime();
        Spider.create(new DouBanUserPageProcessor())
                .addUrl("https://www.douban.com/group/lovesh/members")
                        //开启5个线程抓取
                .thread(5)
                        //启动爬虫
                .run();
    }
}
