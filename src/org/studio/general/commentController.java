package org.studio.general;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import net.sf.json.JSONObject;
import org.springframework.web.util.HtmlUtils;
import org.studio.interceptor.UserInterceptor;
import org.studio.utils.JsonUtils;
import org.studio.vo.commentVO;

import java.util.ArrayList;
import java.util.List;

/**
 * org.studio.general
 * Created by lzim on 11/8/14.
 */
public class commentController extends Controller {
    public void index(){
        ArrayList<String> array = new ArrayList<String>();
        array.add("Man");
        array.add("Da");
        array.add("Ren");
        renderJson(array);
    }

    /**
     * 功能:请求留言内容
     *
     * Note:
     * ---------------------------------------------------------------------------------------------
     * 请求格式示例
     *
     * timestamp    ,   当前时间戳
     * dataId       ,   博客ID
     * ---------------------------------------------------------------------------------------------
     * 返回格式示例
     * 成功:
     *
     * 失败:
     * [{"1":"error"}]
     * ---------------------------------------------------------------------------------------------
     * */
//    @Before(UserInterceptor.class)
    public void query() {
        String json = getParaMap().keySet().toString();
        JSONObject param = JsonUtils.Object4JsonString(json);

        List<commentVO> commentVO = new ArrayList<org.studio.vo.commentVO>();
        List<comment> commentList = comment.dao.find("select * from comment where articleId = ?",
                param.get("dataId"));

        for (comment cm : commentList) {
            commentVO cmVO = new commentVO();
            cmVO.setCommentId(Integer.toString(cm.getInt("commentId")));
            cmVO.setArticleId(Integer.toString(cm.getInt("articleId")));
            cmVO.setUserName(cm.getStr("userName"));
            cmVO.setContent(HtmlUtils.htmlUnescape(cm.getStr("content")));
            cmVO.setPreDate((cm.get("preTime")).toString());
            commentVO.add(cmVO);
        }
        if(commentVO.size() == 0){
            renderJson("[{\"1\":\"error\"}]");
        }else
        {
            renderJson(commentVO);
        }
    }

    /**
     * 功能:提交留言
     *
     * Note:
     * ---------------------------------------------------------------------------------------------
     * 请求格式示例
     *
     * timestamp    ,   当前时间戳
     * dataId       ,   博客ID
     * userName     ,   用户名称
     * content      ,   留言内容
     * ---------------------------------------------------------------------------------------------
     * 返回格式示例
     * 成功:
     * [{"2":"ok"}]
     * 失败:
     * [{"2":"error"}]
     * ---------------------------------------------------------------------------------------------
     * */
    @Before(UserInterceptor.class)
    public void submit(){
        try {
            String json = getParaMap().keySet().toString();
            JSONObject param = JsonUtils.Object4JsonString(json);
            List art= Db.query("select * from article where artId ="+ param.get("dataId"));
            if( art != null){
                long nComment = Db.queryLong("select count(1) from comment where articleId = " + param.get("dataId")) +1 ;
                String strComment = String.format("%03d", nComment);
                Record record = new Record();
                record.set("commentId",param.get("dataId")+strComment);
                record.set("articleId",param.get("dataId"));
                record.set("userName", param.get("userName"));
                record.set("content",param.get("content"));

                boolean isok = Db.save("comment",record);
                if (!isok) {
                    renderJson("[{\"2\":\"error\"}]");
                }else{
                    renderJson("[{\"2\":\"ok\"}]");
                }
            }else{
                renderJson("[{\"500\":\"error\"}]");
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJson("[{\"500\":\"error\"}]");
        }

    }
}
