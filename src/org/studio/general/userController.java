package org.studio.general;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import net.sf.json.JSONObject;
import org.springframework.web.util.HtmlUtils;
import org.studio.idcode.MyCaptchaRender;
import org.studio.interceptor.UserInterceptor;
import org.studio.utils.JsonUtils;
import org.studio.vo.userVO;

import java.util.ArrayList;
import java.util.List;

/**
 * org.studio.general
 * Created by idiot on 11/8/14.
 */

public class userController extends Controller{
    public void index(){
        ArrayList<String> array = new ArrayList<String>();
        array.add("Man");
        array.add("Da");
        array.add("Ren");
        renderJson(array);
    }
    /**
     * 功能:注册用户
     *
     * Note:
     * ---------------------------------------------------------------------------------------------
     * 请求格式示例
     * {"userName" : "小白菜", "userPass" : "****","userSex" : "男",
     * "userMood" : "0","userMail" : "******@qq.com","userIurl" : "blog.***.com"}
     * userName     ,   用户名
     * userPass     ,   密码
     * userSex      ,   性别
     * userMood     ,   用户信息
     * userMail     ,   用户邮箱
     * userIurl     ,   用户主页
     * ---------------------------------------------------------------------------------------------
     * 返回格式示例
     * 成功:
     * [{"2" : "ok"}]
     * 失败:
     * [{"2":"error"}]
     * ---------------------------------------------------------------------------------------------
     * */
    public void register(){
        try {
            String json = getParaMap().keySet().toString();
            JSONObject param = JsonUtils.Object4JsonString(json);
            //List art= Db.query("select * from article where artId =" + param.get("dataId"));
            long nUser = Db.queryLong("select count(*) from userinfo") +1 ;
            String strUser = String.format("%03d", nUser);
            System.out.println(strUser);

            String userName = param.getString("userName");
            String userPass = param.getString("userPass");
            if(userName != null){
                if(-1 != userName.indexOf(' '))
                {
                    return;
                }
            }else{
                return;
            }

            if(userPass != null){
                if(-1 != userPass.indexOf(' '))
                {
                    return;
                }
            }else{
                return;
            }

            userinfo users = userinfo.dao.findFirst("select * from userinfo where userName=\""+userName+"\"");
            System.out.println(users);
            if(users !=null){
                renderJson("[{\"7\":\"error\"}]");
                return;
            }

            Record record = new Record();
            record.set("userId",strUser);
            record.set("userName",param.get("userName"));
            record.set("userPass", param.get("userPass"));
            record.set("userSex",param.get("userSex"));
            record.set("userMood",param.get("userMood"));
            record.set("userMail",param.get("userMail"));
            record.set("userIurl",param.get("userIurl"));

            String inputRandomCode = param.getString("idcode");
            boolean validate = MyCaptchaRender.validate(this, inputRandomCode);

            if(validate){
                boolean isOk = Db.save("userinfo",record);

                if (!isOk) {
                    renderJson("[{\"2\":\"error\"}]");
                }else{
                    renderJson("[{\"2\":\"ok\"}]");
                }
            }else{
                renderJson("[{\"6\":\"error\"}]");
            }

        } catch (Exception e) {
            e.printStackTrace();
            renderJson("[{\"500\":\"error\"}]");
        }
    }


    /**
     * 功能:修改用户信息
     *
     * Note:
     * ---------------------------------------------------------------------------------------------
     * 请求格式示例
     * {"userId" : "1","newSex" : "男",
     * "newMood" : "0","newMail" : "******@qq.com","newIurl" : "blog.***.com"}
     * userSex      ,   新性别
     * userMood     ,   用户新简介
     * userMail     ,   用户新邮箱
     * newrIurl     ,   新用户主页
     * ---------------------------------------------------------------------------------------------
     * 返回格式示例
     * 成功:
     * [{"2" : "ok"}]
     * 失败:
     * [{"2":"error"}]
     * ---------------------------------------------------------------------------------------------
     * */
    public void updateUserInfo(){
        try {
            boolean isOk =false;
            String json = getParaMap().keySet().toString();
            JSONObject param = JsonUtils.Object4JsonString(json);

            String userId = getSessionAttr("dataId");
            String predate= userinfo.dao.findById(userId).get("regDate").toString();
            userinfo.dao.findById(userId).set("userSex",param.get("newSex")).update();

            userinfo.dao.findById(userId).set("userMood",param.get("newMood")).update();

            userinfo.dao.findById(userId).set("userMail",param.get("newMail")).update();

            userinfo.dao.findById(userId).set("userIurl",param.get("newIurl")).update();
            userinfo.dao.findById(userId).set("regDate",predate).update();

            //???????
            isOk = true;
            if (!isOk) {
                renderJson("[{\"2\":\"error\"}]");
            }else{
                renderJson("[{\"2\":\"ok\"}]");
            }

        } catch (Exception e) {
            e.printStackTrace();
            renderJson("[{\"500\":\"error\"}]");
        }
    }



