package helper;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<String> lstTitles = new ArrayList();
    private final List<Fragment> lstfragment = new ArrayList();

    public List<Fragment> getLstfragment() {
        return this.lstfragment;
    }

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public Fragment getItem(int i) {
        return this.lstfragment.get(i);
    }

    public int getCount() {
        return this.lstfragment.size();
    }

    public CharSequence getPageTitle(int i) {
        return this.lstTitles.get(i);
    }

    public void AddFragment(Fragment fragment, String str) {
        this.lstfragment.add(fragment);
        this.lstTitles.add(str);
    }
}