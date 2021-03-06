package com.hongsup.explog.view.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongsup.explog.R;
import com.hongsup.explog.data.user.source.UserRepository;
import com.hongsup.explog.util.PreferenceUtil;
import com.hongsup.explog.view.main.MainActivity;
import com.hongsup.explog.view.setting.alarmsetting.AlarmSettingActivity;
import com.hongsup.explog.view.setting.editprofile.EditProfileActivity;

/**
 * Created by 정인섭 on 2017-12-11.
 */

public class SettingRecyclerAdapter extends RecyclerView.Adapter<SettingRecyclerAdapter.MyHolder> {

    private int mType[] = {0, 1, 0, 1, 0, 1, 1, 1, 2};
    String mList[] = {"계정", "프로필 편집", "서비스 정보", "알림 설정", "고객지원", "버전정보", "공지사항", "", "로그아웃"};
    TypedValue outValue = new TypedValue();
    ISettingView iSettingView;

    public SettingRecyclerAdapter(ISettingView iSettingView) {
        this.iSettingView = iSettingView;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_recycler_list, parent, false);
        return new MyHolder(view, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.textSetting.setText(mList[position]);
        holder.type = mType[position];
    }

    @Override
    public int getItemCount() {
        return mType.length;
    }

    public boolean setsetset(boolean result){
        return result;
    }

    class MyHolder extends RecyclerView.ViewHolder{
        int type;
        TextView textSetting;
        TextView textSwitchSetting;

        public MyHolder(View itemView, int viewType) {
            super(itemView);
            textSetting = itemView.findViewById(R.id.textSetting);
            itemView.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);

            if(mType[viewType]==0){
                textSetting.setTextColor(Color.RED);
                textSetting.setGravity(Gravity.START|Gravity.BOTTOM);
                textSetting.setTextSize(14);
            }else{
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch(textSetting.getText().toString()){

                            case "프로필 편집" :
                                //iSettingView.editProfile();
                                Intent editProfileIntent = new Intent(itemView.getContext(), EditProfileActivity.class);
                                editProfileIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                itemView.getContext().startActivity(editProfileIntent);

                                break;

                            case "알림 설정" :
                                Intent alarmSettingIntent = new Intent(itemView.getContext(), AlarmSettingActivity.class);
                                alarmSettingIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                itemView.getContext().startActivity(alarmSettingIntent);

                                break;

                            case "로그아웃" :
                                new AlertDialog.Builder(itemView.getContext()).setTitle("로그아웃")
                                        .setMessage("로그아웃 하시겠습니까?")
                                        .setCancelable(true)
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //sharedPreference에 있는 값 다 삭제

                                                PreferenceUtil.removeAllValue(itemView.getContext());
                                                UserRepository.getInstance().clearUser();
                                                ((Activity)iSettingView).finish();
                                                //로그아웃이 되면 mainactivity로 빠져나감, flag값을 통해 이전의 mainactivity는 tast에서 사라짐 12/20
                                                Intent intent = new Intent((Context)iSettingView, MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                ((Context)iSettingView).startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).show();


                        }
                    }
                });

                // itemView의 종류에 따라 selectableItemBackground 적용
                itemView.setBackgroundResource(outValue.resourceId);

                if(mType[viewType]==1){
                    textSetting.setTextColor(Color.BLACK);
                    textSetting.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
                    textSetting.setTextSize(18);
                }

                if(mType[viewType]==2){
                    textSetting.setTextColor(Color.GRAY);
                    textSetting.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
                    textSetting.setTextSize(18);
                }

                if(mType[viewType]==3){
                    textSetting.setTextColor(Color.BLACK);
                    textSetting.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
                    textSetting.setTextSize(18);

                }

            }
        }
    }

    interface ISettingView{
        void logOut(boolean isChecked);
    }
}
