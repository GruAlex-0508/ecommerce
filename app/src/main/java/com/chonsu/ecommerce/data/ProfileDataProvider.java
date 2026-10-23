package com.chonsu.ecommerce.data;

import android.content.Context;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.model.ProfileMenuItem;
import com.chonsu.ecommerce.model.UserProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Supplies the content of the profile page.
 * Replace the body of {@link #getUserProfile(Context)} with real user data (API / local storage) later.
 */
public class ProfileDataProvider {

    private ProfileDataProvider() {
    }

    public static UserProfile getUserProfile(Context context) {
        return new UserProfile(
                context.getString(R.string.profile_username),
                context.getString(R.string.profile_cid),
                R.drawable.img_avatar);
    }

    public static List<ProfileMenuItem> getMenuItems() {
        List<ProfileMenuItem> items = new ArrayList<>();

        items.add(ProfileMenuItem.header(R.string.profile_section_account));
        items.add(ProfileMenuItem.item(ProfileMenuItem.ID_SETTINGS,
                R.string.profile_menu_settings, R.drawable.ic_setting, 0));
        items.add(ProfileMenuItem.item(ProfileMenuItem.ID_NOTIFICATION,
                R.string.profile_menu_notification, R.drawable.ic_notification, R.color.cyan_700));
        items.add(ProfileMenuItem.item(ProfileMenuItem.ID_ORDER_HISTORY,
                R.string.profile_menu_order_history, R.drawable.ic_history, R.color.orange_500));

        items.add(ProfileMenuItem.header(R.string.profile_section_support));
        items.add(ProfileMenuItem.item(ProfileMenuItem.ID_PRIVACY,
                R.string.profile_menu_privacy, R.drawable.ic_privacy, R.color.blue_600));
        items.add(ProfileMenuItem.item(ProfileMenuItem.ID_ADDRESS,
                R.string.profile_menu_address, R.drawable.ic_location, R.color.amber_500));
        items.add(ProfileMenuItem.item(ProfileMenuItem.ID_LOGOUT,
                R.string.profile_menu_logout, R.drawable.ic_sign_out, R.color.red_500));

        return items;
    }
}
