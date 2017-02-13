package spider;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import spider.util.HttpClientUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author shiwei
 * @version $$Id: HttpClientUrilTest, V 0.1 2017/2/13 11:44 shiwei Exp $$
 */
public class HttpClientUrilTest {

    public static void main(String args[])
    {
        System.out.println("test");

        String pageListStr = HttpClientUtil.getPage("https://www.douban.com/group/lovesh/members");
        Document docList = null;
        docList = Jsoup.parse(pageListStr);

//        System.out.println(docList);

        Elements memberList = docList.getElementsByClass("member-list");

//        System.out.println(memberList);

        Elements liTag = memberList.get(2).getElementsByTag("li");
        if(!liTag.isEmpty())
        {
            //循环取详细数据
            for (int i = 0; i < liTag.size(); i++)
            {
//                System.out.println(liTag.get(i));
                String contentUrl = liTag.get(i).getElementsByClass("pic").select("a").attr("href");
                String nameStr = liTag.get(i).getElementsByClass("name").select("a").text();
                System.out.println(contentUrl + "|" + nameStr);

                if(!contentUrl.isEmpty())
                {

                }
            }
        }

//        List<String> urlList = new ArrayList<String>();

//        if(!"https://www.douban.com/group/lovesh/members".contains("/people/")){
//            //得到分页内容
//            Element pages = docList.getElementsByClass("paginator").first();
//            int num = pages.getElementsByTag("a").size();
//            String pageMaxStr = pages.getElementsByTag("a").get(num-2).text();
//            int pageMax=0;
//            if(StringUtils.isNotEmpty(pageMaxStr)){
//                pageMax= Integer.parseInt(pageMaxStr);
//                System.out.println(pageMax);
//            }
//            //                    if(pageMax>historyMaxPage){//控制历史抓取页数
//            //                        pageMax = historyMaxPage;
//            //                    }
//            for(int i=1 ;i<pageMax;i++){//翻页请求
//                String link = "";
//                link = "https://www.douban.com/group/lovesh/members" + "?start=" + (i*35);
//                urlList.add(link);//循环处理url，翻页内容
//            }
//
//            System.out.println(urlList);
//        }

        String htmlStr = HttpClientUtil.getPage("https://www.douban.com/people/lililovelymm/");

        Document docPage = Jsoup.parse(htmlStr);

        //                    System.out.println(docPage);

        Element infoNode = docPage.getElementById("profile");

        if(infoNode != null)
        {
            String imgUrl = infoNode.getElementsByClass("basic-info").get(0).select("img").attr("src");

            System.out.println(imgUrl);

            String addressStr = infoNode.getElementsByClass("basic-info").get(0)
                    .getElementsByClass("user-info")
                    .get(0).select("a").text();

            System.out.println(addressStr);

            Element joinTimeNode = infoNode.getElementsByClass("pl").first();

//            System.out.println(joinTimeNode);

            String joinTimeStr = joinTimeNode.text();

            String joinTimeStrs[] = joinTimeStr.split(" ");

            String userNameStr = joinTimeStrs[0];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date=null;
            try {
                date = sdf.parse(joinTimeStrs[1].substring(0,joinTimeStrs[1].length()-2).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            System.out.println(userNameStr);

            System.out.println(date);

            String introStr = infoNode.getElementById("intro_display").text();

            System.out.println(introStr);
        }
    }
}
