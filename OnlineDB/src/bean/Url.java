package bean;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by lvsijian8 on 2017/6/19.
 */
@Entity
public class Url {
    private int urlId;
    private String url;
    private String urlUser;
    private String urlPassword;
    private String urlName;
    private int userId;

    @Override
    public String toString() {
        return "Url{" +
                "urlId=" + urlId +
                ", url='" + url + '\'' +
                ", urlUser='" + urlUser + '\'' +
                ", urlPassword='" + urlPassword + '\'' +
                ", urlName='" + urlName + '\'' +
                ", userId=" + userId +
                '}';
    }

    @Id
    @Column(name = "url_id", nullable = false)
    public int getUrlId() {
        return urlId;
    }

    public void setUrlId(int urlId) {
        this.urlId = urlId;
    }

    @Basic
    @Column(name = "url", nullable = false, length = 64)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "url_user", nullable = false, length = 64)
    public String getUrlUser() {
        return urlUser;
    }

    public void setUrlUser(String urlUser) {
        this.urlUser = urlUser;
    }

    @Basic
    @Column(name = "url_password", nullable = false, length = 32)
    public String getUrlPassword() {
        return urlPassword;
    }

    public void setUrlPassword(String urlPassword) {
        this.urlPassword = urlPassword;
    }

    @Basic
    @Column(name = "url_name", nullable = true, length = 32)
    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Url url1 = (Url) o;

        if (urlId != url1.urlId) return false;
        if (userId != url1.userId) return false;
        if (url != null ? !url.equals(url1.url) : url1.url != null) return false;
        if (urlUser != null ? !urlUser.equals(url1.urlUser) : url1.urlUser != null) return false;
        if (urlPassword != null ? !urlPassword.equals(url1.urlPassword) : url1.urlPassword != null) return false;
        return urlName != null ? urlName.equals(url1.urlName) : url1.urlName == null;
    }

    @Override
    public int hashCode() {
        int result = urlId;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (urlUser != null ? urlUser.hashCode() : 0);
        result = 31 * result + (urlPassword != null ? urlPassword.hashCode() : 0);
        result = 31 * result + (urlName != null ? urlName.hashCode() : 0);
        result = 31 * result + userId;
        return result;
    }
}
