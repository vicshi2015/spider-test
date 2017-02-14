package spider.core;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import spider.config.InitConfig;
import spider.mapper.DouBanUserMapper;
import spider.model.DouBanUser;
import spider.service.DouBanUserService;
import spider.util.HttpClientUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private        Site site = Site.me().setRetryTimes(10).setSleepTime(5000).setSleepTime(1000)
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
                List<String> Urllist = new ArrayList<String>();
                String url = page.getUrl().toString();
                String pageUrl = url;
                Urllist = saveDoubanUserDate(pageUrl);
                page.addTargetRequests(Urllist);//添加地址，根据url对该地址处理
            }
            //可增加else if 处理不同URL地址
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("DouBanUserPageProcessor-错误信息：process执行报错", e);
        }
    }

    private List<String> saveDoubanUserDate(String pageUrl) {
        logger.info(String.format("准备抓取页面：%s", pageUrl));

        List<String> urlList = new ArrayList<String>();

        Document docList = null;
        String pageListStr = HttpClientUtil.getPage(pageUrl);

        if (StringUtils.isNotEmpty(pageListStr)) {
            try {
                docList = Jsoup.parse(pageListStr);
                Elements memberList = docList.getElementsByClass("member-list");

                if (!memberList.isEmpty()){
                    Elements liTag = memberList.get(2).getElementsByTag("li");
                    if(!liTag.isEmpty())
                    {
                        //循环取详细数据
                        for (int i = 0; i < liTag.size(); i++)
                        {
                            String contentUrl = "";
                            String name = "", userName = "", address = "", headPortrait = "", joinTime = "", intro = "";

                            Element obj = liTag.get(i);
                            contentUrl = obj.getElementsByClass("pic").select("a").attr("href");
                            name = liTag.get(i).getElementsByClass("name").select("a").text();

                            if(StringUtils.isNotEmpty(contentUrl)) {
                                String htmlStr = HttpClientUtil.getPage(contentUrl);

                                Document docPage = Jsoup.parse(htmlStr);

                                Element infoNode = docPage.getElementById("profile");

                                if(infoNode != null)
                                {
                                    headPortrait = infoNode.getElementsByClass("basic-info").get(0).select("img").attr("src");

                                    address = infoNode.getElementsByClass("basic-info").get(0)
                                            .getElementsByClass("user-info")
                                            .get(0).select("a").text();

                                    Element joinTimeNode = infoNode.getElementsByClass("pl").first();

                                    String joinTimeStr = joinTimeNode.text();

                                    String joinTimeStrs[] = joinTimeStr.split(" ");

                                    userName = joinTimeStrs[0];
                                    joinTime = joinTimeStrs[1].substring(0,joinTimeStrs[1].length()-2).toString();
                                    intro = infoNode.getElementById("intro_display").text();

                                    DouBanUser user = new DouBanUser();
                                    user.setName(name);
                                    user.setUserName(userName);
                                    user.setAddress(address);
                                    user.setJoinTime(getTime(joinTime));
                                    user.setHeadPortrait(headPortrait);
                                    user.setIntro(intro);

                                    logger.info(String.format("获取到用户数据：%s", user.toString()));

                                    //插入数据库
                                    //过滤重复数据
                                    if(douBanUserService.getUserByUserName(user.getUserName()) == null) {
                                        douBanUserService.insertAndGetId(user);
                                    }
                                    else
                                    {
                                        logger.info("过滤重复用户");
                                    }
                                }
                            }
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
                logger.error("DouBanUserPageProcessor-错误信息：", e);
            }
        }

        return urlList;
    }

    @Override

    public Site getSite() {
        return site;
    }

    private Date getTime(String joinTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date = sdf.parse(joinTime);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("DouBanUserPageProcessor-错误信息：时间转换失败" + joinTime);
        }

        return date;
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
