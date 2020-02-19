package com.talkover.service;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.iflytek.aiui.AIUIAgent;
import com.iflytek.aiui.AIUIConstant;
import com.iflytek.aiui.AIUIEvent;
import com.iflytek.aiui.AIUIListener;
import com.iflytek.aiui.AIUIMessage;
import com.talkover.MyAccessibilityService;
import com.talkover.R;
import com.talkover.ui.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class VoiceToTextService extends Service {
    private static String TAG = "VoiceToText";
    //录音权限
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    private Toast mToast;

    private AIUIAgent mAIUIAgent = null;

    //交互状态
    private int mAIUIState = AIUIConstant.STATE_IDLE;

    @Override
    public void onCreate() {
        super.onCreate();
        requestPermission();
        startVoiceNlp();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    //开始录音
    private void startVoiceNlp() {
        if (checkAIUIAgent()) {
            Log.i(TAG, "start voice nlp");

            // 先发送唤醒消息，改变AIUI内部状态，只有唤醒状态才能接收语音输入
            // 默认为oneshot 模式，即一次唤醒后就进入休眠，如果语音唤醒后，需要进行文本语义，请将改段逻辑copy至startTextNlp()开头处
            if (AIUIConstant.STATE_WORKING != this.mAIUIState) {
                AIUIMessage wakeupMsg = new AIUIMessage(AIUIConstant.CMD_WAKEUP, 0, 0, "", null);
                mAIUIAgent.sendMessage(wakeupMsg);
            }

            // 打开AIUI内部录音机，开始录音
            String params = "sample_rate=16000,data_type=audio";
            AIUIMessage writeMsg = new AIUIMessage(AIUIConstant.CMD_START_RECORD, 0, 0, params, null);
            mAIUIAgent.sendMessage(writeMsg);
        }
    }


    private boolean checkAIUIAgent() {
        if (null == mAIUIAgent) {
            Log.i(TAG, "create aiui agent");

            //创建AIUIAgent
            mAIUIAgent = AIUIAgent.createAgent(this, getAIUIParams(), mAIUIListener);
        }

        if (null == mAIUIAgent) {
            final String strErrorTip = "创建 AIUI Agent 失败！";
            showTip(strErrorTip);
        }

        return null != mAIUIAgent;
    }

    /**
     * 读取配置
     */
    private String getAIUIParams() {
        String params = "";

        AssetManager assetManager = getResources().getAssets();
        try {
            InputStream ins = assetManager.open("cfg/aiui_phone.cfg");
            byte[] buffer = new byte[ins.available()];

            ins.read(buffer);
            ins.close();

            params = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return params;
    }


    //AIUI事件监听器
    private AIUIListener mAIUIListener = new AIUIListener() {
        String name = "";
        String message = "";

        @Override
        public void onEvent(AIUIEvent event) {
            switch (event.eventType) {
                case AIUIConstant.EVENT_WAKEUP:
                    //唤醒事件
                    Log.i(TAG, "on event: " + event.eventType);
                    showTip("进入识别状态");
                    break;

                case AIUIConstant.EVENT_RESULT: {
                    //结果事件
//                    Log.i( TAG,  "on event: "+ event );
                    try {
                        JSONObject bizParamJson = new JSONObject(event.info);
                        JSONObject data = bizParamJson.getJSONArray("data").getJSONObject(0);
                        JSONObject params = data.getJSONObject("params");
                        JSONObject content = data.getJSONArray("content").getJSONObject(0);
                        if (content.has("cnt_id")) {
                            String cnt_id = content.getString("cnt_id");
                            JSONObject cntJson = new JSONObject(new String(event.data.getByteArray(cnt_id), "utf-8"));
                            JSONArray words = cntJson.getJSONObject("text").getJSONArray("ws");
                            String result = "";
                            for (int i = 0; i < words.length(); i++) {
                                result += words.getJSONObject(i).getJSONArray("cw").getJSONObject(0).getString("w").toString();
                            }
                            Log.i(TAG, result);

                            if(result.contains("发送")){
                                String[] list = result.split("给|发送");
                                name = list[1];
                                Log.i(TAG, list[0]);
                                Log.i(TAG, list[1]);
                            }else{
                                message += result;
                                if(!name.equals("") && !message.equals("")) {
                                    message += result;
                                    Intent accessibility = new Intent(getApplicationContext(), MyAccessibilityService.class);
                                    accessibility.putExtra("name", name);
                                    accessibility.putExtra("content", message);
                                    startService(accessibility);
                                }
                            }

                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }

                }
                break;


                case AIUIConstant.EVENT_ERROR: {
                    //错误事件
                    Log.i(TAG, "on event: " + event.eventType);
                    showTip("错误: " + event.arg1 + "\n" + event.info);
                }
                break;

                case AIUIConstant.EVENT_VAD: {
                    //vad事件
                    if (AIUIConstant.VAD_BOS == event.arg1) {
                        //找到语音前端点
                        showTip("找到vad_bos");
                    } else if (AIUIConstant.VAD_EOS == event.arg1) {
                        //找到语音后端点
                        showTip("找到vad_eos");
                    } else {
                        showTip("" + event.arg2);
                    }

                }
                break;

                case AIUIConstant.EVENT_START_RECORD: {
                    //开始录音事件
                    Log.i(TAG, "on event: " + event.eventType);
                    showTip("开始录音");
                }
                break;

                case AIUIConstant.EVENT_STOP_RECORD: {
                    //停止录音事件
                    Log.i(TAG, "on event: " + event.eventType);
                    showTip("停止录音");
                }
                break;

                case AIUIConstant.EVENT_STATE: {    // 状态事件
                    mAIUIState = event.arg1;

                    if (AIUIConstant.STATE_IDLE == mAIUIState) {
                        // 闲置状态，AIUI未开启
                        showTip("STATE_IDLE");
                    } else if (AIUIConstant.STATE_READY == mAIUIState) {
                        // AIUI已就绪，等待唤醒
                        showTip("STATE_READY");
                    } else if (AIUIConstant.STATE_WORKING == mAIUIState) {
                        // AIUI工作中，可进行交互
                        showTip("STATE_WORKING");
                    }
                }
                break;


                default:
                    break;
            }
        }

    };


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (null != this.mAIUIAgent) {
            AIUIMessage stopMsg = new AIUIMessage(AIUIConstant.CMD_STOP, 0, 0, null, null);
            mAIUIAgent.sendMessage(stopMsg);

            this.mAIUIAgent.destroy();
            this.mAIUIAgent = null;
        }
    }

    private void showTip(final String str) {
        Log.e(TAG, "showTip: " + str);
    }


    //申请录音权限
    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            if (i != PackageManager.PERMISSION_GRANTED) {
//                ContextCompat.requestPermissions(this, permissions, 321);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Smart Talk")
                        .setContentText("请求打开录音权限");
            }
        }
    }
}
