package action;

import bean.Url;
import com.opensymphony.xwork2.ActionSupport;
import dao.url.UrlDao;

/**
 * Created by lvsijian8 on 2017/6/19.
 */
public class UrlAction extends ActionSupport {
    private Url url;
    private UrlDao urlDao=new UrlDao();

    public String create(){
        if (urlDao.insert(url) == 1) {
            url.setUrlId(1);
        }
        return SUCCESS;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }
}
