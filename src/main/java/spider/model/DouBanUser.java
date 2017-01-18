package spider.model;

import java.util.Date;

/**
 *
 * @author shiwei
 * @version $$Id: DouBanUser, V 0.1 2017/1/18 10:55 shiwei Exp $$
 */
public class DouBanUser {
    private String name;
    private String userName;
    private String address;
    private Date   joinTime;
    private String headPortrait;
    private String intro;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Override
    public String toString() {
        return "DouBanUser{" +
               "name='" + name + '\'' +
               ", userName='" + userName + '\'' +
               ", address='" + address + '\'' +
               ", joinTime=" + joinTime +
               ", headPortrait='" + headPortrait + '\'' +
               ", intro='" + intro + '\'' +
               '}';
    }
}