    /**
     * 功能:修改用户密码
     *
     * Note:
     * ---------------------------------------------------------------------------------------------
     * 请求格式示例
     * {"userIdForPass" : "1",
     * "oldPass" : "*******","newPass" : "******"}
     * userIdFor     ,   要修改密码的用户的ID
     * oldPass     ,   旧密码
     * newPass     ,   新密码
     * ---------------------------------------------------------------------------------------------
     * 返回格式示例
     * 成功:
     * [{"2" : "ok"}]
     * 失败:
     * [{"2":"error"}]
     * ---------------------------------------------------------------------------------------------
     * */
    public void modifyPass(){
        try {
            boolean isOk = false;
            String json = getParaMap().keySet().toString();
            JSONObject param = JsonUtils.Object4JsonString(json);

            String userId = getSessionAttr("dataId");
            String oldPass = (String)param.get("oldPass");
            String newPass = (String)param.get("newPass");
            userinfo user = userinfo.dao.findById(userId);
            if(oldPass.equals(user.getStr("userPass"))){
                user.set("userPass", newPass).update();
                isOk = true;
            }
            if (!isOk) {
                renderJson("[{\"2\":\"error\"}]");
            }else{
                renderJson("[{\"2\":\"ok\"}]");
            }

        } catch (Exception e) {
            e.printStackTrace();
            renderJson("[{\"500\":\"error\"}]");
        }
    }



    public void login(){
        try {
            boolean isOk =false;
            String json = getParaMap().keySet().toString();
            JSONObject param = JsonUtils.Object4JsonString(json);

            String userName = (String)param.get("username");
            String password = (String)param.get("password");

            List<userinfo> info= userinfo.dao.find("select * from userinfo where userName='"+userName+"'");
            if (info.isEmpty()){
                renderJson("[{\"3\":\"error\"}]");
            }else{
                String realPass = info.get(0).getStr("userPass");
                if(realPass != null){
                    if(password.equals(realPass)){
                        setSessionAttr("islogin", "true");
                        setSessionAttr("dataId",info.get(0).get("userId").toString());
                        setSessionAttr("userName", info.get(0).get("userName").toString());
                        System.out.println(getSessionAttr("dataId"));
                        renderJson("[{\"5\":\"ok\"}]");
                    }else {
                        renderJson("[{\"4\":\"error\"}]");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJson("[{\"500\":\"error\"}]");
        }
    }

    public void getUserInfo(){
        try{
            String userId = getSessionAttr("dataId");
            List<userinfo> info= userinfo.dao.find("select * from userinfo where userId='"+userId+"'");
            List<userVO> userVOs = new ArrayList<userVO>();
            for (userinfo user : info) {
                userVO uVO = new userVO();

                uVO.setRegDate(user.get("regDate").toString());
                uVO.setUserId(Integer.toString(user.getInt("userId")));
                uVO.setUserMail(user.getStr("userMail"));
                uVO.setUserMood(user.getStr("userMood"));
                uVO.setUserName(user.getStr("userName"));
                uVO.setUserSex(user.getStr("userSex"));
                uVO.setUserUrl(user.getStr("userIurl"));

                userVOs.add(uVO);
            }
            if (userVOs.size() == 0) {
                renderJson("[{\"1\":\"error\"}]");
            } else {
                renderJson(userVOs);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getUserInfoById(){
        try{
            String json = getParaMap().keySet().toString();
            JSONObject param = JsonUtils.Object4JsonString(json);
            String userId = param.getString("dataId");
            List<userinfo> info= userinfo.dao.find("select * from userinfo where userId='"+userId+"'");
            List<userVO> userVOs = new ArrayList<userVO>();
            for (userinfo user : info) {
                userVO uVO = new userVO();

                uVO.setRegDate(user.get("regDate").toString());
                uVO.setUserId(Integer.toString(user.getInt("userId")));
                uVO.setUserMail(user.getStr("userMail"));
                uVO.setUserMood(user.getStr("userMood"));
                uVO.setUserName(user.getStr("userName"));
                uVO.setUserSex(user.getStr("userSex"));
                uVO.setUserUrl(user.getStr("userIurl"));

                userVOs.add(uVO);
            }
            if (userVOs.size() == 0) {
                renderJson("[{\"1\":\"error\"}]");
            } else {
                renderJson(userVOs);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Before(UserInterceptor.class)
    public void isLogin(){
        try {
            String isLogin = getSessionAttr("islogin");
            String userId = getSessionAttr("dataId");
            String userName = getSessionAttr("userName");
            if (isLogin != null) {
                renderJson("[{\"8\":\"ok\",\"username\":\""+userName+"\",\"userid\":\""+userId+"\"}]");
            }else {
                renderJson("[{\"8\":\"error\"}]");
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJson("[{\"500\":\"error\"}]");
        }
    }

    public void logout(){
        try{
            if(getSessionAttr("islogin") != null)
                removeSessionAttr("islogin");
            if(getSessionAttr("dataId") != null)
                removeSessionAttr("dataId");
            if(getSessionAttr("userName") != null)
                removeSessionAttr("userName");
            renderJson("[{\"6\":\"ok\"}]");
        }catch (Exception e){
            e.printStackTrace();
            renderJson("[{\"500\":\"error\"}]");
        }
    }
}