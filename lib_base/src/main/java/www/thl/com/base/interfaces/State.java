package www.thl.com.base.interfaces;

/**
 * 编写时间：2017/7/1 16:31
 * 编写人：taohaili
 **/
public interface State {

    public void showLoading();

    public void showError();

    public void showNetError();

    public void showContent();

    public void showEmpty();

}
