package module.list;

/**
 * Created by mags on 2016/11/28.
 */

public class SideBarBaseModel {
    private String sortLetters;//排序字母
    private String sortName;

    public String getSortLetters() {
        return sortLetters;
    }

    public SideBarBaseModel setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
        return this;
    }

    public String getSortName() {
        return sortName;
    }

    public SideBarBaseModel setSortName(String sortName) {
        this.sortName = sortName;
        return this;
    }
}
