package me.allen.calculator.util;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class LayoutUtil {

    public static <T> ArrayList<T> getViewsFromViewGroup(View root, Class<T> clazz) {
        ArrayList<T> result = new ArrayList<T>();
        for (View view : getAllViewsFromRoots(root))
            if (clazz.isInstance(view))
                result.add(clazz.cast(view));
        return result;
    }

    public static ArrayList<View> getAllViewsFromRoots(View... roots) {
        ArrayList<View> result = new ArrayList<View>();
        for (View root : roots)
            getAllViews(result, root);
        return result;
    }

    private static void getAllViews(ArrayList<View> allviews, View parent) {
        allviews.add(parent);
        if (parent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) parent;
            for (int i = 0; i < viewGroup.getChildCount(); i++)
                getAllViews(allviews, viewGroup.getChildAt(i));
        }
    }

}
