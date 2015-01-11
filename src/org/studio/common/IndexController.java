package org.studio.common;

import com.jfinal.core.Controller;

import java.util.ArrayList;

/**
 * org.studio.common
 * Created by lzim on 11/8/14.
 */
public class IndexController extends Controller {
    public void index(){
        ArrayList<String> array = new ArrayList<String>();
        array.add("Man");
        array.add("Da");
        array.add("Ren");
        renderJson(array);
    }
}