package com.example.kiboooo.userdefinedview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Kiboooo on 2017/11/26.
 *
 */

public class textdialpg extends DialogFragment {

    private Date Begin, End;


   public static textdialpg newInstance(){
       return new textdialpg();
   }

    public interface dialogListener{
        void onDialogListener(Date BeginDate,Date EndDate);

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.dialoge, null);

        final MaterialCalendarView V = view.findViewById(R.id.calen_begin);
        MaterialCalendarView V2 = view.findViewById(R.id.calen_over);
        Calendar calendar = getFirstDayForWeekInMonth();
        calendar.add(Calendar.DAY_OF_MONTH, +6);
        Log.e("WeekInMonth", new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(calendar.getTime()) );

        builder.setView(view)
                .setPositiveButton("确 认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Begin != null && End != null && End.compareTo(Begin)>=0) {
                            dialogListener listener = (dialogListener) getActivity();
                            listener.onDialogListener(Begin, End);
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"架梁开始时间不允许比架梁结束时间晚，请核对！",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("取 消", null);

        V.state()
                .edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                //设置日期范围
                .setMinimumDate(getFirstDayForWeekInMonth())
                .setMaximumDate(calendar)
                .commit();

        V2.state()
                .edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                //设置日期范围
                .setMinimumDate(getFirstDayForWeekInMonth())
                .setMaximumDate(calendar)
                .commit();

        V.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
//                dialogListener listener = (dialogListener) getActivity();
//                listener.onDialogListener(date.getDate());
                Begin = date.getDate();
            }
        });

        V2.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
//                dialogListener listener = (dialogListener) getActivity();
//                listener.onDialogListener(date.getDate());
                End = date.getDate();
            }
        });

        return builder.create();
    }


    private Calendar getFirstDayForWeekInMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        Log.e("WeekInMonth", new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(calendar.getTime()));
        int first = calendar.get(Calendar.DAY_OF_WEEK) == 1 ? 8 : calendar.get(Calendar.DAY_OF_WEEK);
        Log.e("WeekInMonth", "" + first);
        calendar.add(Calendar.DAY_OF_MONTH, 9-first);
        Log.e("WeekInMonth", new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(calendar.getTime()) + "  " + first);
        return calendar;
    }
}
