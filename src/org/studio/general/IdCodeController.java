package org.studio.general;
        import java.util.logging.Logger;
        import com.jfinal.core.Controller;
        import org.studio.idcode.*;
/**
 * Created by idiot on 15-1-7.
 */
public class IdCodeController extends Controller{
    public void index()
    {
        render(new MyCaptchaRender(60,22,4,true));
    }
}

