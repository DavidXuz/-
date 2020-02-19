package com.talkover;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.talkover.model.TemplateEntity;

import java.util.List;

public class MyAccessibilityService extends AccessibilityService {
    public static final String TAG = "tgs:";

    private AccessibilityServiceInfo info;

    private TemplateEntity template;

    private int state = 0;

    private String name;
    private String content;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        name = intent.getStringExtra("name");
        content = intent.getStringExtra("content");

        Intent appIntent = getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq");
        startActivity(appIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    public void setTemplate(TemplateEntity template){
        this.template = template;
    }

    @Override
    protected void onServiceConnected() {
        String[] packages = {"com.tencent.mobileqq"};
        info = new AccessibilityServiceInfo();
        info.eventTypes=AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
        info.notificationTimeout = 100;
        info.packageNames = null;
        setServiceInfo(info);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (name == null) {
            return;
        }
        if (state == 1)
            return;

        try {
//            Log.e(TAG, event.toString());
            String friend = name;

            if (event.getPackageName().toString().equals("com.tencent.mobileqq")) {
                Thread.sleep(1000);
                // click search
                AccessibilityNodeInfo search = findNodeById("com.tencent.mobileqq:id/et_search_keyword");
                AccessibilityNodeInfo cancle = findNodeById("com.tencent.mobileqq:id/btn_cancel_search");
                if (search != null) {
                    search.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
                Thread.sleep(1000);
                if (cancle != null) {
                    Bundle arguments = new Bundle();
                    arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, friend);
                    search.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);

                    Thread.sleep(3000);

                   List<AccessibilityNodeInfo> nodes = findNodesById("com.tencent.mobileqq:id/title");
                   for(AccessibilityNodeInfo node: nodes){
                       Rect bound = new Rect();
                       node.getBoundsInScreen(bound);
                       if (bound.top > 200 && bound.bottom < 300) {
                           Log.e(TAG, bound.toString());
                           node.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                           break;
                       }
                   }
                }
                AccessibilityNodeInfo inputEt = findNodeById("com.tencent.mobileqq:id/input");
                Bundle arguments = new Bundle();
                arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, content);
                inputEt.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                Thread.sleep(1000);
                findNodeById("com.tencent.mobileqq:id/fun_btn").performAction(AccessibilityNodeInfo.ACTION_CLICK);
                state = 1;
            }
        }catch (Exception e){

        }

    }


    @Override
    public void onInterrupt() {

    }

    public AccessibilityNodeInfo findNodeById(String id) {
        AccessibilityNodeInfo info = getRootInActiveWindow();
        List<AccessibilityNodeInfo> nodes = info.findAccessibilityNodeInfosByViewId(id);

        if (nodes.size() == 0)
            return null;
        return nodes.get(0);
    }

    public List<AccessibilityNodeInfo> findNodesById(String id){
        AccessibilityNodeInfo info = getRootInActiveWindow();
        return info.findAccessibilityNodeInfosByViewId(id);
    }

    /**
     * 根据Text搜索所有符合条件的节点, 模糊搜索方式
     */
    public List<AccessibilityNodeInfo> findNodesByText(String text) {

        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            return nodeInfo.findAccessibilityNodeInfosByText(text);
        }
        return null;
    }

}
