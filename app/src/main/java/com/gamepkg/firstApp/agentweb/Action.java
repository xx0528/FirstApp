package com.gamepkg.firstApp.agentweb;

import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Action {

    public transient static final int ACTION_PERMISSION = 1;
    public transient static final int ACTION_FILE = 2;
    public transient static final int ACTION_CAMERA = 3;
    public transient static final int ACTION_VIDEO = 4;
    private ArrayList<String> mPermissions = new ArrayList<>();
    private int mAction;
    private int mFromIntention;
    private Intent mIntent;
    private Uri mUri;
    private AgentActionFragment.RationaleListener mRationaleListener;
    private AgentActionFragment.PermissionListener mPermissionListener;
    private AgentActionFragment.ChooserListener mChooserListener;

    public Action() {
    }

    public ArrayList<String> getPermissions() {
        return mPermissions;
    }

    public void setPermissions(ArrayList<String> permissions) {
        this.mPermissions = permissions;
    }

    public void setPermissions(String[] permissions) {
        this.mPermissions = new ArrayList<>(Arrays.asList(permissions));
    }

    public int getAction() {
        return mAction;
    }

    public void setAction(int action) {
        this.mAction = action;
    }


    public int getFromIntention() {
        return mFromIntention;
    }

    public static Action createPermissionsAction(String[] permissions) {
        Action mAction = new Action();
        mAction.setAction(Action.ACTION_PERMISSION);
        List<String> mList = Arrays.asList(permissions);
        mAction.setPermissions(new ArrayList<String>(mList));
        return mAction;
    }

    public Action setFromIntention(int fromIntention) {
        this.mFromIntention = fromIntention;
        return this;
    }

    public AgentActionFragment.RationaleListener getRationaleListener() {
        return mRationaleListener;
    }

    public void setRationaleListener(AgentActionFragment.RationaleListener rationaleListener) {
        mRationaleListener = rationaleListener;
    }

    public AgentActionFragment.PermissionListener getPermissionListener() {
        return mPermissionListener;
    }

    public void setPermissionListener(AgentActionFragment.PermissionListener permissionListener) {
        mPermissionListener = permissionListener;
    }

    public AgentActionFragment.ChooserListener getChooserListener() {
        return mChooserListener;
    }

    public void setChooserListener(AgentActionFragment.ChooserListener chooserListener) {
        mChooserListener = chooserListener;
    }

    public Intent getIntent() {
        return mIntent;
    }

    public Uri getUri() {
        return mUri;
    }

    public void setUri(Uri uri) {
        mUri = uri;
    }

    public void setIntent(Intent intent) {
        mIntent = intent;
    }
}
