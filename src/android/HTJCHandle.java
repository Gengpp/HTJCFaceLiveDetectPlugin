package com.plugin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.livedetect.LiveDetectActivity;
import com.livedetect.data.ConstantValues;
import com.livedetect.utils.FileUtils;
import com.livedetect.utils.LogUtil;
import com.livedetect.utils.SerializableObjectForData;
import com.livedetect.utils.StringUtils;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import static android.app.Activity.RESULT_OK;


public class HTJCHandle extends CordovaPlugin {
    private final int START_LIVEDETECT = 0;
    private final String TAG = HTJCHandle.class.getSimpleName();
    private CallbackContext mCallbackContext;

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        if (action.equals("htjcFaceDetectAction")) {
            this.mCallbackContext = callbackContext;
            SerializableObjectForData mSerializableObjectForData = new SerializableObjectForData();
            Intent intent = new Intent(cordova.getActivity(),
                    LiveDetectActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("mSerializableObjectForData",
                    mSerializableObjectForData);
            intent.putExtras(bundle);
            cordova.startActivityForResult((CordovaPlugin) this, intent, START_LIVEDETECT);
            return true;

        } else {

            return false;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        LogUtil.i(TAG, " 109 requestCode = " + requestCode + " resultCode = " + resultCode);
        if (requestCode == START_LIVEDETECT) {
            switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
                case RESULT_OK:
                    if (null != intent) {
                        Bundle result = intent.getBundleExtra("result");
                        if (null != result) {
                            //只有失败时返回  失败动作  l代表静止凝视动作  s代表摇头动作，n代表点头动作
                            String mMove = result.getString("mMove");
                            //只有失败时返回  失败的原因: 0代表默认失败提示，1代表无人脸，2代表多人脸，3代表活检动作失败，4代表错误动作的攻击，5代表超时，6代表图片加密失败，7代表3D检测失败，8代表肤色检测失败
                            String mRezion = result.getString("mRezion");
                            //活检是否通过 true代表检测通过，false代表检测失败
                            boolean isLivePassed = result.getBoolean("check_pass");
                            //图片的byte[]的形式，格式为jpg。失败时返回值为null
                            byte[] picbyte = result.getByteArray("pic_result");
                            if (StringUtils.isNotNull(mMove)) {
                                LogUtil.i(TAG, " mMove = " + mMove);
                            }
                            if (StringUtils.isNotNull(mRezion)) {//10为 初始化 失败 ，11为授权过期
                                LogUtil.i(TAG, " mRezion = " + mRezion);
                                Toast.makeText(cordova.getActivity(), " mRezion = " + mRezion, Toast.LENGTH_SHORT).show();
                            }
                            LogUtil.i(TAG, " isLivePassed= " + isLivePassed);
                            if (null != picbyte) {
                                LogUtil.i(TAG, " picbyte = " + picbyte.length);
                            }
                            if (isLivePassed) {//成功
                                mCallbackContext.success(Base64.encodeToString(picbyte, Base64.DEFAULT));
                            } else {
                                mCallbackContext.success(getFailMessage(mRezion));
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private String getFailMessage(String mRezion) {
        FileUtils.setmContext(cordova.getActivity());
        String failMessage = "失败";
        if (StringUtils.isStrEqual(mRezion, "0")) {
            failMessage = cordova.getActivity().getResources().getString
                    (FileUtils.getResIdByTypeAndName(ConstantValues.RES_TYPE_STRING, "htjc_fail_remind_default"));
        } else if (StringUtils.isStrEqual(mRezion, ConstantValues.BAD_REASON.NO_FACE)) {
            failMessage = cordova.getActivity().getResources().getString
                    (FileUtils.getResIdByTypeAndName(ConstantValues.RES_TYPE_STRING, "htjc_fail_remind_noface"));
        } else if (StringUtils.isStrEqual(mRezion, ConstantValues.BAD_REASON.MORE_FACE)) {
            failMessage = cordova.getActivity().getResources().getString
                    (FileUtils.getResIdByTypeAndName(ConstantValues.RES_TYPE_STRING, "htjc_fail_remind_moreface"));
        } else if (StringUtils.isStrEqual(mRezion, ConstantValues.BAD_REASON.NOT_LIVE)) {//非活体
            failMessage = cordova.getActivity().getResources().getString
                    (FileUtils.getResIdByTypeAndName(ConstantValues.RES_TYPE_STRING, "htjc_fail_remind_notlive"));
        } else if (StringUtils.isStrEqual(mRezion, ConstantValues.BAD_REASON.BAD_MOVEMENT_TYPE)) {//互斥
            failMessage = cordova.getActivity().getResources().getString
                    (FileUtils.getResIdByTypeAndName(ConstantValues.RES_TYPE_STRING, "htjc_fail_remind_badmovementtype"));
        } else if (StringUtils.isStrEqual(mRezion, ConstantValues.BAD_REASON.TIME_OUT)) {//超时
            failMessage = cordova.getActivity().getResources().getString
                    (FileUtils.getResIdByTypeAndName(ConstantValues.RES_TYPE_STRING, "htjc_fail_remind_timeout"));
        } else if (StringUtils.isStrEqual(mRezion, ConstantValues.BAD_REASON.GET_PGP_FAILED)) {
            failMessage = cordova.getActivity().getResources().getString
                    (FileUtils.getResIdByTypeAndName(ConstantValues.RES_TYPE_STRING, "htjc_fail_remind_pgp_fail"));
        } else if (StringUtils.isStrEqual(mRezion, ConstantValues.BAD_REASON.CHECK_3D_FAILED)) {//3d
            failMessage = cordova.getActivity().getResources().getString
                    (FileUtils.getResIdByTypeAndName(ConstantValues.RES_TYPE_STRING, "htjc_fail_remind_3d"));
        } else if (StringUtils.isStrEqual(mRezion, ConstantValues.BAD_REASON.CHECK_SKIN_COLOR_FAILED)) {//肤色
            failMessage = cordova.getActivity().getResources().getString
                    (FileUtils.getResIdByTypeAndName(ConstantValues.RES_TYPE_STRING, "htjc_fail_remind_badcolor"));
        } else if (StringUtils.isStrEqual(mRezion, ConstantValues.BAD_REASON.CHECK_CONTINUITY_COLOR_FAILED)) {//连续性
            failMessage = cordova.getActivity().getResources().getString
                    (FileUtils.getResIdByTypeAndName(ConstantValues.RES_TYPE_STRING, "htjc_fail_remind_badcontinuity"));
        } else if (StringUtils.isStrEqual(mRezion, ConstantValues.BAD_REASON.CHECK_ABNORMALITY_FAILED)) {//
            failMessage = cordova.getActivity().getResources().getString
                    (FileUtils.getResIdByTypeAndName(ConstantValues.RES_TYPE_STRING, "htjc_fail_remind_abnormality"));
        }
        FileUtils.setmContext(null);
        return failMessage;
    }

}
