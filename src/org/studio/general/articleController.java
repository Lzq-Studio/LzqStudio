package org.studio.general;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import net.sf.json.JSONObject;
import org.studio.interceptor.UserInterceptor;
import org.studio.utils.JsonUtils;
import org.studio.vo.articleVO;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.util.*;
/**
 * org.studio.general
 * Created by idiot on 11/8/14.
 */
public class articleController extends Controller {
    public void index(){
        ArrayList<String> array = new ArrayList<String>();
        array.add("Man");
        array.add("Da");
        array.add("Ren");
        renderJson(array);
    }


    public void addArticle() {
        /**
         * 功能:发表新博客
         *
         * Note:
         * ---------------------------------------------------------------------------------------------
         * 请求格式示例
         * {"timestamp":1415520897962,"dataId":"1001","content":"今天很想"}
         * timestamp    ,   当前时间戳
         * dataId       ,   博客ID
         * userName     ,   用户名
         * content      ,   博客
         * ---------------------------------------------------------------------------------------------
         * 返回格式示例
         * 成功:
         * [{"2":"ok","dataId":"1001009"}]
         * 失败:
         * [{"2":"error"}]
         * ---------------------------------------------------------------------------------------------
         * */
        try {
            String json = getParaMap().keySet().toString();
            System.out.println(getParaMap().keySet());
            JSONObject param = JsonUtils.Object4JsonString(json);
            List usr = Db.query("select * from userinfo where userId =" + getSessionAttr("dataId").toString());
            if (!usr.isEmpty()) {
                long nArticleId = Db.queryInt("select MAX(artId) from article") + 1;
                String strArticleId = String.format("%03d", nArticleId);
                String uerName = userinfo.dao.findById(getSessionAttr("dataId")).getStr("userName");
                Record record = new Record();
                record.set("userId",getSessionAttr("dataId").toString());
                record.set("artId", strArticleId);
                record.set("title", param.get("title"));
                record.set("userName", uerName);
                record.set("content", HtmlUtils.htmlEscape(param.get("content").toString()));
                boolean isOk = Db.save("article", record);
                if (!isOk) {
                    renderJson("[{\"2\":\"error\"}]");
                } else {
                    renderJson("[{\"2\":\"ok\",\"dataId\":\""+strArticleId+"\"}]");
                }
            } else {
                renderJson("[{\"3\":\"error\"}]");
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJson("[{\"500\":\"error\"}]");
        }

    }

    public void getArticle() {
        /**
         * 功能:以artId请求单个博客
         *
         * Note:
         * ---------------------------------------------------------------------------------------------
         * 请求格式示例
         * {"timestamp":1415514322572,"artId":"1000001001"}
         * timestamp    ,   当前时间戳
         * artId       ,   博客ID
         * ---------------------------------------------------------------------------------------------
         * 返回格式示例
         * 成功:
         *[{"content":"我很想","title":"n","preDate":"2014-11-12 22:01:33.0",
         * "clickNum":"22","userId":"1000001",
         * "userName":"ManDaRen","artId":"1000001001"}]
         * 失败:
         * [{"1":"error"}]
         * ---------------------------------------------------------------------------------------------
         * */
        String json = getParaMap().keySet().toString();
        JSONObject param = JsonUtils.Object4JsonString(json);
        List<articleVO> articleVOs = new ArrayList<articleVO>();
        List<article> articleList = article.dao.find("select * from article where artId = ?", param.get("dataId"));
        if (articleList.isEmpty()){
            renderJson("[{\"3\":\"error\"}]");
        }else{
            Record record = Db.findFirst("select * from article where artId = ?", param.get("dataId"));
            int num = record.getInt("clickNum");
            record.set("clickNum", num + 1);
            boolean isOk = Db.update("article", "artId", record);

            for (article at : articleList) {
                articleVO atVO = new articleVO();
                atVO.setTitle(at.getStr("title"));
                atVO.setArtId((at.getInt("artId")).toString());
                atVO.setUserId(at.getInt("userId").toString());
                atVO.setUserName(at.getStr("userName"));
                atVO.setContent(HtmlUtils.htmlUnescape(at.getStr("content")));
                atVO.setClickNum(at.get("clickNum").toString());
                atVO.setPreDate(at.get("preDate").toString());

                articleVOs.add(atVO);
            }
            if (articleVOs.size() == 0) {
                renderJson("[{\"1\":\"error\"}]");
            } else {
                renderJson(articleVOs);
            }
        }
    }

    private final int pageVol = 10; //请求返回条数

    public void getNew() {
        /**
         * 功能:请求最新博客
         *
         * Note:
         * ---------------------------------------------------------------------------------------------
         * 请求格式示例
         * {"timestamp":1415514322572}
         * timestamp    ,   当前时间戳
         * ---------------------------------------------------------------------------------------------
         * 返回格式示例
         * 成功:(返回十条记录)
         * [
         * {"content":"600","title":"600","preDate":"2014-11-11 20:59:48.0","userId":"1000001","userName":"ManDaRen",
         * "artId":"1000001013"},
         * {"content":"510","title":"510","preDate":"2014-11-11 20:58:34.0","userId":"1000001","userName":"ManDaRen",
         * "artId":"1000001012"}
         * ]
         * 失败:
         * [{"1":"error"}]
         * ---------------------------------------------------------------------------------------------
         * */
        List<article> articleList = article.dao.find("select * from article order by preDate desc limit ? ;",pageVol);
        List<articleVO> articleVOs = new ArrayList<articleVO>();

        for (article at : articleList) {
            articleVO atVO = new articleVO();
            atVO.setTitle(at.getStr("title"));
            atVO.setArtId((at.getInt("artId")).toString());
            atVO.setUserId(Integer.toString(at.getInt("userId")));
            atVO.setUserName(at.getStr("userName"));
            atVO.setContent(HtmlUtils.htmlUnescape(at.getStr("content")));
            atVO.setClickNum(Integer.toString(at.getInt("clickNum")));
            atVO.setPreDate(at.get("preDate").toString());
            articleVOs.add(atVO);
        }

        if (articleVOs.size() == 0) {
            renderJson("[{\"1\":\"error\"}]");
        } else {
            renderJson(articleVOs);
        }

    }

    public void getHot() {
        /**
         * 功能:请求最热博客
         *
         * Note:
         * ---------------------------------------------------------------------------------------------
         * 请求格式示例
         * {"timestamp":1415514322572}
         * timestamp    ,   当前时间戳
         * ---------------------------------------------------------------------------------------------
         * 返回格式示例
         * 成功:(返回十条记录)
         * [
         * {"content":"我很想","title":"n","preDate":"2014-11-12 22:01:33.0","clickNum":"23","userId":"1000001",
         * "userName":"ManDaRen","artId":"1000001001"},
         * {"content":"504 Gateway Timeout\n作为网关","title":"504","preDate":"2014-11-11 21:44:23.0",
         * "clickNum":"6","userId":"1000001","userName":"ManDaRen","artId":"1000001006"}
         * ]
         * 失败:
         * [{"1":"error"}]
         * ---------------------------------------------------------------------------------------------
         * */
        String json = getParaMap().keySet().toString();
        JSONObject param = JsonUtils.Object4JsonString(json);
        List<articleVO> articleVOs = new ArrayList<articleVO>();
        List<article> articleList = article.dao.find("select * from article order by clickNum desc limit ? ;",pageVol);

        for (article at : articleList) {
            articleVO atVO = new articleVO();

            atVO.setTitle(at.getStr("title"));
            atVO.setArtId(Integer.toString(at.getInt("artId")));
            atVO.setUserId(Integer.toString(at.getInt("userId")));
            atVO.setUserName(at.getStr("userName"));
            atVO.setPreDate((at.get("preDate")).toString());
            atVO.setContent(HtmlUtils.htmlUnescape(at.getStr("content")));
            atVO.setClickNum(Integer.toString(at.getInt("clickNum")));

            articleVOs.add(atVO);
        }
        if (articleVOs.size() == 0) {
            renderJson("[{\"1\":\"error\"}]");
        } else {
            renderJson(articleVOs);
        }
    }

public void getAll() {
        /**
         * 功能:
         * --------------------------------------------
         * */
        String json = getParaMap().keySet().toString();
        JSONObject param = JsonUtils.Object4JsonString(json);
        int page = Integer.parseInt(param.get("page").toString());
        System.out.println(page);
        List<articleVO> articleVOs = new ArrayList<articleVO>();
        List<article> articleList = article.dao.find("select * from article order by clickNum desc limit ?,? ;",(page-1)*pageVol,pageVol);

        for (article at : articleList) {
            articleVO atVO = new articleVO();

            atVO.setTitle(at.getStr("title"));
            atVO.setArtId(Integer.toString(at.getInt("artId")));
            atVO.setUserId(Integer.toString(at.getInt("userId")));
            atVO.setUserName(at.getStr("userName"));
            atVO.setPreDate((at.get("preDate")).toString());
            atVO.setContent(HtmlUtils.htmlUnescape(at.getStr("content")));
            atVO.setClickNum(Integer.toString(at.getInt("clickNum")));

            articleVOs.add(atVO);
        }
        if (articleVOs.size() == 0) {
            renderJson("[{\"1\":\"error\"}]");
        } else {
            renderJson(articleVOs);
        }
    }
    public void getPageCount(){
        try{
            Number num  = (Number)Db.query("select count(*) from article").get(0);
            int count = num.intValue();
            int pageCount = count / pageVol + 1;
            renderJson("[{\"pageCount\":\""+pageCount+"\"}]");
        }catch (Exception e){
            e.printStackTrace();
            renderJson("[{\"500\":\"error\"}]");
        }
    }

}