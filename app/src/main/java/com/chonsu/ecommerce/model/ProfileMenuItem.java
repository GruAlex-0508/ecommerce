package com.chonsu.ecommerce.model;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

/**
 * One entry of the profile list: either a section header ("Account") or a clickable row.
 */
public class ProfileMenuItem {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    public static final int ID_NONE = 0;
    public static final int ID_SETTINGS = 1;
    public static final int ID_NOTIFICATION = 2;
    public static final int ID_ORDER_HISTORY = 3;
    public static final int ID_PRIVACY = 4;
    public static final int ID_ADDRESS = 5;
    public static final int ID_LOGOUT = 6;

    private final int type;
    private final int id;
    @StringRes
    private final int titleRes;
    @DrawableRes
    private final int iconRes;
    /**
     * 0 means "use the theme's colorPrimary".
     */
    @ColorRes
    private final int iconColorRes;

    private ProfileMenuItem(int type, int id, @StringRes int titleRes, @DrawableRes int iconRes, @ColorRes int iconColorRes) {
        this.type = type;
        this.id = id;
        this.titleRes = titleRes;
        this.iconRes = iconRes;
        this.iconColorRes = iconColorRes;
    }

    public static ProfileMenuItem header(@StringRes int titleRes) {
        return new ProfileMenuItem(TYPE_HEADER, ID_NONE, titleRes, 0, 0);
    }

    public static ProfileMenuItem item(int id, @StringRes int titleRes, @DrawableRes int iconRes, @ColorRes int iconColorRes) {
        return new ProfileMenuItem(TYPE_ITEM, id, titleRes, iconRes, iconColorRes);
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public int getIconRes() {
        return iconRes;
    }

    public int getIconColorRes() {
        return iconColorRes;
    }
}
